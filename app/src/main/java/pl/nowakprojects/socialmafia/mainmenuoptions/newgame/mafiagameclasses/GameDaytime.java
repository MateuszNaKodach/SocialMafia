package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Mateusz on 25.10.2016.
 */

@Parcel
abstract class GameDaytime {

    TheGame mTheGame;
    final TheGame.Daytime mDayTime;
    final int miThisDaytimeNumber;
    final int miBeginAlivePlayersAmount;
    final int miBeginTownPlayersAmount;
    final int miBeginMafiaPlayersAmount;
    final int miBeginSyndicatePlayersAmount;

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
}
