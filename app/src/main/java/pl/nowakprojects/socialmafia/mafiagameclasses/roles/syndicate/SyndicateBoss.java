package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class SyndicateBoss extends PlayerRole {

    public SyndicateBoss(){
        super();
        setName(R.string.syndicateBoss);
        setDescription(R.string.syndicateBossDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(-1);
    }
}
