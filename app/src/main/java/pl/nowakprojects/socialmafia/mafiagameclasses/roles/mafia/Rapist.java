package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
public class Rapist extends PlayerRole implements ContextRoleAction{

    public Rapist(){
        super();
        setNameId(R.string.rapist);
        setDescriptionId(R.string.rapistDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(130);
    }

    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        ShowingPlayerRoleDialog showingPlayerRoleDialog = new ShowingPlayerRoleDialog(chosePlayers[0]);
        showingPlayerRoleDialog.show(fragmentManager, "RapistAction");
    }
}
