package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.parceler.Parcels;

import java.util.concurrent.TimeUnit;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

public class TheGameActionActivity extends AppCompatActivity {

    TheGame theGame;
    boolean IS_LOADED_GAME = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receiveGameSettings(); //odbiera ustawienia gry
        DayTimeFragment dayTimeFragment = new DayTimeFragment();
        NightTimeFragment nightTimeFragment = new NightTimeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dayOrNightTimeFragment,dayTimeFragment,"TIME_FRAGMENT");
        fragmentTransaction.commit();

    }


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

            dayTimerTextView = (TextView) fragmentView.findViewById(R.id.dayTimerTextView);
            final TimeCounterClass dayTimeTimer = new TimeCounterClass(theGame.getDaytime(),1000);
            dayTimeTimer.start();

         //   Button finishDayButton = (Button) findViewById(R.id.dayTimeEnded);

           /* finishDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("finishDayButton.onClick","Przycisk Wcisniety!");
                }
            });*/

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


        public NightTimeFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_day_time, container, false);
        }

    }



}
