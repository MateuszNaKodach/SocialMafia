package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses;
/**
 * Created by Mateusz on 2016-02-20.
 */

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.TheGameActionActivity;
import pl.nowakprojects.socialmafia.utitles.GameRolesWakeHierarchyComparator;

/**
 * MLONG_MAX_DAY_TIME - ograniczenie czasowe na dzien, gracz i tak decyfuje czy konczy
 * teraz DODAC CZAS GRY ( BEDZIE POTRZEBNY DO SAVE!!!)
 * Parceler wymaga nieprywatnych pól!!!
 */

@Parcel
public class TheGame {

	public enum Daytime {DAY, NIGHT, JUDGEMENT};

	ArrayList<HumanPlayer> playersInfoList; 	// LISTA ZAPISANYCH GRACZY
	ArrayList<GameDaytime> mlistTheGameDaytimes;

	// LISTA WSZYSTKICH WYBRANYCH RÓL (Z POWTÓRZENIAMI)
	//ArrayList<PlayerRole> currentGameRoles = new ArrayList<PlayerRole>();

	// STATYSTYKI GRY:
	long MLONG_MAX_DAY_TIME = 180000; // czas dnia w milisekundach
	int I_MAX_DUELS_AMOUNT = 3; //maksymalna ilosc pojedynkow na dzien
	int I_MAX_DUELS_CHALLENGES = 10; //maksymalna ilosc  wyzwan na dzien

	//początkowe ustawienia
	int players = 0;
	int mafia = 0;
	int town = 0;
	int miSyndicateStartAmount = 0;
	int doublers = 0;

	//zrobic nowa zmienna, GameDay of the game, i dodawac na poczatku kazdego dnia, nocy, i beda dane konkretnego dnia i nocy!!!
	int miCurrentNightNumber = -1;
	int miCurrentDayNumber = 0;
	Daytime mdaytimeCurrentDaytime;

	boolean mbFinished = false; //czy gra została skończona
	//boolean coquetteMEGA = true;

	int i_actions_made_this_time = 0;

	//To wszystko muszą być listy, bo jest kilka możliwości!!!
	ArrayList<HumanPlayer> lastNightHealingByMedicPlayers;
	ArrayList<HumanPlayer> lastNightHeatingByDarkMedicPlayers;
	ArrayList<HumanPlayer> lastNightDealingByDealerPlayers;
	ArrayList<HumanPlayer> lastDayOperateByDentistPlayer;
	ArrayList<HumanPlayer> lastNightKilledPlayer;

	public TheGame() {
		lastNightHealingByMedicPlayers = new ArrayList<>();
		lastNightHeatingByDarkMedicPlayers = new ArrayList<>();
		lastNightDealingByDealerPlayers = new ArrayList<>();
		lastDayOperateByDentistPlayer = new ArrayList<>();
		lastNightKilledPlayer = new ArrayList<>();
	};

	public void clearMadeActionsCount(){

	}

	public void startNewNight(){
		miCurrentNightNumber++;
		this.mdaytimeCurrentDaytime=Daytime.NIGHT;
		mlistTheGameDaytimes.add(new GameNight(this,Daytime.NIGHT));
	}

	public void startNewDay(){
		miCurrentDayNumber++;
		this.mdaytimeCurrentDaytime=Daytime.DAY;
		mlistTheGameDaytimes.add(new GameDay(this,Daytime.DAY));
	}

	public ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if(humanPlayer.isAlive()) {
				if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNights))
					result.add(humanPlayer);
				if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNightsBesideZero))
					result.add(humanPlayer);
			}
		}
		Collections.sort(result,new GameRolesWakeHierarchyComparator());
		if(!result.isEmpty())
			result.get(0).getPlayerRole().setB_isRoleTurn(true);
		return result;
	}// private ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers()

	public ArrayList<HumanPlayer> getZeroNightHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNightAndActionRequire))
				result.add(humanPlayer);
			if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.OnlyZeroNight))
				result.add(humanPlayer);
		}
		Collections.sort(result,new GameRolesWakeHierarchyComparator());
		if(!result.isEmpty())
			result.get(0).getPlayerRole().setB_isRoleTurn(true);
		return result;
	}// private ArrayList<HumanPlayer> getZeroNightHumanPlayers()

	public ArrayList<HumanPlayer> getTownHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if (humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
				result.add(humanPlayer);
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()

	public ArrayList<HumanPlayer> getPlayersInfoList() {
		return playersInfoList;
	}

	public void addLastNightHealingByMedicPlayers(HumanPlayer humanPlayer){
		if(!lastNightHealingByMedicPlayers.contains(humanPlayer))
			lastNightHealingByMedicPlayers.add(humanPlayer);
	}

	public void addLastDayOperateByDentistPlayer(HumanPlayer humanPlayer){
		if(!lastDayOperateByDentistPlayer.contains(humanPlayer))
			lastDayOperateByDentistPlayer.add(humanPlayer);
	}

	public void addLastNightDealingByDealerPlayers(HumanPlayer humanPlayer){
		if(!lastNightDealingByDealerPlayers.contains(humanPlayer))
			lastNightDealingByDealerPlayers.add(humanPlayer);
	}

	public void addLastNightKilledPlayer(HumanPlayer humanPlayer){
		if(!lastNightKilledPlayer.contains(humanPlayer))
			lastNightKilledPlayer.add(humanPlayer);
	}

	public void addLastNightHeatingByDarkMedicPlayers(HumanPlayer humanPlayer){
		if(!lastNightHeatingByDarkMedicPlayers.contains(humanPlayer))
			lastNightHeatingByDarkMedicPlayers.add(humanPlayer);
	}

	public int iActionMadeThisTime(){
		// i_actions_made_this_time++;
		return ++i_actions_made_this_time;
	}

	public HumanPlayer findHumanPlayerByName(String playerName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(humanPlayer.getPlayerName().equals(playerName))
				return humanPlayer;

		return null;
	}


	public HumanPlayer findHumanPlayerByRoleName(String sRoleName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(humanPlayer.getPlayerName().equals(sRoleName))
				return humanPlayer;

		return null;
	}

	public List<HumanPlayer> getLiveHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : playersInfoList) {
			if (humanPlayer.isAlive())
				result.add(humanPlayer);
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()

	public List<HumanPlayer> getLiveMafiaPlayers(){
		return getLiveSelectedFractionPlayers(PlayerRole.Fraction.MAFIA);
	}

	public List<HumanPlayer> getLiveTownPlayers(){
		return getLiveSelectedFractionPlayers(PlayerRole.Fraction.TOWN);
	}

	public List<HumanPlayer> getLiveSyndicatePlayers(){
		return getLiveSelectedFractionPlayers(PlayerRole.Fraction.SYNDICATE);
	}

	private List<HumanPlayer> getLiveSelectedFractionPlayers(PlayerRole.Fraction fraction) {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : playersInfoList) {
			if (humanPlayer.getPlayerRole().isFractionRole(fraction))
				result.add(humanPlayer);
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()

	public boolean isNightDaytimeNow(){
		return isDaytimeNow(Daytime.NIGHT);
	}

	public boolean isDayDaytimeNow(){
		return isDaytimeNow(Daytime.DAY);
	}

	public boolean isSpecialZeroNightNow(){
		return isNightDaytimeNow()&&(this.getMiCurrentNightNumber()==0);
	}

	private boolean isDaytimeNow(Daytime daytime){
		return mdaytimeCurrentDaytime==daytime;
	}

	//GETTERS AND SETTERS-----------------------------------------------------------------------------

	public int iGetActionsMadeThisTime(){
		return i_actions_made_this_time;
	}


	public ArrayList<HumanPlayer> getLastDayOperateByDentistPlayers() {
		return lastDayOperateByDentistPlayer;
	}

	public ArrayList<HumanPlayer> getLastNightDealingByDealerPlayers() {
		return lastNightDealingByDealerPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightHealingByMedicPlayers() {
		return lastNightHealingByMedicPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightHeatingByDarkMedicPlayers() {
		return lastNightHeatingByDarkMedicPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightKilledPlayer() {
		return lastNightKilledPlayer;
	}

	public void setPlayersInfoList(ArrayList<HumanPlayer> playersInfoList) {
		this.playersInfoList = playersInfoList;
	}


	public long getMLONG_MAX_DAY_TIME() {
		return MLONG_MAX_DAY_TIME;
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

	public int getMiSyndicateStartAmount() {
		return miSyndicateStartAmount;
	}

	public int getDoublers() {
		return doublers;
	}

	public int getMiCurrentNightNumber() {
		return miCurrentNightNumber;
	}

	public int getMiCurrentDayNumber() {
		return miCurrentDayNumber;
	}

	public void setMLONG_MAX_DAY_TIME(long MLONG_MAX_DAY_TIME) {
		this.MLONG_MAX_DAY_TIME = MLONG_MAX_DAY_TIME;
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

	public void setMiSyndicateStartAmount(int miSyndicateStartAmount) {
		this.miSyndicateStartAmount = miSyndicateStartAmount;
	}

	public void setDoublers(int doublers) {
		this.doublers = doublers;
	}

	public void setMiCurrentNightNumber(int miCurrentNightNumber) {
		this.miCurrentNightNumber = miCurrentNightNumber;
	}

	public void setMiCurrentDayNumber(int miCurrentDayNumber) {
		this.miCurrentDayNumber = miCurrentDayNumber;
	}

	public boolean isMbFinished() {
		return mbFinished;
	}

}