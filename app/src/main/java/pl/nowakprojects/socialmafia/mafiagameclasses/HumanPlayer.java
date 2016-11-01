package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import pl.nowakprojects.socialmafia.R;

@Parcel
public class HumanPlayer {
    //int playerIndex; // index gracza, dla ulatwienia indentyfikacji
    //int playerPoints=0; //punkty jakie gracz uzbieral w trakcie gier
    String playerName = ""; // imię gracza
    PlayerRole playerRole; // rola gracza
    boolean alive = true; // czy gracz jeszcze żyje
    int warns = 0; //ostrzezenia za lamanie zasad
    int stigmas = 0; //naznaczenia
    ArrayList<HumanPlayer> guard = new ArrayList<>(); //ochroniarz, ktos kto ginie za danego gracza I OCHRONIARZ TEŻ!!!
    ArrayList<HumanPlayer> blackMailer = new ArrayList<>(); //szantazysta, nie moze na niego zaglosowac itp. CHYBA ZROBIC LISTE SZANTAZYSTÓW!!!
    ArrayList<HumanPlayer> lover = new ArrayList<>(); //kochanek - nie mozna na niego glosowac, jesli ginie jeden z nich - gina oboje I KOCHANKÓW TEŻ
    boolean isDealed = false;

    //For ShowingRoles:
    boolean wasRoleShowed = false;

    public HumanPlayer() {
    }

    public HumanPlayer(String playerName) {
        this.playerName = playerName;
    }

    public HumanPlayer(String playerName, PlayerRole playerRole) {
        this.playerName = playerName;
        this.playerRole = playerRole;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getRoleName() {
        return this.playerRole.getName();
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public boolean isWasRoleShowed() {
        return wasRoleShowed;
    }

    public void setWasRoleShowed(boolean wasRoleShowed) {
        this.wasRoleShowed = wasRoleShowed;
    }

    public void setGuard(HumanPlayer guard) {
        this.guard.add(guard);
    }

    public void setLover(HumanPlayer lover) {
        if(!this.lover.contains(lover))
            this.lover.add(lover);
    }

    public ArrayList<HumanPlayer> getLover() {
        return lover;
    }

    public ArrayList<HumanPlayer> getGuardsList() {
        return guard;
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
        for(HumanPlayer x: guard)
            if(x.isAlive())
                return x;
        return this;
    }


    //public static ArrayList<HumanPlayer> getLoversToKill(HumanPlayer player){
     //   if(player.getLover().isEmpty())
      //      return player;
    //}

    public ArrayList<HumanPlayer> getBlackMailer() {
        return blackMailer;
    }

    public void setBlackMailer(HumanPlayer blackMailer) {
        if(!this.blackMailer.contains(blackMailer))
            this.blackMailer.add(blackMailer);
    }

    public void setNotAlive(){
        alive = false;
    }

    public boolean hasGuard(){
        if(guard.isEmpty())
            return false;
        for(HumanPlayer hp: guard)
            if(hp.isAlive()&&hp.isNotDealed())
                return true;

        return false;
    }

    public boolean isNotDealed(){
        return !isDealed;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public boolean hasLover(){
        if(lover.isEmpty())
            return false;
        for(HumanPlayer hp: lover)
            if(hp.isAlive()&&hp.isNotDealed())
                return true;

        return false;
    }

    public ArrayList<HumanPlayer> getAliveLovers(){
        ArrayList<HumanPlayer> aliveLoversList = new ArrayList<>();

        for(HumanPlayer hp: lover)
            if(hp.isAlive()&&hp.isNotDealed())
                aliveLoversList.add(hp);

        return aliveLoversList;

    }

    public HumanPlayer getFirstAliveGuard(){
        if(hasGuard())
            for(HumanPlayer hp: guard)
                if(hp.isAlive()&&hp.isNotDealed())
                    return hp;

        return null;
    }

    public boolean hit(){
        this.getPlayerRole().lifes-=1;
        if(getPlayerRole().getLifes()<=0)
            this.setNotAlive();

        return isAlive();
    }

    public boolean hasTerroristRole(){
        return getRoleName()== R.string.terrorist;
    }

    public boolean hasEmoRole(){
        return getRoleName()== R.string.emo;
    }


    /*
    private static ArrayList<HumanPlayer> killDuringTheGame(HumanPlayer humanPlayer){
        ArrayList<HumanPlayer> playersToKill = new ArrayList<>();
            playersToKill.add(HumanPlayer.getFirstGuard(humanPlayer)); //zabija ostatniego z ciagu murzynów, np. jesli murzyn murszyni murzyna
            //pomyslec tutaj nad rekursja ogonowa! Bo co jesli murzynia siebie nawzajem!? Albo dwa arguemty do poprzedniego wyniku

        //jesli znaleziono murzyna, to zmieniamy gracza na niego i lecimy dalej:
            if(!playersToKill.isEmpty())
                humanPlayer = playersToKill.get(0);

                //teraz sprawdzimy kochanków (dla gracza albo dla murzyna):
                if(!humanPlayer.getLover().isEmpty()) {
                    for (HumanPlayer playerLover : humanPlayer.getLover()) {
                        HumanPlayer.killDuringTheGame(playerLover);
                    }
                }

        for(HumanPlayer hp: playersToKill)
            hp.setNotAlive();

        return playersToKill;
    }

    public static ArrayList<HumanPlayer> killThemDuringTheGame(ArrayList<HumanPlayer> humanPlayersToKill) {
        ArrayList<HumanPlayer> killedPlayers = new ArrayList<HumanPlayer>();

        for(HumanPlayer hp: humanPlayersToKill)
            if(hp.isAlive())
                killedPlayers.addAll(HumanPlayer.killDuringTheGame(hp));

        return killedPlayers;

    }
    */
    public int howManyLifes(){
        return this.playerRole.getLifes();
    }

    public void reviveThePlayer(){
        alive = true;
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
    /*
	 * Widok roli, z przyciskiej odkryj/zakryj, do pokazania każdemu graczowi
	 */

	/*
	 * Lista graczy, z stanem, do GameAction
	 */

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
}
