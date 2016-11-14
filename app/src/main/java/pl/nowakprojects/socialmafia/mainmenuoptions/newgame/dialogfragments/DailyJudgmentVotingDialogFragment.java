package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.DailyVotingFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.interfaces.OnDaytimeFinishedListener;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.interfaces.OnGoodOrBadCheckedListener;
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
    private MaterialDialog mConfirmMaterialDialog;
    private MaterialDialog mKilledMaterialDialog;

    public DailyJudgmentVotingDialogFragment(){}

    public DailyJudgmentVotingDialogFragment(TheGame theGame, HumanPlayer firstPlayerToVote, HumanPlayer secondPlayerToVote) {
        this.mTheGame = theGame;
        this.mFirstPlayerToVote = firstPlayerToVote;
        this.mSecondPlayerToVote = secondPlayerToVote;
        mDailyVotedResult=mTheGame.getCurrentDayOutVoted();
    }

    //Views
    View fragmentView;
    @BindView(R.id.duelCircleSeekBar) CircularSeekBar seekBar;
    @BindView(R.id.dailyOutvoted) TextView mDailyOutVoted;
    @BindView(R.id.choseRedPlayerName) TextView mFirstPlayerName;
    @BindView(R.id.choseBluePlayerName) TextView mSecondPlayerName;
    @BindView(R.id.button_confirmDailyJudgment) Button mConfirmJudgmentButton;

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

    private float firstPlayerVotes(){
        if(mTheGame.getLiveHumanPlayers().size()-seekBar.getProgress()<0)
            return 0;
        else if(Math.ceil(mTheGame.getLiveHumanPlayers().size()-seekBar.getProgress())>mTheGame.getLiveHumanPlayers().size())
            return mTheGame.getLiveHumanPlayers().size();
        else
            return mTheGame.getLiveHumanPlayers().size()-seekBar.getProgress();
    }

    private float secondPlayerVotes(){
        if(seekBar.getProgress()<0)
            return 0;
        else if(Math.ceil(seekBar.getProgress())>mTheGame.getLiveHumanPlayers().size())
            return mTheGame.getLiveHumanPlayers().size();
        else
            return seekBar.getProgress();
    }

    private HumanPlayer outvotedHumanPlayer(){
        if(seekBar.getProgress()>mTheGame.getLiveHumanPlayers().size()-seekBar.getProgress())
            return mSecondPlayerToVote;
        else
            return mFirstPlayerToVote;
    }

    private String killingResultString(){
        String result="";
        for(HumanPlayer hp: mTheGame.getmTemporaryLastTimeKilledPlayerList())
            result+="- "+hp.getPlayerName()+" \n";
        return result;
    }

    private void vUiSetupUserInterface(){
        vUiSetupDialogFragment();
        vUiSetupCircleSeekBar();
        vUiSetupTextView();
        vUiSetupButtonListener();
        vUiShowGameTipFragment();
    }

    private void vUiSetupDialogFragment(){
        setCancelable(false);
        getDialog().setTitle(R.string.dailyJudgment);
    }

    private void vUiSetupTextView(){
        if(mDailyVotedResult==DailyVotingFragment.OUTVOTED.CHECKING)
            mDailyOutVoted.setText(R.string.checking);
        else
            mDailyOutVoted.setText(R.string.killing);

        vUiUpdateTextView();
    }

    private void vUiSetupCircleSeekBar(){
        seekBar.setMax(mTheGame.getLiveHumanPlayers().size());
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                vUiUpdateTextView();
            }
            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
    }

    private void vUiSetupButtonListener(){
        mConfirmJudgmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiSetConfirmMaterialDialog();
                mConfirmMaterialDialog.show();
            }
        });
    }

    private void vUiUpdateTextView(){
        mFirstPlayerName.setText(getString(R.string.dialy_voting_player_one,mFirstPlayerToVote.getPlayerName(),Double.toString(Math.ceil((double)firstPlayerVotes()))));
        mSecondPlayerName.setText(getString(R.string.dialy_voting_player_one,mSecondPlayerToVote.getPlayerName(),Double.toString(Math.ceil((double)secondPlayerVotes()))));
       /* if(firstPlayerVotes()>secondPlayerVotes()){
            mFirstPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            mSecondPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        }else if(firstPlayerVotes()<secondPlayerVotes()){
            mFirstPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            mSecondPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        }else if(firstPlayerVotes()==secondPlayerVotes())
            mFirstPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        mSecondPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);*/
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
                            mTheGame.kill(outvotedHumanPlayer());
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
