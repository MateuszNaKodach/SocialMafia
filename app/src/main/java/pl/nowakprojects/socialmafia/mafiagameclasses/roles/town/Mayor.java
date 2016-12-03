package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Mayor extends PlayerRole {

    public Mayor(){
        super();
        setName(R.string.mayor);
        setDescription(R.string.mayorDescription);
        setIconResourceID(R.drawable.ic_lawyer);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
