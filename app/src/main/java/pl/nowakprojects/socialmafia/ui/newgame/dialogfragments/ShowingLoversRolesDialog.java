package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class ShowingLoversRolesDialog extends DialogFragment {

    private HumanPlayer choosenPlayer;
    private HumanPlayer choosenPlayer2;
    private TextView showedPlayerRoleText;
    private TextView showedPlayerRoleText2;
    private TextView showedPlayerFraction;
    private TextView showedPlayerFraction2;

    public ShowingLoversRolesDialog(HumanPlayer choosenPlayer, HumanPlayer choosenPlayer2) {
        this.choosenPlayer = choosenPlayer;
        this.choosenPlayer2 = choosenPlayer2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_showing_lovers_roles, null);
        setCancelable(false);
        getDialog().setTitle(R.string.loversRoles);

        showedPlayerRoleText = (TextView) view.findViewById(R.id.showedPlayerRoleText2);
        showedPlayerRoleText.setText(getString(choosenPlayer.getRoleName()));
        showedPlayerFraction = (TextView) view.findViewById(R.id.showedPlayerFraction2);
        showedPlayerFraction.setText(getString(choosenPlayer.getPlayerRole().getFractionNameStringID()));

        showedPlayerRoleText2 = (TextView) view.findViewById(R.id.showedPlayerRoleText);
        showedPlayerRoleText2.setText(getString(choosenPlayer2.getRoleName()));
        showedPlayerFraction2 = (TextView) view.findViewById(R.id.showedPlayerFraction);
        showedPlayerFraction2.setText(getString(choosenPlayer2.getPlayerRole().getFractionNameStringID()));


        Button understandButton = (Button) view.findViewById(R.id.understandButton);
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

}
