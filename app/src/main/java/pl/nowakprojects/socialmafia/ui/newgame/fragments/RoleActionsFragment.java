package pl.nowakprojects.socialmafia.ui.newgame.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.ui.newgame.adapters.DaytimeRoleActionsAdapter;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class RoleActionsFragment extends Fragment {

    private TheGame mTheGame;
    DaytimeRoleActionsAdapter mDaytimeRoleActionsAdapter;


    public RoleActionsFragment(TheGame theGame) {
        mTheGame=theGame;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDaytimeRoleActionsAdapter = new DaytimeRoleActionsAdapter(this, getActivity().getApplicationContext(), mTheGame);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_time_role_actions, null, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersActionsRecyclerView);
        playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL, false));
        playersActionsRecyclerView.setAdapter(mDaytimeRoleActionsAdapter);
    }


}
