package pl.nowakprojects.socialmafia.ui.newgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;

/**
 * Wybór liczby graczy
 */
public class PickPlayersAmountActivity extends AppCompatActivity {

    static final String EXTRA_PLAYERS_AMOUNT = "pl.nowakprojects.socialmafia.mafiagameclasses.EXTRA_PLAYERS_AMOUNT";
    private final String LOG_TAG = "SOCIALMAFIA: PickPlayersAmountActivity.class"; //tag dla logów


    private static final int PLAYERS_MAX_AMOUNT = 70; //maksymalna liczba graczy
    private static final int PLAYERS_MIN_AMOUNT = 5; //minimalna liczba graczy
    private long miPickedDayTime = 5;


    private int mPickedPlayersAmount = PLAYERS_MIN_AMOUNT; //poczatkowa wybrana liczba graczy


    @BindView(R.id.playersamountpicker) NumberPicker mPlayersAmountNumberPicker;
    @BindView(R.id.goToPlayerNamesButton)   Button mNextSettingsButton;
    @BindView(R.id.daytimeSeekBar) SeekBar mDaytimeSeekBar;
    @BindView(R.id.pickedDayTimeTextView) TextView mPickedDayTimeTextView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_players_amount);
        ButterKnife.bind(this);

        vUiSetupUserInterface();
    }

    private void vUiSetupUserInterface(){
        vUiSetupToolbar();
        vUiSetupNumberPicker();
        vUiSetupButtonListener();
        vUiSetupSeekBar();
        vUiUpdateTextView();
    }

    private void vUiSetupToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void vUiSetupButtonListener(){
        mNextSettingsButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Przekazuje liczbe graczy do aktywności wpisywania imion
             * @param view
             */
            @Override
            public void onClick(View view) {

                miPickedDayTime*=60000;
                SharedPreferences.Editor sharedPreferences = getPreferences(Context.MODE_PRIVATE).edit();
                sharedPreferences.putString(getString(R.string.sharedpref_daytime), String.valueOf(miPickedDayTime));
                sharedPreferences.apply();

                Intent intent = new Intent(getApplicationContext(), TapPlayersNamesActivity.class);
                intent.putExtra(EXTRA_PLAYERS_AMOUNT, mPickedPlayersAmount);
                startActivity(intent);
            }
        });
    }

    private void vUiSetupNumberPicker(){
        mPlayersAmountNumberPicker.setMinValue(PLAYERS_MIN_AMOUNT);
        mPlayersAmountNumberPicker.setMaxValue(PLAYERS_MAX_AMOUNT);
        mPlayersAmountNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                mPickedPlayersAmount =newValue; //przy zmianie wartości nadpisuje wybraną liczbę graczy
            }
        });
    }

    private void vUiSetupSeekBar(){
        mDaytimeSeekBar.setMax(30);
        mDaytimeSeekBar.setProgress(10);

        mDaytimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                miPickedDayTime=mDaytimeSeekBar.getProgress();
                vUiUpdateTextView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void vUiUpdateTextView(){
        mPickedDayTimeTextView.setText(getString(R.string.timeofdaytime, miPickedDayTime));
    }
}
