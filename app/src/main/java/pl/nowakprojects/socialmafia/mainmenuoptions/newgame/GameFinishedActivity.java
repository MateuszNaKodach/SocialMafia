package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.MainActivity;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

public class GameFinishedActivity extends AppCompatActivity {

    TheGame mFinishedGame;

    @BindView(R.id.mainmenu_button) Button gameFinishedButton;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);

        ButterKnife.bind(this);

        vReceiveExtraObjects();

        vUiSetupUserInterface();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //niemozliwy powrót do zakończonej gry!
    }

    private void vReceiveExtraObjects(){
        mFinishedGame = Parcels.unwrap(getIntent().getParcelableExtra(TheGameActionActivity.EXTRA_FINISHED_GAME));
    }


    private void vUiSetupUserInterface(){
        vUiSetupToolbar();
        vUiSetupButtonListener();
    }

    private void vUiSetupToolbar(){
        setSupportActionBar(toolbar);
    }

    private void vUiSetupButtonListener(){
        gameFinishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiGoToMainActivity();
            }
        });
    }


    private void vUiGoToMainActivity(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
