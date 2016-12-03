package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Medic extends PlayerRole {

    public Medic(){
        super();
        setName(R.string.medic);
        setDescription(R.string.medicDescription);
        setIconResourceID(R.drawable.icon_role_medic);
        setFraction(Fraction.TOWN);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(120);
    }
}
