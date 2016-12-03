package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Rapist extends PlayerRole {

    public Rapist(){
        super();
        setName(R.string.rapist);
        setDescription(R.string.rapistDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.OnlyZeroNight);
        setNightWakeHierarchyNumber(130);
    }
}
