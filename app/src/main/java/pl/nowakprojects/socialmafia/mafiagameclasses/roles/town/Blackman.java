package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Blackman extends PlayerRole {

    public Blackman(){
        super();
        setName(R.string.black);
        setDescription(R.string.blackDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNightAndActionRequire);
        setNightWakeHierarchyNumber(10);
    }
}
