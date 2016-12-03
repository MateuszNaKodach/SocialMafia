package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.ui.newgame.interfaces.OnGoodOrBadCheckedListener;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class ShowingPlayerGoodOrBadDialog extends DialogFragment {

    private HumanPlayer choosenPlayer;
    private Button understandButton;
    private TextView showedPlayerName;
    private ImageView thumbIcon;
    private boolean dailyJudgmentShowing=false;
    OnGoodOrBadCheckedListener mRoleCheckedCallback;
    DailyJudgmentVotingDialogFragment dailyJudgmentVotingDialogFragment;

    public ShowingPlayerGoodOrBadDialog(HumanPlayer choosenPlayer) {
        this.choosenPlayer = choosenPlayer;
    }

    public ShowingPlayerGoodOrBadDialog(HumanPlayer choosenPlayer, DailyJudgmentVotingDialogFragment dailyJudgmentVotingDialogFragment) {
        this.choosenPlayer = choosenPlayer;
        this.dailyJudgmentVotingDialogFragment = dailyJudgmentVotingDialogFragment;
        dailyJudgmentShowing=true;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        //CZEMU TO NIE DZIALA!!!??!!?!?!
        /*if(dailyJudgmentShowing) {
            try {
                mRoleCheckedCallback = (OnGoodOrBadCheckedListener) getParentFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString()
                        + " must implement interface!");
            }
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_showing_player_good_bad, null);
        setCancelable(false);
        getDialog().setTitle(R.string.checkedPlayer);

        showedPlayerName = (TextView) view.findViewById(R.id.showedPlayerName);
        showedPlayerName.setText(choosenPlayer.getPlayerName());
        thumbIcon = (ImageView) view.findViewById(R.id.thumb);
        if (choosenPlayer.getPlayerRole().getFraction() == PlayerRole.Fraction.MAFIA || choosenPlayer.hasJewRole())
            thumbIcon.setImageResource(R.drawable.icon_thumbdown2);
        else
            thumbIcon.setImageResource(R.drawable.icon_thumbup3);

        understandButton = (Button) view.findViewById(R.id.understandButton);
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dailyJudgmentShowing)
                    dailyJudgmentVotingDialogFragment.onGodOrBadChecked();
                dismiss();
            }
        });
        return view;
    }

}
