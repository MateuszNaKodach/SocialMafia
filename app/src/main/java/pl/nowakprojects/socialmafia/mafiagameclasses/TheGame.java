package pl.nowakprojects.socialmafia.mafiagameclasses;
/**
 * Created by Mateusz on 2016-02-20.
 */

import android.content.Context;

import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.utitles.GameRolesWakeHierarchyComparator;

/**
 * maxDailyTime - ograniczenie czasowe na dzien, gracz i tak decyfuje czy konczy
 * teraz DODAC CZAS GRY ( BEDZIE POTRZEBNY DO SAVE!!!)
 * Parceler wymaga nieprywatnych pól!!!
 */

@Parcel
public class TheGame {

	static TheGame instance;

	@Transient
	Context context;

	int gameId = 0;


	public enum Daytime {DAY, NIGHT, JUDGEMENT}


	ArrayList<HumanPlayer> playersInfoList; 	// LISTA ZAPISANYCH GRACZY

	//ArrayList<GameDaytime> mlistTheGameDaytimes = new ArrayList<>();

	// LISTA WSZYSTKICH WYBRANYCH RÓL (Z POWTÓRZENIAMI)
	//ArrayList<PlayerRole> currentGameRoles = new ArrayList<PlayerRole>();

	// STATYSTYKI GRY:
	long maxDailyTime = 180000; // czas dnia w milisekundach
	int maxDailyDuelAmount = 3; //maksymalna ilosc pojedynkow na dzien
	int maxDailyDuelChallenges = 10; //maksymalna ilosc  wyzwan na dzien
	//boolean mbFinished = false; //czy gra została skończona

	//początkowe ustawienia
	int playersStartAmount = 0;
	int mafiaStartAmount = 0;
	int townStartAmount = 0;
	int syndicateStartAmount = 0;
	int doublersStartAmount = 0;

	//Ogolne zmienne do gry:
	int currentNightNumber = -1;
	int currentDayNumber = 0;


	//Zmienne do aktualnego Daytime
	Daytime currentDaytime;
	DailyVotingFragment.OUTVOTED currentDayOutVoted;
	int currentDaytimeMadeRoleActions = 0;
	ArrayList<HumanPlayer> temporaryLastTimeKilledPlayersList;
	public ArrayList<HumanPlayer> choseDailyJudgmentPlayersList;


	//Zmienne do aktualnej nocy
	ArrayList<HumanPlayer> lastNightHealingByMedicPlayers;
	ArrayList<HumanPlayer> lastNightHeatingByDarkMedicPlayers;
	ArrayList<HumanPlayer> lastNightDealingByDealerPlayers;
	ArrayList<HumanPlayer> lastNightHittingByMafiaPlayer;
	ArrayList<HumanPlayer> lastNightKilledPlayer;

	//Zmienne do aktualnego dnia
	ArrayList<HumanPlayer> lastDayOperateByDentistPlayer;
	int miThisDayRemainedDuels= maxDailyDuelAmount;
	int miThisDayThrownChallenges=0;

	public TheGame() {
		lastNightHealingByMedicPlayers = new ArrayList<>();
		lastNightHeatingByDarkMedicPlayers = new ArrayList<>();
		lastNightHittingByMafiaPlayer = new ArrayList<>();
		lastNightDealingByDealerPlayers = new ArrayList<>();
		lastDayOperateByDentistPlayer = new ArrayList<>();
		lastNightKilledPlayer = new ArrayList<>();
		temporaryLastTimeKilledPlayersList = new ArrayList<>();
		choseDailyJudgmentPlayersList = new ArrayList<>();
	}

	public TheGame(Context context) {
		this.context = context;
		lastNightHealingByMedicPlayers = new ArrayList<>();
		lastNightHeatingByDarkMedicPlayers = new ArrayList<>();
		lastNightHittingByMafiaPlayer = new ArrayList<>();
		lastNightDealingByDealerPlayers = new ArrayList<>();
		lastDayOperateByDentistPlayer = new ArrayList<>();
		lastNightKilledPlayer = new ArrayList<>();
		temporaryLastTimeKilledPlayersList = new ArrayList<>();
		choseDailyJudgmentPlayersList = new ArrayList<>();
	}

	public void setContext(Context context){
		this.context = context;
	}

	public boolean isGameFinished(){
		return calculateWinner()!= PlayerRole.Fraction.NOFRACTION;
	}

	public boolean isFirstDay() { return this.currentDayNumber ==0;}

	private PlayerRole.Fraction calculateWinner(){
			//if(gameWithoutSyndicate())
				if(getLiveMafiaPlayersAmount()>=getLiveTownPlayersAmount())
					return PlayerRole.Fraction.MAFIA;
				else if(getLiveMafiaPlayersAmount()==0&&getLiveTownPlayersAmount()>0)
					return PlayerRole.Fraction.TOWN;

				return PlayerRole.Fraction.NOFRACTION;
	}

	public boolean gameWithoutSyndicate(){
		return syndicateStartAmount ==0;
	}

	public void beginKilling(){
		if(temporaryLastTimeKilledPlayersList !=null)
			temporaryLastTimeKilledPlayersList.clear();
	}

	public boolean isKillingJudgment(){
		return currentDayOutVoted == DailyVotingFragment.OUTVOTED.KILLING;
	}

	public boolean isCheckingJudgment(){
		return currentDayOutVoted == DailyVotingFragment.OUTVOTED.CHECKING;
	}

	//przeiwdziec jak murzyn murzyni murzyna!
	//dodac wybór łowcy
	public void kill(HumanPlayer humanPlayer){
		if(humanPlayer.isAlive()) {
			if (humanPlayer.hasGuard())
				kill(humanPlayer.getGuardToKill()); //to przejdzie do zabicia odpowiedniego gracza
			else {
					humanPlayer.hit(); //dostaje hita, jak jest emo to nie ginie, sprawdamy czy nie byl emo, czyli czy zginal

				if(humanPlayer.isDead()) {
					//beginKilling();
					temporaryLastTimeKilledPlayersList.add(humanPlayer); //dodawanie do licy ostatnio zabitych
					if (humanPlayer.hasLover()) {
						for (HumanPlayer hp : humanPlayer.getAliveLoversList())
							kill(hp); //zabija wszystkich kochanków
					}//if (humanPlayer.hasLover())

					if (humanPlayer.hasTerroristRole())
						kill(findPreviousPlayerTo(humanPlayer));
				}//if(!humanPlayer.isAlive())
			}//else
		}//if(humanPlayer.isAlive())
	}//public static void kill(HumanPlayer humanPlayer)

	//Co zrobic jak np. dwaj lekarze leczyli tego gracza!?
	public ArrayList<HumanPlayer> calculateNightimeResult(){
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for(HumanPlayer hp: getLastNightHittingByMafiaPlayer())
			if(wasPlayerHitByDarkMedic(hp))
				result.add(hp);
			else if(!wasPlayerHealed(hp))
				result.add(hp);

		for(HumanPlayer hp: result)
			kill(hp);

		return result;
	}

	private boolean wasPlayerHealed(HumanPlayer humanPlayer){
			for(HumanPlayer hp2: getLastNightHealingByMedicPlayers())
				if(humanPlayer==hp2)
					return true;

		return false;
	}

	private boolean wasPlayerHitByDarkMedic(HumanPlayer humanPlayer){
		for(HumanPlayer hp2: getLastNightHeatingByDarkMedicPlayers())
			if(humanPlayer==hp2)
				return true;

		return false;
	}

	public HumanPlayer getLastKilledPlayer(){
		if(temporaryLastTimeKilledPlayersList.isEmpty())
			return null;
		else
			return temporaryLastTimeKilledPlayersList.get(temporaryLastTimeKilledPlayersList.size()-1);
	}

	public HumanPlayer findPreviousPlayerTo(HumanPlayer humanPlayer){
		for(int i=playersInfoList.indexOf(humanPlayer)-1; i>=0;i--)
			if(playersInfoList.get(i).isAlive())
				return playersInfoList.get(i);

		for(int i=playersInfoList.size()-1; i>playersInfoList.indexOf(humanPlayer);i--)
			if(playersInfoList.get(i).isAlive())
				return playersInfoList.get(i);

		return null;
	}

	public void clearMadeActionsCount(){

	}

	public ArrayList<HumanPlayer> getTemporaryLastTimeKilledPlayersList() {
		return temporaryLastTimeKilledPlayersList;
	}

	public String lastTimeKilledPlayersString(){
		String result="";
		for(HumanPlayer hp: this.getTemporaryLastTimeKilledPlayersList())
			result+=""+hp.getPlayerName()+", ";
		return result;
	}

	public void startNewNight(){
		clearMadeActions();
		lastNightHealingByMedicPlayers.clear();
		lastNightHeatingByDarkMedicPlayers.clear();
		lastNightHittingByMafiaPlayer.clear();
		//undealed all players
		for(HumanPlayer dealed: lastNightDealingByDealerPlayers)
			dealed.setDealed(false);

		lastNightDealingByDealerPlayers.clear();
		lastNightKilledPlayer.clear();
		currentDaytimeMadeRoleActions = 0;
		currentNightNumber++;
		this.currentDaytime =Daytime.NIGHT;
		//mlistTheGameDaytimes.add(new GameNight(this,Daytime.NIGHT));
	}

	public void startNewDay(){
		miThisDayRemainedDuels= maxDailyDuelAmount;
		miThisDayThrownChallenges=0;
		lastDayOperateByDentistPlayer.clear();
		choseDailyJudgmentPlayersList.clear();
		currentDaytimeMadeRoleActions = 0;
		currentDayOutVoted =null;
		currentDayNumber++;
		this.currentDaytime =Daytime.DAY;
		//mlistTheGameDaytimes.add(new GameDay(this,Daytime.DAY));
	}

	public void clearMadeActions(){
		for(HumanPlayer hp: this.getLiveHumanPlayers()){
			hp.playerTurn =false;
			hp.roleActionMade =false;
		}

	}

	public boolean isJudgeInTheGameSettings(){
		HumanPlayer hp1 = findHumanPlayerByRoleName(context.getString(R.string.judge));
		HumanPlayer hp2 = findHumanPlayerByRoleName(context.getString(R.string.blackJudge));
		return (hp1!=null || hp2!=null);
	}

	public HumanPlayer getJudgePlayer(){
		HumanPlayer hp1 = findLiveHumanPlayerByRoleName(context.getString(R.string.judge));
		HumanPlayer hp2 = findLiveHumanPlayerByRoleName(context.getString(R.string.blackJudge));

		return hp1!=null ? hp1 : hp2;

	}

	public ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if(humanPlayer.isAlive()||!humanPlayer.isAlive()) {
				if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNights))
					result.add(humanPlayer);
				if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNightsBesideZero))
					result.add(humanPlayer);
			}
		}

		if(isMafiaInTheGame())
			result.add(new HumanPlayer(context.getString(R.string.mafiaKill), RolesDataObjects.getMafiaKillRole()));

		Collections.sort(result,new GameRolesWakeHierarchyComparator());
		if(!result.isEmpty())
			result.get(0).setPlayerTurn(true);
		return result;
	}// private ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers()

	public ArrayList<HumanPlayer> getThisNightHumanPlayers(){
		if(currentNightNumber ==0)
			return getZeroNightHumanPlayers();
		else
			return getAllNightsBesideZeroHumanPlayers();
	}

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
			result.get(0).setPlayerTurn(true);
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

	public void addLastNightHittingByMafiaPlayer(HumanPlayer humanPlayer){
		if(!lastNightHittingByMafiaPlayer.contains(humanPlayer))
			lastNightHittingByMafiaPlayer.add(humanPlayer);
	}

	public ArrayList<HumanPlayer> getLastDayOperateByDentistPlayer() {
		return lastDayOperateByDentistPlayer;
	}

	public ArrayList<HumanPlayer> getLastNightHittingByMafiaPlayer() {
		return lastNightHittingByMafiaPlayer;
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
		// miDaytimeRolesActionsMadeThis++;
		return ++currentDaytimeMadeRoleActions;
	}

	public HumanPlayer findHumanPlayerByName(String playerName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(humanPlayer.getPlayerName().equals(playerName))
				return humanPlayer;

		return null;
	}

	public HumanPlayer findLiveHumanPlayerByName(String playerName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(humanPlayer.getPlayerName().equals(playerName)&&humanPlayer.isAlive())
				return humanPlayer;

		return null;
	}


	public HumanPlayer findHumanPlayerByRoleName(String sRoleName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(context.getString(humanPlayer.getRoleName()).equals(sRoleName))
				return humanPlayer;

		return null;
	}

	public HumanPlayer findLiveHumanPlayerByRoleName(String sRoleName){
		for(HumanPlayer humanPlayer: playersInfoList)
			if(context.getString(humanPlayer.getRoleName()).equals(sRoleName)&&humanPlayer.isAlive())
				return humanPlayer;

		return null;
	}

	public List<String> getLiveHumanPlayersNames() {
		ArrayList<String> result = new ArrayList<>();
		for (HumanPlayer humanPlayer : playersInfoList) {
			if (humanPlayer.isAlive())
				result.add(humanPlayer.getPlayerName());
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()


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

	public int getLiveMafiaPlayersAmount(){
		return getLiveMafiaPlayers().size();
	}

	public int getLiveTownPlayersAmount(){
		return getLiveTownPlayers().size();
	}

	public int getLiveSyndicatePlayersAmount(){
		return getLiveSyndicatePlayers().size();
	}


	private List<HumanPlayer> getLiveSelectedFractionPlayers(PlayerRole.Fraction fraction) {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : playersInfoList) {
			if (humanPlayer.getPlayerRole().isFractionRole(fraction)&&humanPlayer.isAlive())
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
		return isNightDaytimeNow()&&(this.getCurrentNightNumber()==0);
	}

	private boolean isDaytimeNow(Daytime daytime){
		return currentDaytime ==daytime;
	}

	public boolean isMafiaBossAlive() {
		HumanPlayer mafiaboss = findLiveHumanPlayerByRoleName(context.getString(R.string.boss));
		HumanPlayer mafiaboss2 = findLiveHumanPlayerByRoleName(context.getString(R.string.blackmailerBoss));

		return mafiaboss!=null|| mafiaboss2!=null;
	}

	//GETTERS AND SETTERS-----------------------------------------------------------------------------

	public int iGetActionsMadeThisTime(){
		return currentDaytimeMadeRoleActions;
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


	public long getMaxDailyTime() {
		return maxDailyTime;
	}

	public int getPlayersStartAmount() {
		return playersStartAmount;
	}

	public int getMafiaStartAmount() {
		return mafiaStartAmount;
	}

	public int getTownStartAmount() {
		return townStartAmount;
	}

	public int getSyndicateStartAmount() {
		return syndicateStartAmount;
	}


	public boolean isMafiaInTheGame(){
		for(HumanPlayer hp: getLiveHumanPlayers())
			if(hp.getPlayerRole().getFraction() == PlayerRole.Fraction.MAFIA)
				return true;

		return false;
	}
	public int getDoublersStartAmount() {
		return doublersStartAmount;
	}

	public int getCurrentNightNumber() {
		return currentNightNumber;
	}

	public int getCurrentDayNumber() {
		return currentDayNumber;
	}

	public void setMaxDailyTime(long maxDailyTime) {
		this.maxDailyTime = maxDailyTime;
	}

	public void setPlayersStartAmount(int playersStartAmount) {
		this.playersStartAmount = playersStartAmount;
	}

	public void setMafiaStartAmount(int mafiaStartAmount) {
		this.mafiaStartAmount = mafiaStartAmount;
	}

	public void setTownStartAmount(int townStartAmount) {
		this.townStartAmount = townStartAmount;
	}

	public void setSyndicateStartAmount(int syndicateStartAmount) {
		this.syndicateStartAmount = syndicateStartAmount;
	}

	public void setDoublersStartAmount(int doublersStartAmount) {
		this.doublersStartAmount = doublersStartAmount;
	}

	public void setCurrentNightNumber(int currentNightNumber) {
		this.currentNightNumber = currentNightNumber;
	}

	public void setCurrentDayNumber(int currentDayNumber) {
		this.currentDayNumber = currentDayNumber;
	}

	public int getMaxDailyDuelAmount() {
		return maxDailyDuelAmount;
	}

	public void setMaxDailyDuelAmount(int maxDailyDuelAmount) {
		this.maxDailyDuelAmount = maxDailyDuelAmount;
	}

	public int getMaxDailyDuelChallenges() {
		return maxDailyDuelChallenges;
	}

	public void setMaxDailyDuelChallenges(int maxDailyDuelChallenges) {
		this.maxDailyDuelChallenges = maxDailyDuelChallenges;
	}

	public Daytime getCurrentDaytime() {
		return currentDaytime;
	}

	public void setCurrentDaytime(Daytime currentDaytime) {
		this.currentDaytime = currentDaytime;
	}

	public DailyVotingFragment.OUTVOTED getCurrentDayOutVoted() {
		return currentDayOutVoted;
	}

	public void setCurrentDayOutVoted(DailyVotingFragment.OUTVOTED currentDayOutVoted) {
		this.currentDayOutVoted = currentDayOutVoted;
	}

	public int getCurrentDaytimeMadeRoleActions() {
		return currentDaytimeMadeRoleActions;
	}

	public void setCurrentDaytimeMadeRoleActions(int currentDaytimeMadeRoleActions) {
		this.currentDaytimeMadeRoleActions = currentDaytimeMadeRoleActions;
	}

	public void setTemporaryLastTimeKilledPlayersList(ArrayList<HumanPlayer> temporaryLastTimeKilledPlayersList) {
		this.temporaryLastTimeKilledPlayersList = temporaryLastTimeKilledPlayersList;
	}

	public ArrayList<HumanPlayer> getChoseDailyJudgmentPlayersList() {
		return choseDailyJudgmentPlayersList;
	}

	public void setChoseDailyJudgmentPlayersList(ArrayList<HumanPlayer> choseDailyJudgmentPlayersList) {
		this.choseDailyJudgmentPlayersList = choseDailyJudgmentPlayersList;
	}

	public void setLastNightHealingByMedicPlayers(ArrayList<HumanPlayer> lastNightHealingByMedicPlayers) {
		this.lastNightHealingByMedicPlayers = lastNightHealingByMedicPlayers;
	}

	public void setLastNightHeatingByDarkMedicPlayers(ArrayList<HumanPlayer> lastNightHeatingByDarkMedicPlayers) {
		this.lastNightHeatingByDarkMedicPlayers = lastNightHeatingByDarkMedicPlayers;
	}

	public void setLastNightDealingByDealerPlayers(ArrayList<HumanPlayer> lastNightDealingByDealerPlayers) {
		this.lastNightDealingByDealerPlayers = lastNightDealingByDealerPlayers;
	}

	public void setLastNightHittingByMafiaPlayer(ArrayList<HumanPlayer> lastNightHittingByMafiaPlayer) {
		this.lastNightHittingByMafiaPlayer = lastNightHittingByMafiaPlayer;
	}

	public void setLastNightKilledPlayer(ArrayList<HumanPlayer> lastNightKilledPlayer) {
		this.lastNightKilledPlayer = lastNightKilledPlayer;
	}

	public void setLastDayOperateByDentistPlayer(ArrayList<HumanPlayer> lastDayOperateByDentistPlayer) {
		this.lastDayOperateByDentistPlayer = lastDayOperateByDentistPlayer;
	}

	public int getMiThisDayRemainedDuels() {
		return miThisDayRemainedDuels;
	}

	public void setMiThisDayRemainedDuels(int miThisDayRemainedDuels) {
		this.miThisDayRemainedDuels = miThisDayRemainedDuels;
	}

	public int getMiThisDayThrownChallenges() {
		return miThisDayThrownChallenges;
	}

	public void setMiThisDayThrownChallenges(int miThisDayThrownChallenges) {
		this.miThisDayThrownChallenges = miThisDayThrownChallenges;
	}
}