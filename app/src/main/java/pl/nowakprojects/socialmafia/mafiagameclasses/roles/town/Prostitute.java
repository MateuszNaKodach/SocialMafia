package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Prostitute extends PlayerRole {

    public Prostitute(){
        super();
        setName(R.string.prostitute);
        setDescription(R.string.prostituteDescription);
        setIconResourceID(R.drawable.icon_prostitute);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(60);
    }
}
