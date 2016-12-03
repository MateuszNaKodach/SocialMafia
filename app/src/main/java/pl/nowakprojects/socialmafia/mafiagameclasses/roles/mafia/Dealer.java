package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Dealer extends PlayerRole {

    public Dealer(){
        super();
        setName(R.string.dealer);
        setDescription(R.string.dealerDescription);
        setIconResourceID(R.drawable.icon_dealer);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(30);
    }
}
