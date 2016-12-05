package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Conductor extends PlayerRole {

    public Conductor(){
        super();
        setNameId(R.string.conductor);
        setDescriptionId(R.string.conductorDescription);
        setIconResourceID(R.drawable.icon_conductor);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.OnceAGame);
    }
}
