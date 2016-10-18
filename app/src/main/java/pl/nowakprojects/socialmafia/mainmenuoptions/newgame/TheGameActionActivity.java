package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.utitles.GameRolesWakeHierarchyComparator;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

public class TheGameActionActivity extends AppCompatActivity {

    TheGame theGame;
    boolean IS_LOADED_GAME = false;

    DayTimeFragment dayTimeFragment;
    NightTimeFragment nightTimeFragment;
    NightTimeRoleActionsFragment nightTimeRoleActionsFragment;

    final String TIME_FRAGMENT = "TheGameActionActivity.TIME_FRAGMENT";
    final String TIME_ROLE_ACTIONS_FRAGMENT = "TheGameActionActivity.TIME_ROLE_ACTIONS_FRAGMENT";
    final String GAME_TIP_FRAGMENT = "TheGameActionActivity.GAME_TIP_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        receiveGameSettings(); //odbiera ustawienia gry

        startMafiaGameAction();

        startNightAction();
        // startDayAction();
        //startMafiaGameAction();
    }

    @Override
    public void onBackPressed() {
        //okienko potwierdzenia czy na pewno opuscic gre
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.the_game_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_thegame_playerslist){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            TheGameActionPlayersGameStatusDialogFragment theGameActionPlayersGameStatusDialogFragment = new  TheGameActionPlayersGameStatusDialogFragment(theGame);
            theGameActionPlayersGameStatusDialogFragment.show(fragmentManager, "PlayersListFragment");
            return true;}
        else if(itemId == R.id.menu_thegame_savegame)
            return true;
        else if(itemId == R.id.menu_thegame_exitgame)
            return true;

        return false;
    }

    /**
     * TheGame functions:
     */

    void startMafiaGameAction() {
        //GameView proporties:
        dayTimeFragment = new DayTimeFragment();
        //dayTimeRoleActionsFragment = new DayTimeRoleActionsFragment();
        nightTimeFragment = new NightTimeFragment(theGame);
        //dopóki gra nie jest zakończona ciągle leci dzień - noc:
        // while(!theGame.isFinished()){
        //      startNightAction();
        //      startDayAction();}

        //  endTheGameAndShowResults();
    }

    void startNightAction() {
        if (theGame.getNightNumber() == 0)
            nightTimeRoleActionsFragment = new NightTimeRoleActionsFragment(getZeroNightHumanPlayers());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dayOrNightTimeFragment, nightTimeFragment, TIME_FRAGMENT);
        fragmentTransaction.add(R.id.dayOrNightTimeRoleActionsFragment, nightTimeRoleActionsFragment, TIME_ROLE_ACTIONS_FRAGMENT);
        fragmentTransaction.commit();

        showGameTipFragment(null,getString(R.string.night_time_tip));
    }

    void endNightAction() {
        if(!(theGame.getNightNumber()==0))
            makeJudgmentAction();
        theGame.setNightNumber(theGame.getNightNumber() + 1);
    }

    void makeJudgmentAction(){

    }

    void startDayAction() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dayOrNightTimeFragment,dayTimeFragment,TIME_FRAGMENT);
        fragmentTransaction.commit();
    }

    void endTheGameAndShowResults() {
    }


    /**
     * Game app functions:
     */
    void receiveGameSettings() {
        if (IS_LOADED_GAME)
            receiveLoadGameSettings();
        else
            receiveNewGameSettings();
    }


    /**
     * Odbieranie ustawień z nowej gry
     */
    void receiveNewGameSettings() {
        theGame = Parcels.unwrap(getIntent().getParcelableExtra(ConnectPlayersToRolesActivity.EXTRA_NEW_GAME));
    }

    ;

    void receiveLoadGameSettings() {
        //WCZYTYWANIE Z BAZDY DANYCH i tworzenie theGame na podstawie tego
    }

    ;

    public class DayTimeFragment extends Fragment {


        TextView dayTimerTextView;

        public DayTimeFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_day_time, container, false);

            //Odliczanie czasu na jeden dzień (czas ustawiony wczesniej):
            dayTimerTextView = (TextView) fragmentView.findViewById(R.id.dayTimerTextView);
            final TimeCounterClass dayTimeTimer = new TimeCounterClass(theGame.getDaytime(), 1000);
            dayTimeTimer.start();

            //Przycisk konczacy dzien:
            Button finishDayButton = (Button) fragmentView.findViewById(R.id.finishTheDayButton);
            finishDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("finishDayButton.onClick", "Przycisk Wcisniety!");

                }
            });


            return fragmentView;
        }


        /**
         * Odliczanie czasu do końca dnia - timer
         */
        public class TimeCounterClass extends CountDownTimer {
            public TimeCounterClass(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long l) {
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                        TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                dayTimerTextView.setText(hms);
            }

            @Override
            public void onFinish() {
                dayTimerTextView.setText(R.id.dayTimeEnded);
                Vibrator vibrator = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                vibrator.vibrate(1500);
            }
        }

    }

    public class NightTimeFragment extends Fragment {

        TheGame theGame;
        String nightNumer;
        TextView nightNumberTextView;
        Button finishTheNightButton;
        private int madeNightActionsAmount = 0;
        AlertDialog confirmationAlertDialog;

        public NightTimeFragment(TheGame theGame) {
            this.theGame = theGame;
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_night_time, container, false);

            nightNumberTextView = (TextView) fragmentView.findViewById(R.id.nightNumberTextView);
            finishTheNightButton = (Button) fragmentView.findViewById(R.id.finishTheNightButton);
            finishTheNightButton.setEnabled(false);
            updateNightNumberTextView();

            finishTheNightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildConfirmationAlertDialog();
                    confirmationAlertDialog.show();
                }
            });

            return fragmentView;
        }

        /**
         * Aktualizuje numer kolejnej nocy
         */
        void updateNightNumberTextView() {
            nightNumberTextView.setText((getString(R.string.night_number, theGame.getNightNumber())));
        }

        public void buildConfirmationAlertDialog() {
            final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this.getActivity());
            confirmationDialog.setTitle(getString(R.string.fisnish_the_night));
            confirmationDialog.setMessage(getString(R.string.finish_the_night_are_you_sure));
            confirmationDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                /**
                 * Zamyka okno z opisem roli
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    endNightAction();
                    startDayAction();
                    confirmationAlertDialog.cancel();
                }
            });

            confirmationDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    confirmationAlertDialog.cancel();
                }
            });

            confirmationAlertDialog = confirmationDialog.create();
        }

    }

    public class TheGameStatsFragment extends Fragment {

        TheGame theGame;
        String nightNumer;
        TextView nightNumberTextView;
        Button finishTheNightButton;
        private int madeNightActionsAmount = 0;

        public TheGameStatsFragment(TheGame theGame) {
            this.theGame = theGame;
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_night_time, container, false);

            nightNumberTextView = (TextView) fragmentView.findViewById(R.id.nightNumberTextView);
            finishTheNightButton = (Button) fragmentView.findViewById(R.id.finishTheNightButton);
            finishTheNightButton.setEnabled(false);
            updateNightNumberTextView();

            return fragmentView;
        }

        /**
         * Aktualizuje numer kolejnej nocy
         */
        void updateNightNumberTextView() {
            nightNumberTextView.setText((getString(R.string.night_number, theGame.getNightNumber())));
        }

    }

    public class NightTimeRoleActionsFragment extends Fragment {

        DayOrNightRoleActionsAdapter dayOrNightRoleActionsAdapter;
        ArrayList<HumanPlayer> nightHumanPlayers;


        public NightTimeRoleActionsFragment() {
            // Required empty public constructor
        }

        public NightTimeRoleActionsFragment(ArrayList<HumanPlayer> nightHumanPlayers) {
            this.nightHumanPlayers = nightHumanPlayers;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            dayOrNightRoleActionsAdapter = new DayOrNightRoleActionsAdapter(getApplicationContext(), nightHumanPlayers);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_time_role_actions, null, false);
            return fragmentView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersActionsRecyclerView);
            playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL, false));
            playersActionsRecyclerView.setAdapter(dayOrNightRoleActionsAdapter);
        }

        /**
         * Adapter do wykonywania roli graczy
         */
        public class DayOrNightRoleActionsAdapter extends RecyclerView.Adapter<DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder> {

            private ArrayList<HumanPlayer> actionPlayers;

            private LayoutInflater inflater;
            private Context context;

            public DayOrNightRoleActionsAdapter(Context context, ArrayList<HumanPlayer> actionPlayers) {
                this.actionPlayers = actionPlayers; //ogarnac zeby tylko te co się budzą danej porze dnia!!
                this.context = context;
                this.inflater = LayoutInflater.from(context);
            }

            @Override
            public PlayerRoleActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.player_role_action_layout, null); //moze tutaj null zamiast parent
                return new PlayerRoleActionViewHolder(view);
            }

            @Override
            public void onBindViewHolder(PlayerRoleActionViewHolder holder, int position) {
                PlayerRole playerRole = actionPlayers.get(position).getPlayerRole();
                //  holder.roleIcon.setImageResource(playerRole.getIconResourceID());
                holder.roleName.setText(playerRole.getName());
                holder.playerName.setText(actionPlayers.get(position).getPlayerName());

                //Dodawanie opcji do Spinnera
                ArrayList<String> playerNames = new ArrayList<String>();
                for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
                    if (!(humanPlayer.getPlayerName().equals(actionPlayers.get(position).getPlayerName()))) //wszystkich oprócz samego gracza
                        playerNames.add(humanPlayer.getPlayerName());
                }
                ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
                choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.choosingSpinner.setAdapter(choosingSpinnerAdapter);
                //-------------------------------------------------------

                if (playerRole.getName() == R.string.priest){
                    holder.choosingSpinner2.setVisibility(View.VISIBLE);
                    holder.choosingSpinner2.setAdapter(choosingSpinnerAdapter);
                    }
                else
                    holder.choosingSpinner2.setVisibility(View.GONE);

                //sprawdza czy akcja została wykoanna
                holder.choosingSpinner.setEnabled(false);
                holder.choosingSpinner2.setEnabled(false);

                if(playerRole.isB_isRoleTurn()){
                    holder.itemView.findViewById(R.id.confirmButton).setEnabled(true);
                    holder.choosingSpinner.setEnabled(!playerRole.is_actionMade());
                    holder.choosingSpinner2.setEnabled(!playerRole.is_actionMade());}
                else
                    holder.itemView.findViewById(R.id.confirmButton).setEnabled(false);

            }

            @Override
            public int getItemCount() {
                return actionPlayers.size();
            }

            class PlayerRoleActionViewHolder extends RecyclerView.ViewHolder {

                // private ImageView roleIcon;
                private TextView roleName;
                private TextView playerName;
                private Button confirmButton;
                private Spinner choosingSpinner;
                private Spinner choosingSpinner2;

                private AlertDialog roleDescriptionDialog;

                public PlayerRoleActionViewHolder(View itemView) {

                    super(itemView);

                    // roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
                    roleName = (TextView) itemView.findViewById(R.id.roleName);
                    playerName = (TextView) itemView.findViewById(R.id.playerName);
                    confirmButton = (Button) itemView.findViewById(R.id.confirmButton);
                    choosingSpinner = (Spinner) itemView.findViewById(R.id.playersNamesSpinner);

                    //Jeśli graczem jest ksiądz to używamy innego layoutu, dla wyboru 2 graczy do kochanków
//                    if(actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest)
                    choosingSpinner2 = (Spinner) itemView.findViewById(R.id.playersNamesSpinner2);

                    //zatwierdzenie wykonania roli gracza
                    confirmButton.setOnClickListener(new View.OnClickListener() {

                        private boolean isAllActionsMade(){
                            return theGame.iGetActionsMadeThisTime()==actionPlayers.size();
                        }

                        private void enableEndOfDayTimeButton(){
                            if(isAllActionsMade())
                                nightTimeFragment.finishTheNightButton.setEnabled(true);
                        }
                        /**
                         * Rola została wykonana
                         * Nie jest już możliwa zmiana wybranych graczy
                         * Dodajemy do sumy wykonanych ról
                         */
                        void roleActionWasMade() {
                            if(!(actionPlayers.get(getAdapterPosition()).getPlayerRole().is_actionMade())){
                            theGame.iActionMadeThisTime();
                            choosingSpinner.setEnabled(false);
                            choosingSpinner2.setEnabled(false);
                            confirmButton.setText(R.string.roleActionDone);

                            enableEndOfDayTimeButton();
                            actionPlayers.get(getAdapterPosition()).getPlayerRole().set_bActionMade(true);
                            }
                            if((getAdapterPosition()+1)<actionPlayers.size()){
                                actionPlayers.get(getAdapterPosition()+1).getPlayerRole().setB_isRoleTurn(true);
                                notifyItemChanged(getAdapterPosition()+1);
                            }

                            //notifyItemChanged(getAdapterPosition());
                        }


                        @Override
                        public void onClick(View view) {
                            if(confirmButton.isEnabled()&&confirmButton.getText()!=getString(R.string.roleActionDone)) {
                                //sprawdza czy to ksiądz, aby wykonać odpowiednią funkcję
                                if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest) {
                                    if (theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).equals(theGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString())))
                                        Toast.makeText(getApplicationContext(), getString(R.string.theSameLovers), Toast.LENGTH_LONG).show();
                                    else {
                                        makePriestAction(theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), theGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString()));
                                        roleActionWasMade();
                                    }
                                } else {
                                    makeRoleAction(actionPlayers.get(getAdapterPosition()), theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()));
                                    roleActionWasMade();
                                }
                            }
                        }
                    });

                }

                /**
                 * Wykonywanie roli graczy
                 *
                 * @param actionPlayer
                 * @param choosenPlayer
                 */
                void makeRoleAction(HumanPlayer actionPlayer, HumanPlayer choosenPlayer) {
                    switch (actionPlayer.getRoleName()) {
                        case R.string.policeman:
                            makePolicemanAction(choosenPlayer);
                            break;
                        case R.string.prostitute:
                            makeProstituteAction(choosenPlayer);
                            break;
                        case R.string.medic:
                            makeMedicAction(choosenPlayer);
                            break;
                        case R.string.black:
                            makeBlackManAction(choosenPlayer);
                            break;
                        case R.string.blackJudge:
                            makeBlackManAction(choosenPlayer);
                            break;
                        case R.string.blackmailer:
                            makeBlackmailerAction(choosenPlayer);
                            break;
                        case R.string.blackmailerBoss:
                            makeBlackmailerAction(choosenPlayer);
                            break;
                        case R.string.darkmedic:
                            makeDarkMedicAction(choosenPlayer);
                            break;
                        case R.string.dealer:
                            makeDealerAction(choosenPlayer);
                            break;
                        case R.string.deathAngel:
                            makeDeathAngelAction(choosenPlayer);
                            break;
                        case R.string.bartender:
                            //makeBartenderAction(choosenPlayer);
                            break;
                    }
                }

                private void makeProstituteAction(HumanPlayer choosenPlayer) {
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingPlayerRoleDialog theGameActionShowingPlayerRoleDialog = new TheGameActionShowingPlayerRoleDialog(choosenPlayer);
                    theGameActionShowingPlayerRoleDialog.show(fragmentManager, "ProstituteAction");
                }

                private void makePolicemanAction(HumanPlayer choosenPlayer) {
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingPlayerRoleDialog theGameActionShowingPlayerRoleDialog = new TheGameActionShowingPlayerRoleDialog(choosenPlayer);
                    theGameActionShowingPlayerRoleDialog.show(fragmentManager, "PolicemanAction");
                }

                private void makeBlackManAction(HumanPlayer choosenPlayer) {
                    if (!(choosenPlayer.getGuard().contains(actionPlayers.get(getAdapterPosition()))))
                        choosenPlayer.setGuard(actionPlayers.get(getAdapterPosition()));

                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
                }

                private void makeBlackmailerAction(HumanPlayer choosenPlayer) {
                    if (!(choosenPlayer.getBlackMailer().contains(actionPlayers.get(getAdapterPosition()))))
                        choosenPlayer.setBlackMailer(actionPlayers.get(getAdapterPosition()));

                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
                }

                private void makePriestAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
                    choosenPlayer1.setLover(choosenPlayer2);
                    choosenPlayer2.setLover(choosenPlayer1);
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new TheGameActionShowingLoversRolesDialog(choosenPlayer1, choosenPlayer2);
                    theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
                }

                private void makeMedicAction(HumanPlayer choosenPlayer){
                    theGame.addLastNightHealingByMedicPlayers(choosenPlayer);
                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isHealingThisNight), Toast.LENGTH_LONG).show();

                }

                private void makeDarkMedicAction(HumanPlayer choosenPlayer){
                    theGame.addLastNightHeatingByDarkMedicPlayers(choosenPlayer);
                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isHeatingThisNight), Toast.LENGTH_LONG).show();

                }

                private void makeDealerAction(HumanPlayer choosenPlayer){
                    theGame.addLastNightDealingByDealerPlayers(choosenPlayer);
                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isDealingThisNight), Toast.LENGTH_LONG).show();

                }

                private void makeDeathAngelAction(HumanPlayer choosenPlayer){
                    choosenPlayer.addStigma();
                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isSignedThisNight), Toast.LENGTH_LONG).show();

                }

                private void makeBartenderAction(){
                    //przusawnie strzalu, wybiera stronę i ilość
                }

                //----daytime::
                private void makeJudgeDecisionAction(){};

                //-----

                public class TheGameActionShowingLoversRolesDialog extends DialogFragment {

                    private HumanPlayer choosenPlayer;
                    private HumanPlayer choosenPlayer2;
                    private Button understandButton;
                    private TextView showedPlayerRoleText;
                    private TextView showedPlayerRoleText2;
                    private TextView showedPlayerFraction;
                    private TextView showedPlayerFraction2;
                    private ImageView showedPlayerRoleIcon;
                    private ImageView showedPlayerRoleIcon2;

                    TheGameActionShowingLoversRolesDialog(HumanPlayer choosenPlayer, HumanPlayer choosenPlayer2) {
                        this.choosenPlayer = choosenPlayer;
                        this.choosenPlayer2 = choosenPlayer2;
                    }

                    @Nullable
                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        View view = inflater.inflate(R.layout.dialog_showing_lovers_roles, null);
                        setCancelable(false);
                        getDialog().setTitle(R.string.loversRoles);

                        //showingPlayerRoleExplanation = (TextView) view.findViewById(R.id.showingPlayerRoleExplanation);
                        showedPlayerRoleText = (TextView) view.findViewById(R.id.showedPlayerRoleText2);
                        showedPlayerRoleText.setText(getString(choosenPlayer.getRoleName()));
                        showedPlayerFraction = (TextView) view.findViewById(R.id.showedPlayerFraction2);
                        showedPlayerFraction.setText(getString(choosenPlayer.getPlayerRole().getFractionNameStringID()));
                        //  showedPlayerRoleIcon = (ImageView) view.findViewById(R.id.loverIcon1);
                        //  showedPlayerRoleIcon.setImageResource(choosenPlayer.getPlayerRole().getIconResourceID());

                        showedPlayerRoleText2 = (TextView) view.findViewById(R.id.showedPlayerRoleText);
                        showedPlayerRoleText2.setText(getString(choosenPlayer2.getRoleName()));
                        showedPlayerFraction2 = (TextView) view.findViewById(R.id.showedPlayerFraction);
                        showedPlayerFraction2.setText(getString(choosenPlayer2.getPlayerRole().getFractionNameStringID()));
                        //  showedPlayerRoleIcon2 = (ImageView) view.findViewById(R.id.loverIcon2);
                        //  showedPlayerRoleIcon2.setImageResource(choosenPlayer2.getPlayerRole().getIconResourceID());


                        Button understandButton = (Button) view.findViewById(R.id.understandButton);
                        understandButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dismiss();
                            }
                        });
                        return view;
                    }

                }

                public class TheGameActionShowingPlayerRoleDialog extends DialogFragment {

                    private HumanPlayer choosenPlayer;
                    private Button understandButton;
                    //private AutoResizeTextView showedPlayerRoleText;
                    private TextView showedPlayerRoleText;
                    private TextView showedPlayerFraction;
                    private ImageView showedPlayerRoleIcon;

                    TheGameActionShowingPlayerRoleDialog(HumanPlayer choosenPlayer) {
                        this.choosenPlayer = choosenPlayer;
                    }

                    @Nullable
                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        View view = inflater.inflate(R.layout.dialog_showing_player_role, null);
                        setCancelable(false);
                        getDialog().setTitle(R.string.checkedRole);

                        showedPlayerRoleIcon = (ImageView) view.findViewById(R.id.roleIco);
                        showedPlayerRoleIcon.setImageResource(choosenPlayer.getPlayerRole().getIconResourceID());
                        // showedPlayerRoleText = (AutoResizeTextView) view.findViewById(R.id.showedPlayerRoleText);
                        showedPlayerRoleText = (TextView) view.findViewById(R.id.showedPlayerRoleText);
                        showedPlayerRoleText.setText(getString(choosenPlayer.getRoleName()));
                        showedPlayerFraction = (TextView) view.findViewById(R.id.showedPlayerFraction);
                        showedPlayerFraction.setText(getString(choosenPlayer.getPlayerRole().getFractionNameStringID()));

                        understandButton = (Button) view.findViewById(R.id.understandButton);
                        understandButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dismiss();
                            }
                        });
                        return view;
                    }

                }



            }


        }

        /**
         * Spinner adapter - do wyboru innego gracza
         */
        //  public class ChoosingPlayerSpinnerAdapter extends ArrayAdapter<HumanPlayer>{

        //  public ChoosingPlayerSpinnerAdapter(){}

        // }


    }


    private ArrayList<HumanPlayer> getTownHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
            if (humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
                result.add(humanPlayer);
        }
        return result;
    }// private ArrayList<HumanPlayer> getTownHumanPlayers()

    private ArrayList<HumanPlayer> getZeroNightHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
            if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNightAndActionRequire))
                result.add(humanPlayer);
            if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNight))
                result.add(humanPlayer);
        }
        Collections.sort(result,new GameRolesWakeHierarchyComparator());
        result.get(0).getPlayerRole().setB_isRoleTurn(true);
        return result;
    }// private ArrayList<HumanPlayer> getZeroNightHumanPlayers()

    private ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
            if(humanPlayer.isAlive()) {
                if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNights))
                    result.add(humanPlayer);
                if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNightsBesideZero))
                    result.add(humanPlayer);
            }
        }
        Collections.sort(result,new GameRolesWakeHierarchyComparator());
        result.get(0).getPlayerRole().setB_isRoleTurn(true);
        return result;
    }// private ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers()

   private GameTipFragment showGameTipFragment(String sTipTitle, String sTipContent){
       GameTipFragment gameTipFragment = new GameTipFragment(sTipTitle,sTipContent);

       FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       fragmentTransaction.add(R.id.gameTipFragment, gameTipFragment, GAME_TIP_FRAGMENT);
       fragmentTransaction.commit();

       return gameTipFragment;
   }

}//public class TheGameActionActivity extends AppCompatActivity
