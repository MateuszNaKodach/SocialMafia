package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Dentist extends PlayerRole {

    public Dentist(){
        super();
        setNameId(R.string.dentist);
        setDescriptionId(R.string.dentistDescription);
        setIconResourceID(R.drawable.icon_dentist);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.AllNightsBesideZero);
    }
}
