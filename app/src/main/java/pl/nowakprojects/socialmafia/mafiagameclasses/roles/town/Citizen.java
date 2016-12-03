package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Citizen extends PlayerRole {

    public Citizen(){
        super();
        this.setName(R.string.citizen);
        this.setDescription(R.string.citizenDescription);
        this.setIconResourceID(R.drawable.image_template);
        this.setFraction(Fraction.TOWN);
        this.setActionType(ActionType.NoAction);
    }
}
