package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class SyndicateSpeedy extends PlayerRole {

    public SyndicateSpeedy(){
        super();
        setName(R.string.syndicateSpeedy);
        setDescription(R.string.syndicateSpeedyDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.ActionRequire);
    }
}
