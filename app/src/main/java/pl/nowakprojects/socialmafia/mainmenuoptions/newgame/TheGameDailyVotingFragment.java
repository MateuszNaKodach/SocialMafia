package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameDailyVotingFragment extends Fragment {

    private TheGameActionActivity theGameActionActivity;
    TheGame theGame;
    SeekBar seekbar_checkingVote;
    SeekBar seekbar_killingVote;
    TextView textView_iCheckingVotes;
    TextView textView_iKillingVotes;
    Button button_confirmVoting;
    AlertDialog confirmVotingDialog;

    // int i_checkingVotes=0;
    //int i_killingVotes=0;

    public TheGameDailyVotingFragment(){

    }

    public TheGameDailyVotingFragment(TheGameActionActivity theGameActionActivity, TheGame theGame) {
        this.theGameActionActivity = theGameActionActivity;
        this.theGame = theGame;
        //i_checkingVotes=theGame.getLiveHumanPlayers().size();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_judgment, container, false);

        textView_iCheckingVotes = (TextView) fragmentView.findViewById(R.id.textView_iCheckingVotes);
        textView_iKillingVotes = (TextView) fragmentView.findViewById(R.id.textView_iKillingVotes);
        button_confirmVoting = (Button) fragmentView.findViewById(R.id.confirmJudge);

        seekbar_checkingVote = (SeekBar) fragmentView.findViewById(R.id.checkingSlider);
        seekbar_checkingVote.setMax(theGame.getLiveHumanPlayers().size());
        seekbar_checkingVote.setProgress(seekbar_checkingVote.getMax());
        seekbar_checkingVote.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //i_checkingVotes=progress;
                seekbar_killingVote.setProgress(seekbar_killingVote.getMax() - progress);
                vUpdateProgressTextView(seekbar_checkingVote, textView_iCheckingVotes);
                vUpdateProgressTextView(seekbar_killingVote, textView_iKillingVotes);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar_killingVote = (SeekBar) fragmentView.findViewById(R.id.killingSlider);
        seekbar_killingVote.setMax(seekbar_checkingVote.getMax());
        seekbar_killingVote.setProgress(0);
        seekbar_killingVote.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //i_killingVotes=progress;
                seekbar_checkingVote.setProgress(seekbar_checkingVote.getMax() - progress);
                vUpdateProgressTextView(seekbar_checkingVote, textView_iCheckingVotes);
                vUpdateProgressTextView(seekbar_killingVote, textView_iKillingVotes);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        textView_iCheckingVotes.setText(String.valueOf(seekbar_checkingVote.getProgress()));
        textView_iKillingVotes.setText(String.valueOf(seekbar_killingVote.getProgress()));

        button_confirmVoting.setOnClickListener(new View.OnClickListener() {
            boolean killing = false;

            @Override
            public void onClick(View view) {
                if (seekbar_killingVote.getProgress() > seekbar_checkingVote.getProgress())
                    killing = true;
                createPopupAlertDialog(getString(R.string.confirmVoting), (killing ? getString(R.string.confirmKilling) : getString(R.string.confirmChecking)), null, null).show();
                confirmVotingDialog.show();
            }
        });
        return fragmentView;
    }

    private String vUpdateProgressTextView(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
        return textView.getText().toString();
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

}
