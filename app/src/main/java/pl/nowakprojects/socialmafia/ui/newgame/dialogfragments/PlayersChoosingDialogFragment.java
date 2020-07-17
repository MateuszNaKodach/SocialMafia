package pl.nowakprojects.socialmafia.ui.newgame.dialogfragments;

import androidx.fragment.app.DialogFragment;

/**
 * Adapter do przeglądania statusu gracza
 */

public class PlayersChoosingDialogFragment extends DialogFragment {


  /*  private PlayersStatusAdapter mPlayersStatusAdapter;
    private TheGame mTheGame;
    @BindView(R.id.playersStatusRecyclerView)   RecyclerView mPlayersStatusRecyclerView;
    @BindView(R.id.returnButton) Button mDialogCancelButton;

    public PlayersChoosingDialogFragment(TheGame theGame) {
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
        mPlayersStatusAdapter = new PlayersStatusAdapter(this, getActivity().getApplicationContext(), mTheGame);
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
*/

}//public class PlayersStatusDialogFragment extends DialogFragment
