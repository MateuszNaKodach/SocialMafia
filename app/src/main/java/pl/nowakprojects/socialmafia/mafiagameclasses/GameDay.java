package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Mateusz on 23.10.2016.
 */
@Parcel
class GameDay extends GameDaytime{

    int miThisDayRemainedDuels;
    int miThisDayThrownChallenges=0;

    ArrayList<HumanPlayer> mlistDuelsKilledHumanPlayers = new ArrayList<>();


    GameDay(){
        super();
    }

    GameDay(TheGame theGame, TheGame.Daytime dayTime) {
        super(theGame, dayTime);
        miThisDayRemainedDuels=mTheGame.I_MAX_DUELS_AMOUNT;
    }
}
