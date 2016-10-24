package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameDailyDuelChallengesFragment extends Fragment {

    TheGame theGame;

    TextView textView_remainedDuels;
    TextView textView_thrownChallenges;
    Spinner spinner_hpAgressive;
    Spinner spinner_hpInsulted;
    Button button_confirmChallenge;

    AlertDialog confirmationDialog;

    public TheGameDailyDuelChallengesFragment(){

    }

    public TheGameDailyDuelChallengesFragment(TheGame theGame) {
        this.theGame = theGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_day_duels_challenge, container, false);
        textView_remainedDuels = (TextView) fragmentView.findViewById(R.id.textView_remainedDuels);
        //textView_remainedDuels.setText(getString(R.string.remained_duels))
        textView_thrownChallenges = (TextView) fragmentView.findViewById(R.id.textView_thrownChallenges);

        spinner_hpAgressive = (Spinner) fragmentView.findViewById(R.id.spinner_agressive);
        spinner_hpInsulted = (Spinner) fragmentView.findViewById(R.id.spinner_insulted);
        //Dodawanie opcji do Spinnera=--------------------------------------------------------------
        ArrayList<String> playerNames = new ArrayList<String>();
        for (HumanPlayer humanPlayer : theGame.getLiveHumanPlayers())
                playerNames.add(humanPlayer.getPlayerName());

        ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
        choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hpAgressive.setAdapter(choosingSpinnerAdapter);
        spinner_hpInsulted.setAdapter(choosingSpinnerAdapter);
        //------------------------------------------------------------------------------------------

        button_confirmChallenge = (Button) fragmentView.findViewById(R.id.button_confirmChallenge);;
        button_confirmChallenge.setOnClickListener(new View.OnClickListener() {
            String s_hpAgressive = spinner_hpAgressive.getSelectedItem().toString();
            String s_hpInsulted = spinner_hpInsulted.getSelectedItem().toString();

            @Override
            public void onClick(View view) {
                createPopupAlertDialog(getString(R.string.duelActionDialogTitle,
                        s_hpAgressive,
                        s_hpInsulted),
                        getString(R.string.confirmDuelChallenge,s_hpAgressive,s_hpInsulted), null, null).show();
                confirmationDialog.show();
            }
        });

        return fragmentView;
    }


    public AlertDialog createPopupAlertDialog(final String sTitle, final String sMessage, String sPositive, String sNegative) {
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
                confirmationDialog.cancel();
                createPopupAlertDialog(sTitle,
                        getString(R.string.agreeDuelChallenge), null, null).show();
                confirmationDialog.show();
            }
        });

        popupAlertDialog.setNegativeButton(sNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmationDialog.cancel();
            }
        });

        confirmationDialog = popupAlertDialog.create();
        return confirmationDialog;
    }

}
