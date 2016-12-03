package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Coquette extends PlayerRole {

    public Coquette(){
        super();
        setName(R.string.coquette);
        setDescription(R.string.coquetteDescription);
        setIconResourceID(R.drawable.icon_prostitute2);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.NoAction);
    }
}
