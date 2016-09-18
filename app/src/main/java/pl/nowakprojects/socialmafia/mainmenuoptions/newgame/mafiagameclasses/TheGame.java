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

	public enum DayTime {DAY, NIGHT, JUDGEMENT};

	ArrayList<HumanPlayer> playersInfoList; 	// LISTA ZAPISANYCH GRACZY

	// LISTA WSZYSTKICH WYBRANYCH RÓL (Z POWTÓRZENIAMI)
	//ArrayList<PlayerRole> currentGameRoles = new ArrayList<PlayerRole>();

	// STATYSTYKI GRY:
	long daytime = 180000; // czas dnia w milisekundach

	int players = 0;
	int mafia = 0;
	int town = 0;
	int sindicate = 0;
	int doublers = 0;

	int nightNumber = 0;
	int dayNumber = 1;

	boolean isFinished = false; //czy gra została skończona
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

	public long getDaytime() {
		return daytime;
	}

	public int getPlayers() {
		return players;
	}

	public int getMafia() {
		return mafia;
	}

	public int getTown() {
		return town;
	}

	public int getSindicate() {
		return sindicate;
	}

	public int getDoublers() {
		return doublers;
	}

	public int getNightNumber() {
		return nightNumber;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDaytime(long daytime) {
		this.daytime = daytime;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public void setMafia(int mafia) {
		this.mafia = mafia;
	}

	public void setTown(int town) {
		this.town = town;
	}

	public void setSindicate(int sindicate) {
		this.sindicate = sindicate;
	}

	public void setDoublers(int doublers) {
		this.doublers = doublers;
	}

	public void setNightNumber(int nightNumber) {
		this.nightNumber = nightNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public HumanPlayer findHumanPlayerByName(String playerName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(humanPlayer.getPlayerName().equals(playerName))
				return humanPlayer;

		return null;
	}
}