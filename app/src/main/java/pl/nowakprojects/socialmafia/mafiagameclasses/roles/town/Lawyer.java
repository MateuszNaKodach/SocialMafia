package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Lawyer extends PlayerRole {

    public Lawyer(){
        super();
        setName(R.string.lawyer);
        setDescription(R.string.lawyerDescription);
        setIconResourceID(R.drawable.ic_lawyer);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
