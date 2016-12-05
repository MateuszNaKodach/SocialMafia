package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Boss extends PlayerRole {

    public Boss(){
        super();
        setNameId(R.string.boss);
        setDescriptionId(R.string.bossDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.NoAction);
        setNightWakeHierarchyNumber(90);
    }
}
