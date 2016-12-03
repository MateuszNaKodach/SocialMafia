package pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Created by Mateusz on 02.12.2016.
 */
@Parcel
public class MafiaKilling extends PlayerRole {

    public MafiaKilling(){
        super();
        setName(R.string.mafiaKill);
        setDescription(R.string.mafiaKillDescription);
        setIconResourceID(R.drawable.image_template);
        setFraction(Fraction.GROUP);
        setActionType(ActionType.AllNightsBesideZero);
        setNightWakeHierarchyNumber(81);
    }
}
