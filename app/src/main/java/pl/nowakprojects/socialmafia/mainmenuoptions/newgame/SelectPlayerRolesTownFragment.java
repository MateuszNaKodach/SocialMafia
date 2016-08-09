package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

public class SelectPlayerRolesTownFragment extends Fragment {

    RecyclerView townRolesList;


    public SelectPlayerRolesTownFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_select_player_roles_town, container, false);

        townRolesList = (RecyclerView) rootView.findViewById(R.id.selectTownRolesRecyclerView);
        townRolesList.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        townRolesList.setAdapter(new PlayerChoosingRoleAdapter(RolesDataObjects.getTownRolesList(),this.getContext()));
        return rootView;
    }



}
