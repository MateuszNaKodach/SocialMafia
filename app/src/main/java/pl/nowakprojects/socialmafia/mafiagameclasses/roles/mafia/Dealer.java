package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.GameStateModifierRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Dealer extends PlayerRole implements GameStateModifierRoleAction{

    public Dealer(){
        super();
        setNameId(R.string.dealer);
        setDescriptionId(R.string.dealerDescription);
        setIconResourceID(R.drawable.icon_dealer);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(30);
    }

    //@Override
    public void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        theGame.appendLastNightDealingByDealerPlayers(chosePlayers[0]);
        theGame.findHumanPlayerByName(chosePlayers[0].getPlayerName()).setDealed(true);
        Toast.makeText(mContext.getApplicationContext(), chosePlayers[0].getPlayerName() + " " + mContext.getString(R.string.isDealingThisNight), Toast.LENGTH_LONG).show();
    }

}
