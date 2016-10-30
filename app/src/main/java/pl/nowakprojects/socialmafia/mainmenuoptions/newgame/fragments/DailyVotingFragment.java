package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.TheGameDuelActionVotingFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DailyVotingFragment extends Fragment {

    private TheGame mTheGame;
    private MaterialDialog mConfirmMaterialDialog;


    public DailyVotingFragment(){}

    public DailyVotingFragment(TheGame theGame) {this.mTheGame = theGame;}


    @BindView(R.id.checkingSlider)   SeekBar mCheckingVoteSeekBar;
    @BindView(R.id.killingSlider)   SeekBar mKillingVoteSeekBar;
    @BindView(R.id.textView_iCheckingVotes)  TextView mCheckingVotesTextView;
    @BindView(R.id.textView_iKillingVotes)  TextView mKillingVotesTextView;
    @BindView(R.id.confirmJudge)  Button mConfirmVotingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_judgment, container, false);

        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();

        return fragmentView;
    }



    //Setting User Interface methods:--------------------------------------------------------------
    private void vUiSetupUserInterface(){
        vUiSetupButtonListener();
        vUiSetupTextView();
        vUiSetupSeekBar();
    }

    private void vUiSetupSeekBar(){
        mCheckingVoteSeekBar.setMax(mTheGame.getLiveHumanPlayers().size());
        mCheckingVoteSeekBar.setProgress(mCheckingVoteSeekBar.getMax());
        mCheckingVoteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //i_checkingVotes=progress;
                mKillingVoteSeekBar.setProgress(mKillingVoteSeekBar.getMax() - progress);
                sUiUpdateProgressTextView(mCheckingVoteSeekBar, mCheckingVotesTextView);
                sUiUpdateProgressTextView(mKillingVoteSeekBar, mKillingVotesTextView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mKillingVoteSeekBar.setMax(mCheckingVoteSeekBar.getMax());
        mKillingVoteSeekBar.setProgress(0);
        mKillingVoteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //i_killingVotes=progress;
                mCheckingVoteSeekBar.setProgress(mCheckingVoteSeekBar.getMax() - progress);
                sUiUpdateProgressTextView(mCheckingVoteSeekBar, mCheckingVotesTextView);
                sUiUpdateProgressTextView(mKillingVoteSeekBar, mKillingVotesTextView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void vUiSetupTextView(){
        mCheckingVotesTextView.setText(String.valueOf(mCheckingVoteSeekBar.getProgress()));
        mKillingVotesTextView.setText(String.valueOf(mKillingVoteSeekBar.getProgress()));
    }

    private void vUiSetupButtonListener(){
        mConfirmVotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiSetupMaterialDialog();
                mConfirmMaterialDialog.show();
            }
        });
    }

    private String sUiUpdateProgressTextView(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
        return textView.getText().toString();
    }

    private void vUiSetupMaterialDialog(){

        mConfirmMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                .title(((mKillingVoteSeekBar.getProgress() > mCheckingVoteSeekBar.getProgress()) ?
                        getString(R.string.killing) : getString(R.string.checking)))
                .content((mKillingVoteSeekBar.getProgress() > mCheckingVoteSeekBar.getProgress()) ?
                        getString(R.string.confirmKilling) : getString(R.string.confirmChecking))
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .build();



    }


}
