package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Blackman extends PlayerRole implements ContextRoleAction {

    public Blackman(){
        super();
        setNameId(R.string.black);
        setDescriptionId(R.string.blackDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNightAndActionRequire);
        setNightWakeHierarchyNumber(10);
    }

    @Override
    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        if (!(chosePlayers[0].getGuardsList().contains(actionPlayer)))
            chosePlayers[0].addGuard(actionPlayer);

        Toast.makeText(fragment.getActivity().getApplicationContext(), chosePlayers[0].getPlayerName() + " " + fragment.getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
    }
}
