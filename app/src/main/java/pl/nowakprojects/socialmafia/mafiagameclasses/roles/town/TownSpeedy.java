package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class TownSpeedy extends PlayerRole {

    public TownSpeedy(){
        super();
        setName(R.string.townspeedy);
        setDescription(R.string.townspeedyDescription);
        setIconResourceID(R.drawable.ic_fastman);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
