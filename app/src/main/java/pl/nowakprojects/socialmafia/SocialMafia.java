package pl.nowakprojects.socialmafia;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class SocialMafia extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

    }
}
