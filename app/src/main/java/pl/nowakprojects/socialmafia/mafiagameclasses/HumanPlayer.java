package pl.nowakprojects.socialmafia.mafiagameclasses;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.List;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;

@Parcel
public class HumanPlayer{
    //int playerIndex; // index gracza, dla ulatwienia indentyfikacji
    //int playerPoints=0; //punkty jakie gracz uzbieral w trakcie gier
    String playerName = ""; // imię gracza
    PlayerRole playerRole; // rola gracza
    boolean alive = true; // czy gracz jeszcze żyje
    int warns = 0; //ostrzezenia za lamanie zasad
    int stigmas = 0; //naznaczenia
    List<HumanPlayer> guardsList = new ArrayList<>(); //ochroniarz, ktos kto ginie za danego gracza I OCHRONIARZ TEŻ!!!
    List<HumanPlayer> blackMailersList = new ArrayList<>(); //szantazysta, nie moze na niego zaglosowac itp. CHYBA ZROBIC LISTE SZANTAZYSTÓW!!!
    List<HumanPlayer> loversList = new ArrayList<>(); //kochanek - nie mozna na niego glosowac, jesli ginie jeden z nich - gina oboje I KOCHANKÓW TEŻ
    boolean dealed = false; //czy dilowany
    int lifes = 0;
    boolean roleUsed = false;
    boolean mainRole = false; //jesli jest wiecej niz jedna rola, do danej funkcji, np. sędzia główny
    boolean playerTurn = false;
    boolean roleActionMade = false;


    //For ShowingRoles:
    boolean wasRoleShowed = false;

    @Transient
    MaterialDialog mPlayerInfoDialog;

    //CONSTRUCTORS:
    public HumanPlayer() {
    }

   /* public HumanPlayer(String playerName) {
        this.playerName = playerName;
    }*/

    public HumanPlayer(String playerName, PlayerRole playerRole) {
        this.playerName = playerName;
        this.playerRole = playerRole;
        if(isNotDealedEmo())
            lifes=2;
        else
            lifes=1;
    }



    //GAME METHODS:
    public void hit(){
        setLifes(getLifes()-1);
        if(getLifes()<=0)
            this.setNotAlive();
    }


    public void addGuard(HumanPlayer guard) {
        if(!this.guardsList.contains(guard))
            this.guardsList.add(guard);
    }

    public boolean isNotAlive(){
        return !alive;
    }

    public void addLover(HumanPlayer lover) {
        if(!this.loversList.contains(lover))
            this.loversList.add(lover);
    }


    private void showRoleDescriptionDialog(){
        playerRole.showRoleDescriptionDialog();
    }

    //dochodzi do obroncy, ktory ma zginac metoda rekurencji
    /*public static HumanPlayer getFirstGuard(HumanPlayer player){
        if(player.getGuardsList().isEmpty())
            return player;
        else
            return getFirstGuard(player.getAliveFirstGuard());
    }

    //zwraca pierwszego zywego obronce:
    public HumanPlayer getAliveFirstGuard(){
        for(HumanPlayer x: guardsList)
            if(x.isAlive())
                return x;
        return this;
    }*/


    public List<HumanPlayer> getBlackMailersList() {
        return blackMailersList;
    }

    public void addBlackMailer(HumanPlayer blackMailer) {
        if(!this.blackMailersList.contains(blackMailer))
            this.blackMailersList.add(blackMailer);
    }

    public void setNotAlive(){
        alive = false;
    }

    public boolean hasGuard(){
        return isAliveAndNotDealedPlayerOnTheList(guardsList);
    }

    public boolean isNotDealed(){
        return !dealed;
    }

    public boolean isDealed() {
        return dealed;
    }

    public boolean hasLover(){
        return isAliveAndNotDealedPlayerOnTheList(loversList);
    }

    private boolean isAliveAndNotDealedPlayerOnTheList(List<HumanPlayer> playersList){
        return !Stream.of(playersList).noneMatch(HumanPlayer::isAliveAndNotDealed);
    }


    public List<HumanPlayer> getAliveLoversList(){
        return Stream.of(loversList).filter(HumanPlayer::isAliveAndNotDealed).collect(Collectors.toList());
    }

    public HumanPlayer getGuardToKill(){
        return Stream.of(guardsList).filter(HumanPlayer::isAliveAndNotDealed).findFirst().get();
    }

    public boolean isAliveAndNotDealed(){
        return isAlive() && isNotDealed();
    }


    public int howManyLifes(){
        return this.getLifes();
    }

    public void reviveThePlayer(){
        if(isDead()){
            lifes++;
            alive = true;
        }
    }


    //ROLES CHECKING:
   // public boolean hasRole(String roleName){
    //    return getContext().getString(getRoleName()).equals(roleName);
    //}


    //public boolean hasNotDealedRole(String roleName){
    //    return isNotDealed() && hasRole(roleName);
    //}

    //public boolean hasNotDealedRole(int roleNameId){
    //    return hasNotDealedRole(getContext().getString(getRoleName()));
    //}

   // public boolean hasRole(String roleName){
    //    return getContext().getString(getRoleName()).equals(roleName);
    //}


    public boolean isNotDealedTerrorist(){
        return hasNotDealedRole(R.string.terrorist);
    }

    public boolean isNotDealedEmo(){
        return hasNotDealedRole(R.string.emo);
    }

    public boolean isNotDealedSaint(){
        return hasNotDealedRole(R.string.saint);
    }

    public boolean isNotDealedJew(){return hasNotDealedRole(R.string.jew);}

    public boolean isNotDealedFractionSpeedy(){
        return isNotDealed() && (hasRole(R.string.mafiaspeedy)|| hasRole(R.string.townspeedy) || hasRole(R.string.syndicateSpeedy));
    }

    public boolean hasNotDealedRole(int roleNameId){
        return isNotDealed() && hasRole(roleNameId);
    }

    public boolean hasRole(int roleNameId){
        return roleNameId == getRoleName();
    }




    public void showPlayerInfoDialog(Context context){
        buildPlayerInfoDialog(context);

        mPlayerInfoDialog.show();
    }

    private void buildPlayerInfoDialog(Context context){
        mPlayerInfoDialog = new MaterialDialog.Builder(context)
                .title(getPlayerName())
                .content(generatePlayerDescription(context))
                .positiveText(R.string.ok)
                .build();
    }

    private String generatePlayerDescription(Context context){
        return context.getString(
                R.string.player_description,
                lifes,
                stigmas,
                generatePlayerGuardsString(),
                generatePlayerBlackmailersString(),
                generatePlayerLoversString()
        );
    }

    private String generatePlayerLoversString(){
        return generatePlayersConnectionListWith(loversList);
    }

    private String generatePlayerBlackmailersString(){
        return generatePlayersConnectionListWith(blackMailersList);

    }

    private String generatePlayerGuardsString(){
        return generatePlayersConnectionListWith(guardsList);
    }

    private String generatePlayersConnectionListWith(List<HumanPlayer> playersConnectedToThisPlayerList){
        StringBuilder playersConnectionsStringBuffer = new StringBuilder();
        for(HumanPlayer hp: playersConnectedToThisPlayerList)
            playersConnectionsStringBuffer.append(" ").append(hp.getPlayerName()).append(", ");

        if(playersConnectionsStringBuffer.toString().isEmpty())
            playersConnectionsStringBuffer.append("---");

        return playersConnectionsStringBuffer.toString();
    }



    //OBJECT METHODS OVERRIDE:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanPlayer)) return false;

        HumanPlayer that = (HumanPlayer) o;

        if (alive != that.alive) return false;
        if (!playerName.equals(that.playerName)) return false;
        return playerRole.equals(that.playerRole);

    }

    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + playerRole.hashCode();
        result = 31 * result + (alive ? 1 : 0);
        return result;
    }


    //GETTERS AND SETTERS:

    public void setDealed(boolean dealed) {
        this.dealed = dealed;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getRoleName() {
        return this.playerRole.getNameId();
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public boolean isDead(){
        return !isAlive();
    }

    public boolean isAlive() {
        return alive;
    }
    //public boolean isAlive(HumanPlayer hp) {
    //    return hp.isAlive();
    //}

    public void setStigmas(int stigmas) {
        this.stigmas = stigmas;
    }

    public int getStigmas() {
        return stigmas;
    }

    public void addStigma(){
        this.setStigmas(getStigmas()+1);
    }

    public void setWasRoleShowed(boolean wasRoleShowed) {
        this.wasRoleShowed = wasRoleShowed;
    }

    public List<HumanPlayer> getLoversList() {
        return loversList;
    }

    public List<HumanPlayer> getGuardsList() {
        return guardsList;
    }

    public boolean isWasRoleShowed() {
        return wasRoleShowed;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public boolean isRoleUsed() {
        return roleUsed;
    }

    public boolean isMainRole() {
        return mainRole;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isRoleActionMade() {
        return roleActionMade;
    }

    public void setRoleActionMade(boolean roleActionMade) {
        this.roleActionMade = roleActionMade;
    }

    public Context getContext(){
        return getPlayerRole().getContext();
    }

    public void setContext(Context context){
        getPlayerRole().setContext(context);
    }
}
