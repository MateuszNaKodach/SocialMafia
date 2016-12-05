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
public class Darkmedic extends PlayerRole implements GameStateModifierRoleAction{

    public Darkmedic(){
        super();
        setNameId(R.string.darkmedic);
        setDescriptionId(R.string.darkmedicDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(121);
    }

    @Override
    public void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        if (actionPlayer.isNotDealed())
            commitNotDealedRole(theGame, actionPlayer, chosePlayers);
        else
            showDealedRoleToast();
    }

    //@Override
    public void commitNotDealedRole(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        if(chosePlayers[0].isNotDealed()){
            theGame.addLastNightHeatingByDarkMedicPlayers(chosePlayers[0]);
            Toast.makeText(mContext.getApplicationContext(), chosePlayers[0].getPlayerName() + " " + mContext.getString(R.string.isHeatingThisNight), Toast.LENGTH_LONG).show();}
    }

}
