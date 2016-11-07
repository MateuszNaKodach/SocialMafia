package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DailyVotingFragment extends Fragment {

    public enum OUTVOTED {KILLING, CHECKING, DRAW}


    private enum DRAWSOLUTION {CANCEL, GRAVEWILL, RANDOMKILLORCHECK}


    private TheGame mTheGame;
    private MaterialDialog mConfirmMaterialDialog;
    private MaterialDialog mGraveWillMaterialDialog;
    private boolean mbVotingConfirmed;
    OUTVOTED mVotedResult;

    public DailyVotingFragment() {
    }

    public DailyVotingFragment(TheGame theGame) {
        this.mTheGame = theGame;
    }


    @BindView(R.id.checkingSlider)
    SeekBar mCheckingVoteSeekBar;
    @BindView(R.id.killingSlider)
    SeekBar mKillingVoteSeekBar;
    @BindView(R.id.textView_iCheckingVotes)
    TextView mCheckingVotesTextView;
    @BindView(R.id.textView_iKillingVotes)
    TextView mKillingVotesTextView;
    @BindView(R.id.confirmJudge)
    Button mConfirmVotingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_judgment, container, false);

        ButterKnife.bind(this, fragmentView);
        vUiSetupUserInterface();

        return fragmentView;
    }


    private OUTVOTED calculateVotingResult() {
        if (mKillingVoteSeekBar.getProgress() > mCheckingVoteSeekBar.getProgress())
            return OUTVOTED.KILLING;
        else if (mKillingVoteSeekBar.getProgress() < mCheckingVoteSeekBar.getProgress())
            return OUTVOTED.CHECKING;
        else
            return OUTVOTED.DRAW;
    }

    private String sVotingResultTitle(OUTVOTED votingResult) {
        if (votingResult == OUTVOTED.KILLING)
            return getString(R.string.killing);
        else if (votingResult == OUTVOTED.CHECKING)
            return getString(R.string.checking);
        else
            return getString(R.string.draw);
    }

    private DRAWSOLUTION bringDrawSolution(int solutionIndex) {
        if (solutionIndex == 2)
            return DRAWSOLUTION.RANDOMKILLORCHECK;
        else if (solutionIndex == 1)
            return DRAWSOLUTION.GRAVEWILL;
        else
            return DRAWSOLUTION.CANCEL;
    }

    private void vConfrimVoting() {
        mbVotingConfirmed = true;
        mCheckingVoteSeekBar.setEnabled(false);
        mKillingVoteSeekBar.setEnabled(false);
        mConfirmVotingButton.setEnabled(false);
        if(mVotedResult==OUTVOTED.KILLING)
            mConfirmVotingButton.setText(getString(R.string.confirmed_voting,getString(R.string.killing)));
        else
            mConfirmVotingButton.setText(getString(R.string.confirmed_voting,getString(R.string.checking)));
        mTheGame.setCurrentDayOutVoted(mVotedResult);
    }

    private OUTVOTED randomVotingResult() {
        int random = (int) (Math.random() * 2);
        if (random == 0)
            return OUTVOTED.KILLING;
        else
            return OUTVOTED.CHECKING;
    }


    //Setting User Interface methods:--------------------------------------------------------------
    private void vUiSetupUserInterface() {
        vUiSetupButtonListener();
        vUiSetupSeekBar();
        vUiSetupTextView();
    }

    public void vUiUpdateUserInterface() {
        if (mbVotingConfirmed == false) {
            vUiSetupTextView();
            vUiSetupSeekBar();
        }
    }

    private void vUiSetupSeekBar() {
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

    private void vUiSetupTextView() {
        mCheckingVotesTextView.setText(String.valueOf(mCheckingVoteSeekBar.getProgress()));
        mKillingVotesTextView.setText(String.valueOf(mKillingVoteSeekBar.getProgress()));
    }

    private void vUiSetupButtonListener() {
        mConfirmVotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vUiSetMaterialDialog();
                mConfirmMaterialDialog.show();
            }
        });
    }

    private String sUiUpdateProgressTextView(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
        return textView.getText().toString();
    }

    private void vUiSetMaterialDialog() {
        //if (mVotedResult == null)
            mVotedResult = calculateVotingResult();

        if (mVotedResult == OUTVOTED.KILLING || mVotedResult == OUTVOTED.CHECKING) {
            mConfirmMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                    .title(sVotingResultTitle(mVotedResult))
                    .autoDismiss(false)
                    .content(mVotedResult == OUTVOTED.KILLING ?
                            getString(R.string.confirmKilling) : getString(R.string.confirmChecking))
                    .positiveText(R.string.yes)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mTheGame.setCurrentDayOutVoted(mVotedResult);
                            vConfrimVoting();
                            mConfirmMaterialDialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mConfirmMaterialDialog.dismiss();
                        }
                    })
                    .negativeText(R.string.no)
                    .build();
        } else {

            mConfirmMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                    .title(R.string.draw)
                    .content(R.string.draw_how_to_solve)
                    .items(R.array.voting_draw_solutions)
                    .autoDismiss(false)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            return true;
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (bringDrawSolution(mConfirmMaterialDialog.getSelectedIndex()) == DRAWSOLUTION.GRAVEWILL) {
                                if (mTheGame.getLastKilledPlayer()!=null&&mGraveWillMaterialDialog!=null){
                                    mGraveWillMaterialDialog.show();
                                }else
                                    Toast.makeText(getActivity(),R.string.nobody_killed,Toast.LENGTH_SHORT).show();

                            } else if (bringDrawSolution(mConfirmMaterialDialog.getSelectedIndex()) == DRAWSOLUTION.RANDOMKILLORCHECK) {
                                mVotedResult = randomVotingResult();
                                mConfirmMaterialDialog.dismiss();
                                vUiSetMaterialDialog();
                            } else {
                                mVotedResult = null;
                                mConfirmMaterialDialog.dismiss();
                            }
                        }
                    })
                    .positiveText(R.string.choose)
                    .show();
        }


        if(mTheGame.getLastKilledPlayer()!=null) {
            mGraveWillMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                    .title(getString(R.string.gravewilltitle, mTheGame.getLastKilledPlayer().getPlayerName()))
                    .content(R.string.gravewill_explanation)
                    .items(R.array.check_kill_choice)
                    .autoDismiss(false)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            return true;
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (mGraveWillMaterialDialog.getSelectedIndex() == 0)
                                mVotedResult = OUTVOTED.CHECKING;
                            else
                                mVotedResult = OUTVOTED.KILLING;

                            //vConfrimVoting();
                            mGraveWillMaterialDialog.dismiss();
                            vUiSetMaterialDialog();
                        }
                    })
                    .positiveText(R.string.choose)
                    .build();
        }
    }




    }


