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
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class TheGameActionShowingPlayerGoodOrBad extends DialogFragment {

    private HumanPlayer choosenPlayer;
    private Button understandButton;
    private TextView showedPlayerName;
    private ImageView thumbIcon;

    TheGameActionShowingPlayerGoodOrBad(HumanPlayer choosenPlayer) {
        this.choosenPlayer = choosenPlayer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_showing_player_role, null);
        setCancelable(false);
        getDialog().setTitle(R.string.checkedPlayer);

        showedPlayerName = (TextView) view.findViewById(R.id.showedPlayerName);
        showedPlayerName.setText(choosenPlayer.getPlayerName());
        thumbIcon = (ImageView) view.findViewById(R.id.roleIco);
        if (choosenPlayer.getPlayerRole().getFraction() == PlayerRole.Fraction.TOWN)
            thumbIcon.setImageResource(R.drawable.icon_thumbup3);
        else
            thumbIcon.setImageResource(R.drawable.icon_thumbdown2);

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
