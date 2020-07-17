package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.ui.newgame.adapters.PlayersStatusAdapter;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.utitles.GameTipFragment;

/**
 * Adapter do przeglądania statusu gracza
 */

public class PlayersStatusDialogFragment extends DialogFragment {


    private PlayersStatusAdapter mPlayersStatusAdapter;
    private TheGame mTheGame;
    @BindView(R.id.playersStatusRecyclerView)   RecyclerView mPlayersStatusRecyclerView;
    @BindView(R.id.returnButton) Button mDialogCancelButton;

    public PlayersStatusDialogFragment(TheGame theGame) {
        this.mTheGame = theGame;
    }// PlayersStatusDialogFragment()

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_players_status_fragment, null); //cos tutaj dodac za prent!!!

        ButterKnife.bind(this,view);

        vUiSetupUserInterface();

        return view;
    }



    private void vUiSetupUserInterface(){
        vUiSetupDialogFragment();
        vUiShowGameTipFragment();
        vUiSetupRecyclerView();
        vUiSetupButtonListener();
    }

    private void vUiSetupDialogFragment(){
        //setCancelable(false);
        getDialog().setTitle(R.string.playersList);
    }

    private void vUiSetupRecyclerView(){
        mPlayersStatusAdapter = new PlayersStatusAdapter(this, this.getContext(), mTheGame);
        mPlayersStatusRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), GridLayoutManager.VERTICAL,false));
        mPlayersStatusRecyclerView.setAdapter(mPlayersStatusAdapter);
    }

    private void vUiSetupButtonListener(){
        mDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void vUiShowGameTipFragment(){
        GameTipFragment.vShow(this,null,getString(R.string.tip_playersList),false);
    }


}//public class PlayersStatusDialogFragment extends DialogFragment
