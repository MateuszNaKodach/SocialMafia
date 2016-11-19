package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class NightTimeFragment extends Fragment {

    private TheGameActionActivity theGameActionActivity;
    TheGame theGame;
    TextView nightNumberTextView;
    Button finishTheNightButton;
    private int madeNightActionsAmount = 0;
    AlertDialog confirmationAlertDialog;

    public NightTimeFragment(TheGameActionActivity theGameActionActivity, TheGame theGame) {
        this.theGameActionActivity = theGameActionActivity;
        this.theGame = theGame;
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_night_time, container, false);

        nightNumberTextView = (TextView) fragmentView.findViewById(R.id.nightNumberTextView);
        finishTheNightButton = (Button) fragmentView.findViewById(R.id.finishTheNightButton);

        if(theGame.getThisNightHumanPlayers().size()!=0)
            finishTheNightButton.setEnabled(false);

        updateNightNumberTextView();

        finishTheNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildConfirmationAlertDialog();
                confirmationAlertDialog.show();
            }
        });

        return fragmentView;
    }

    /**
     * Aktualizuje numer kolejnej nocy
     */
    void updateNightNumberTextView() {
        nightNumberTextView.setText((getString(R.string.night_number, theGame.getCurrentNightNumber())));
    }

    @Subscribe
    public void onCheckingRolesActionsMadeEvent(Boolean bool){
        if(bool)
            finishTheNightButton.setEnabled(true);
    }

    public void buildConfirmationAlertDialog() {
        final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this.getActivity());
        confirmationDialog.setTitle(getString(R.string.fisnish_the_night));
        confirmationDialog.setMessage(getString(R.string.finish_the_night_are_you_sure));
        confirmationDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            /**
             * Zamyka okno z opisem roli
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                theGameActionActivity.endNightAction();
                theGameActionActivity.startDayAction();
                confirmationAlertDialog.cancel();
            }
        });

        confirmationDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmationAlertDialog.cancel();
            }
        });

        confirmationAlertDialog = confirmationDialog.create();
    }

}
