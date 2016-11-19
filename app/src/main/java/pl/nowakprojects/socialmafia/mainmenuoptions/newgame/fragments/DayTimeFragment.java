package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.DailyJudgmentVotingDialogFragment;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DayTimeFragment extends Fragment {

    public static final String DAILY_JUDGMENT_DIALOG = "TheGameActionActivity.DAILY_JUDGMENT_DIALOG";

    TheGame mTheGame;

    DailyJudgmentVotingDialogFragment mDailyJudgmentVotingDialogFragment;
    MaterialDialog mChoosingPlayerMaterialDialog;
    MaterialDialog mDayBeginStatsMaterialDalog;
    TimeCounterClass dayTimeTimer;
    @BindView(R.id.dayTimerTextView)    TextView mDayTimerTextView;
    @BindView(R.id.dayNumberTextView)    TextView mDayNumberTextView;
    @BindView(R.id.finishTheDayButton)  Button mFinishDayButton;


    public DayTimeFragment(TheGame theGame) {
        this.mTheGame=theGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_time, container, false);

        ButterKnife.bind(this,fragmentView);

        vUiSetupUserInterface();
        startDayTimer();


        return fragmentView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        dayTimeTimer.cancel();
    }

    private void startDayTimer(){
        dayTimeTimer = new TimeCounterClass(mTheGame.getMaxDailyTime(), 1000);
        dayTimeTimer.start();
    }

    private String sDailyJudgmentTitle(){
        if(mTheGame.getCurrentDayOutVoted()== DailyVotingFragment.OUTVOTED.KILLING)
            return getString(R.string.killing);
        else
            return getString(R.string.checking);
    }

    private void vUiSetupUserInterface(){
        vUiSetupTextView();
        vUiSetupButtonListener();
        vUiSetupMaterialDialog();

        mDayBeginStatsMaterialDalog.show();
    }

    private void vUiSetupTextView(){
        mDayNumberTextView.setText(getString(R.string.day_number,mTheGame.getCurrentDayNumber()));
    }

    private void vUiSetupButtonListener(){
        mFinishDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTheGame.getCurrentDayOutVoted()==null)
                    Toast.makeText(getActivity(),R.string.no_kill_check_choose,Toast.LENGTH_SHORT).show();
                else {
                    //dayTimeTimer.cancel();
                    vUiSetMaterialDialog();
                    mChoosingPlayerMaterialDialog.show();
                }
            }
        });

    }

    private void vUiSetupMaterialDialog(){
        //if(mTheGame.isFirstDay()){}

        mDayBeginStatsMaterialDalog = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.game_stats)
                .cancelable(false)
                .content(
                        getString(R.string.new_day_stats,
                                mTheGame.getCurrentDayNumber(),
                                //TUTAJ COS NIE DZIALA!? JAKBY LISTA ZAWSZE BYLA PUSTA NA POCZATKU DNIA
                                mTheGame.getTemporaryLastTimeKilledPlayersList().isEmpty() ? getString(R.string.noone_died) : getString(R.string.somebody_died, mTheGame.lastTimeKilledPlayersString()),
                                getString(R.string.fraction_stats,mTheGame.getPlayersStartAmount(),mTheGame.getLiveHumanPlayers().size(),mTheGame.getLiveTownPlayers().size(),mTheGame.getLiveMafiaPlayers().size(),mTheGame.getLiveSyndicatePlayers().size())
                        )
                )
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .build();
    }

    private void vUiSetMaterialDialog(){

            mChoosingPlayerMaterialDialog = new MaterialDialog.Builder(this.getActivity())
                        .title(sDailyJudgmentTitle())
                        .autoDismiss(false)
                        .items(mTheGame.getLiveHumanPlayersNames())
                        .content(R.string.players_voting_explanation)
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
                                Integer[] selected = mChoosingPlayerMaterialDialog.getSelectedIndices();
                                if(selected!=null&&selected.length==2){
                                    for(int i=0;i<selected.length;i++)
                                        mTheGame.choseDailyJudgmentPlayersList.add(mTheGame.getLiveHumanPlayers().get(selected[i]));

                                    mChoosingPlayerMaterialDialog.dismiss();
                                    vUiSetAndShowDailyJudgmentFragment();
                                }else
                                    Toast.makeText(getActivity(),R.string.needmoreplayers,Toast.LENGTH_SHORT).show();

                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mChoosingPlayerMaterialDialog.dismiss();
                            }
                        })
                        .build();

        }

    private void vUiSetAndShowDailyJudgmentFragment(){
        mDailyJudgmentVotingDialogFragment = new DailyJudgmentVotingDialogFragment(mTheGame,mTheGame.choseDailyJudgmentPlayersList.get(0),mTheGame.choseDailyJudgmentPlayersList.get(1));
        mDailyJudgmentVotingDialogFragment.show(getChildFragmentManager(),DAILY_JUDGMENT_DIALOG);
    }

    /**
     * Odliczanie czasu do końca dnia - timer
     */
    public class TimeCounterClass extends CountDownTimer {
        public TimeCounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                    TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                    TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

            mDayTimerTextView.setText(hms);
        }

        @Override
        public void onFinish() {
            mDayTimerTextView.setText(getString(R.string.dayTimeEnded));
            Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
        }
    }

}
