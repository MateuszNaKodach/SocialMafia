package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Timestopper extends PlayerRole {

    public Timestopper(){
        super();
        setName(R.string.timestopper);
        setDescription(R.string.timestopperDescription);
        setIconResourceID(R.drawable.icon_timestopper);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.ActionRequire);
    }
}
