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

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameActionShowingPlayerRoleDialog extends DialogFragment {

    private HumanPlayer choosenPlayer;
    private Button understandButton;
    //private AutoResizeTextView showedPlayerRoleText;
    private TextView showedPlayerRoleText;
    private TextView showedPlayerFraction;
    private TextView showedPlayerName;
    private ImageView showedPlayerRoleIcon;

    TheGameActionShowingPlayerRoleDialog(HumanPlayer choosenPlayer) {
        this.choosenPlayer = choosenPlayer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_showing_player_role, null);
        setCancelable(false);
        getDialog().setTitle(R.string.checkedRole);

        showedPlayerName = (TextView) view.findViewById(R.id.showedPlayerName);
        showedPlayerName.setText(choosenPlayer.getPlayerName());
        showedPlayerRoleIcon = (ImageView) view.findViewById(R.id.roleIco);
        showedPlayerRoleIcon.setImageResource(choosenPlayer.getPlayerRole().getIconResourceID());
        // showedPlayerRoleText = (AutoResizeTextView) view.findViewById(R.id.showedPlayerRoleText);
        showedPlayerRoleText = (TextView) view.findViewById(R.id.showedPlayerRoleText);
        showedPlayerRoleText.setText(getString(choosenPlayer.getRoleName()));
        showedPlayerFraction = (TextView) view.findViewById(R.id.showedPlayerFraction);
        showedPlayerFraction.setText(getString(choosenPlayer.getPlayerRole().getFractionNameStringID()));

        understandButton = (Button) view.findViewById(R.id.understandButton);
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

}
