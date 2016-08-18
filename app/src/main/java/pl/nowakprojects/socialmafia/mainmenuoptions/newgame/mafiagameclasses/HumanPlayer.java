package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import org.parceler.Parcel;

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
	boolean alive=true; // czy gracz jeszcze żyje
	HumanPlayer guard=null; //ochroniarz, ktos kto ginie za danego gracza

	//For ShowingRoles:
	private boolean wasRoleShowed = false;

	public HumanPlayer(){}

	public HumanPlayer(String playerName){
		this.playerName=playerName;
	}
	public HumanPlayer(String playerName, PlayerRole playerRole){
		this.playerName=playerName;
		this.playerRole=playerRole;
	}

	public String getPlayerName(){
		return this.playerName;
	}

	public int getRoleName(){
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

	public boolean isAlive(){
		return alive;
	}
	/*
	 * Widok roli, z przyciskiej odkryj/zakryj, do pokazania każdemu graczowi
	 */

	/*
	 * Lista graczy, z stanem, do GameAction
	 */


}
