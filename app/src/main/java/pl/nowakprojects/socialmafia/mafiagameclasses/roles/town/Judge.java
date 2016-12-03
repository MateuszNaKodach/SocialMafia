package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Judge extends PlayerRole {

    public Judge(){
        super();
        setName(R.string.judge);
        setDescription(R.string.judgeDescription);
        setIconResourceID(R.drawable.icon_judge);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.ActionRequire);
    }
}
