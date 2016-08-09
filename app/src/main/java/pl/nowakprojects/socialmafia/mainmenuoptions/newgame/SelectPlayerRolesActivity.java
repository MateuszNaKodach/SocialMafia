package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

/**
 * Wybór ról jakie znajdą się w aktualnej grze
 */
public class SelectPlayerRolesActivity extends AppCompatActivity {

    private RecyclerView townRolesList;
    private RecyclerView mafiaRolesList;
    private RecyclerView syndicateRolesList;

    private PlayerChoosingRoleAdapter townRolesAdapter;
    private PlayerChoosingRoleAdapter mafiaRolesAdapter;

    private ArrayList<PlayerRole> townRoles;
    private ArrayList<PlayerRole> mafiaRoles;
    private ArrayList<PlayerRole> syndicateRoles;


    private ArrayList<PlayerRole> allSelectedRoles; //selected roles from all fractions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Stworzenie recyclerView dla kazdej z frakcji --------------------------------------------------------
        townRoles = RolesDataObjects.getTownRolesList();
        townRolesList = (RecyclerView) findViewById(R.id.selectTownRolesRecyclerView);
        townRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        townRolesAdapter = new PlayerChoosingRoleAdapter(townRoles,this);
        townRolesList.setAdapter(townRolesAdapter);

        mafiaRoles = RolesDataObjects.getMafiaRolesList();
        mafiaRolesList = (RecyclerView) findViewById(R.id.selectMafiaRolesRecyclerView);
        mafiaRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mafiaRolesAdapter = new PlayerChoosingRoleAdapter(mafiaRoles,this);
        mafiaRolesList.setAdapter(mafiaRolesAdapter);

        syndicateRoles = RolesDataObjects.getSyndicateRolesList();
        syndicateRolesList = (RecyclerView) findViewById(R.id.selectSyndicateRolesRecyclerView);
        syndicateRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        PlayerChoosingRoleAdapter syndicateRolesAdapter = new PlayerChoosingRoleAdapter(syndicateRoles,this);
        syndicateRolesList.setAdapter(syndicateRolesAdapter);
        //-----------------------------------------------------------------------------------------------------------


    }

}