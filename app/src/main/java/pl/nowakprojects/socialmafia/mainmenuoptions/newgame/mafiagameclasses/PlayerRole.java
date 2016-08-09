package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 20.06.2016.
 */
public class PlayerRole {

    Activity activity;

    public PlayerRole(int name, int description, int iconResourceID, Fraction fraction, ActionType actionType, int nightWakeHierarchyNumber) {
        this.name = name;
        this.description = description;
        this.iconResourceID = iconResourceID;
        this.fraction = fraction;
        this.actionType = actionType;
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
    }

    public enum Fraction {TOWN, MAFIA, SYNDICATE};
    public enum ActionType {ZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction };

    public int name = 0;
    int description = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber;

    public int rolePlayersAmount=0;

    public TextView roleAmount;






    public void increaseRoleAmount() {
        if (rolePlayersAmount < 1) //premierum version will make more than 1 possibilty
            rolePlayersAmount++;
    }

    public void decreaseRoleAmount(){
        if(rolePlayersAmount>0) //premierum version will make more than 1 possibilty
            rolePlayersAmount--;
    }


    public void updateRoleAmountTextView(){
        Integer amount = new Integer(this.rolePlayersAmount);
        roleAmount.setText(amount.toString());
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

}
