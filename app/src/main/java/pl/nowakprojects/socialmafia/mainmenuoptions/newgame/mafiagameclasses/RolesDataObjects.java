package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import java.util.ArrayList;

/**
 * Created by Mateusz on 08.08.2016.
 */

import pl.nowakprojects.socialmafia.R;

/**
 * Generowanie obiektów graczy dla RecyclerView wyboru, oraz pozniejszej gry
 */
public class RolesDataObjects {

    public static ArrayList<PlayerRole> getTownRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.armshop,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.policeman,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.prostitute,R.string.madmanDescription,R.drawable.icon_prostitute, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.medic,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.townspeedy,R.string.madmanDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.judge,R.string.madmanDescription,R.drawable.icon_judge, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.black,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.blackJudge,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.priest,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.jew,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.terrorist,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.lawyer,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.mayor,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.emo,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));

        return playerRoles;
    }


    public static ArrayList<PlayerRole> getMafiaRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.boss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.blackmailer,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.blackmailerBoss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.coquette,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.darkmedic,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.mafiaspeedy,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.dealer,R.string.madmanDescription,R.drawable.icon_dealer, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.gravedigger,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.rapist,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));

        return playerRoles;
    }

    public static ArrayList<PlayerRole> getSyndicateRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.sindicateBoss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.deathAngel,R.string.madmanDescription,R.drawable.icon_deathangel, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.witch,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.saint,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.sindicateSpeedy,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.conductor,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.bartender,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.timestopper,R.string.madmanDescription,R.drawable.icon_timestopper, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.hunter,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.dentist,R.string.madmanDescription,R.drawable.icon_dentist, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));

        return playerRoles;
    }
}