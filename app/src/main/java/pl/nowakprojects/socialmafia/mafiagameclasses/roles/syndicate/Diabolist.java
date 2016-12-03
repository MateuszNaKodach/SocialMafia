package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Diabolist extends PlayerRole {

    public Diabolist(){
        super();
        setName(R.string.diabolist);
        setDescription(R.string.diabolistDescription);
        setIconResourceID(R.drawable.ic_diabolist);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(190);
    }
}
