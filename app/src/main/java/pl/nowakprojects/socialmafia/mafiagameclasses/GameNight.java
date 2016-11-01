package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

/**
 * Created by Mateusz on 23.10.2016.
 */
@Parcel
class GameNight extends GameDaytime{

    GameNight(){
        super();
    }

    GameNight(TheGame theGame, TheGame.Daytime dayTime) {
        super(theGame, dayTime);
    }
}
