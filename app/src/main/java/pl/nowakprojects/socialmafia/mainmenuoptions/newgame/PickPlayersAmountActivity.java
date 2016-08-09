package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import pl.nowakprojects.socialmafia.MainActivity;
import pl.nowakprojects.socialmafia.R;

public class PickPlayersAmountActivity extends AppCompatActivity {

    static final String EXTRA_PLAYERS_AMOUNT = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_PLAYERS_AMOUNT";
    private final String LOG_TAG = "SOCIALMAFIA: PickPlayersAmountActivity.class";
    private NumberPicker playersAmountNumberPicker;
    private Button goToPlayerNamesButton;
    private static final int PLAYERS_MAX_AMOUNT = 70;
    private static final int PLAYERS_MIN_AMOUNT = 5;
    private int pickedPlayersAmount = PLAYERS_MIN_AMOUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_players_amount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playersAmountNumberPicker = (NumberPicker) findViewById(R.id.playersamountpicker);
        playersAmountNumberPicker.setMinValue(PLAYERS_MIN_AMOUNT);
        playersAmountNumberPicker.setMaxValue(PLAYERS_MAX_AMOUNT);
        playersAmountNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                pickedPlayersAmount=newValue;
            }
        });

        goToPlayerNamesButton = (Button) findViewById(R.id.goToPlayerNamesButton);
        goToPlayerNamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG,String.valueOf(pickedPlayersAmount));
                Intent intent = new Intent(getApplicationContext(), TapPlayersNamesActivity.class);
                intent.putExtra(EXTRA_PLAYERS_AMOUNT,pickedPlayersAmount);
                startActivity(intent);
            }
        });

    }

}
