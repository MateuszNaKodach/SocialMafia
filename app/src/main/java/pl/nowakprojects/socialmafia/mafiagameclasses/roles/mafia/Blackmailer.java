package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Blackmailer extends PlayerRole implements ContextRoleAction{

    public Blackmailer(){
        super();
        setNameId(R.string.blackmailer);
        setDescriptionId(R.string.blackmailerDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(80);
    }

    //@Override
    public void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers) {
        if (!(chosePlayers[0].getBlackMailersList().contains(actionPlayer)))
            chosePlayers[0].appendBlackMailer(actionPlayer);

        Toast.makeText(fragment.getActivity().getApplicationContext(), chosePlayers[0].getPlayerName() + " " + fragment.getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
    }
}
