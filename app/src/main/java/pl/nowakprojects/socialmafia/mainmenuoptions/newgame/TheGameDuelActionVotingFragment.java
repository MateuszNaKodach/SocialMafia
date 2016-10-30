package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameDuelActionVotingFragment extends DialogFragment {

    //Members:
    TheGame theGame;
    DialogFragment dialogFragment;
    HumanPlayer mAgressivePlayer;
    HumanPlayer mInsultedPlayer;
    AlertDialog confirmVotingDialog;
    ArrayList<HumanPlayer> loosers;

    public TheGameDuelActionVotingFragment(){}

    public TheGameDuelActionVotingFragment(TheGame theGame, HumanPlayer hpAgressivePlayer, HumanPlayer hpInsultedPlayer) {
        this.theGame = theGame;
        mAgressivePlayer=hpAgressivePlayer;
        mInsultedPlayer=hpInsultedPlayer;
        dialogFragment = this;
    }

    //Views
    View fragmentView;
    @BindView(R.id.agressiveKillingSlider) SeekBar seekbarKillAgressivePlayerVotes;
    @BindView(R.id.insultedKillingSlider)  SeekBar seekbarKillInsultedPlayerVotes;
    @BindView(R.id.textView_iKillAgressivePlayerVotes) TextView textView_iKillAgressivePlayerVotes;
    @BindView(R.id.textView_iKillInsultedPlayerVotes) TextView textView_iKillInsultedPlayerVotes;
    @BindView(R.id.textView_sAgressivePlayerName) TextView textView_sAgressivePlayerName;
    @BindView(R.id.textView_sInsultedPlayerName) TextView textView_sInsultedPlayerName;
    @BindView(R.id.button_confirmDuel) Button button_confirmVoting;
    @BindView(R.id.oneDuelIcon) ImageView gunIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.dialog_one_duel_action, container, false);
        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();
        return fragmentView;
    }

    public AlertDialog createPopupAlertDialog(String sTitle, String sMessage, String sPositive, String sNegative) {
        if (sPositive == null)
            sPositive = getString(R.string.yes);
        if (sNegative == null)
            sNegative = getString(R.string.no);


        AlertDialog.Builder popupAlertDialog = new AlertDialog.Builder(this.getActivity());
        popupAlertDialog.setTitle(sTitle);
        popupAlertDialog.setMessage(sMessage);
        popupAlertDialog.setPositiveButton(sPositive, new DialogInterface.OnClickListener() {
            /**
             * Zamyka okno z opisem roli
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitDuelResult();
                dialogFragment.dismiss();
            }
        });

        popupAlertDialog.setNegativeButton(sNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmVotingDialog.cancel();
            }
        });

        confirmVotingDialog = popupAlertDialog.create();
        return confirmVotingDialog;
    }

    private void submitDuelResult(){
        hpCalculateDuelResult();
        Log.i(stringDuelResults(),"WYNIK POJEDYNKU");
    }
    private void hpCalculateDuelResult(){
        loosers = new ArrayList<>();
        if(seekbarKillInsultedPlayerVotes.getProgress()==seekbarKillAgressivePlayerVotes.getProgress()){
            loosers.add(mAgressivePlayer);
            loosers.add(mInsultedPlayer);
        }
        else if(seekbarKillInsultedPlayerVotes.getProgress()>seekbarKillAgressivePlayerVotes.getProgress())
            loosers.add(mAgressivePlayer);
        else
            loosers.add(mInsultedPlayer);

        loosers = HumanPlayer.killThemDuringTheGame(loosers);
    }

    private String stringDuelResults(){

        String result = "Zabici zostali: ";
        for(HumanPlayer hp: loosers)
            result+=hp.getPlayerName()+", ";

        return result;
    }

    //UserInterface Methods:
    private void vUiSetupUserInterface(){
        vUiSetupSeekBars();
        vUiSetupTextViews();
        vUiSetButtonsListeners();
    }

    private void vUiSetupTextViews(){
        textView_sAgressivePlayerName.setText(mAgressivePlayer.getPlayerName());
        textView_sInsultedPlayerName.setText(mInsultedPlayer.getPlayerName());
    }

    private void vUiSetupSeekBars(){
        vUiUpdateSeekBarsMaxValues();
        seekbarKillAgressivePlayerVotes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                vUiUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarKillInsultedPlayerVotes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                vUiUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void vUiSetButtonsListeners(){
        button_confirmVoting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createPopupAlertDialog(getString(R.string.confirm),"Result",
                        null, null).show();
                confirmVotingDialog.show();
            }
        });
    }

    private void vUiUpdate(){
        vUiUpdateSeekBars();
        vUiUpdateGunIcon();
    }

    private void vUiUpdateGunIcon(){
        //if(gunIcon.getScaleY()==1)
         //   gunIcon.setScaleY(-1f);
    }

    private void vUiUpdateSeekBars(){
        vUiUpdateSeekBarsMaxValues();
        vUiUpdateProgressTextViews();
    }

    private void vUiUpdateSeekBarsMaxValues(){
        seekbarKillAgressivePlayerVotes.setMax(theGame.getLiveHumanPlayers().size()-seekbarKillInsultedPlayerVotes.getProgress());
        seekbarKillInsultedPlayerVotes.setMax(theGame.getLiveHumanPlayers().size()-seekbarKillAgressivePlayerVotes.getProgress());
    }

    private void vUiUpdateProgressTextViews(){
        vUiUpdateProgressTextView(seekbarKillInsultedPlayerVotes, textView_iKillInsultedPlayerVotes);
        vUiUpdateProgressTextView(seekbarKillAgressivePlayerVotes, textView_iKillAgressivePlayerVotes);
    }

    private void vUiUpdateProgressTextView(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
    }

}
