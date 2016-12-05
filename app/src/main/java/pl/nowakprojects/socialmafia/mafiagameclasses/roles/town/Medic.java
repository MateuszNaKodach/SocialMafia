package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

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
public class Medic extends PlayerRole implements GameStateModifierRoleAction {

    public Medic(){
        super();
        setNameId(R.string.medic);
        setDescriptionId(R.string.medicDescription);
        setIconResourceID(R.drawable.icon_role_medic);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(120);
    }

    @Override
    public void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        theGame.addLastNightHealingByMedicPlayers(chosePlayers[0]);
        Toast.makeText(mContext.getApplicationContext(), chosePlayers[0].getPlayerName() + " " + mContext.getString(R.string.isHealingThisNight), Toast.LENGTH_LONG).show();
    }
}
