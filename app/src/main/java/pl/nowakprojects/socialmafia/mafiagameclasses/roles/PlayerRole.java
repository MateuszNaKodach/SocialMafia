package pl.nowakprojects.socialmafia.mafiagameclasses.roles;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.ParcelProperty;
import org.parceler.Transient;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.mafia.*;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.syndicate.*;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.town.*;
import com.google.auto.value.AutoValue;
/**
 * Created by Mateusz on 20.06.2016.
 */

@AutoValue
@Parcel
public abstract class PlayerRole {

    @Transient
    protected Context mContext;


    public enum Fraction {TOWN, MAFIA, SYNDICATE, NEUTRAL, GROUP, NOFRACTION};
    public enum ActionType {OnlyZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction, MafiaAction, OnlyZeroNightAndActionRequire, Double };

    String name;
    int nameId = 0;
    int descriptionId = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber = -1; // -1 - nie budza sie w nocy
    boolean roleUsed = false; //for OneAction roles

    int rolePlayersAmount=0;
    boolean onlyPremium=false;

    @Transient
    private MaterialDialog roleDescriptionDialog;

    //fabryka abstrakcyjna!!!
    @ParcelProperty("roleNameId") public int roleNameId(){
        return nameId;
    };

    protected PlayerRole(Context context){
        setContext(context);
        setName(context.getString(nameId));
    }


    @ParcelFactory
    static PlayerRole makeRoleFromNameId(int roleNameId){
        switch(roleNameId){
            //ustawic kontekst w kazdej klasie dziedziczacej
            //ustawic kontekt setterem w PlayerRolesMaganer
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

    protected static PlayerRole getMultiPlayerRoleFromNameId(int roleNameId){
        switch(roleNameId){
            case R.string.mafiaKill:
                return new MafiaKilling();
            default:
                throw new IllegalArgumentException();
        }

    }


    // empty constructor needed by the Parceler library
    protected PlayerRole(){

    }



    public boolean isMafiaRole(){
        return isFractionRole(Fraction.MAFIA);
    }

    public boolean isTownRole(){
        return isFractionRole(Fraction.TOWN);
    }

    public boolean isSyndicateRoles(){
        return isFractionRole(Fraction.SYNDICATE);
    }

    public boolean isFractionRole(Fraction checkedFraction){
        return fraction==checkedFraction;
    }


    public boolean isBasicRole(){
        return isOrdinaryCitizenRole()||isOrdinaryMafiosoRole();
    }

    private boolean isOrdinaryCitizenRole(){
        return getNameId() == R.string.citizen;
    }

    private boolean isOrdinaryMafiosoRole(){
        return getNameId() == R.string.mafioso;
    }


    protected void showDealedRoleToast(){
        Toast.makeText(mContext.getApplicationContext(), mContext.getString(R.string.dealExplanation), Toast.LENGTH_LONG).show();
    }

    public void showRoleDescriptionDialog(){
        if(roleDescriptionDialog==null)
            buildRoleDescriptionDialog(mContext);

        roleDescriptionDialog.show();
    }

    private void buildRoleDescriptionDialog(Context context){
        roleDescriptionDialog = new MaterialDialog.Builder(context)
                .title(context.getString(getNameId()))
                .content(context.getString(getDescriptionId()))
                .positiveText(R.string.ok)
                .build();
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

    public void setContext(Context context) {
        mContext=context;
        if(mContext!=null)
            setName(mContext.getString(getNameId()));
    }

    protected void setOnlyPremium(){
        this.onlyPremium=true;
    }

    //NORMALLY GETTERS AND SETTERS:
    public int getNameId() {
        return nameId;
    }

    protected void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnlyPremium() {
        return onlyPremium;
    }

    public void setOnlyPremium(boolean onlyPremium) {
        this.onlyPremium = onlyPremium;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isRoleUsed() {
        return roleUsed;
    }

    public void setRoleUsed(boolean roleUsed) {
        this.roleUsed = roleUsed;
    }



    //OBJECT METHODS OVERRIDE:
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerRole)) return false;

        PlayerRole that = (PlayerRole) o;

        if (nameId != that.nameId) return false;
        if (descriptionId != that.descriptionId) return false;
        if (iconResourceID != that.iconResourceID) return false;
        return fraction == that.fraction;

    }

    @Override
    public int hashCode() {
        int result = nameId;
        result = 31 * result + descriptionId;
        result = 31 * result + iconResourceID;
        return result;
    }

}
