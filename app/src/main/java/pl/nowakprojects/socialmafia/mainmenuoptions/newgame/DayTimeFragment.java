package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DayTimeFragment extends Fragment {


    private TheGameActionActivity theGameActionActivity;
    TextView dayTimerTextView;

    public DayTimeFragment(TheGameActionActivity theGameActionActivity) {
        this.theGameActionActivity = theGameActionActivity;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_time, container, false);

        //Odliczanie czasu na jeden dzień (czas ustawiony wczesniej):
        dayTimerTextView = (TextView) fragmentView.findViewById(R.id.dayTimerTextView);
        final TimeCounterClass dayTimeTimer = new TimeCounterClass(theGameActionActivity.theGame.getMLONG_MAX_DAY_TIME(), 1000);
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
            Vibrator vibrator = (Vibrator) theGameActionActivity.getSystemService(theGameActionActivity.getApplicationContext().VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
        }
    }

}
