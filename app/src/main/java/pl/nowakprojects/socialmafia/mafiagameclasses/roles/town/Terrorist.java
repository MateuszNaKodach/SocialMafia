package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Terrorist extends PlayerRole {

    public Terrorist(){
        super();
        setNameId(R.string.terrorist);
        setDescriptionId(R.string.terroristDescription);
        setIconResourceID(R.drawable.icon_terrorist);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
