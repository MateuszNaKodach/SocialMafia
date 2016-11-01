package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DayTimeFragment extends Fragment {

    TheGame mTheGame;

    @BindView(R.id.dayTimerTextView)    TextView mDayTimerTextView;
    @BindView(R.id.dayNumberTextView)    TextView mDayNumberTextView;
    @BindView(R.id.finishTheDayButton)  Button mFinishDayButton;


    public DayTimeFragment(TheGame theGame) {
        this.mTheGame=theGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_time, container, false);

        ButterKnife.bind(this,fragmentView);

        vUiSetupUserInterface();
        startDayTimer();


        return fragmentView;
    }


    private void startDayTimer(){
        final TimeCounterClass dayTimeTimer = new TimeCounterClass(mTheGame.getMdMaxDayTime(), 1000);
        dayTimeTimer.start();
    }

    private void vUiSetupUserInterface(){
        vUiSetupTextView();
        vUiSetupButtonListener();
    }

    private void vUiSetupTextView(){
        mDayNumberTextView.setText(getString(R.string.day_number,mTheGame.getMiCurrentDayNumber()));
    }

    private void vUiSetupButtonListener(){
        mFinishDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

            mDayTimerTextView.setText(hms);
        }

        @Override
        public void onFinish() {
            mDayTimerTextView.setText(getString(R.string.dayTimeEnded));
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
        }
    }

}
