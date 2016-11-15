/*package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;


@Parcel
class GameDaytime {

    TheGame mTheGame;
    TheGame.Daytime mDayTime;
    int miThisDaytimeNumber;
    int miBeginAlivePlayersAmount;
    int miBeginTownPlayersAmount;
    int miBeginMafiaPlayersAmount;
    int miBeginSyndicatePlayersAmount;

    ArrayList<HumanPlayer> mlistThisDaytimeKilledPlayers = new ArrayList<>();

    GameDaytime(){}

    GameDaytime(TheGame theGame, TheGame.Daytime dayTime ){
        mTheGame=theGame;
        mDayTime=dayTime;
        if(mDayTime== TheGame.Daytime.DAY)
            miThisDaytimeNumber=mTheGame.getMiCurrentDayNumber();
        else
            miThisDaytimeNumber=mTheGame.getMiCurrentNightNumber();

        miBeginAlivePlayersAmount=mTheGame.getLiveHumanPlayers().size();
        miBeginTownPlayersAmount=mTheGame.getLiveTownPlayers().size();
        miBeginMafiaPlayersAmount=mTheGame.getLiveMafiaPlayers().size();
        miBeginSyndicatePlayersAmount=mTheGame.getLiveSyndicatePlayers().size();
    }
}*/
