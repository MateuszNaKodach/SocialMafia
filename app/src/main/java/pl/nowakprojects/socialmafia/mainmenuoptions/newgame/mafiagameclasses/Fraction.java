package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 20.06.2016.
 */
public class Fraction {
    private PlayerRole.Fraction type;
    private int icon;
    private ArrayList<PlayerRole> fractionRoles = new ArrayList<PlayerRole>();

    public Fraction(PlayerRole.Fraction type) {
        this.type = type;
        icon = fractionToDrawable();
        createFractionRoles();
    }

    public void createFractionRoles(){
            switch(this.type){
                case TOWN:{
                    //fractionRoles.add(new PlayerRole());
                    //fractionRoles.add(new PlayerRole());
                }
            }
    }

    public int fractionToDrawable() {
        switch (this.type) {
            case TOWN:
                return R.drawable.icon_fractiontown;
            case SYNDICATE:
                return R.drawable.icon_fractionsyndicate;
            case MAFIA:
                return R.drawable.icon_fractionmafia;
        }
        return 0;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public String getFractionName(){
        return type.toString();
    }

    public PlayerRole.Fraction getType() {
        return type;
    }

    public void setType(PlayerRole.Fraction type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ArrayList<PlayerRole> getFractionRoles() {
        return fractionRoles;
    }

    public void setFractionRoles(ArrayList<PlayerRole> fractionRoles) {
        this.fractionRoles = fractionRoles;
    }
}
