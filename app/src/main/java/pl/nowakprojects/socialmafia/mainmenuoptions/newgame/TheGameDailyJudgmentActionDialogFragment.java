package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 25.10.2016.
 */

public class TheGameDailyJudgmentActionDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_players_status_fragment, null); //cos tutaj dodac za prent!!!
        setCancelable(false);
        getDialog().setTitle(R.string.playersList);

        //showGameTipFragment(null,getString(R.string.tip_playersList));

       /* playerGameStatusRoleAdapter = new TheGameActionPlayersGameStatusDialogFragment.PlayerGameStatusRoleAdapter(getActivity().getApplicationContext(),theGame.getPlayersInfoList());
        RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersStatusRecyclerView);
        playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), GridLayoutManager.VERTICAL,false));
        playersActionsRecyclerView.setAdapter(playerGameStatusRoleAdapter);*/

        Button returnButton = (Button) view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }


}

