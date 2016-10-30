package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;

import org.parceler.Parcels;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DuelChallengesFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

public class TheGameActionActivity extends AppCompatActivity {

    final String TIME_FRAGMENT = "TheGameActionActivity.TIME_FRAGMENT";
    final String TIME_ROLE_ACTIONS_FRAGMENT = "TheGameActionActivity.TIME_ROLE_ACTIONS_FRAGMENT";
    final String GAME_TIP_FRAGMENT = "TheGameActionActivity.GAME_TIP_FRAGMENT";
    final String DAILY_VOTING_FRAGMENT = "TheGameActionActivity.DAILY_VOTING_FRAGMENT";
    final String DAILY_DUELS_FRAGMENT = "TheGameActionActivity.DAILY_DUELS_FRAGMENT";

    TheGame theGame;
    boolean IS_LOADED_GAME = false;

    DayTimeFragment dayTimeFragment;
    NightTimeFragment nightTimeFragment;
    RoleActionsFragment roleActionsFragment;
    DailyVotingFragment gameDailyVotingFragment;
    DuelChallengesFragment gameDailyDuelChallengesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receiveGameSettings(); //odbiera ustawienia gry

        startMafiaGameAction();

    }

    @Override
    public void onBackPressed() {
        //okienko potwierdzenia czy na pewno opuscic gre
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.the_game_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_thegame_playerslist){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            TheGameActionPlayersGameStatusDialogFragment theGameActionPlayersGameStatusDialogFragment = new  TheGameActionPlayersGameStatusDialogFragment(theGame);
            theGameActionPlayersGameStatusDialogFragment.show(fragmentManager, "PlayersListFragment");
            return true;}
        else if(itemId == R.id.menu_thegame_savegame)
            return true;
        else if(itemId == R.id.menu_thegame_exitgame)
            return true;

        return false;
    }

    /**
     * TheGame functions:
     */

    void startMafiaGameAction() {
        nightTimeFragment = new NightTimeFragment(this, theGame);
        //GameView proporties:
        dayTimeFragment = new DayTimeFragment(this);
        //dayTimeRoleActionsFragment = new DayTimeRoleActionsFragment();

        startNightAction();

        //dopóki gra nie jest zakończona ciągle leci dzień - noc:
        // while(!mTheGame.isMbFinished()){
        //      startNightAction();
        //      startDayAction();}

        //  endTheGameAndShowResults();
    }

    void startNightAction() {
        theGame.startNewNight();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (theGame.isSpecialZeroNightNow()){
            roleActionsFragment = new RoleActionsFragment(theGame,this);
            fragmentTransaction.add(R.id.dayOrNightTimeFragment, nightTimeFragment, TIME_FRAGMENT);
        }else {
            roleActionsFragment = new RoleActionsFragment(theGame,this);
            fragmentTransaction.remove(gameDailyVotingFragment);
        }

        fragmentTransaction.add(R.id.dayOrNightTimeRoleActionsFragment, roleActionsFragment, TIME_ROLE_ACTIONS_FRAGMENT);

        fragmentTransaction.commit();

        showGameTipFragment(null,getString(R.string.night_time_tip));
    }

    void endNightAction() {
        if(!(theGame.getMiCurrentNightNumber()==0))
            makeJudgmentAction();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(roleActionsFragment);
        fragmentTransaction.commit();

        theGame.setMiCurrentNightNumber(theGame.getMiCurrentNightNumber() + 1);

    }

    void makeJudgmentAction(){

    }

    void startDayAction() {
        theGame.startNewDay();
        //roleActionsFragment = new RoleActionsFragment(getDayHumanPlayers());
        gameDailyVotingFragment = new DailyVotingFragment(theGame);
        gameDailyDuelChallengesFragment = new DuelChallengesFragment(theGame);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dayOrNightTimeFragment,dayTimeFragment,TIME_FRAGMENT);
        fragmentTransaction.add(R.id.dayOrNightTimeRoleActionsFragment, roleActionsFragment, TIME_ROLE_ACTIONS_FRAGMENT);
        fragmentTransaction.add(R.id.dayJudgmentFragment, gameDailyVotingFragment, DAILY_VOTING_FRAGMENT);
        fragmentTransaction.add(R.id.dayDuelsFragment, gameDailyDuelChallengesFragment, DAILY_DUELS_FRAGMENT);
        fragmentTransaction.commit();
    }

    void endTheGameAndShowResults() {
    }


    /**
     * Game app functions:
     */
    private void receiveGameSettings() {
        if (IS_LOADED_GAME)
            receiveLoadGameSettings();
        else
            receiveNewGameSettings();
    }


    /**
     * Odbieranie ustawień z nowej gry
     */
    private void receiveNewGameSettings() {
        theGame = Parcels.unwrap(getIntent().getParcelableExtra(ConnectPlayersToRolesActivity.EXTRA_NEW_GAME));
        //mTheGame.setmTheGameActionActivity(this);
    }

    ;

    private void receiveLoadGameSettings() {
        //WCZYTYWANIE Z BAZDY DANYCH i tworzenie mTheGame na podstawie tego
    }

    ;


    /*private ArrayList<HumanPlayer> getDayHumanPlayers() {
        ArrayList<HumanPlayer> result = new ArrayList<HumanPlayer>();
        for (HumanPlayer humanPlayer : mTheGame.getPlayersInfoList()) {
            if(humanPlayer.isAlive()) {
                if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.))
                    result.add(humanPlayer);
                if (humanPlayer.getPlayerRole().getActionType().equals(PlayerRole.ActionType.AllNightsBesideZero))
                    result.add(humanPlayer);
            }
        }
        Collections.sort(result,new GameRolesWakeHierarchyComparator());
        if(!result.isEmpty())
            result.get(0).getPlayerRole().setB_isRoleTurn(true);
        return result;
    }// private ArrayList<HumanPlayer> getAllNightsBesideZeroHumanPlayers()*/

   private GameTipFragment showGameTipFragment(String sTipTitle, String sTipContent){
       GameTipFragment gameTipFragment = new GameTipFragment(sTipTitle,sTipContent);

       FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       fragmentTransaction.add(R.id.gameTipFragment, gameTipFragment, GAME_TIP_FRAGMENT);
       fragmentTransaction.commit();

       return gameTipFragment;
   }

}//public class TheGameActionActivity extends AppCompatActivity
