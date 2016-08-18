package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

public class TheGameActionActivity extends AppCompatActivity {

    TheGame theGame;
    boolean IS_LOADED_GAME = false;

    DayTimeFragment dayTimeFragment;
    NightTimeFragment nightTimeFragment;
    RecyclerView playersInfoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receiveGameSettings(); //odbiera ustawienia gry

        //playersInfoRecyclerView = (RecyclerView) findViewById(R.id.playersStatusRecyclerView);
        //playersInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //playersInfoRecyclerView.setAdapter(new PlayerGameStatusRoleAdapter(getApplicationContext()));

        startMafiaGameAction();
    }

    /**
     * TheGame functions:
     */

    void startMafiaGameAction(){
        //GameView proporties:
        dayTimeFragment = new DayTimeFragment();
        nightTimeFragment = new NightTimeFragment();
        //dopóki gra nie jest zakończona ciągle leci dzień - noc:
        while(!theGame.isFinished()){
            startNightAction();
            startDayAction();}

        endTheGameAndShowResults();
    }

    void startNightAction(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dayOrNightTimeFragment,nightTimeFragment,"TIME_FRAGMENT");
        fragmentTransaction.commit();
    }

    void endNightAction(){
        theGame.setNightNumber(theGame.getNightNumber()+1);
    }

    void startDayAction(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dayOrNightTimeFragment,dayTimeFragment,"TIME_FRAGMENT");
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
            Button finishDayButton = (Button) findViewById(R.id.finishTheDayButton);
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


        TextView nightNumberTextView;

        public NightTimeFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View fragmentView = inflater.inflate(R.layout.fragment_night_time, container, false);

            nightNumberTextView = (TextView) findViewById(R.id.nightNumberTextView);
            updateNightNumberTextView();

            return fragmentView;
        }


        void updateNightNumberTextView(){
            nightNumberTextView.setText(R.string.night_number + String.valueOf(theGame.getNightNumber()));
        }
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


}
