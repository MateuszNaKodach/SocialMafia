package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Emo extends PlayerRole {

    public Emo(){
        super();
        setNameId(R.string.emo);
        setDescriptionId(R.string.emoDescription);
        setIconResourceID(R.drawable.ic_emo);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
