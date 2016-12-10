package pl.nowakprojects.socialmafia.mafiagameclasses;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRolesManager;
import pl.nowakprojects.socialmafia.utitles.GameRolesWakeHierarchyComparator;

/**
 * Created by Mateusz on 10.12.2016.
 */
@Parcel
public class GamePlayersManager {

    final TheGame theGame;
    ArrayList<HumanPlayer> playersInfoList;

    @ParcelConstructor
    GamePlayersManager(TheGame theGame){
        this.theGame = theGame;
        this.playersInfoList = theGame.getPlayersInfoList();
    }

    public HumanPlayer getJudgePlayer(){
        HumanPlayer hp1 = findLiveHumanPlayerByRoleName(getContext().getString(R.string.judge));
        HumanPlayer hp2 = findLiveHumanPlayerByRoleName(getContext().getString(R.string.blackJudge));

        return hp1!=null ? hp1 : hp2;

    }

    public boolean isMafiaBossAlive() {
        HumanPlayer mafiaboss = findLiveHumanPlayerByRoleName(getContext().getString(R.string.boss));
        HumanPlayer mafiaboss2 = findLiveHumanPlayerByRoleName(getContext().getString(R.string.blackmailerBoss));

        return mafiaboss!=null|| mafiaboss2!=null;
    }

    public HumanPlayer findHumanPlayerByName(String playerName){
        return Stream.of(getPlayersInfoList()).filter(
                hp -> hp.getPlayerName().equals(playerName)
        ).findFirst().get();
    }

    public HumanPlayer findLiveHumanPlayerByName(String playerName){
        return Stream.of(getPlayersInfoList()).filter(
                hp -> hp.getPlayerName().equals(playerName) && hp.isAlive()
        ).findFirst().get();
    }

    protected HumanPlayer findHumanPlayerByRoleName(String sRoleName){
        Optional<HumanPlayer> foundPlayer = Stream.of(getPlayersInfoList()).filter(
                hp -> getContext().getString(hp.getRoleName()).equals(sRoleName)
        ).findFirst();

        return foundPlayer.isPresent() ? foundPlayer.get() : null;
    }

    private HumanPlayer findLiveHumanPlayerByRoleName(String sRoleName){
        Optional<HumanPlayer> foundPlayer = Stream.of(getPlayersInfoList()).filter(
                hp -> getContext().getString(hp.getRoleName()).equals(sRoleName) && hp.isAlive()
        ).findFirst();

        return foundPlayer.isPresent() ? foundPlayer.get() : null;
    }

    public List<String> getLiveHumanPlayersNames() {
        return Stream.of(getPlayersInfoList())
                .filter(HumanPlayer::isAlive)
                .map(HumanPlayer::getPlayerName)
                .collect(Collectors.toList());
    }

    public List<HumanPlayer> getLiveHumanPlayers() {
        return Stream.of(getPlayersInfoList())
                .filter(HumanPlayer::isAlive)
                .collect(Collectors.toList());
    }

    public List<HumanPlayer> getLiveMafiaPlayers(){
        return getLiveSelectedFractionPlayers(PlayerRole.Fraction.MAFIA);
    }

    public List<HumanPlayer> getLiveTownPlayers(){
        return getLiveSelectedFractionPlayers(PlayerRole.Fraction.TOWN);
    }

    public List<HumanPlayer> getLiveSyndicatePlayers(){
        return getLiveSelectedFractionPlayers(PlayerRole.Fraction.SYNDICATE);
    }

    protected int getLiveMafiaPlayersAmount(){
        return getLiveMafiaPlayers().size();
    }

    protected int getLiveTownPlayersAmount(){
        return getLiveTownPlayers().size();
    }

    public int getLiveSyndicatePlayersAmount(){
        return getLiveSyndicatePlayers().size();
    }

    private List<HumanPlayer> getLiveSelectedFractionPlayers(PlayerRole.Fraction fraction) {
        return Stream.of(getPlayersInfoList())
                .filter(hp -> hp.isAlive() && hp.hasFraction(fraction))
                .collect(Collectors.toList());
    }


    //COS ŹLE - DLACZEGO TO ZMIENIA WARTOSC!?
    public List<HumanPlayer> getThisNightHumanPlayers(){
        if(theGame.currentNightNumber ==0)
            return getZeroNightHumanPlayers();
        else
            return getNormalNightRolesHumanPlayers();
    }

    private void appendMafiaKillingAbstractPlayerTo(List<HumanPlayer> humanPlayers){
        humanPlayers.add(new HumanPlayer(getContext().getString(R.string.mafia), PlayerRolesManager.getInstance(getContext()).getMafiaKillRole()));

    }

    public List<HumanPlayer> getNormalNightRolesHumanPlayers() {
        List<HumanPlayer> result = Stream.of(getPlayersInfoList())
                .filter(HumanPlayer::hasRoleForNormalNight)
                .collect(Collectors.toList());

        if(theGame.isMafiaInTheGame())
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
        theGame.getTemporaryLastTimeKilledPlayersList().add(humanPlayer);
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

    private HumanPlayer findPreviousPlayerTo(HumanPlayer humanPlayer){
        for(int i=getPlayersInfoList().indexOf(humanPlayer)-1; i>=0;i--)
            if(getPlayersInfoList().get(i).isAlive())
                return getPlayersInfoList().get(i);


        for(int i=getPlayersInfoList().size()-1; i>getPlayersInfoList().indexOf(humanPlayer);i--)
            if(getPlayersInfoList().get(i).isAlive())
                return getPlayersInfoList().get(i);

        return null;
    }


    public ArrayList<HumanPlayer> getPlayersInfoList() {
        return playersInfoList;
    }

    private Context getContext(){
        return theGame.mContext;
    }
}
