package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 20.06.2016.
 */
@Parcel
public class PlayerRole {

    public enum Fraction {TOWN, MAFIA, SYNDICATE, NEUTRAL, GROUP};
    public enum ActionType {OnlyZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction, MafiaAction, OnlyZeroNightAndActionRequire, Double };

    int name = 0;
    int description = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber;
    int rolePlayersAmount=0;
    boolean roleUsed = false;
    int lifes = 0;

    //PlayerActionViewHolder
    boolean b_isRoleTurn = false;
    HumanPlayer hp_lastChoosenPlayer;
    boolean b_actionMade = false;

    public PlayerRole(int name, int description, int iconResourceID, Fraction fraction, ActionType actionType, int nightWakeHierarchyNumber) {
        this.name = name;
        this.description = description;
        this.iconResourceID = iconResourceID;
        this.fraction = fraction;
        this.actionType = actionType;
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
        if(this.name == R.string.emo)
            lifes=2;
        else
            lifes=1;
    }


    // empty constructor needed by the Parceler library
    public PlayerRole(){

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


    //GETTERS AND SETTERS:
    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
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

    public boolean isRoleUsed() {
        return roleUsed;
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

    public boolean is_actionMade() {
        return b_actionMade;
    }

    public void set_bActionMade(boolean b_actionMade) {
        this.b_actionMade = b_actionMade;
    }

    public void setB_isRoleTurn(boolean b_isRoleTurn) {
        this.b_isRoleTurn = b_isRoleTurn;
    }

    public boolean isB_isRoleTurn() {
        return b_isRoleTurn;
    }


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
