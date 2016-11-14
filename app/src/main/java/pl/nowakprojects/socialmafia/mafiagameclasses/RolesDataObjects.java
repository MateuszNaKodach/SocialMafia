package pl.nowakprojects.socialmafia.mafiagameclasses;

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
        playerRoles.add(new PlayerRole(R.string.citizen,R.string.citizenDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.NoAction,-1));
        playerRoles.add(new PlayerRole(R.string.madman,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnceAGame,-1));
        playerRoles.add(new PlayerRole(R.string.armshop,R.string.armshopDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.policeman,R.string.policemanDescription,R.drawable.icon_policeman, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.AllNightsBesideZero,50));
        playerRoles.add(new PlayerRole(R.string.prostitute,R.string.prostituteDescription,R.drawable.icon_prostitute, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnlyZeroNight,60));
        playerRoles.add(new PlayerRole(R.string.medic,R.string.medicDescription,R.drawable.icon_role_medic, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.AllNightsBesideZero,120));
        playerRoles.add(new PlayerRole(R.string.townspeedy,R.string.madmanDescription,R.drawable.ic_fastman, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.judge,R.string.judgeDescription,R.drawable.icon_judge, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.black,R.string.blackDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnlyZeroNightAndActionRequire,10));
        playerRoles.add(new PlayerRole(R.string.blackJudge,R.string.blackJudgeDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnlyZeroNightAndActionRequire,20));
        playerRoles.add(new PlayerRole(R.string.priest,R.string.priestDescription,R.drawable.ic_priest, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.OnlyZeroNight,160));
      //  playerRoles.add(new PlayerRole(R.string.parson,R.string.priestParsonDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.AllNights,180));
      //  playerRoles.add(new PlayerRole(R.string.priestParson,R.string.priestParsonDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.AllNights,170));
        playerRoles.add(new PlayerRole(R.string.jew,R.string.jewDescription,R.drawable.ic_jew, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.terrorist,R.string.terroristDescription,R.drawable.icon_terrorist, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.lawyer,R.string.lawyerDescription,R.drawable.ic_lawyer, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.mayor,R.string.mayorDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.emo,R.string.emoDescription,R.drawable.ic_emo, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.ActionRequire,-1));

        return playerRoles;
    }


    public static ArrayList<PlayerRole> getMafiaRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.mafioso,R.string.mafiosoDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.NoAction,-1));
        playerRoles.add(new PlayerRole(R.string.boss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.NoAction,90));
        playerRoles.add(new PlayerRole(R.string.blackmailer,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.OnlyZeroNight,80));
        playerRoles.add(new PlayerRole(R.string.blackmailerBoss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.OnlyZeroNight,100));
        playerRoles.add(new PlayerRole(R.string.coquette,R.string.madmanDescription,R.drawable.icon_prostitute2, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.NoAction,-1));
        playerRoles.add(new PlayerRole(R.string.darkmedic,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.AllNightsBesideZero,120));
        playerRoles.add(new PlayerRole(R.string.mafiaspeedy,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.dealer,R.string.madmanDescription,R.drawable.icon_dealer, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.AllNightsBesideZero,30));
        playerRoles.add(new PlayerRole(R.string.gravedigger,R.string.madmanDescription,R.drawable.ic_gravedigger, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.AllNightsBesideZero,70));
        playerRoles.add(new PlayerRole(R.string.rapist,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.OnlyZeroNight,130));
        playerRoles.add(new PlayerRole(R.string.hitler,R.string.hitlerDescription,R.drawable.image_template, PlayerRole.Fraction.MAFIA, PlayerRole.ActionType.OnlyZeroNight,150));

        return playerRoles;
    }

    public static ArrayList<PlayerRole> getSyndicateRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.sindicateBoss,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.OnlyZeroNight,-1));
        playerRoles.add(new PlayerRole(R.string.deathAngel,R.string.madmanDescription,R.drawable.icon_deathangel, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.AllNightsBesideZero,140));
        playerRoles.add(new PlayerRole(R.string.witch,R.string.madmanDescription,R.drawable.ic_diabolist, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.AllNightsBesideZero,190));
        playerRoles.add(new PlayerRole(R.string.saint,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.sindicateSpeedy,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.conductor,R.string.madmanDescription,R.drawable.icon_conductor, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.bartender,R.string.madmanDescription,R.drawable.ic_bartender, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.OnceAGame,110));
        playerRoles.add(new PlayerRole(R.string.timestopper,R.string.madmanDescription,R.drawable.icon_timestopper, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.hunter,R.string.madmanDescription,R.drawable.image_template, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.ActionRequire,-1));
        playerRoles.add(new PlayerRole(R.string.dentist,R.string.madmanDescription,R.drawable.icon_dentist, PlayerRole.Fraction.SYNDICATE, PlayerRole.ActionType.AllNightsBesideZero,-1));

        return playerRoles;
    }

    public static ArrayList<PlayerRole> getNeutralRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
     //   playerRoles.add(new PlayerRole(R.string.roleDobule,R.string.doubleDescription,R.drawable.image_template, PlayerRole.Fraction.NEUTRAL, PlayerRole.ActionType.Double,40));
        return playerRoles;
    }

    /**
     * Roles that are NOT belong to one player, like mafia kill shot.
     * @return
     */
    public static  ArrayList<PlayerRole> getMultiPlayersRoles(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(new PlayerRole(R.string.mafiaKill,R.string.mafiaKillDescription,R.drawable.image_template, PlayerRole.Fraction.GROUP, PlayerRole.ActionType.AllNightsBesideZero,81));
        return playerRoles;
    }
}
