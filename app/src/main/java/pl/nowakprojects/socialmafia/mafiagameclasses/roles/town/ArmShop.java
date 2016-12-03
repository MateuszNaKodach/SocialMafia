package pl.nowakprojects.socialmafia.mafiagameclasses.roles.town;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Armshop extends PlayerRole {

    public Armshop(){
        super();
        this.setName(R.string.armshop);
        this.setDescription(R.string.armshopDescription);
        this.setIconResourceID(R.drawable.image_template);
        this.setFraction(Fraction.TOWN);
        this.setActionType(ActionType.ActionRequire);
    }
}
