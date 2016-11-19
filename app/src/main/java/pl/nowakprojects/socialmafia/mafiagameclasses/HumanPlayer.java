package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import pl.nowakprojects.socialmafia.R;

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

    //CONSTRUCTORS:
    public HumanPlayer() {
    }

    public HumanPlayer(String playerName) {
        this.playerName = playerName;
    }

    public HumanPlayer(String playerName, PlayerRole playerRole) {
        this.playerName = playerName;
        this.playerRole = playerRole;
        if(this.playerRole.name == R.string.emo)
            lifes=2;
        else
            lifes=1;
    }



    //GAME METHODS:
    public boolean hit(){
        this.lifes-=1;
        if(getLifes()<=0)
            this.setNotAlive();

        return isAlive();
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


    //dochodzi do obroncy, ktory ma zginac metoda rekurencji
    public static HumanPlayer getFirstGuard(HumanPlayer player){
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
    }


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
        if(guardsList.isEmpty())
            return false;
        for(HumanPlayer hp: guardsList)
            if(hp.isAlive()&&hp.isNotDealed())
                return true;

        return false;
    }

    public boolean isNotDealed(){
        return !dealed;
    }

    public boolean isDealed() {
        return dealed;
    }

    public boolean hasLover(){
        if(loversList.isEmpty())
            return false;
        for(HumanPlayer hp: loversList)
            if(hp.isAlive()&&hp.isNotDealed())
                return true;

        return false;
    }

    public void setDealed(boolean dealed) {
        this.dealed = dealed;
    }

    public List<HumanPlayer> getAliveLoversList(){
        ArrayList<HumanPlayer> aliveLoversList = new ArrayList<>();

        for(HumanPlayer hp: loversList)
            if(hp.isAlive()&&hp.isNotDealed())
                aliveLoversList.add(hp);

        return aliveLoversList;

    }

    public HumanPlayer getGuardToKill(){
        if(hasGuard())
            for(HumanPlayer hp: guardsList)
                if(hp.isAlive()&&hp.isNotDealed())
                    return hp;

        return null;
    }

    public int howManyLifes(){
        return this.getLifes();
    }

    public void reviveThePlayer(){
        alive = true;
    }


    //ROLES CHECKING:
    public boolean hasTerroristRole(){
        return isNotDealed() && getRoleName()== R.string.terrorist;
    }

    public boolean hasEmoRole(){
        return isNotDealed() && getRoleName()== R.string.emo;
    }

    public boolean hasJewRole(){
        return isNotDealed() && getRoleName()== R.string.jew;
    }

    public boolean hasSpeedyRole(){
        return this.isNotDealed() && getRoleName()== R.string.mafiaspeedy || getRoleName()== R.string.townspeedy || getRoleName()== R.string.sindicateSpeedy;
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
    public String getPlayerName() {
        return this.playerName;
    }

    public int getRoleName() {
        return this.playerRole.getName();
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
}
