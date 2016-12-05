package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

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
public class DeathAngel extends PlayerRole implements GameStateModifierRoleAction{

    public DeathAngel(){
        super();
        setNameId(R.string.deathAngel);
        setDescriptionId(R.string.deathAngelDescription);
        setIconResourceID(R.drawable.icon_deathangel);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(140);
    }

    @Override
    public void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        chosePlayers[0].addStigma();
        Toast.makeText(mContext.getApplicationContext(), chosePlayers[0].getPlayerName() + " " + mContext.getString(R.string.isSignedThisNight), Toast.LENGTH_LONG).show();
    }
}
