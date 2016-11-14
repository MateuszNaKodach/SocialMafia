package pl.nowakprojects.socialmafia.realmmodels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 14.11.2016.
 */

public class TheGameModel extends RealmObject {

    @PrimaryKey
    private int gameId;

    long mdMaxDayTime = 180000; // czas dnia w milisekundach
    int mdMaxDuelAmount = 3; //maksymalna ilosc pojedynkow na dzien
    int mdMaxDuelChallenges = 10; //maksymalna ilosc  wyzwan na dzien
    boolean mbFinished = false; //czy gra została skończona

    //początkowe ustawienia
    int players = 0;
    int mafia = 0;
    int town = 0;
    int miSyndicateStartAmount = 0;
    int doublers = 0;

    //Ogolne zmienne do gry:
    int miCurrentNightNumber = -1;
    int miCurrentDayNumber = 0;


    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
