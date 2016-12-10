package pl.nowakprojects.socialmafia.mafiagameclasses;
/**
 * Created by Mateusz on 2016-02-20.
 */

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRolesManager;
import pl.nowakprojects.socialmafia.ui.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.utitles.GameRolesWakeHierarchyComparator;

/**
 * maxDailyTime - ograniczenie czasowe na dzien, gracz i tak decyfuje czy konczy
 * teraz DODAC CZAS GRY ( BEDZIE POTRZEBNY DO SAVE!!!)
 * Parceler wymaga nieprywatnych pól!!!
 * Moze zrobic interfejs i implementation
 */

@Parcel
final public class TheGame {

	//OGARNAC PARCEL I FINAL!!!
	GameInitialSettings gameInitialSettings;

	GamePlayersManager gamePlayersManager;
	//static TheGame mInstance;

	@Transient
	Context mContext;

	//@Transient
	//PlayerRolesManager playerRolesManager;

	//int gameId = 0;

	public enum Daytime {DAY, NIGHT, JUDGEMENT}

	//ArrayList<GameDaytime> mlistTheGameDaytimes = new ArrayList<>();

	//Ogolne zmienne do gry:
	int currentNightNumber = -1;
	int currentDayNumber = 0;


	//Zmienne do aktualnego Daytime
	Daytime currentDaytime;
	DailyVotingFragment.OUTVOTED currentDayOutVoted;
	int currentDaytimeMadeRoleActions;
	ArrayList<HumanPlayer> temporaryLastTimeKilledPlayersList;
	public ArrayList<HumanPlayer> choseDailyJudgmentPlayersList;


	//Zmienne do aktualnej nocy
	ArrayList<HumanPlayer> lastNightHealingByMedicPlayers;
	ArrayList<HumanPlayer> lastNightHittingByDarkMedicPlayers;
	ArrayList<HumanPlayer> lastNightDealingByDealerPlayers;
	ArrayList<HumanPlayer> lastNightHittingByMafiaPlayers;
	ArrayList<HumanPlayer> lastNightKilledPlayers;

	//Zmienne do aktualnego dnia
	ArrayList<HumanPlayer> lastDayOperateByDentistPlayers;
	int currentDayRemainedDuels;
	int currentDayThrownChallenges;

	@ParcelConstructor
	public TheGame(GameInitialSettings gameInitialSettings) {
		this.gameInitialSettings = gameInitialSettings;
		this.gamePlayersManager = new GamePlayersManager(this);
		initializeTheGame(null);
	}

	public TheGame(GameInitialSettings gameInitialSettings, Context context) {
		this.gameInitialSettings = gameInitialSettings;
		this.gamePlayersManager = new GamePlayersManager(this);
		initializeTheGame(context);
		//setupGameAndRolesContext(context);
	}

	private void initializeTheGame(Context context){
		this.mContext = context;
		lastNightHealingByMedicPlayers = new ArrayList<>();
		lastNightHittingByDarkMedicPlayers = new ArrayList<>();
		lastNightHittingByMafiaPlayers = new ArrayList<>();
		lastNightDealingByDealerPlayers = new ArrayList<>();
		lastDayOperateByDentistPlayers = new ArrayList<>();
		lastNightKilledPlayers = new ArrayList<>();
		temporaryLastTimeKilledPlayersList = new ArrayList<>();
		choseDailyJudgmentPlayersList = new ArrayList<>();
	}

	public void setupGameAndRolesContext(Context context){
		this.mContext = context.getApplicationContext();
		PlayerRolesManager.setContextForPlayersRoles(getPlayersInfoList(),context);
	}

	public boolean isGameFinished(){
		return calculateWinner()!= PlayerRole.Fraction.NOFRACTION;
	}

	public boolean isFirstDay() { return this.currentDayNumber ==1;}


	//TODO DOKONCZYC KONCZYC JESLI JEST SYNDYKAT!!!!
	private PlayerRole.Fraction calculateWinner(){
			//if(gameWithoutSyndicate())
				if(isMoreOrEqualMafiaThanTownPlayers())
					return PlayerRole.Fraction.MAFIA;
				else if(isThereNoMafiaAndMoreTownPlayers())
					return PlayerRole.Fraction.TOWN;

				return PlayerRole.Fraction.NOFRACTION;
	}


	private boolean gameWithoutSyndicate(){
		return gameInitialSettings.getSyndicateStartAmount() ==0;
	}

	public void beginKilling(){
		if(temporaryLastTimeKilledPlayersList !=null)
			temporaryLastTimeKilledPlayersList.clear();
	}


	private boolean isMoreOrEqualMafiaThanTownPlayers(){
		return getGamePlayersManager().getLiveMafiaPlayersAmount()>=getGamePlayersManager().getLiveTownPlayersAmount();
	}

	private boolean isThereNoMafiaAndMoreTownPlayers(){
		return getGamePlayersManager().getLiveMafiaPlayersAmount()==0&&getGamePlayersManager().getLiveTownPlayersAmount()>0;
	}

	public boolean isKillingJudgment(){
		return currentDayOutVoted == DailyVotingFragment.OUTVOTED.KILLING;
	}

	public boolean isCheckingJudgment(){
		return currentDayOutVoted == DailyVotingFragment.OUTVOTED.CHECKING;
	}

	public void kill(HumanPlayer humanPlayer){
		gamePlayersManager.kill(humanPlayer);
	}

	//Co zrobic jak np. dwaj lekarze leczyli tego gracza!?
	public void commitNightKillingResults(){
		this.beginKilling();
		commitKillForPlayerList(findPlayersWillHaveKilledBecauseOfMafia());
	}

	private void commitKillForPlayerList(List<HumanPlayer> playersList){
		Stream.of(playersList).forEach(this::kill);
	}

	private List<HumanPlayer> findPlayersWillHaveKilledBecauseOfMafia(){
		return Stream.of(getLastNightHittingByMafiaPlayers()).filter(this::goingToDieAfterMafiaMedicAndDarkMedicActions).collect(Collectors.toList());
	}

	private boolean goingToDieAfterMafiaMedicAndDarkMedicActions(HumanPlayer humanPlayer){
		return (WasHealedAndHitByDarkMedic(humanPlayer) || wasNotPlayerHealed(humanPlayer));
	}

	private boolean WasHealedAndHitByDarkMedic(HumanPlayer humanPlayer){
		return wasPlayerHitByDarkMedic(humanPlayer) && wasPlayerHealed(humanPlayer);
	}

	private boolean wasPlayerHealed(HumanPlayer humanPlayer){
		return isPlayerOnTheList(humanPlayer, getLastNightHealingByMedicPlayers());
	}

	private boolean wasNotPlayerHealed(HumanPlayer humanPlayer){
		return !isPlayerOnTheList(humanPlayer, getLastNightHealingByMedicPlayers());
	}

	private boolean wasPlayerHitByDarkMedic(HumanPlayer humanPlayer){
		return isPlayerOnTheList(humanPlayer, getLastNightHittingByDarkMedicPlayers());
	}

	private boolean isPlayerOnTheList(HumanPlayer humanPlayer, ArrayList<HumanPlayer> playersList){
		return !Stream.of(playersList).noneMatch(hp->hp==humanPlayer);
	}

	public HumanPlayer getLastKilledPlayer(){
		if(temporaryLastTimeKilledPlayersList.isEmpty())
			return null;
		else
			return temporaryLastTimeKilledPlayersList.get(temporaryLastTimeKilledPlayersList.size()-1);
	}


	public void clearMadeActionsCount(){

	}

	public ArrayList<HumanPlayer> getTemporaryLastTimeKilledPlayersList() {
		return temporaryLastTimeKilledPlayersList;
	}

	public String lastTimeKilledPlayersString(){
		StringBuilder lastTimeKilledPlayersStringBuffer = new StringBuilder();

		for(HumanPlayer hp: this.getTemporaryLastTimeKilledPlayersList())
			lastTimeKilledPlayersStringBuffer.append("").append(hp.getPlayerName()).append(", ");

		if(lastTimeKilledPlayersStringBuffer.toString().isEmpty())
			lastTimeKilledPlayersStringBuffer.append("---");

		return lastTimeKilledPlayersStringBuffer.toString();
	}

	public void startNewNight(){
		clearMadeActions();
		clearLastNightStats();
		setCurrentDaytimeMadeRoleActions(0);
		setCurrentNightNumber(getCurrentNightNumber()+1);
		setCurrentDaytime(Daytime.NIGHT);
		//mlistTheGameDaytimes.add(new GameNight(this,Daytime.NIGHT));
	}

	private void clearLastNightStats(){
		getLastNightHealingByMedicPlayers().clear();
		getLastNightHittingByDarkMedicPlayers().clear();
		getLastNightHittingByMafiaPlayers().clear();
		getLastNightKilledPlayers().clear();
		undealLastNightDealedPlayers();
	}

	private void undealLastNightDealedPlayers(){
		Stream.of(getLastNightDealingByDealerPlayers()).forEach(hp -> hp.setDealed(false));
		lastNightDealingByDealerPlayers.clear();
	}

	public void startNewDay(){
		currentDayRemainedDuels = gameInitialSettings.getMaxDailyDuelAmount();
		currentDayThrownChallenges =0;
		lastDayOperateByDentistPlayers.clear();
		choseDailyJudgmentPlayersList.clear();
		currentDaytimeMadeRoleActions = 0;
		currentDayOutVoted =null;
		currentDayNumber++;
		this.currentDaytime =Daytime.DAY;
		//mlistTheGameDaytimes.add(new GameDay(this,Daytime.DAY));
	}

	private void clearMadeActions(){
		Stream.of(getGamePlayersManager().getLiveHumanPlayers()).forEach(hp -> {hp.playerTurn =false; hp.roleActionMade =false;});
	}

	public boolean isJudgeInTheGameSettings(){
		HumanPlayer hp1 = getGamePlayersManager().findHumanPlayerByRoleName(mContext.getString(R.string.judge));
		HumanPlayer hp2 = getGamePlayersManager().findHumanPlayerByRoleName(mContext.getString(R.string.blackJudge));
		return (hp1!=null || hp2!=null);
	}



	/*public ArrayList<HumanPlayer> getTownHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if (humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
				result.add(humanPlayer);
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()*/


	public int getAmountActionsMadeThisTime(){
		// miDaytimeRolesActionsMadeThis++;
		return ++currentDaytimeMadeRoleActions;
	}

	public boolean isSpecialZeroNightNow(){
		return isNightDaytimeNow()&&(this.getCurrentNightNumber()==0);
	}

	public boolean isNightDaytimeNow(){
		return isExactlyThisDaytimeNow(Daytime.NIGHT);
	}

	public boolean isDayDaytimeNow(){
		return isExactlyThisDaytimeNow(Daytime.DAY);
	}

	private boolean isExactlyThisDaytimeNow(Daytime daytime){
		return currentDaytime ==daytime;
	}



	public void setPlayersInfoList(ArrayList<HumanPlayer> playersInfoList) {
		//this.playersInfoList = playersInfoList;
		PlayerRolesManager.setContextForPlayersRoles(playersInfoList, mContext);
	}

	protected boolean isMafiaInTheGame(){
		return gamePlayersManager.getLiveMafiaPlayers().size()>0;
	}

	//GETTERS AND SETTERS-----------------------------------------------------------------------------

	public int iGetActionsMadeThisTime(){
		return currentDaytimeMadeRoleActions;
	}

	public ArrayList<HumanPlayer> getLastDayOperateByDentistPlayers() {
		return lastDayOperateByDentistPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightDealingByDealerPlayers() {
		return lastNightDealingByDealerPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightHealingByMedicPlayers() {
		return lastNightHealingByMedicPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightHittingByDarkMedicPlayers() {
		return lastNightHittingByDarkMedicPlayers;
	}

	public ArrayList<HumanPlayer> getLastNightKilledPlayers() {
		return lastNightKilledPlayers;
	}

	public long getMaxDailyTime() {
		return gameInitialSettings.getMaxDailyTimeInMilliseconds();
	}

	public int getPlayersStartAmount() {
		return gameInitialSettings.getPlayersStartAmount();
	}


	public int getCurrentNightNumber() {
		return currentNightNumber;
	}

	public int getCurrentDayNumber() {
		return currentDayNumber;
	}


	public void setCurrentNightNumber(int currentNightNumber) {
		this.currentNightNumber = currentNightNumber;
	}

	public void setCurrentDayNumber(int currentDayNumber) {
		this.currentDayNumber = currentDayNumber;
	}

	public int getMaxDailyDuelAmount() {
		return gameInitialSettings.getMaxDailyDuelAmount();
	}


	public int getMaxDailyDuelChallenges() {
		return gameInitialSettings.getMaxDailyDuelChallenges();
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

	public void setLastNightHittingByDarkMedicPlayers(ArrayList<HumanPlayer> lastNightHittingByDarkMedicPlayers) {
		this.lastNightHittingByDarkMedicPlayers = lastNightHittingByDarkMedicPlayers;
	}

	public void setLastNightDealingByDealerPlayers(ArrayList<HumanPlayer> lastNightDealingByDealerPlayers) {
		this.lastNightDealingByDealerPlayers = lastNightDealingByDealerPlayers;
	}

	public void setLastNightHittingByMafiaPlayers(ArrayList<HumanPlayer> lastNightHittingByMafiaPlayers) {
		this.lastNightHittingByMafiaPlayers = lastNightHittingByMafiaPlayers;
	}

	public void setLastNightKilledPlayers(ArrayList<HumanPlayer> lastNightKilledPlayers) {
		this.lastNightKilledPlayers = lastNightKilledPlayers;
	}

	public void setLastDayOperateByDentistPlayers(ArrayList<HumanPlayer> lastDayOperateByDentistPlayers) {
		this.lastDayOperateByDentistPlayers = lastDayOperateByDentistPlayers;
	}

	public int getCurrentDayRemainedDuels() {
		return currentDayRemainedDuels;
	}

	public void setCurrentDayRemainedDuels(int currentDayRemainedDuels) {
		this.currentDayRemainedDuels = currentDayRemainedDuels;
	}

	public int getCurrentDayThrownChallenges() {
		return currentDayThrownChallenges;
	}

	public void setCurrentDayThrownChallenges(int currentDayThrownChallenges) {
		this.currentDayThrownChallenges = currentDayThrownChallenges;
	}

	public ArrayList<HumanPlayer> getPlayersInfoList() {
		return gameInitialSettings.getPlayersInfoList();
	}

	public void appendLastNightHealingByMedicPlayers(HumanPlayer humanPlayer){
		if(!lastNightHealingByMedicPlayers.contains(humanPlayer))
			lastNightHealingByMedicPlayers.add(humanPlayer);
	}

	public void appendLastNightHittingByMafiaPlayer(HumanPlayer humanPlayer){
		if(!lastNightHittingByMafiaPlayers.contains(humanPlayer))
			lastNightHittingByMafiaPlayers.add(humanPlayer);
	}

	private ArrayList<HumanPlayer> getLastNightHittingByMafiaPlayers() {
		return lastNightHittingByMafiaPlayers;
	}

	public void appendLastDayOperateByDentistPlayers(HumanPlayer humanPlayer){
		if(!lastDayOperateByDentistPlayers.contains(humanPlayer))
			lastDayOperateByDentistPlayers.add(humanPlayer);
	}

	public void appendLastNightDealingByDealerPlayers(HumanPlayer humanPlayer){
		if(!lastNightDealingByDealerPlayers.contains(humanPlayer))
			lastNightDealingByDealerPlayers.add(humanPlayer);
	}

	public void appendLastNightKilledPlayers(HumanPlayer humanPlayer){
		if(!lastNightKilledPlayers.contains(humanPlayer))
			lastNightKilledPlayers.add(humanPlayer);
	}

	public void appendLastNightHeatingByDarkMedicPlayers(HumanPlayer humanPlayer){
		if(!lastNightHittingByDarkMedicPlayers.contains(humanPlayer))
			lastNightHittingByDarkMedicPlayers.add(humanPlayer);
	}

	public void setContext(Context context){
		this.mContext = context;
	}

	public List<HumanPlayer> getLiveHumanPlayers() {
		return getGamePlayersManager().getLiveHumanPlayers();
	}

	public HumanPlayer findHumanPlayerByName(String playerName){
		return getGamePlayersManager().findHumanPlayerByName(playerName);
	}

	public List<String> getLiveHumanPlayersNames(){
		return getGamePlayersManager().getLiveHumanPlayersNames();
	}

	public GamePlayersManager getGamePlayersManager() {
		return gamePlayersManager;
	}
}