package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class MafiaSpeedy extends PlayerRole {

    public MafiaSpeedy(){
        super();
        setName(R.string.mafiaspeedy);
        setDescription(R.string.mafiaspeedyDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.MAFIA);
        setActionType(ActionType.ActionRequire);
    }
}
