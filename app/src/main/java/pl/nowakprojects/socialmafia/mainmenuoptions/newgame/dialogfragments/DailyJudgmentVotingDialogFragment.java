package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments;

import android.content.Context;
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
import me.tankery.lib.circularseekbar.CircularSeekBar;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.interfaces.OnPlayerKilledListener;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DailyJudgmentVotingDialogFragment extends DialogFragment {

    OnPlayerKilledListener mPlayerKillCallback;

    DialogFragment mInstance;
    //Members:
    TheGame mTheGame;
    HumanPlayer mAgressiveHumanPlayer;
    HumanPlayer mInsultedHumanPlayer;
    AlertDialog mConfirmVotingAlertDialog;
    ArrayList<HumanPlayer> mLoosersList;

    public DailyJudgmentVotingDialogFragment(){}

    public DailyJudgmentVotingDialogFragment(TheGame mTheGame, HumanPlayer hpAgressivePlayer, HumanPlayer hpInsultedPlayer) {
        this.mTheGame = mTheGame;
        mInstance = this;
    }

    //Views
    View fragmentView;

    @BindView(R.id.duelCircleSeekBar) CircularSeekBar seekBar;

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
        fragmentView = inflater.inflate(R.layout.dialog_daily_judgment, container, false);
        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();
        return fragmentView;
    }


    private void vUiSetupUserInterface(){
        vUiShowGameTipFragment();
    }

    private void vUiSetupCircleSeekBar(){
        seekBar.setMax(mTheGame.getLiveHumanPlayers().size());
        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                String message = String.format("Progress changed to %.2f, fromUser %s", progress, fromUser);
                Log.d("Main", message);
                //textProgress.setText(message);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                Log.d("Main", "onStopTrackingTouch");
                //textEvent.setText("");
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                Log.d("Main", "onStartTrackingTouch");
                //textEvent.setText("touched | ");
            }
        });
    }


    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.tip_duel),false);
    }



}
