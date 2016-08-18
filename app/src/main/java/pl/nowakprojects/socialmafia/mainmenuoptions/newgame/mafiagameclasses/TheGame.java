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
	ArrayList<HumanPlayer> playersInfoList;
	// LISTA WSZYSTKICH WYBRANYCH RÓL (Z POWTÓRZENIAMI)
	//ArrayList<PlayerRole> currentGameRoles = new ArrayList<PlayerRole>();

	// STATYSTYKI GRY:
	int players = 0;
	int mafia = 0;
	int town = 0;
	int sindicate = 0;
	int doublers = 0;
	//int daytime = 0; // 0 means infintivie

	//boolean coquetteMEGA = true;


	public TheGame() {
	};



	/*
	 * Lista graczy i ich ról (do odkrywania i zakryawnia)
	 */


	/*
	 * Scena, generowanie liczby graczy i wproawdzanie ich imion
	 */


	public ArrayList<HumanPlayer> getPlayersInfoList() {
		return playersInfoList;
	}

	public void setPlayersInfoList(ArrayList<HumanPlayer> playersInfoList) {
		this.playersInfoList = playersInfoList;
	}
}