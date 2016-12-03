package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import pl.nowakprojects.socialmafia.ui.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.ui.newgame.interfaces.OnDaytimeFinishedListener;
import pl.nowakprojects.socialmafia.ui.newgame.interfaces.OnGoodOrBadCheckedListener;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

/**
 * Created by Mateusz on 19.10.2016.
 */

//NIE ZOSTAL PRZEWIDZIANY REMIS W GLOSOWANIU
public class DailyJudgmentVotingDialogFragment extends DialogFragment implements OnGoodOrBadCheckedListener {

    OnDaytimeFinishedListener mDaytimeFinishedCallback;

    DailyJudgmentVotingDialogFragment dailyJudgmentVotingDialogFragment;
    TheGame mTheGame;
    DailyVotingFragment.OUTVOTED mDailyVotedResult;
    HumanPlayer mFirstPlayerToVote;
    HumanPlayer mSecondPlayerToVote;
    HumanPlayer mThirdPlayerToVote;
    ArrayList<String> mFirstPlayersNamesVotesList = new ArrayList<>();
    ArrayList<String> mSecondPlayersNamesVotesList = new ArrayList<>();
    ArrayList<String> mThirdPlayersNamesVotesList = new ArrayList<>();


    MaterialDialog mVotingFirstPlayersMaterialDialog;
    MaterialDialog mVotingSecondPlayersMaterialDialog;
    MaterialDialog mVotingThirdPlayersMaterialDialog;
    private MaterialDialog mConfirmMaterialDialog;
    private MaterialDialog mKilledMaterialDialog;

    public DailyJudgmentVotingDialogFragment(){}

    public DailyJudgmentVotingDialogFragment(TheGame theGame, HumanPlayer firstPlayerToVote, HumanPlayer secondPlayerToVote) {
        this.mTheGame = theGame;
        this.mFirstPlayerToVote = firstPlayerToVote;
        this.mSecondPlayerToVote = secondPlayerToVote;
        mDailyVotedResult=mTheGame.getCurrentDayOutVoted();
    }

    public DailyJudgmentVotingDialogFragment(TheGame theGame, HumanPlayer firstPlayerToVote, HumanPlayer secondPlayerToVote, HumanPlayer thirdPlayerToVote) {
        this.mTheGame = theGame;
        this.mFirstPlayerToVote = firstPlayerToVote;
        this.mSecondPlayerToVote = secondPlayerToVote;
        this.mThirdPlayerToVote = thirdPlayerToVote;
        mDailyVotedResult=mTheGame.getCurrentDayOutVoted();
    }

    //Views
    View fragmentView;
    @BindView(R.id.dailyOutvoted) TextView mDailyOutVoted;
    @BindView(R.id.firstPlayerNameAndVotes) TextView firstPlayerNameAndVotesTextView;
    @BindView(R.id.secondPlayerNameAndVotes) TextView secondPlayerNameAndVotesTextView;
    @BindView(R.id.thirdPlayerNameAndVotes) TextView thirdPlayerNameAndVotesTextView;
    @BindView(R.id.firstPlayerKillButton) Button firstPlayerKillButton;
    @BindView(R.id.secondPlayerKillButton) Button secondPlayerKillButton;
    @BindView(R.id.thirdPlayerKillButton) Button thirdPlayerKillButton;
    @BindView(R.id.button_confirmDailyJudgment) Button mConfirmJudgmentButton;
    /*@BindView(R.id.duelCircleSeekBar) CircularSeekBar seekBar;
    @BindView(R.id.dailyOutvoted) TextView mDailyOutVoted;
    @BindView(R.id.choseRedPlayerName) TextView mFirstPlayerName;
    @BindView(R.id.choseBluePlayerName) TextView mSecondPlayerName;
    @BindView(R.id.button_confirmDailyJudgment) Button mConfirmJudgmentButton;*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mDaytimeFinishedCallback = (OnDaytimeFinishedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement interface!");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachFragment(getParentFragment());
        dailyJudgmentVotingDialogFragment=this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.dialog_daily_judgment, container, false);
        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();
        return fragmentView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
       // GameTipFragment.vRemove(this);
    }

    private HumanPlayer outvotedHumanPlayer(){
        if(mFirstPlayersNamesVotesList.size()<mSecondPlayersNamesVotesList.size())
            return mSecondPlayerToVote;
        else
            return mFirstPlayerToVote;
    }

    private ArrayList<HumanPlayer> generateToKillList(){
        ArrayList<HumanPlayer> playersToKill = new ArrayList<>();
            playersToKill.add(outvotedHumanPlayer());

        if(outvotedHumanPlayer()==mFirstPlayerToVote&&outvotedHumanPlayer().hasSaintRole()){
            for(String playerName: mFirstPlayersNamesVotesList)
                playersToKill.add(mTheGame.findLiveHumanPlayerByName(playerName));
        }else if((outvotedHumanPlayer()==mSecondPlayerToVote&&outvotedHumanPlayer().hasSaintRole())){
            for(String playerName: mSecondPlayersNamesVotesList)
                playersToKill.add(mTheGame.findLiveHumanPlayerByName(playerName));
        }

        return playersToKill;
    }

    private String killingResultString(){
        String result="";
        for(HumanPlayer hp: mTheGame.getTemporaryLastTimeKilledPlayersList())
            result+="- "+hp.getPlayerName()+" \n";
        return result;
    }

    private void vUiSetupUserInterface(){
        vUiSetupPlayersTextAndButtonVisiblity();
        vUiSetupDialogFragment();
        //vUiSetupCircleSeekBar();
        vUiSetupButtonText();
        vUiSetupTextView();
        vUiSetupButtonListener();
        vUiShowGameTipFragment();
    }

    private void vUiSetupDialogFragment(){
        setCancelable(false);
        getDialog().setTitle(R.string.dailyJudgment);
    }

    private void vUiSetupPlayersTextAndButtonVisiblity(){
        if(mThirdPlayerToVote==null){
            thirdPlayerKillButton.setVisibility(View.GONE);
            thirdPlayerNameAndVotesTextView.setVisibility(View.GONE);
        }

    }

    private void vUiSetupTextView(){
        if(mDailyVotedResult==DailyVotingFragment.OUTVOTED.CHECKING)
            mDailyOutVoted.setText(R.string.checking);
        else
            mDailyOutVoted.setText(R.string.killing);

        vUiUpdateTextView();
    }

    private String getVoteTitle(){
        if(mDailyVotedResult==DailyVotingFragment.OUTVOTED.CHECKING)
            return getContext().getString(R.string.checking);
        else
            return getContext().getString(R.string.killing);
    }

    private void vUiSetupButtonText(){
        firstPlayerKillButton.setText(getString(R.string.who_want_vote,mFirstPlayerToVote.getPlayerName()));
        secondPlayerKillButton.setText(getString(R.string.who_want_vote,mSecondPlayerToVote.getPlayerName()));
    }

    private void vUiSetupButtonListener(){

        firstPlayerKillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiUpdateMaterialDialog();
                mVotingFirstPlayersMaterialDialog.show();
            }
        });

        secondPlayerKillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiUpdateMaterialDialog();
                mVotingSecondPlayersMaterialDialog.show();
            }
        });

        mConfirmJudgmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiSetConfirmMaterialDialog();
                mConfirmMaterialDialog.show();
            }
        });
    }

    public void vUiUpdateMaterialDialog(){
        List<String> itemsForFirstVotingPlayer = mTheGame.getLiveHumanPlayersNames();
        itemsForFirstVotingPlayer.removeAll(mSecondPlayersNamesVotesList);

        List<String> itemsForSecondVotingPlayer = mTheGame.getLiveHumanPlayersNames();
        itemsForSecondVotingPlayer.removeAll(mFirstPlayersNamesVotesList);

        mVotingFirstPlayersMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(firstPlayerKillButton.getText())
                .items(itemsForFirstVotingPlayer)
                .content(R.string.judgment_voting_explanation,getVoteTitle(),mFirstPlayerToVote.getPlayerName())
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
                        Integer[] selected = mVotingFirstPlayersMaterialDialog.getSelectedIndices();

                        mFirstPlayersNamesVotesList.clear();

                        if(selected!=null)
                            for (int i = 0; i < selected.length; i++)
                                mFirstPlayersNamesVotesList.add(mTheGame.getLiveHumanPlayersNames().get(selected[i]));

                        vUiUpdateTextView();
                    }
                })
                .build();

        mVotingSecondPlayersMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(secondPlayerKillButton.getText())
                .items(itemsForSecondVotingPlayer)
                .content(R.string.judgment_voting_explanation,getVoteTitle(),mSecondPlayerToVote.getPlayerName())
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
                        Integer[] selected = mVotingSecondPlayersMaterialDialog .getSelectedIndices();

                        mSecondPlayersNamesVotesList.clear();

                        if(selected!=null)
                            for (int i = 0; i < selected.length; i++)
                                mSecondPlayersNamesVotesList.add(mTheGame.getLiveHumanPlayersNames().get(selected[i]));

                        vUiUpdateTextView();
                    }
                })
                .build();
    }

    private void vUiUpdateTextView(){
        firstPlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mFirstPlayerToVote.getPlayerName(),mFirstPlayersNamesVotesList.size()));
        secondPlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mSecondPlayerToVote.getPlayerName(),mSecondPlayersNamesVotesList.size()));

        if(mThirdPlayerToVote!=null)
            thirdPlayerNameAndVotesTextView.setText(getString(R.string.player_name_votes,mThirdPlayerToVote.getPlayerName(),mThirdPlayersNamesVotesList.size()));
    }

    private void vUiSetConfirmMaterialDialog(){
        mConfirmMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.confirm)
                .content(getString(R.string.confirmChoosePlayer, outvotedHumanPlayer().getPlayerName()))
                .positiveText(R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(mTheGame.isCheckingJudgment()){
                        ShowingPlayerGoodOrBadDialog showingPlayerGoodOrBadDialog = new ShowingPlayerGoodOrBadDialog(outvotedHumanPlayer(), dailyJudgmentVotingDialogFragment);
                            //showingPlayerGoodOrBadDialog.setTargetFragment(this,0);
                        showingPlayerGoodOrBadDialog.show(getChildFragmentManager(), "PolicemanAction");
                        }else if(mTheGame.isKillingJudgment()){
                            mTheGame.beginKilling();
                            for(HumanPlayer hp: generateToKillList())
                                mTheGame.kill(hp);
                            vUiSetAndShowKillingResultMaterialDialog();
                        }
                    }
                })
                .negativeText(R.string.no)
                .build();
    }

    private void vUiSetAndShowKillingResultMaterialDialog(){
        mKilledMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.killedWere)
                .content(killingResultString())
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getDialog().dismiss();
                        mDaytimeFinishedCallback.onDaytimeFinished();
                    }
                })
                .show();
    }

    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.tip_daily_judgment),true);
    }


    @Override
    public void onGodOrBadChecked() {
        //mConfirmMaterialDialog.dismiss();
        getDialog().cancel();
        mDaytimeFinishedCallback.onDaytimeFinished();
    }
}
