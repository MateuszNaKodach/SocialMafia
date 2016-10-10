package pl.nowakprojects.socialmafia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import pl.nowakprojects.socialmafia.mainmenuoptions.gamerules.GameRulesActivity;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.PickPlayersAmountActivity;

public class MainActivity extends AppCompatActivity {

    static boolean IS_PREMIUM_VESRION = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonStartNewGame = (ImageButton) findViewById(R.id.buttonStartNewGame);
        ImageButton buttonLoadGame = (ImageButton) findViewById(R.id.buttonLoadGame);
        ImageButton buttonGameRules = (ImageButton) findViewById(R.id.buttonGameRules);
        ImageButton buttonAppPreferences = (ImageButton) findViewById(R.id.buttonAppPreferences);
        View facebookLayout = (View) findViewById(R.id.facebookFanPageLayout);

        buttonStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PickPlayersAmountActivity.class);
                startActivity(intent);
            }
        });

        buttonGameRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameRulesActivity.class);
                startActivity(intent);
            }
        });

        facebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse("https://www.facebook.com/Social-Mafia-1764817623732317/");
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
            }
        });
    }
}
