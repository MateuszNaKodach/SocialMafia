package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Bartender extends PlayerRole {

    public Bartender(){
        super();
        setName(R.string.bartender);
        setDescription(R.string.bartenderDescription);
        setIconResourceID(R.drawable.ic_bartender);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.OnceAGame);
        setNightWakeHierarchyNumber(110);
    }
}
