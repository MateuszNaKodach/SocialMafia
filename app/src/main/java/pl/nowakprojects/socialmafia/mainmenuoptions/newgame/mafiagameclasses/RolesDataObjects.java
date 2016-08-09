package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import java.util.ArrayList;

/**
 * Created by Mateusz on 08.08.2016.
 */

import pl.nowakprojects.socialmafia.R;

public class RolesDataObjects {

    public static ArrayList<PlayerRole> getTownRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        return playerRoles;
    }
}
