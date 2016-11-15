/*package pl.nowakprojects.socialmafia.mafiagameclasses;

import pl.nowakprojects.socialmafia.R;


public class Fraction {
    private PlayerRole.Fraction type;
    private int iconID;

    public Fraction(){
    }

    public Fraction(PlayerRole.Fraction type) {
        this.type = type;
        iconID = fractionIconID();
    }


    public int fractionIconID() {
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

    public int getFractionNameId(){
        switch (this.type) {
            case TOWN:
                return R.string.town;
            case SYNDICATE:
                return R.string.syndicate;
            case MAFIA:
                return R.string.mafia;
        }
        return 0;
    }

    public PlayerRole.Fraction getType() {
        return type;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

}*/
