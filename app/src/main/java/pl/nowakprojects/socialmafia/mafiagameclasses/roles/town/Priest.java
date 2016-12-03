package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Priest extends PlayerRole {

    public Priest(){
        super();
        setName(R.string.priest);
        setDescription(R.string.priestDescription);
        setIconResourceID(R.drawable.ic_priest);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(160);
    }
}
