package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.DuelVotingDialogFragment;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class DuelChallengesFragment extends Fragment{


    TheGame mTheGame;
    String msCurrentAgressivePlayer;
    String msCurrentInsultedPlayer;
    MaterialDialog mChallengeConfirmationDialog;
    MaterialDialog mChallengedInsultedAgreeDialog;
    android.support.v4.app.FragmentManager fragmentManager;


    public DuelChallengesFragment(){}
    public DuelChallengesFragment(TheGame mTheGame) {
        this.mTheGame = mTheGame;
    }


    @BindView(R.id.textView_remainedDuels) TextView mRemainedDuelsTextView;
    @BindView(R.id.textView_thrownChallenges)TextView mThrownChallengesTextView;
    @BindView(R.id.spinner_agressive)Spinner mAgresivePlayerSpinner;
    @BindView(R.id.spinner_insulted)Spinner mInsultedPlayerSpinner;
    @BindView(R.id.button_confirmChallenge) Button mConfirmChallengeButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_day_duels_challenge, container, false);

        ButterKnife.bind(this,fragmentView);
        vUiSetupUserInterface();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
       // vUiUpdateSpinners();
    }


    public boolean samePlayersChose(){
     return (mTheGame.findHumanPlayerByName(mAgresivePlayerSpinner.getSelectedItem().toString())==mTheGame.findHumanPlayerByName(mInsultedPlayerSpinner.getSelectedItem().toString()));
    }

    //Setting User Interface methods:---------------------------------------------------------------
    private void vUiSetupUserInterface(){
        vUiSetupSpinners();
        vUiSetupButtonsListeners();
    }

    public void vUiUpdateUserInterface(){
        vUiUpdateSpinners();
    }

    private void vUiSetupSpinners(){
        ArrayList<String> playerNames = new ArrayList<String>();
        for (HumanPlayer humanPlayer : mTheGame.getLiveHumanPlayers())
            playerNames.add(humanPlayer.getPlayerName());

        ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
        choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgresivePlayerSpinner.setAdapter(choosingSpinnerAdapter);
        mInsultedPlayerSpinner.setAdapter(choosingSpinnerAdapter);
    }

    private void vUiUpdateSpinners(){
        ArrayList<String> playerNames = new ArrayList<String>();
        for (HumanPlayer humanPlayer : mTheGame.getLiveHumanPlayers())
            playerNames.add(humanPlayer.getPlayerName());

        ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
        mAgresivePlayerSpinner.setAdapter(choosingSpinnerAdapter);
        mInsultedPlayerSpinner.setAdapter(choosingSpinnerAdapter);
    }

    private void vUiSetupButtonsListeners(){
        mConfirmChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(samePlayersChose())
                    Toast.makeText(getActivity(),R.string.theSameDuelPlayers,Toast.LENGTH_SHORT).show();
                else{
                    vUiSetupMaterialDialog();
                    mChallengeConfirmationDialog.show();
                }
            }
        });
    }

    private void vUiSetupMaterialDialog(){
        msCurrentAgressivePlayer = mAgresivePlayerSpinner.getSelectedItem().toString();
        msCurrentInsultedPlayer = mInsultedPlayerSpinner.getSelectedItem().toString();

        mChallengeConfirmationDialog = new MaterialDialog.Builder(this.getActivity())
                .title(getString(R.string.duelActionDialogTitle, msCurrentAgressivePlayer, msCurrentInsultedPlayer))
                .content(getString(R.string.confirmDuelChallenge, msCurrentAgressivePlayer, msCurrentInsultedPlayer))
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mChallengedInsultedAgreeDialog.show();
                    }
                })
                .build();

        mChallengedInsultedAgreeDialog = new MaterialDialog.Builder(this.getActivity())
                .title( getString(R.string.duelActionDialogTitle, msCurrentAgressivePlayer, msCurrentInsultedPlayer))
                .content(getString(R.string.agreeDuelChallenge, msCurrentAgressivePlayer, msCurrentInsultedPlayer))
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        fragmentManager = getChildFragmentManager();
                        DuelVotingDialogFragment theGameDuelActionVotingFragment = new DuelVotingDialogFragment(mTheGame, mTheGame.findHumanPlayerByName(mAgresivePlayerSpinner.getSelectedItem().toString()), mTheGame.findHumanPlayerByName(mInsultedPlayerSpinner.getSelectedItem().toString()));
                        theGameDuelActionVotingFragment.show(fragmentManager, "DuelVotingDialogFragment");
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //AKCJA SEDZIEGO!!!
                    }
                })
                .build();


    }

}
