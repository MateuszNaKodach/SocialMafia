package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class Mafioso extends PlayerRole {

    public Mafioso(){
        super();
        this.setNameId(R.string.mafioso);
        this.setDescriptionId(R.string.mafiosoDescription);
        this.setIconResourceID(R.drawable.image_template);
        this.setFraction(Fraction.MAFIA);
        this.setActionType(ActionType.NoAction);
    }
}
