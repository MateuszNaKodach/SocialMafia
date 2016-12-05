package pl.nowakprojects.socialmafia.mafiagameclasses.roles;

import android.content.Context;

import com.annimon.stream.Stream;

import org.parceler.Transient;
import org.parceler.javaxinject.Singleton;

import java.util.ArrayList;

/**
 * Created by Mateusz on 08.08.2016.
 */

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia.MafiaKilling;

/**
 * Generowanie obiektów graczy dla RecyclerView wyboru, oraz pozniejszej gry
 */
@Singleton
public class PlayerRolesManager {

    public static PlayerRolesManager mInstance;

    @Transient
    Context mContext;

    ArrayList<PlayerRole> mMafiaRolesList;
    ArrayList<PlayerRole> mTownRolesList;
    ArrayList<PlayerRole> mSyndicateRolesList;


    public static PlayerRolesManager getInstance(Context context){
        if(mInstance == null)
            return new PlayerRolesManager(context);
        else {
            if(mInstance.mContext==null) {
                mInstance.mContext = context;
                mInstance.setContextForAllRoles();
            }
            return mInstance;
        }
    }

    private PlayerRolesManager(Context context){
        mContext=context;
    }

    private void setContextForRoles(ArrayList<PlayerRole> rolesList){
        Stream.of(rolesList).forEach(playerRole->playerRole.setContext(mContext));
    }

    public static void setContextForRoles(ArrayList<PlayerRole> rolesList, Context context){
        Stream.of(rolesList).forEach(playerRole->playerRole.setContext(context));
    }

    public static void setContextForPlayersRoles(ArrayList<HumanPlayer> playersList, Context context){
        Stream.of(playersList).forEach(player->player.setContext(context));
    }

    private void setContextForAllRoles(){
        setContextForRoles(mMafiaRolesList);
        setContextForRoles(mTownRolesList);
        setContextForRoles(mSyndicateRolesList);
    }

    public ArrayList<PlayerRole> getTownRolesList(){
       if(mTownRolesList==null)
           mTownRolesList = generateTownRolesList();

        return mTownRolesList;
    }


    public ArrayList<PlayerRole> getMafiaRolesList(){
        if(mMafiaRolesList==null)
            mMafiaRolesList = generateMafiaRolesList();

        return mMafiaRolesList;
    }

    public ArrayList<PlayerRole> getSyndicateRolesList(){
        if(mSyndicateRolesList==null)
            mSyndicateRolesList = generateSyndicateRolesList();

        return mSyndicateRolesList;
    }

    private ArrayList<PlayerRole> generateTownRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();

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

        setContextForRoles(playerRoles);
        return playerRoles;
    }

    private ArrayList<PlayerRole> generateMafiaRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();

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

        setContextForRoles(playerRoles);

        return playerRoles;
    }

    private ArrayList<PlayerRole> generateSyndicateRolesList(){
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

        setContextForRoles(playerRoles);

        return playerRoles;
    }

    //public static ArrayList<PlayerRole> getSyndicateRolesList(){
    //    return generateSyndicateRolesList();
   // }

    public ArrayList<PlayerRole> getNeutralRolesList(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
     //   playerRoles.add(new PlayerRole(R.string.roleDobule,R.string.doubleDescription,R.drawable.image_template, PlayerRole.Fraction.NEUTRAL, PlayerRole.ActionType.Double,40));
        return playerRoles;
    }

    /**
     * Roles that are NOT belong to one player, like mafia kill shot.
     * @return
     */
    public ArrayList<PlayerRole> getMultiPlayersRoles(){
        ArrayList<PlayerRole> playerRoles = new ArrayList<>();
        playerRoles.add(getMafiaKillRole());

        setContextForRoles(playerRoles);

        return playerRoles;
    }

    public PlayerRole getMafiaKillRole(){
        MafiaKilling mafiaKilling = new MafiaKilling();
        mafiaKilling.getMultiPlayerRoleFromNameId(R.string.mafiaKill);
        mafiaKilling.setContext(mContext);
        return mafiaKilling;
    }

}
