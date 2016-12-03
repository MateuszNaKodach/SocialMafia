package pl.nowakprojects.socialmafia.mafiagameclasses;

import java.util.ArrayList;

/**
 * Created by Mateusz on 08.08.2016.
 */

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

/**
 * Generowanie obiektów graczy dla RecyclerView wyboru, oraz pozniejszej gry
 */
public class RolesDataObjects {

    public static ArrayList<PlayerRole> getTownRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
       /* int[] townRolesNamesIds = {
                R.string.citizen,
                R.string.armshop,
                R.string.policeman,
                R.string.prostitute
        };*/
        //Stream.of(Arrays.asList(townRolesNamesIds)).forEach(name -> playerRoles.add(PlayerRole.makeRoleFromNameId(name)));
        //pomyslec tutaj nad stream API!!!
        //playerRoles.add(new PlayerRole(R.string.citizen,R.string.citizenDescription,R.drawable.image_template, PlayerRole.Fraction.TOWN, PlayerRole.ActionType.NoAction,-1));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.citizen));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.armshop));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.policeman));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.prostitute));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.medic));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.townspeedy));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.judge));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.black));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.blackJudge));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.priest));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.jew));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.terrorist));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.lawyer));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.mayor));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.emo));

        return playerRoles;
    }


    public static ArrayList<PlayerRole> getMafiaRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        /*PlayerRole[] playerRolesArray = {
                PlayerRole.makeRoleFromNameId(R.string.mafioso),
                PlayerRole.makeRoleFromNameId(R.string.boss),
                PlayerRole.makeRoleFromNameId(R.string.blackmailer),
                PlayerRole.makeRoleFromNameId(R.string.blackmailerBoss),
                PlayerRole.makeRoleFromNameId(R.string.coquette),
                PlayerRole.makeRoleFromNameId(R.string.darkmedic),
                PlayerRole.makeRoleFromNameId(R.string.mafiaspeedy),
                PlayerRole.makeRoleFromNameId(R.string.dealer),
                PlayerRole.makeRoleFromNameId(R.string.gravedigger),
                PlayerRole.makeRoleFromNameId(R.string.rapist),
                PlayerRole.makeRoleFromNameId(R.string.hitler)
        };*/

        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.mafioso));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.boss));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.blackmailer));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.blackmailerBoss));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.coquette));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.darkmedic));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.mafiaspeedy));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.dealer));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.gravedigger));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.rapist));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.hitler));

        return playerRoles;
    }

    public static ArrayList<PlayerRole> getSyndicateRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.syndicateBoss)); //brak przydzielonego budzenia!!!
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.deathAngel));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.diabolist));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.saint));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.syndicateSpeedy));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.conductor));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.bartender));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.timestopper));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.hunter));
        playerRoles.add(PlayerRole.makeRoleFromNameId(R.string.dentist));
        return playerRoles;
    }

    //public static ArrayList<PlayerRole> getSyndicateRolesList(){
    //    return generateSyndicateRolesList();
   // }

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
        playerRoles.add(getMafiaKillRole());
        return playerRoles;
    }

    public static PlayerRole getMafiaKillRole(){
        return PlayerRole.getMultiPlayerRoleFromNameId(R.string.mafiaKill);
    }

}
