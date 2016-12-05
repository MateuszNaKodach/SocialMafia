package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.ui.newgame.dialogfragments.ShowingPlayerRoleDialog;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Prostitute extends PlayerRole implements ContextRoleAction {

    public Prostitute(){
        super();
        setNameId(R.string.prostitute);
        setDescriptionId(R.string.prostituteDescription);
        setIconResourceID(R.drawable.icon_prostitute);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(60);
    }

    //@Override
    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        ShowingPlayerRoleDialog showingPlayerRoleDialog = new ShowingPlayerRoleDialog(chosePlayers[0]);
        showingPlayerRoleDialog.show(fragmentManager, "ProstituteAction");
    }
}
