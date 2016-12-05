package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.ui.newgame.dialogfragments.ShowingPlayerGoodOrBadDialog;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Policeman extends PlayerRole implements ContextRoleAction{

    public Policeman(){
        super();
        this.setNameId(R.string.policeman);
        this.setDescriptionId(R.string.policemanDescription);
        this.setIconResourceID(R.drawable.icon_policeman);
        this.setFraction(Fraction.TOWN);
        this.setActionType(ActionType.AllNightsBesideZero);
        this.setNightWakeHierarchyNumber(50);
    }

    //@Override
    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        ShowingPlayerGoodOrBadDialog showingPlayerGoodOrBadDialog = new ShowingPlayerGoodOrBadDialog(chosePlayers[0]);
        showingPlayerGoodOrBadDialog.show(fragmentManager, "PolicemanAction");
    }
}
