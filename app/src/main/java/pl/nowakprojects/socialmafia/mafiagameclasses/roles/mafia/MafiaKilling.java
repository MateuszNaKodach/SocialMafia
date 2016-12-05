package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.GameStateModifierRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class MafiaKilling extends PlayerRole implements GameStateModifierRoleAction{

    public MafiaKilling(){
        super();
        setNameId(R.string.mafiaKill);
        setDescriptionId(R.string.mafiaKillDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.GROUP);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(81);
    }

    @Override
    public void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        theGame.addLastNightHittingByMafiaPlayer(chosePlayers[0]);
        if(chosePlayers[1]!=null){
            theGame.addLastNightHittingByMafiaPlayer(chosePlayers[1]);}

        Toast.makeText(mContext.getApplicationContext(),
                chosePlayers[0].getPlayerName() + (
                        chosePlayers[1]!=null ?
                                (" " + mContext.getString(R.string.and2) + " " +chosePlayers[1].getPlayerName()) : "") + " " +
                        mContext.getString(R.string.hadHitByMafiaNow), Toast.LENGTH_LONG).show();
    }
}


