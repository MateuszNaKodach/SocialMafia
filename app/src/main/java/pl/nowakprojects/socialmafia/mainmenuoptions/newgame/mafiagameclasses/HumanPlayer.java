package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class HumanPlayer {
	int playerIndex; // index gracza, dla ulatwienia indentyfikacji
	int playerPoints=0; //punkty jakie gracz uzbieral w trakcie gier
	String playerName = ""; // imię gracza
	PlayerRole playerRole; // rola gracza
	boolean alive=true; // czy gracz jeszcze żyje
	HumanPlayer guard=null; //ochroniarz, ktos kto ginie za danego gracza

	public HumanPlayer(String playerName){
		this.playerName=playerName;
	}

	public String getPlayerName(){
		return this.playerName;
	}

	public String getRoleName(){
		return this.playerRole.toString();
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
