package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcels;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.PlayersStatusDialogFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DayTimeFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DuelChallengesFragment;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.RoleActionsFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.interfaces.OnDaytimeFinishedListener;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.interfaces.OnPlayerKilledListener;

public class TheGameActionActivity extends AppCompatActivity implements OnPlayerKilledListener, OnDaytimeFinishedListener {

    static final String EXTRA_FINISHED_GAME = "pl.nowakprojects.socialmafia.mafiagameclasses.EXTRA_FINISHED_GAME";

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
    MaterialDialog mFinishGameCommunicateMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vUiSetupUserInterface();

        receiveGameSettings(); //odbiera ustawienia gry

        startMafiaGameAction();

    }

    @Override
    public void onBackPressed() {
        //okienko potwierdzenia czy na pewno opuscic gre
        super.onBackPressed();
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
            PlayersStatusDialogFragment theGameActionPlayersGameStatusDialogFragment = new PlayersStatusDialogFragment(theGame);
            theGameActionPlayersGameStatusDialogFragment.show(fragmentManager, "PlayersListFragment");
            return true;}
        else if(itemId == R.id.menu_thegame_savegame)
            return true;
        else if(itemId == R.id.menu_thegame_exitgame)
            return true;

        return false;
    }


    //SETTINGS RECEIVE:
    private void receiveGameSettings() {
        if (IS_LOADED_GAME)
            receiveLoadGameSettings();
        else
            receiveNewGameSettings();
    }

    private void receiveNewGameSettings() {
        theGame = Parcels.unwrap(getIntent().getParcelableExtra(ConnectPlayersToRolesActivity.EXTRA_NEW_GAME));
        theGame.setContext(getApplicationContext());
    }

    private void receiveLoadGameSettings() {
        //WCZYTYWANIE Z BAZDY DANYCH i tworzenie mTheGame na podstawie tego
    }

    //GAME TIME FUNCTIONS:
    void startMafiaGameAction() {
        nightTimeFragment = new NightTimeFragment(this, theGame);
        //GameView proporties:
        dayTimeFragment = new DayTimeFragment(theGame);
        //dayTimeRoleActionsFragment = new DayTimeRoleActionsFragment();
        vUiShowGameTipFragment();
        startNightAction();
    }

    void startNightAction() {
        theGame.startNewNight();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (theGame.isSpecialZeroNightNow()){
            roleActionsFragment = new RoleActionsFragment(theGame);
            fragmentTransaction.add(R.id.dayOrNightTimeFragment, nightTimeFragment, TIME_FRAGMENT);
        }else {
            fragmentTransaction.replace(R.id.dayOrNightTimeFragment, nightTimeFragment, TIME_FRAGMENT);
            roleActionsFragment = new RoleActionsFragment(theGame);
            fragmentTransaction.remove(gameDailyVotingFragment);
            fragmentTransaction.remove(gameDailyDuelChallengesFragment);
        }

        fragmentTransaction.add(R.id.dayOrNightTimeRoleActionsFragment, roleActionsFragment, TIME_ROLE_ACTIONS_FRAGMENT);

        fragmentTransaction.commit();
    }

    void endNightAction() {
        theGame.calculateNightimeResult();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(roleActionsFragment);
        fragmentTransaction.commit();


    }

    void startDayAction() {
        dayTimeFragment = new DayTimeFragment(theGame);

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

    private void startNextDaytimeAction(){
        if(theGame.isNightDaytimeNow())
            startNightAction();
        else if(theGame.isDayDaytimeNow())
            startNightAction();
    }

    void finishGameAction(){
        mFinishGameCommunicateMaterialDialog.show();
    }


    @Override
    public void onPlayerKilled() {
        vUiUpdateFragmentsAfterKill();

        if(theGame.isGameFinished()){
            finishGameAction();
        }
    }

    @Override
    public void onDaytimeFinished() {
        //getSupportFragmentManager().findFragmentByTag(DayTimeFragment.DAILY_JUDGMENT_DIALOG).
        if(theGame.isGameFinished()){
            finishGameAction();
        }
        startNextDaytimeAction();
    }


    //UserInterface settings:-----------------------------------------------------------------------
    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.night_time_tip),false);
    }

    private void vUiSetupUserInterface(){
        vUiSetupMaterialDialog();
    }

    private void vUiUpdateUserInterface(){

    }

    private void vUiUpdateFragmentsAfterKill(){
        DuelChallengesFragment duelChallengesFragment = (DuelChallengesFragment)
                getSupportFragmentManager().findFragmentById(R.id.dayDuelsFragment);

        if(duelChallengesFragment!=null)
            duelChallengesFragment.vUiUpdateUserInterface();

        DailyVotingFragment dailyVotingFragment = (DailyVotingFragment)
                getSupportFragmentManager().findFragmentById(R.id.dayJudgmentFragment);

        if (dailyVotingFragment != null)
            dailyVotingFragment.vUiUpdateUserInterface();
    }

    private void vUiSetupMaterialDialog(){
        mFinishGameCommunicateMaterialDialog = new MaterialDialog.Builder(this)
                .title("GRA ZAKONCZONA!")
                .content("GRA ZAKONCZONA!")
                .positiveText(R.string.seedetails)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        vUiGoToGameFinishedActivity();
                    }
                })
                .build();
    }

    private void vUiGoToGameFinishedActivity(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_FINISHED_GAME, Parcels.wrap(theGame));
        Intent intent = new Intent(getApplicationContext(), GameFinishedActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}//public class TheGameActionActivity extends AppCompatActivity
