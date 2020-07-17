package pl.nowakprojects.socialmafia.ui.loadgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import io.realm.Realm;
import io.realm.RealmResults;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.realmmodels.TheGameModel;

public class LoadGameActivity extends AppCompatActivity {

    private Realm realmDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        realmDataBase = Realm.getDefaultInstance();
        realmDataBase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TheGameModel theGameModel = realmDataBase.createObject(TheGameModel.class,5);
            }
        });

        RealmResults<TheGameModel> savedGames = realmDataBase.where(TheGameModel.class).findAll();
        for(TheGameModel game: savedGames)
            Log.d("ZAPISANA GRA",String.valueOf(game.getGameId()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmDataBase.close();
    }
}
