package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

public class SelectPlayerRolesActivity extends AppCompatActivity {

    RecyclerView townRolesList;
    RecyclerView mafiaRolesList;
    RecyclerView syndicateRolesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        townRolesList = (RecyclerView) findViewById(R.id.selectTownRolesRecyclerView);
        townRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        townRolesList.setAdapter(new PlayerChoosingRoleAdapter(RolesDataObjects.getTownRolesList(),this));

        mafiaRolesList = (RecyclerView) findViewById(R.id.selectMafiaRolesRecyclerView);
        mafiaRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mafiaRolesList.setAdapter(new PlayerChoosingRoleAdapter(RolesDataObjects.getTownRolesList(),this));

        syndicateRolesList = (RecyclerView) findViewById(R.id.selectSyndicateRolesRecyclerView);
        syndicateRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        syndicateRolesList.setAdapter(new PlayerChoosingRoleAdapter(RolesDataObjects.getTownRolesList(),this));
    }

}
