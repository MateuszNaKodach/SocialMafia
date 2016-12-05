package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Gravedigger extends PlayerRole {

    public Gravedigger(){
        super();
        setNameId(R.string.gravedigger);
        setDescriptionId(R.string.gravediggerDescription);
        setIconResourceID(R.drawable.ic_gravedigger);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(70);
    }
}
