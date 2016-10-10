package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
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
import java.util.concurrent.TimeUnit;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

public class TheGameActionActivity extends AppCompatActivity {

    TheGame theGame;
    boolean IS_LOADED_GAME = false;

    DayTimeFragment dayTimeFragment;
    NightTimeFragment nightTimeFragment;
    NightTimeRoleActionsFragment nightTimeRoleActionsFragment;
    RecyclerView playersInfoRecyclerView;

    final String TIME_FRAGMENT = "TIME_FRAGMENT";
    final String TIME_ROLE_ACTIONS_FRAGMENT = "TIME_ROLE_ACTIONS_FRAGMENT";

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
        //playersInfoRecyclerView = (RecyclerView) findViewById(R.id.playersStatusRecyclerView);
        //playersInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //playersInfoRecyclerView.setAdapter(new PlayerGameStatusRoleAdapter(getApplicationContext()));

        //startMafiaGameAction();
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
            TheGameActionPlayersGameStatusDialogFragment theGameActionPlayersGameStatusDialogFragment = new  TheGameActionPlayersGameStatusDialogFragment();
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
        nightTimeFragment = new NightTimeFragment();
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

    }

    void endNightAction() {
        theGame.setNightNumber(theGame.getNightNumber() + 1);
    }

    void startDayAction() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.dayOrNightTimeFragment,dayTimeFragment,TIME_FRAGMENT);
        fragmentTransaction.commit();
    }

    void endTheGameAndShowResults() {
    }

    ;

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

        String nightNumer;
        TextView nightNumberTextView;
        Button finishTheNightButton;
        private int madeNightActionsAmount = 0;

        public NightTimeFragment() {
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

                if (playerRole.getName() == R.string.priest)
                    holder.choosingSpinner2.setAdapter(choosingSpinnerAdapter);
                else
                    holder.choosingSpinner2.setVisibility(View.GONE);

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

                        /**
                         * Rola została wykonana
                         * Nie jest już możliwa zmiana wybranych graczy
                         * Dodajemy do sumy wykonanych ról
                         */
                        void roleActionWasMade() {
                            choosingSpinner.setEnabled(false);
                            choosingSpinner2.setEnabled(false);
                            confirmButton.setText(R.string.roleActionDone);
                            // madeNightActionsAmount++;
                        }


                        @Override
                        public void onClick(View view) {
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
                        case R.string.prostitute:
                            makeProstituteAction(choosenPlayer);
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
                    }
                }

                void makeProstituteAction(HumanPlayer choosenPlayer) {
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingPlayerRoleDialog theGameActionShowingPlayerRoleDialog = new TheGameActionShowingPlayerRoleDialog(choosenPlayer);
                    theGameActionShowingPlayerRoleDialog.show(fragmentManager, "ProstituteAction");
                }

                void makeBlackManAction(HumanPlayer choosenPlayer) {
                    if (!(choosenPlayer.getGuard().contains(actionPlayers.get(getAdapterPosition()))))
                        choosenPlayer.setGuard(actionPlayers.get(getAdapterPosition()));

                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
                }

                void makeBlackmailerAction(HumanPlayer choosenPlayer) {
                    if (!(choosenPlayer.getBlackMailer().contains(actionPlayers.get(getAdapterPosition()))))
                        choosenPlayer.setBlackMailer(actionPlayers.get(getAdapterPosition()));

                    Toast.makeText(getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
                }

                void makePriestAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
                    choosenPlayer1.setLover(choosenPlayer2);
                    choosenPlayer2.setLover(choosenPlayer1);
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new TheGameActionShowingLoversRolesDialog(choosenPlayer1, choosenPlayer2);
                    theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
                }

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


    /**
     * Adapter do przeglądania statusu gracza
     */

    public class TheGameActionPlayersGameStatusDialogFragment extends DialogFragment {

        PlayerGameStatusRoleAdapter playerGameStatusRoleAdapter;

        TheGameActionPlayersGameStatusDialogFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_players_status_fragment, null); //cos tutaj dodac za prent!!!
            //setCancelable(false);
            getDialog().setTitle(R.string.playersList);

            playerGameStatusRoleAdapter = new PlayerGameStatusRoleAdapter(getApplicationContext(),theGame.getPlayersInfoList());
            RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersStatusRecyclerView);
            playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), GridLayoutManager.VERTICAL,false));
            playersActionsRecyclerView.setAdapter(playerGameStatusRoleAdapter);

            Button returnButton = (Button) view.findViewById(R.id.returnButton);
            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            return view;
        }

        public class PlayerGameStatusRoleAdapter extends RecyclerView.Adapter<PlayerGameStatusRoleAdapter.PlayerStatusViewHolder> {

            private ArrayList<HumanPlayer> humanPlayersList;
            private LayoutInflater inflater;
            private Context context;

            public PlayerGameStatusRoleAdapter(Context context, ArrayList<HumanPlayer> humanPlayersList) {
                this.humanPlayersList = humanPlayersList;
                this.inflater = LayoutInflater.from(context);
                this.context = context;
            }

            @Override
            public PlayerStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.player_game_status_layout, parent, false);
                return new PlayerStatusViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final PlayerStatusViewHolder holder, final int position) {
                HumanPlayer humanPlayer = humanPlayersList.get(position);
                holder.playerName.setText(humanPlayer.getPlayerName());
                holder.playerRoleIcon.setImageResource(humanPlayer.getPlayerRole().getIconResourceID());
                holder.roleName.setText(getString(humanPlayer.getRoleName()));
                holder.fractionName.setText(getString(humanPlayer.getPlayerRole().getFractionNameStringID()));
                showProperlyPlayerStatus(humanPlayer, holder);

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        showPlayerStatusPopupMenu(holder.itemView,position, holder);
                        return true;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return humanPlayersList.size();
            }

            private void showPlayerStatusPopupMenu(View view, final int position, final PlayerStatusViewHolder playerStatusViewHolder){
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.player_status_menu, popupMenu.getMenu());
                //ustawianie kill albo revive
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.statusmenu_kill_the_player:{

                                if(humanPlayersList.get(position).isAlive())
                                    humanPlayersList.get(position).killThePlayer();
                                else
                                    humanPlayersList.get(position).reviveThePlayer();

                                showProperlyPlayerStatus(humanPlayersList.get(position), playerStatusViewHolder);
                                break;
                            }
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

            private void showProperlyPlayerStatus(HumanPlayer humanPlayer, PlayerStatusViewHolder playerStatusViewHolder) {
                if (humanPlayer.isAlive())
                    playerStatusViewHolder.playerStatus.setImageResource(R.drawable.icon_heart);
                else {
                    playerStatusViewHolder.playerStatus.setImageResource(R.drawable.icon_ghost);
                   // playerStatusViewHolder.playerRoleIcon.setImageResource(R.drawable.icon_ghost);
                }
            }

            class PlayerStatusViewHolder extends RecyclerView.ViewHolder {

                private ImageView playerRoleIcon;
                private TextView playerName;
                private TextView roleName;
                private TextView fractionName;
                private ImageView playerStatus;
                private ImageView menuIcon;

                private AlertDialog roleDescriptionDialog;

                public PlayerStatusViewHolder(View itemView) {
                    super(itemView);

                    playerRoleIcon = (ImageView) itemView.findViewById(R.id.roleIco);
                    playerName = (TextView) itemView.findViewById(R.id.playerName);
                    roleName = (TextView) itemView.findViewById(R.id.roleName);
                    fractionName = (TextView) itemView.findViewById(R.id.fractionName);
                    playerStatus = (ImageView) itemView.findViewById(R.id.playerStatusIcon);

                   // menuIcon = (ImageView) itemView.findViewById(R.id.context_menu_dots);

//                    playerRoleIcon.setImageResource(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getIconResourceID());

                    /**
                     * Przy naciśnięciu karty roli pojawią się jej opis
                     */
               /* playerRoleIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            buildRoleDescriptionDialog();
                            roleDescriptionDialog.show();}
                });*/

                    /**
                     * Menu kontekstowe
                     */


                }

                /**
                 * Tworzy okienko wyświetlające opis roli
                 */
                public void buildRoleDescriptionDialog() {
                    final AlertDialog.Builder descriptionDialog = new AlertDialog.Builder(context);
                    descriptionDialog.setTitle(context.getString(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getName()));
                    descriptionDialog.setMessage(context.getString(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getDescription()));
                    descriptionDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        /**
                         * Zamyka okno z opisem roli
                         * @param dialog
                         * @param which
                         */
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            roleDescriptionDialog.cancel();
                        }
                    });

                    roleDescriptionDialog = descriptionDialog.create();
                }

            }
        }
    }



    ArrayList<HumanPlayer> getTownHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
            if (humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
                result.add(humanPlayer);
        }
        return result;
    }

    ArrayList<HumanPlayer> getZeroNightHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : theGame.getPlayersInfoList()) {
            if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNightAndActionRequire))
                result.add(humanPlayer);
            if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNight))
                result.add(humanPlayer);
        }

        return result;
    }
}
