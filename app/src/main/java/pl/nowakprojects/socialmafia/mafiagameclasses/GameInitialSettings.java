package pl.nowakprojects.socialmafia.mafiagameclasses;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;

/**
 * Created by Mateusz on 10.12.2016.
 */

@Parcel
public final class GameInitialSettings {
    protected final int gameId;
    final ArrayList<HumanPlayer> playersInfoList;

    final long maxDailyTimeInMilliseconds;
    final int maxDailyDuelAmount;
    final int maxDailyDuelChallenges;

    //początkowe ustawienia
    final int playersStartAmount;
    final int mafiaStartAmount;
    final int townStartAmount;
    final int syndicateStartAmount;
    final int doublersStartAmount;

    @ParcelConstructor
    public GameInitialSettings(int gameId,
                               ArrayList<HumanPlayer> playersInfoList,
                               long maxDailyTimeInMilliseconds,
                               int maxDailyDuelAmount,
                               int maxDailyDuelChallenges,
                               int playersStartAmount,
                               int mafiaStartAmount,
                               int townStartAmount,
                               int syndicateStartAmount,
                               int doublersStartAmount) {
        this.gameId = gameId;
        this.playersInfoList = playersInfoList;
        this.maxDailyTimeInMilliseconds = maxDailyTimeInMilliseconds;
        this.maxDailyDuelAmount = maxDailyDuelAmount;
        this.maxDailyDuelChallenges = maxDailyDuelChallenges;
        this.playersStartAmount = playersStartAmount;
        this.mafiaStartAmount = mafiaStartAmount;
        this.townStartAmount = townStartAmount;
        this.syndicateStartAmount = syndicateStartAmount;
        this.doublersStartAmount = doublersStartAmount;
    }

    public int getGameId() {
        return gameId;
    }

    public ArrayList<HumanPlayer> getPlayersInfoList() {
        return playersInfoList;
    }

    public long getMaxDailyTimeInMilliseconds() {
        return maxDailyTimeInMilliseconds;
    }

    public int getMaxDailyDuelAmount() {
        return maxDailyDuelAmount;
    }

    public int getMaxDailyDuelChallenges() {
        return maxDailyDuelChallenges;
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


    /*private final long maxDailyTime = 180000; // czas dnia w milisekundach
    private final int maxDailyDuelAmount = 3; //maksymalna ilosc pojedynkow na dzien
    private final int maxDailyDuelChallenges = 10; //maksymalna ilosc  wyzwan na dzien

    //początkowe ustawienia
    private final int playersStartAmount = 0;
    private final int mafiaStartAmount = 0;
    private final int townStartAmount = 0;
    private final int syndicateStartAmount = 0;
    private final int doublersStartAmount = 0;*/






}
