package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcel;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 20.06.2016.
 */
@Parcel
public class PlayerRole {

    public enum Fraction {TOWN, MAFIA, SYNDICATE};
    public enum ActionType {OnlyZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction, MafiaAction, OnlyZeroNightAndActionRequire };

    int name = 0;
    int description = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber;
    int rolePlayersAmount=0;
    boolean roleUsed = false;

    public PlayerRole(int name, int description, int iconResourceID, Fraction fraction, ActionType actionType, int nightWakeHierarchyNumber) {
        this.name = name;
        this.description = description;
        this.iconResourceID = iconResourceID;
        this.fraction = fraction;
        this.actionType = actionType;
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
    }

    // empty constructor needed by the Parceler library
    public PlayerRole(){

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

    //public String getFractionName() {
    //    switch(fraction){
    //    }
    //}

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

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
