package pl.nowakprojects.socialmafia.mafiagameclasses;
/**
 * Created by Mateusz on 2016-02-20.
 */

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.parceler.Parcel;
import org.parceler.Transient;

import java.net.HttpURLConnection;
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
public class TheGame {

	//static TheGame mInstance;

	@Transient
	Context mContext;

	//@Transient
	//PlayerRolesManager playerRolesManager;

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
	ArrayList<HumanPlayer> lastNightHittingByDarkMedicPlayers;
	ArrayList<HumanPlayer> lastNightDealingByDealerPlayers;
	ArrayList<HumanPlayer> lastNightHittingByMafiaPlayers;
	ArrayList<HumanPlayer> lastNightKilledPlayers;

	//Zmienne do aktualnego dnia
	ArrayList<HumanPlayer> lastDayOperateByDentistPlayers;
	int currentDayRemainedDuels = maxDailyDuelAmount;
	int currentDayThrownChallenges =0;

	public TheGame() {
		initializeTheGame(null);
	}

	public TheGame(Context context) {
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
		PlayerRolesManager.setContextForPlayersRoles(playersInfoList,context);
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

	private boolean isMoreOrEqualMafiaThanTownPlayers(){
		return getLiveMafiaPlayersAmount()>=getLiveTownPlayersAmount();
	}

	private boolean isThereNoMafiaAndMoreTownPlayers(){
		return getLiveMafiaPlayersAmount()==0&&getLiveTownPlayersAmount()>0;
	}

	private boolean gameWithoutSyndicate(){
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
			killAlivePlayer(humanPlayer);
		}
	}

	private void killAlivePlayer(HumanPlayer humanPlayer){
		if (humanPlayer.hasGuard())
			kill(humanPlayer.getGuardToKill()); //to przejdzie do zabicia odpowiedniego gracza
		else
			killPlayerWithoutBodyguard(humanPlayer);
	}

	private void killPlayerWithoutBodyguard(HumanPlayer humanPlayer){
		humanPlayer.hit(); //dostaje hita, jak jest emo to nie ginie, sprawdamy czy nie byl emo, czyli czy zginal

		if(humanPlayer.isDead())
			confirmPlayerDeath(humanPlayer);
	}

	private void confirmPlayerDeath(HumanPlayer humanPlayer){
		insertToLastTimeKilledList(humanPlayer);
		killPlayerLovers(humanPlayer);
		killNextToIfHasTerroristRole(humanPlayer);
	}

	private void insertToLastTimeKilledList(HumanPlayer humanPlayer){
		temporaryLastTimeKilledPlayersList.add(humanPlayer);
	}

	private void killPlayerLovers(HumanPlayer humanPlayer){
		if (humanPlayer.hasLover()) {
			for (HumanPlayer hp : humanPlayer.getAliveLoversList())
				kill(hp); //zabija wszystkich kochanków
		}
	}

	private void killNextToIfHasTerroristRole(HumanPlayer humanPlayer){
		if (humanPlayer.isNotDealedTerrorist())
			kill(findPreviousPlayerTo(humanPlayer));
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

	private HumanPlayer findPreviousPlayerTo(HumanPlayer humanPlayer){
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
		currentDayRemainedDuels = maxDailyDuelAmount;
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
		Stream.of(getLiveHumanPlayers()).forEach(hp -> {hp.playerTurn =false; hp.roleActionMade =false;});
	}

	public boolean isJudgeInTheGameSettings(){
		HumanPlayer hp1 = findHumanPlayerByRoleName(mContext.getString(R.string.judge));
		HumanPlayer hp2 = findHumanPlayerByRoleName(mContext.getString(R.string.blackJudge));
		return (hp1!=null || hp2!=null);
	}

	public HumanPlayer getJudgePlayer(){
		HumanPlayer hp1 = findLiveHumanPlayerByRoleName(mContext.getString(R.string.judge));
		HumanPlayer hp2 = findLiveHumanPlayerByRoleName(mContext.getString(R.string.blackJudge));

		return hp1!=null ? hp1 : hp2;

	}

	private void appendMafiaKillingAbstractPlayerTo(List<HumanPlayer> humanPlayers){
		humanPlayers.add(new HumanPlayer(mContext.getString(R.string.mafia), PlayerRolesManager.getInstance(mContext).getMafiaKillRole()));

	}

	public List<HumanPlayer> getNormalNightRolesHumanPlayers() {
		List<HumanPlayer> result = Stream.of(getPlayersInfoList())
				.filter(HumanPlayer::hasRoleForNormalNight)
				.collect(Collectors.toList());

		if(isMafiaInTheGame())
			appendMafiaKillingAbstractPlayerTo(result);


		if(!result.isEmpty()){
			sortPlayersListInWakingHierarchy(result);
			setTurnForFirstPlayerInList(result);
		}

		return result;
	}// private ArrayList<HumanPlayer> getNormalNightRolesHumanPlayers()


	public List<HumanPlayer> getZeroNightHumanPlayers() {
		List<HumanPlayer> result = Stream.of(getPlayersInfoList())
				.filter(HumanPlayer::hasRoleForZeroNight)
				.sorted(new GameRolesWakeHierarchyComparator())
				.collect(Collectors.toList());

		if(!result.isEmpty())
			setTurnForFirstPlayerInList(result);

		return result;
	}

	private void sortPlayersListInWakingHierarchy(List<HumanPlayer> playersList){
		Collections.sort(playersList,new GameRolesWakeHierarchyComparator());
	}

	private void setTurnForFirstPlayerInList(List<HumanPlayer> playersList){
			playersList.get(0).setPlayerTurn(true);
	}

	/*public ArrayList<HumanPlayer> getTownHumanPlayers() {
		ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
		for (HumanPlayer humanPlayer : getPlayersInfoList()) {
			if (humanPlayer.getPlayerRole().getFraction().equals(PlayerRole.Fraction.TOWN))
				result.add(humanPlayer);
		}
		return result;
	}// private ArrayList<HumanPlayer> getTownHumanPlayers()*/

	//COS ŹLE - DLACZEGO TO ZMIENIA WARTOSC!?
	public List<HumanPlayer> getThisNightHumanPlayers(){
		if(currentNightNumber ==0)
			return getZeroNightHumanPlayers();
		else
			return getNormalNightRolesHumanPlayers();
	}

	public int getAmountActionsMadeThisTime(){
		// miDaytimeRolesActionsMadeThis++;
		return ++currentDaytimeMadeRoleActions;
	}

	public HumanPlayer findHumanPlayerByName(String playerName){
		return Stream.of(playersInfoList).filter(
				hp -> hp.getPlayerName().equals(playerName)
		).findFirst().get();
	}

	public HumanPlayer findLiveHumanPlayerByName(String playerName){
		return Stream.of(playersInfoList).filter(
				hp -> hp.getPlayerName().equals(playerName) && hp.isAlive()
		).findFirst().get();
	}

	private HumanPlayer findHumanPlayerByRoleName(String sRoleName){
		Optional<HumanPlayer> foundPlayer = Stream.of(playersInfoList).filter(
				hp -> mContext.getString(hp.getRoleName()).equals(sRoleName)
		).findFirst();

		return foundPlayer.isPresent() ? foundPlayer.get() : null;
	}

	private HumanPlayer findLiveHumanPlayerByRoleName(String sRoleName){
		Optional<HumanPlayer> foundPlayer = Stream.of(playersInfoList).filter(
				hp -> mContext.getString(hp.getRoleName()).equals(sRoleName) && hp.isAlive()
		).findFirst();

		return foundPlayer.isPresent() ? foundPlayer.get() : null;
	}

	public List<String> getLiveHumanPlayersNames() {
		return Stream.of(playersInfoList)
				.filter(HumanPlayer::isAlive)
				.map(HumanPlayer::getPlayerName)
				.collect(Collectors.toList());
	}

	public List<HumanPlayer> getLiveHumanPlayers() {
		return Stream.of(playersInfoList)
				.filter(HumanPlayer::isAlive)
				.collect(Collectors.toList());
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

	private int getLiveMafiaPlayersAmount(){
		return getLiveMafiaPlayers().size();
	}

	private int getLiveTownPlayersAmount(){
		return getLiveTownPlayers().size();
	}

	public int getLiveSyndicatePlayersAmount(){
		return getLiveSyndicatePlayers().size();
	}

	private List<HumanPlayer> getLiveSelectedFractionPlayers(PlayerRole.Fraction fraction) {
		return Stream.of(playersInfoList)
				.filter(hp -> hp.isAlive() && hp.hasFraction(fraction))
				.collect(Collectors.toList());
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


	public boolean isMafiaBossAlive() {
		HumanPlayer mafiaboss = findLiveHumanPlayerByRoleName(mContext.getString(R.string.boss));
		HumanPlayer mafiaboss2 = findLiveHumanPlayerByRoleName(mContext.getString(R.string.blackmailerBoss));

		return mafiaboss!=null|| mafiaboss2!=null;
	}


	public void setPlayersInfoList(ArrayList<HumanPlayer> playersInfoList) {
		this.playersInfoList = playersInfoList;
		PlayerRolesManager.setContextForPlayersRoles(playersInfoList, mContext);
	}

	private boolean isMafiaInTheGame(){
		return getLiveMafiaPlayers().size()>0;
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
		return playersInfoList;
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
}