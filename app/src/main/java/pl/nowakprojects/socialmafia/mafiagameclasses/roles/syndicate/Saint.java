package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Saint extends PlayerRole {

    public Saint(){
        super();
        setName(R.string.saint);
        setDescription(R.string.saintDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.ActionRequire);
    }
}
