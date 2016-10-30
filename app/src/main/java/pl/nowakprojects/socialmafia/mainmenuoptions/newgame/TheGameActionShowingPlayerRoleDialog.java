package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameActionShowingPlayerRoleDialog extends DialogFragment {

    //Members:
    private HumanPlayer mChoosenHumanPlayer;

    TheGameActionShowingPlayerRoleDialog(HumanPlayer choosenHumanPlayer) {
        this.mChoosenHumanPlayer = choosenHumanPlayer;
    }

    //Views:
    @BindView(R.id.showedPlayerRoleText) TextView showedPlayerRoleTextView;
    @BindView(R.id.showedPlayerFraction) TextView showedPlayerFraction;
    @BindView(R.id.showedPlayerName)    TextView showedPlayerName;
    @BindView(R.id.roleIco) ImageView showedPlayerRoleIcon;
    @BindView(R.id.understandButton)    Button understandButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_showing_player_role, null);
        ButterKnife.bind(this,view);
        vUiSetupUserInterface();
        return view;
    }

    private void vUiSetupUserInterface(){
        vUiSetupTextView();
        vUiSetupImageView();
        vUiSetupButtonListener();
        vUiSetupDialog();
    }

    private void vUiSetupTextView(){
        showedPlayerName.setText(mChoosenHumanPlayer.getPlayerName());
        showedPlayerRoleTextView.setText(getString(mChoosenHumanPlayer.getRoleName()));
        showedPlayerFraction.setText(getString(mChoosenHumanPlayer.getPlayerRole().getFractionNameStringID()));
    }

    private void vUiSetupImageView(){
        showedPlayerRoleIcon.setImageResource(mChoosenHumanPlayer.getPlayerRole().getIconResourceID());

    }

    private void vUiSetupButtonListener(){
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void vUiSetupDialog(){
        setCancelable(false);
        getDialog().setTitle(R.string.checkedRole);
    }

}
