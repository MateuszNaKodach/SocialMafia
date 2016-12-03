package pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class DeathAngel extends PlayerRole {

    public DeathAngel(){
        super();
        setName(R.string.deathAngel);
        setDescription(R.string.deathAngelDescription);
        setIconResourceID(R.drawable.icon_deathangel);
        setFraction(Fraction.SYNDICATE);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(140);
    }
}
