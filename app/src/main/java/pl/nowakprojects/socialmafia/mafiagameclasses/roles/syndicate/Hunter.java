package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Hunter extends PlayerRole {

    public Hunter(){
        super();
        setNameId(R.string.hunter);
        setDescriptionId(R.string.hunterDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.ActionRequire);
    }
}
