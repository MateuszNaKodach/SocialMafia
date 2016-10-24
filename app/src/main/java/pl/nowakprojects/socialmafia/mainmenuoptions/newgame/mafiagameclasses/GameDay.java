package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Mateusz on 23.10.2016.
 */

class GameDay {
    TheGame mTheGame;

    int miThisDayNumber;
    int miThisDayRemainedDuels;
    int miThisDayThrownChallenges;
    int miBeginAlivePlayersAmount;
    int miBeginTownPlayersAmount;
    int miBeginMafiaPlayersAmount;
    int miBeginSyndicatePlayersAmount;

    Set<HumanPlayer> msetThisDayKilledPlayers;  //m - member set- zbior new name conventionb

    public GameDay(TheGame theGame){
        this.mTheGame=theGame;
    }

}
