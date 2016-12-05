package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.ui.newgame.interfaces.OnPlayerKilledListener;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DuelVotingDialogFragment extends DialogFragment {

    OnPlayerKilledListener mPlayerKillCallback;

    DialogFragment mInstance;
    //Members:
    TheGame mTheGame;
    HumanPlayer mAgressiveHumanPlayer;
    HumanPlayer mInsultedHumanPlayer;
    ArrayList<String> mAgressivePlayersNamesVotesList = new ArrayList<>();
    ArrayList<String> mInsultedPlayersNamesVotesList = new ArrayList<>();

    MaterialDialog mVotingAgressivePlayersMaterialDialog;
    MaterialDialog mVotingInsultedPlayersMaterialDialog;
    MaterialDialog mConfirmVotingMaterialDialog;
    MaterialDialog mDuelResultsMaterialDialog;
    //AlertDialog mConfirmVotingAlertDialog;
    ArrayList<HumanPlayer> mLoosersList = new ArrayList<>();


    public DuelVotingDialogFragment(){}

    public DuelVotingDialogFragment(TheGame mTheGame, HumanPlayer hpAgressivePlayer, HumanPlayer hpInsultedPlayer) {
        this.mTheGame = mTheGame;
        mAgressiveHumanPlayer =hpAgressivePlayer;
        mInsultedHumanPlayer =hpInsultedPlayer;
        mInstance = this;
    }

    //Views
    View fragmentView;
    @BindView(R.id.agressivePlayerNameAndVotes) TextView agressivePlayerNameAndVotesTextView;
    @BindView(R.id.insultedPlayerNameAndVotes) TextView insultedPlayerNameAndVotesTextView;
    @BindView(R.id.agressivePlayerKillButton) Button agressivePlayerKillButton;
    @BindView(R.id.insultedPlayerKillButton) Button insultedPlayerKillButton;
    @BindView(R.id.button_confirmDuel) Button button_confirmVoting;

    /*@BindView(R.id.agressiveKillingSlider) SeekBar seekbarKillAgressivePlayerVotes;
    @BindView(R.id.insultedKillingSlider)  SeekBar seekbarKillInsultedPlayerVotes;
    @BindView(R.id.textView_iKillAgressivePlayerVotes) TextView textView_iKillAgressivePlayerVotes;
    @BindView(R.id.textView_iKillInsultedPlayerVotes) TextView textView_iKillInsultedPlayerVotes;
    @BindView(R.id.textView_sAgressivePlayerName) TextView textView_sAgressivePlayerName;
    @BindView(R.id.textView_sInsultedPlayerName) TextView textView_sInsultedPlayerName;
    @BindView(R.id.button_confirmDuel) Button button_confirmVoting;
    @BindView(R.id.oneDuelIcon) ImageView gunIcon;*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mPlayerKillCallback = (OnPlayerKilledListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnPlayerKilledListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.dialog_one_duel_action_new, container, false);
        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();
        //vUiSetupDialog();
        //vUiSetupUserInterface();
        return fragmentView;
    }


    private void hpCalculateDuelResult(){
        mLoosersList.clear();
        //mLoosersList = new ArrayList<>();
        if(mAgressiveHumanPlayer.isNotDealedFractionSpeedy()&&!mInsultedHumanPlayer.isNotDealedFractionSpeedy())
            mLoosersList.add(mInsultedHumanPlayer);
        else if(!mAgressiveHumanPlayer.isNotDealedFractionSpeedy()&&mInsultedHumanPlayer.isNotDealedFractionSpeedy())
            mLoosersList.add(mAgressiveHumanPlayer);
        else if(mInsultedPlayersNamesVotesList.size()==mAgressivePlayersNamesVotesList.size()){
            mLoosersList.add(mAgressiveHumanPlayer);
            mLoosersList.add(mInsultedHumanPlayer);
        }
        else if(mInsultedPlayersNamesVotesList.size()<mAgressivePlayersNamesVotesList.size())
            mLoosersList.add(mAgressiveHumanPlayer);
        else if(mInsultedPlayersNamesVotesList.size()>mAgressivePlayersNamesVotesList.size())
            mLoosersList.add(mInsultedHumanPlayer);


    }

    public void commitDuelResult(){

        additionalSaintKill();

        mTheGame.beginKilling();
        for(HumanPlayer hp: mLoosersList)
            mTheGame.kill(hp);
    }

    public void additionalSaintKill(){
        if(mAgressiveHumanPlayer.isNotDealedSaint()&&mLoosersList.contains(mAgressiveHumanPlayer))
            for(String hpName: mAgressivePlayersNamesVotesList)
                mLoosersList.add(mTheGame.findHumanPlayerByName(hpName));

        if(mInsultedHumanPlayer.isNotDealedSaint()&&mLoosersList.contains(mInsultedHumanPlayer))
            for(String hpName: mInsultedPlayersNamesVotesList)
                mLoosersList.add(mTheGame.findHumanPlayerByName(hpName));
    }

    private String stringDuelResults(){
        String result=getString(R.string.duel_result_players);
        for(HumanPlayer hp: mLoosersList)
            result+="- "+hp.getPlayerName()+" \n";
        return result;

    }

    private String killingResultString(){
        String result="";
        for(HumanPlayer hp: mTheGame.getTemporaryLastTimeKilledPlayersList())
            result+="- "+hp.getPlayerName()+" \n";
        return result;
    }

    public void vUiSetupUserInterface(){
        vUiShowGameTipFragment();
        vUiSetupDialog();
        vUiUpdateTextView();
        vUiUpdateMaterialDialog();
        vUiSetupButtonListeners();
    }

    private void vUiSetupDialog(){
        getDialog().setTitle(R.string.duel);
        getDialog().setCancelable(false);
    }

    public void vUiUpdateTextView(){
        agressivePlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mAgressiveHumanPlayer.getPlayerName(),mAgressivePlayersNamesVotesList.size()));
        insultedPlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mInsultedHumanPlayer.getPlayerName(),mInsultedPlayersNamesVotesList.size()));
        agressivePlayerKillButton.setText(getString(R.string.who_want_kill,mAgressiveHumanPlayer.getPlayerName()));
        insultedPlayerKillButton.setText(getString(R.string.who_want_kill,mInsultedHumanPlayer.getPlayerName()));
    }

    public void vUiUpdateMaterialDialog(){
        List<String> itemsForAgressiveVotingPlayer = mTheGame.getLiveHumanPlayersNames();
        itemsForAgressiveVotingPlayer.removeAll(mInsultedPlayersNamesVotesList);

        List<String> itemsForInsultedVotingPlayer = mTheGame.getLiveHumanPlayersNames();
        itemsForInsultedVotingPlayer.removeAll(mAgressivePlayersNamesVotesList);

        mVotingAgressivePlayersMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(agressivePlayerKillButton.getText())
                .items(itemsForAgressiveVotingPlayer)
                .content(R.string.duel_voting_explanation)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Integer[] selected = mVotingAgressivePlayersMaterialDialog.getSelectedIndices();

                        mAgressivePlayersNamesVotesList.clear();

                        if(selected!=null)
                        for (int i = 0; i < selected.length; i++)
                            mAgressivePlayersNamesVotesList.add(mTheGame.getLiveHumanPlayersNames().get(selected[i]));

                        vUiUpdateTextView();
                    }
                })
                .build();

        mVotingInsultedPlayersMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(insultedPlayerKillButton.getText())
                .items(itemsForInsultedVotingPlayer)
                .content(R.string.duel_voting_explanation)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Integer[] selected = mVotingInsultedPlayersMaterialDialog .getSelectedIndices();

                        mInsultedPlayersNamesVotesList.clear();

                        if(selected!=null)
                            for (int i = 0; i < selected.length; i++)
                                mInsultedPlayersNamesVotesList.add(mTheGame.getLiveHumanPlayersNames().get(selected[i]));

                        vUiUpdateTextView();
                    }
                })
                .build();
    }

    public void vUiSetMaterialDialog(){
        mConfirmVotingMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.duel_result)
                .content(stringDuelResults())
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        commitDuelResult();
                        mPlayerKillCallback.onPlayerKilled();
                        mDuelResultsMaterialDialog.setContent(killingResultString());
                        mDuelResultsMaterialDialog.show();
                        //mInstance.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mConfirmVotingMaterialDialog.cancel();
                    }
                })
                .build();

        mDuelResultsMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.killedWere)
                .content(killingResultString())
                .positiveText(R.string.ok)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mInstance.dismiss();
                    }
                })
                .build();
    }


    public void vUiSetupButtonListeners(){
        agressivePlayerKillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiUpdateMaterialDialog();
                mVotingAgressivePlayersMaterialDialog.show();
            }
        });

        insultedPlayerKillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiUpdateMaterialDialog();
                mVotingInsultedPlayersMaterialDialog.show();
            }
        });

        button_confirmVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hpCalculateDuelResult();
                vUiSetMaterialDialog();
                mConfirmVotingMaterialDialog.show();
            }
        });
    }

    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.tip_duel),false);
    }

    /*
    public AlertDialog createPopupAlertDialog(String sTitle, String sMessage, String sPositive, String sNegative) {
        if (sPositive == null)
            sPositive = getString(R.string.yes);
        if (sNegative == null)
            sNegative = getString(R.string.no);


        AlertDialog.Builder popupAlertDialog = new AlertDialog.Builder(this.getActivity());
        popupAlertDialog.setTitle(sTitle);
        popupAlertDialog.setMessage(sMessage);
        popupAlertDialog.setPositiveButton(sPositive, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitDuelResult();
                mPlayerKillCallback.onPlayerKilled();
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
        if(mAgressiveHumanPlayer.isNotDealedFractionSpeedy()&&!mInsultedHumanPlayer.isNotDealedFractionSpeedy())
            mLoosersList.add(mInsultedHumanPlayer);
        else if(!mAgressiveHumanPlayer.isNotDealedFractionSpeedy()&&mInsultedHumanPlayer.isNotDealedFractionSpeedy())
            mLoosersList.add(mAgressiveHumanPlayer);
        else if(seekbarKillInsultedPlayerVotes.getProgress()==seekbarKillAgressivePlayerVotes.getProgress()){
            mLoosersList.add(mAgressiveHumanPlayer);
            mLoosersList.add(mInsultedHumanPlayer);
        }
        else if(seekbarKillInsultedPlayerVotes.getProgress()<seekbarKillAgressivePlayerVotes.getProgress())
            mLoosersList.add(mAgressiveHumanPlayer);
        else if(seekbarKillInsultedPlayerVotes.getProgress()>seekbarKillAgressivePlayerVotes.getProgress())
            mLoosersList.add(mInsultedHumanPlayer);

       for(HumanPlayer hp: mLoosersList)
           mTheGame.kill(hp);
    }

    private String stringDuelResults(){

        String result = "Zabici zostali: ";
        for(HumanPlayer hp: mTheGame.getTemporaryLastTimeKilledPlayersList())
            result+=hp.getPlayerName()+", ";

        return result;
    }


    //UserInterface Methods:

    private void vUiSetupDialog(){
        getDialog().setTitle(R.string.duel);
        getDialog().setCancelable(false);
    }

    private void vUiSetupUserInterface(){
        vUiShowGameTipFragment();
        vUiSetupSeekBars();
        vUiSetupTextViews();
        vUiSetButtonsListeners();
    }


    private void vUiSetupTextViews(){
        agressivePlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mAgressiveHumanPlayer.getPlayerName()));
        insultedPlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mInsultedHumanPlayer.getPlayerName()));
    }


    private void vUiSetButtonsListeners(){
        agressivePlayerKillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button_confirmVoting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createPopupAlertDialog(getString(R.string.confirm),stringDuelResults(),
                        null, null).show();
                //mConfirmVotingAlertDialog.show();
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


    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.tip_duel),false);
    }
*/


}
