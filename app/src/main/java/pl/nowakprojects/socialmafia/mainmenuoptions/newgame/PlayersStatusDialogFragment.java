package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.adapters.PlayersStatusAdapter;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

/**
 * Adapter do przeglądania statusu gracza
 */

public class PlayersStatusDialogFragment extends DialogFragment {

    private final String GAME_TIP_FRAGMENT = "PlayersStatusDialogFragment.GAME_TIP_FRAGMENT";

    private PlayersStatusAdapter mPlayersStatusAdapter;
    private TheGame mTheGame;

   /* PlayersStatusDialogFragment() {
    }// PlayersStatusDialogFragment()
*/
    PlayersStatusDialogFragment(TheGame theGame) {
        this.mTheGame = theGame;
    }// PlayersStatusDialogFragment()

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_players_status_fragment, null); //cos tutaj dodac za prent!!!
        //setCancelable(false);
        getDialog().setTitle(R.string.playersList);

        showGameTipFragment(null,getString(R.string.tip_playersList));

        mPlayersStatusAdapter = new PlayersStatusAdapter(this, getActivity().getApplicationContext(), mTheGame);
        RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersStatusRecyclerView);
        playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), GridLayoutManager.VERTICAL,false));
        playersActionsRecyclerView.setAdapter(mPlayersStatusAdapter);

        Button returnButton = (Button) view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    private GameTipFragment showGameTipFragment(String sTipTitle, String sTipContent){
        GameTipFragment gameTipFragment = new GameTipFragment(sTipTitle,sTipContent,false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.gameTipFragmentPlayerStatusDialog, gameTipFragment, GAME_TIP_FRAGMENT);
        fragmentTransaction.commit();

        return gameTipFragment;
    }


}//public class PlayersStatusDialogFragment extends DialogFragment
