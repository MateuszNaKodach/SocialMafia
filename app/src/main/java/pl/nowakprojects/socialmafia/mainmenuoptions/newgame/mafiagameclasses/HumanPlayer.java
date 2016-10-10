package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

@Parcel
public class HumanPlayer {
    //int playerIndex; // index gracza, dla ulatwienia indentyfikacji
    //int playerPoints=0; //punkty jakie gracz uzbieral w trakcie gier
    String playerName = ""; // imię gracza
    PlayerRole playerRole; // rola gracza
    boolean alive = true; // czy gracz jeszcze żyje
    ArrayList<HumanPlayer> guard = new ArrayList<>(); //ochroniarz, ktos kto ginie za danego gracza I OCHRONIARZ TEŻ!!!
    ArrayList<HumanPlayer> blackMailer = new ArrayList<>(); //szantazysta, nie moze na niego zaglosowac itp. CHYBA ZROBIC LISTE SZANTAZYSTÓW!!!
    ArrayList<HumanPlayer> lover = new ArrayList<>(); //kochanek - nie mozna na niego glosowac, jesli ginie jeden z nich - gina oboje I KOCHANKÓW TEŻ

    //For ShowingRoles:
    boolean wasRoleShowed = false;

    public HumanPlayer() {
    }

    public HumanPlayer(String playerName) {
        this.playerName = playerName;
    }

    public HumanPlayer(String playerName, PlayerRole playerRole) {
        this.playerName = playerName;
        this.playerRole = playerRole;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getRoleName() {
        return this.playerRole.getName();
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public boolean isWasRoleShowed() {
        return wasRoleShowed;
    }

    public void setWasRoleShowed(boolean wasRoleShowed) {
        this.wasRoleShowed = wasRoleShowed;
    }

    public void setGuard(HumanPlayer guard) {
        this.guard.add(guard);
    }

    public void setLover(HumanPlayer lover) {
        this.lover.add(lover);
    }

    public ArrayList<HumanPlayer> getLover() {
        return lover;
    }

    public ArrayList<HumanPlayer> getGuard() {
        return guard;
    }

    public ArrayList<HumanPlayer> getBlackMailer() {
        return blackMailer;
    }

    public void setBlackMailer(HumanPlayer blackMailer) {
        this.blackMailer.add(blackMailer);
    }

    public void killThePlayer(){
        alive = false;
    }

    public void reviveThePlayer(){
        alive = true;
    }
    public boolean isAlive() {
        return alive;
    }
    /*
	 * Widok roli, z przyciskiej odkryj/zakryj, do pokazania każdemu graczowi
	 */

	/*
	 * Lista graczy, z stanem, do GameAction
	 */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanPlayer)) return false;

        HumanPlayer that = (HumanPlayer) o;

        if (alive != that.alive) return false;
        if (!playerName.equals(that.playerName)) return false;
        return playerRole.equals(that.playerRole);

    }

    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + playerRole.hashCode();
        result = 31 * result + (alive ? 1 : 0);
        return result;
    }
}
