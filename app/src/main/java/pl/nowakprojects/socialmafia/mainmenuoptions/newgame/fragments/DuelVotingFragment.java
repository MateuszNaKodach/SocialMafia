package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments;

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
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DuelVotingFragment extends DialogFragment {

    DialogFragment mInstance;
    //Members:
    TheGame mTheGame;
    HumanPlayer mAgressiveHumanPlayer;
    HumanPlayer mInsultedHumanPlayer;
    AlertDialog mConfirmVotingAlertDialog;
    ArrayList<HumanPlayer> mLoosersList;

    public DuelVotingFragment(){}

    public DuelVotingFragment(TheGame mTheGame, HumanPlayer hpAgressivePlayer, HumanPlayer hpInsultedPlayer) {
        this.mTheGame = mTheGame;
        mAgressiveHumanPlayer =hpAgressivePlayer;
        mInsultedHumanPlayer =hpInsultedPlayer;
        mInstance = this;
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
        vUiSetupDialog();
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
                mInstance.dismiss();
            }
        });

        popupAlertDialog.setNegativeButton(sNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mConfirmVotingAlertDialog.cancel();
            }
        });

        mConfirmVotingAlertDialog = popupAlertDialog.create();
        return mConfirmVotingAlertDialog;
    }

    private void submitDuelResult(){
        hpCalculateDuelResult();
        Log.i(stringDuelResults(),"WYNIK POJEDYNKU");
    }

    private void hpCalculateDuelResult(){
        mLoosersList = new ArrayList<>();
        if(seekbarKillInsultedPlayerVotes.getProgress()==seekbarKillAgressivePlayerVotes.getProgress()){
            mLoosersList.add(mAgressiveHumanPlayer);
            mLoosersList.add(mInsultedHumanPlayer);
        }
        else if(seekbarKillInsultedPlayerVotes.getProgress()>seekbarKillAgressivePlayerVotes.getProgress())
            mLoosersList.add(mAgressiveHumanPlayer);
        else
            mLoosersList.add(mInsultedHumanPlayer);

       for(HumanPlayer hp: mLoosersList)
           mTheGame.kill(hp);
    }

    private String stringDuelResults(){

        mTheGame.getmTemporaryLastTimeKilledPlayerList().clear();

        String result = "Zabici zostali: ";
        for(HumanPlayer hp: mTheGame.getmTemporaryLastTimeKilledPlayerList())
            result+=hp.getPlayerName()+", ";

        return result;
    }

    //UserInterface Methods:
    private void vUiSetupDialog(){
        getDialog().setTitle(R.string.duel);
        getDialog().setCancelable(false);
    }

    private void vUiSetupUserInterface(){
        vUiSetupSeekBars();
        vUiSetupTextViews();
        vUiSetButtonsListeners();
    }

    private void vUiSetupTextViews(){
        textView_sAgressivePlayerName.setText(mAgressiveHumanPlayer.getPlayerName());
        textView_sInsultedPlayerName.setText(mInsultedHumanPlayer.getPlayerName());
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
                createPopupAlertDialog(getString(R.string.confirm),stringDuelResults(),
                        null, null).show();
                mConfirmVotingAlertDialog.show();
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
        seekbarKillAgressivePlayerVotes.setMax(mTheGame.getLiveHumanPlayers().size()-seekbarKillInsultedPlayerVotes.getProgress());
        seekbarKillInsultedPlayerVotes.setMax(mTheGame.getLiveHumanPlayers().size()-seekbarKillAgressivePlayerVotes.getProgress());
    }

    private void vUiUpdateProgressTextViews(){
        vUiUpdateProgressTextView(seekbarKillInsultedPlayerVotes, textView_iKillInsultedPlayerVotes);
        vUiUpdateProgressTextView(seekbarKillAgressivePlayerVotes, textView_iKillAgressivePlayerVotes);
    }

    private void vUiUpdateProgressTextView(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
    }

}