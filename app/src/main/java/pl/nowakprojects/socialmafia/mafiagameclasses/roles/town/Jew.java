package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Jew extends PlayerRole {

    public Jew(){
        super();
        setName(R.string.jew);
        setDescription(R.string.jewDescription);
        setIconResourceID(R.drawable.ic_jew);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
