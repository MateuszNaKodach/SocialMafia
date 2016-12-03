package pl.nowakprojects.socialmafia.mafiagameclasses.roles;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;
import org.parceler.Transient;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia.*;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate.*;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.town.*;

/**
 * Created by Mateusz on 20.06.2016.
 */

@Parcel
public /*abstract*/ class PlayerRole {

    public enum Fraction {TOWN, MAFIA, SYNDICATE, NEUTRAL, GROUP, NOFRACTION};
    public enum ActionType {OnlyZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction, MafiaAction, OnlyZeroNightAndActionRequire, Double };

    int name = 0;
    int description = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber = -1; // -1 - nie budza sie w nocy
    int rolePlayersAmount=0;
    boolean onlyPremium=false;

    @Transient
    MaterialDialog roleDescriptionDialog;

    //PlayerActionViewHolder
    //boolean playerTurn = false;
    //HumanPlayer lastChoseenPlayer;
    //boolean roleActionMade = false;

    //fabryka abstrakcyjna!!!
    /*@ParcelProperty("roleName") public int roleName(){
        return name;
    };*/

    //@ParcelFactory
    public static PlayerRole makeRoleFromName(String roleName){
        return makeRoleFromNameId(Integer.valueOf(roleName));
    }

    public static PlayerRole makeRoleFromNameId(int roleNameId){
        switch(roleNameId){
            //TownRoles------------------------------------------------------------------------
            case R.string.citizen:
                return new Citizen();
            case R.string.madman:
                return new Madman();
            case R.string.armshop:
                return new Armshop();
            case R.string.policeman:
                return new Policeman();
            case R.string.prostitute:
                return new Prostitute();
            case R.string.medic:
                return new Medic();
            case R.string.townspeedy:
                return new TownSpeedy();
            case R.string.judge:
                return new Judge();
            case R.string.black:
                return new Blackman();
            case R.string.blackJudge:
                return new BlackJudge();
            case R.string.priest:
                return new Priest();
            case R.string.jew:
                return new Jew();
            case R.string.terrorist:
                return new Terrorist();
            case R.string.lawyer:
                return new Lawyer();
            case R.string.mayor:
                return new Mayor();
            case R.string.emo:
                return new Emo();
            //MafiaRoles------------------------------------------------------------------------
            case R.string.mafioso:
                return new Mafioso();
            case R.string.boss:
                return new Boss();
            case R.string.blackmailer:
                return new Blackmailer();
            case R.string.blackmailerBoss:
                return new BlackmailerBoss();
            case R.string.coquette:
                return new Coquette();
            case R.string.darkmedic:
                return new Darkmedic();
            case R.string.mafiaspeedy:
                return new MafiaSpeedy();
            case R.string.dealer:
                return new Dealer();
            case R.string.gravedigger:
                return new Gravedigger();
            case R.string.rapist:
                return new Rapist();
            case R.string.hitler:
                return new Hilter();
            //SyndicateRoles------------------------------------------------------------------------
            case R.string.syndicateBoss:
                return new SyndicateBoss();
            case R.string.deathAngel:
                return new DeathAngel();
            case R.string.diabolist:
                return new Diabolist();
            case R.string.saint:
                return new Saint();
            case R.string.syndicateSpeedy:
                return new SyndicateSpeedy();
            case R.string.conductor:
                return new Conductor();
            case R.string.bartender:
                return new Bartender();
            case R.string.timestopper:
                return new Timestopper();
            case R.string.hunter:
                return new Hunter();
            case R.string.dentist:
                return new Dentist();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PlayerRole getMultiPlayerRoleFromNameId(int roleNameId){
        switch(roleNameId){
            case R.string.mafiaKill:
                return new MafiaKilling();
            default:
                throw new IllegalArgumentException();
        }

    }


    // empty constructor needed by the Parceler library
    public PlayerRole(){
        //this.setNightWakeHierarchyNumber(-1);
        //this.onlyPremium=false;
    }

    public boolean isMafiaRole(){
        return fraction==Fraction.MAFIA;
    }

    public boolean isTownRole(){
        return fraction==Fraction.TOWN;
    }

    public boolean isSyndicateRoles(){
        return fraction==Fraction.SYNDICATE;
    }

    public boolean isFractionRole(Fraction checkedFraction){
        return fraction==checkedFraction;
    }

    public int getFractionNameStringID() {
        if(fraction.equals(Fraction.TOWN))
            return R.string.town;
        if(fraction.equals(Fraction.MAFIA))
            return R.string.mafia;
        if(fraction.equals(Fraction.SYNDICATE))
            return R.string.syndicate;

        return R.string.fraction;
    }

    protected void setOnlyPremium(){
        this.onlyPremium=true;
    }

    private void buildRoleDescriptionDialog(Context context){
        roleDescriptionDialog = new MaterialDialog.Builder(context)
                .title(context.getString(getName()))
                .content(context.getString(getDescription()))
                .positiveText(R.string.ok)
                .build();
    }

    public void showRoleDescriptionDialog(Context context){
        if(roleDescriptionDialog==null)
            buildRoleDescriptionDialog(context);

        roleDescriptionDialog.show();
    }

    public void action(Fragment fragment, HumanPlayer... players){
        Toast.makeText(fragment.getActivity().getApplicationContext(), "Jestesmy w PlayerRole", Toast.LENGTH_LONG).show();};

    //GETTERS AND SETTERS:

    public int getName() {
        return name;
    }

    protected void setName(int name) {
        this.name = name;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getIconResourceID() {
        return iconResourceID;
    }

    public void setIconResourceID(int iconResourceID) {
        this.iconResourceID = iconResourceID;
    }

    public Fraction getFraction() {
        return fraction;
    }


    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }


    public int getNightWakeHierarchyNumber() {
        return nightWakeHierarchyNumber;
    }

    public void setNightWakeHierarchyNumber(int nightWakeHierarchyNumber) {
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
    }


    public int getRolePlayersAmount() {
        return rolePlayersAmount;
    }

    public void setRolePlayersAmount(int rolePlayersAmount) {
        this.rolePlayersAmount = rolePlayersAmount;
    }

    public boolean isOrdinaryCitizenRole(){
        return this.name == R.string.citizen;
    }

    public boolean isOrdinaryMafiosoRole(){
        return this.name == R.string.mafioso;
    }

    public boolean isBasicMafiaRole(){
        return isOrdinaryCitizenRole()||isOrdinaryMafiosoRole();
    }

    /*public boolean is_actionMade() {
        return roleActionMade;
    }

    public void set_bActionMade(boolean b_actionMade) {
        this.roleActionMade = b_actionMade;
    }*/

    /*public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }*/


    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerRole)) return false;

        PlayerRole that = (PlayerRole) o;

        if (name != that.name) return false;
        if (description != that.description) return false;
        if (iconResourceID != that.iconResourceID) return false;
        return fraction == that.fraction;

    }


    @Override
    public int hashCode() {
        int result = name;
        result = 31 * result + description;
        result = 31 * result + iconResourceID;
        return result;
    }
}
