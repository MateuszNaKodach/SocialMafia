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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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

import fr.ganfra.materialspinner.MaterialSpinner;

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

    /**
     * TheGame functions:
     */

    void startMafiaGameAction(){
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

    void startNightAction(){
        if(theGame.getNightNumber()==0)
            nightTimeRoleActionsFragment = new NightTimeRoleActionsFragment(getZeroNightHumanPlayers());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dayOrNightTimeFragment,nightTimeFragment,TIME_FRAGMENT);
        fragmentTransaction.add(R.id.dayOrNightTimeRoleActionsFragment,nightTimeRoleActionsFragment,TIME_ROLE_ACTIONS_FRAGMENT);
        fragmentTransaction.commit();

    }

    void endNightAction(){
        theGame.setNightNumber(theGame.getNightNumber()+1);
    }

    void startDayAction(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.dayOrNightTimeFragment,dayTimeFragment,TIME_FRAGMENT);
        fragmentTransaction.commit();
    }

    void endTheGameAndShowResults(){};

    /**
     * Game app functions:
     */
    void receiveGameSettings(){
        if(IS_LOADED_GAME)
            receiveLoadGameSettings();
        else
            receiveNewGameSettings();
    };

    /**
     * Odbieranie ustawień z nowej gry
     */
    void receiveNewGameSettings(){
        theGame = Parcels.unwrap(getIntent().getParcelableExtra(ConnectPlayersToRolesActivity.EXTRA_NEW_GAME));
    };

    void receiveLoadGameSettings(){
        //WCZYTYWANIE Z BAZDY DANYCH i tworzenie theGame na podstawie tego
    };

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
            final TimeCounterClass dayTimeTimer = new TimeCounterClass(theGame.getDaytime(),1000);
            dayTimeTimer.start();

            //Przycisk konczacy dzien:
            Button finishDayButton = (Button) fragmentView.findViewById(R.id.finishTheDayButton);
            finishDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("finishDayButton.onClick","Przycisk Wcisniety!");

                }
            });


            return fragmentView;
        }


        /**
         * Odliczanie czasu do końca dnia - timer
         */
        public class TimeCounterClass extends CountDownTimer{
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
                Vibrator vibrator = (Vibrator)getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                vibrator.vibrate(1500);
            }
        }

    }

    public class NightTimeFragment extends Fragment {


        String nightNumer;
        TextView nightNumberTextView;

        public NightTimeFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_night_time, container, false);

            nightNumberTextView = (TextView) fragmentView.findViewById(R.id.nightNumberTextView);
            updateNightNumberTextView();

            return fragmentView;
        }

        /**
         * Aktualizuje numer kolejnej nocy
         */
        void updateNightNumberTextView(){
            nightNumberTextView.setText((getString(R.string.night_number,theGame.getNightNumber())));
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
            dayOrNightRoleActionsAdapter = new DayOrNightRoleActionsAdapter(getApplicationContext(),nightHumanPlayers);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_time_role_actions, container, false);
            return fragmentView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersActionsRecyclerView);
            playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL,false));
            playersActionsRecyclerView.setAdapter(dayOrNightRoleActionsAdapter);
        }

        /**
         * Adapter do wykonywania roli graczy
         */
        public class DayOrNightRoleActionsAdapter extends RecyclerView.Adapter<DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder> {

            private ArrayList<HumanPlayer> actionPlayers;

            private LayoutInflater inflater;
            private Context context;

            public DayOrNightRoleActionsAdapter(Context context, ArrayList<HumanPlayer> actionPlayers){
                this.actionPlayers = actionPlayers; //ogarnac zeby tylko te co się budzą danej porze dnia!!
                this.context=context;
                this.inflater = LayoutInflater.from(context);
            }

            @Override
            public PlayerRoleActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.player_role_action_layout,parent,false);
                return new PlayerRoleActionViewHolder(view);
            }

            @Override
            public void onBindViewHolder(PlayerRoleActionViewHolder holder, int position) {
                PlayerRole playerRole = actionPlayers.get(position).getPlayerRole();
              //  holder.roleIcon.setImageResource(playerRole.getIconResourceID());
                holder.roleName.setText(playerRole.getName());
                holder.playerName.setText(actionPlayers.get(position).getPlayerName());

                ArrayList<String> playerNames = new ArrayList<String>();
                for(HumanPlayer humanPlayer: theGame.getPlayersInfoList()){
                    playerNames.add(humanPlayer.getPlayerName());
                }
                ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
                choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.choosingSpinner.setAdapter(choosingSpinnerAdapter);
            }

            @Override
            public int getItemCount() {
                return actionPlayers.size();
            }

            class  PlayerRoleActionViewHolder extends RecyclerView.ViewHolder{

               // private ImageView roleIcon;
                private TextView roleName;
                private TextView playerName;
                private Button confirmButton;
                private Spinner choosingSpinner;

                private AlertDialog roleDescriptionDialog;

                public PlayerRoleActionViewHolder(View itemView) {
                    super(itemView);

                   // roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
                    roleName = (TextView) itemView.findViewById(R.id.roleName);
                    playerName = (TextView) itemView.findViewById(R.id.playerName);
                    confirmButton = (Button) itemView.findViewById(R.id.confirmButton);
                    choosingSpinner = (Spinner) itemView.findViewById(R.id.playersNamesSpinner);

                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            choosingSpinner.setEnabled(false);
                            confirmButton.setText(R.string.roleActionDone);
                            makeRoleAction(actionPlayers.get(getAdapterPosition()),theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()));
                        }
                    });

                }


                void makeRoleAction(HumanPlayer actionPlayer, HumanPlayer choosenPlayer){
                    switch(actionPlayer.getRoleName()){
                        case R.string.prostitute:
                            makeProstituteAction(choosenPlayer);break;
                        case R.string.black:
                            makeBlackManAction(choosenPlayer);break;
                        case R.string.blackJudge:
                            makeBlackManAction(choosenPlayer);break;
                        case R.string.blackmailer:
                            makeBlackmailerAction(choosenPlayer);break;
                        case R.string.blackmailerBoss:
                            makeBlackmailerAction(choosenPlayer);break;
                    }
                }

                void makeProstituteAction(HumanPlayer choosenPlayer){
                    FragmentManager fragmentManager = getFragmentManager();
                    TheGameActionShowingPlayerRoleDialog theGameActionShowingPlayerRoleDialog = new TheGameActionShowingPlayerRoleDialog(choosenPlayer);
                    theGameActionShowingPlayerRoleDialog.show(fragmentManager,"ProstituteAction");
                }

                void makeBlackManAction(HumanPlayer choosenPlayer){
                    choosenPlayer.setGuard(actionPlayers.get(getAdapterPosition()));
                    Toast.makeText(getApplicationContext(),choosenPlayer.getPlayerName()+" "+getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
                }

                void makeBlackmailerAction(HumanPlayer choosenPlayer){
                    choosenPlayer.setBlackMailer(actionPlayers.get(getAdapterPosition()));
                    Toast.makeText(getApplicationContext(),choosenPlayer.getPlayerName()+" "+getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
                }

                void makeRoleAction(HumanPlayer actionPlayer, HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2){
                    //makePriestAction();
                }

                public class TheGameActionShowingPlayerRoleDialog extends DialogFragment{

                    private HumanPlayer choosenPlayer;
                    private Button understandButton;
                    private TextView showingPlayerRoleExplanation;
                    private TextView showedPlayerRoleText;
                    private TextView showedPlayerFraction;

                    TheGameActionShowingPlayerRoleDialog(HumanPlayer choosenPlayer){
                        this.choosenPlayer = choosenPlayer;
                    }
                    @Nullable
                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        View view = inflater.inflate(R.layout.dialog_showing_player_role,null);
                        setCancelable(false);
                        getDialog().setTitle(R.string.checkedRole);

                        //showingPlayerRoleExplanation = (TextView) view.findViewById(R.id.showingPlayerRoleExplanation);
                        showedPlayerRoleText = (TextView) view.findViewById(R.id.showedPlayerRoleText);
                        showedPlayerRoleText.setText(getString(choosenPlayer.getRoleName()));
                        showedPlayerFraction = (TextView) view.findViewById(R.id.showedPlayerFraction);
                        showedPlayerFraction.setText(choosenPlayer.getPlayerRole().getFraction().name());


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
    public class PlayerGameStatusRoleAdapter extends RecyclerView.Adapter<PlayerGameStatusRoleAdapter.PlayerStatusViewHolder> {

        private ArrayList<HumanPlayer> humanPlayersList;
        private LayoutInflater inflater;
        private Context context;

        public PlayerGameStatusRoleAdapter(Context context) {
            this.humanPlayersList = theGame.getPlayersInfoList();
            this.inflater = LayoutInflater.from(context);
            this.context = getApplicationContext();
        }

        @Override
        public PlayerStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_game_status_layout, parent, false);
            return new PlayerStatusViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlayerStatusViewHolder holder, int position) {
            HumanPlayer humanPlayer = humanPlayersList.get(position);
            holder.playerName.setText(humanPlayer.getPlayerName());
            holder.playerRoleIcon.setImageResource(humanPlayer.getPlayerRole().getIconResourceID());
            holder.roleName.setText(getString(humanPlayer.getRoleName()));
            showProperlyPlayerStatus(humanPlayer,holder);
        }

        @Override
        public int getItemCount() {
            return humanPlayersList.size();
        }

        public void showProperlyPlayerStatus(HumanPlayer humanPlayer, PlayerStatusViewHolder playerStatusViewHolder){
            if(humanPlayer.isAlive())
                playerStatusViewHolder.playerStatus.setText(R.string.status_alive);
            else{
                playerStatusViewHolder.playerStatus.setText(R.string.status_died);
                playerStatusViewHolder.playerRoleIcon.setImageResource(R.drawable.icon_ghost);}
        }

        class PlayerStatusViewHolder extends RecyclerView.ViewHolder {

            private ImageView playerRoleIcon;
            private TextView playerName;
            private TextView roleName;
            private TextView fractionName;
            private TextView playerStatus;
            private View container;

            private AlertDialog roleDescriptionDialog;

            public PlayerStatusViewHolder(View itemView) {
                super(itemView);

                playerRoleIcon = (ImageView) itemView.findViewById(R.id.playerIco);
                playerName = (TextView) itemView.findViewById(R.id.playerName);
                roleName = (TextView) itemView.findViewById(R.id.roleName);
                fractionName = (TextView) itemView.findViewById(R.id.fractionName);
                playerStatus = (TextView) itemView.findViewById(R.id.playerStatus);

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


    ArrayList<HumanPlayer> getTownHumanPlayers(){
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for(HumanPlayer humanPlayer: theGame.getPlayersInfoList()){
            if(humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
                result.add(humanPlayer);
        }
        return result;
    }

    ArrayList<HumanPlayer> getZeroNightHumanPlayers(){
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for(HumanPlayer humanPlayer: theGame.getPlayersInfoList()){
            if(humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNightAndActionRequire))
                result.add(humanPlayer);
            if(humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNight))
                result.add(humanPlayer);
        }

        return result;
    }
}
