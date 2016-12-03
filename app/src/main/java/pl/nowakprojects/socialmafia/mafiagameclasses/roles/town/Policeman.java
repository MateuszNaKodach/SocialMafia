package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.ui.newgame.dialogfragments.ShowingPlayerGoodOrBadDialog;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Policeman extends PlayerRole {

    public Policeman(){
        super();
        this.setName(R.string.policeman);
        this.setDescription(R.string.policemanDescription);
        this.setIconResourceID(R.drawable.icon_policeman);
        this.setFraction(Fraction.TOWN);
        this.setActionType(ActionType.AllNightsBesideZero);
        this.setNightWakeHierarchyNumber(50);
    }

    @Override
    public void action(Fragment fragment, HumanPlayer... players) {
        //CO JEST!? NIC SIE NIE POKAZALO!?
        Toast.makeText(fragment.getActivity().getApplicationContext(), "Jestesmy w Policeman", Toast.LENGTH_LONG).show();

        FragmentManager fragmentManager = fragment.getFragmentManager();
        ShowingPlayerGoodOrBadDialog showingPlayerGoodOrBadDialog = new ShowingPlayerGoodOrBadDialog(players[0]);
        showingPlayerGoodOrBadDialog.show(fragmentManager, "PolicemanAction");
    }
}
