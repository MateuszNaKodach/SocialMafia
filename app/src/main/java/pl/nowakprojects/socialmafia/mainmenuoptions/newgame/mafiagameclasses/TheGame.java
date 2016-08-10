package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;
/**
 * Created by Mateusz on 2016-02-20.
 */

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * daytime - ograniczenie czasowe na dzien, gracz i tak decyfuje czy konczy
 * teraz DODAC CZAS GRY ( BEDZIE POTRZEBNY DO SAVE!!!)
 */

@Parcel
public class TheGame {
	// LISTA ZAPISANYCH GRACZY
	ArrayList<HumanPlayer> playersInfoList = new ArrayList<HumanPlayer>();
	// LISTA WSZYSTKICH WYBRANYCH RÓL (Z POWTÓRZENIAMI)
	ArrayList<PlayerRole> currentGameRoles = new ArrayList<PlayerRole>();

	// STATYSTYKI GRY:
	int players = 0;
	int mafia = 0;
	int town = 0;
	int sindicate = 0;
	int doublers = 0;
	int daytime = 0; // 0 means infintivie

	// DODATKOWE USTAWIENIA:
	boolean sindicatON = false;
	boolean isBLACKequalsJUDGE = true;
	boolean isBOSSequalsBLACKMAILER = true;
	boolean coquetteMEGA = true;


	public TheGame() {
	};



	/*
	 * Lista graczy i ich ról (do odkrywania i zakryawnia)
	 */


	/*
	 * Scena, generowanie liczby graczy i wproawdzanie ich imion
	 */


	/*
	 * Automatyczne przydzielanie graczom ról
	 */
	public void connectRolesToPlayers() {
		Collections.shuffle(this.currentGameRoles); // pomieszanie roli, żeby
		// nie były według wybori
		// przydzielenie pomieszanych ról do graczy:
		for (int i = 0; i < this.currentGameRoles.size(); i++) {
			playersInfoList.get(i).playerRole = this.currentGameRoles.get(i);
		}
	}
}