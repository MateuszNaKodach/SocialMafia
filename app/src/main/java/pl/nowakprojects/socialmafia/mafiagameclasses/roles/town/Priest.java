package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.ui.newgame.dialogfragments.ShowingLoversRolesDialog;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Priest extends PlayerRole implements ContextRoleAction{

    public Priest(){
        super();
        setNameId(R.string.priest);
        setDescriptionId(R.string.priestDescription);
        setIconResourceID(R.drawable.ic_priest);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(160);
    }

    @Override
    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        chosePlayers[0].addLover(chosePlayers[1]);
        chosePlayers[1].addLover(chosePlayers[0]);
        FragmentManager fragmentManager = fragment.getFragmentManager();
        ShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new ShowingLoversRolesDialog(chosePlayers[0], chosePlayers[1]);
        theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
    }
}
