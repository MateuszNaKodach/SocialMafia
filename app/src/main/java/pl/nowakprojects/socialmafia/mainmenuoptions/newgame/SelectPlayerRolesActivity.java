package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

/**
 * Wybór ról jakie znajdą się w aktualnej grze
 */
public class SelectPlayerRolesActivity extends AppCompatActivity implements PlayerChoosingRoleAdapter.RoleAmountChangedCallback {

    static final String EXTRA_SELECTED_ROLES_LIST = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_SELECTED_ROLES_LIST";
    static final String LOG_TAG = "SelectPlayersRolesActivity.class";

    private RecyclerView rv_townRolesList;
    private RecyclerView rv_mafiaRolesList;
    private RecyclerView rv_syndicateRolesList;

    private PlayerChoosingRoleAdapter adapter_townRolesList;
    private PlayerChoosingRoleAdapter adapter_mafiaRolesList;
    private PlayerChoosingRoleAdapter adapter_syndicateRolesList;

    private ArrayList<PlayerRole> prlist_townRoles;
    private ArrayList<PlayerRole> prlist_mafiaRoles;
    private ArrayList<PlayerRole> prlist_syndicateRoles;


    private ArrayList<PlayerRole> prlist_allSelectedRoles; //selected roles from all fractions
    private ArrayList<String> slist_playersNames; //lista imion graczy

    TextView tv_rolesSelectedAmount;
    TextView tv_townRolesSelectedAmount;
    TextView tv_mafiaRolesSelectedAmount;
    TextView tv_syndicateRolesSelectedAmount;

    private int i_selectedTownRoles = 0;
    private int i_selectedMafiaRoles = 0;
    private int i_selectedSyndicateRoles = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Stworzenie recyclerView dla kazdej z frakcji ---------------------------------------------------------------------------
        prlist_townRoles = RolesDataObjects.getTownRolesList();
        rv_townRolesList = (RecyclerView) findViewById(R.id.selectTownRolesRecyclerView);
        rv_townRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_townRolesList = new PlayerChoosingRoleAdapter(prlist_townRoles,this);
        adapter_townRolesList.setRoleAmountChangedCallback(this);
        rv_townRolesList.setAdapter(adapter_townRolesList);

        prlist_mafiaRoles = RolesDataObjects.getMafiaRolesList();
        rv_mafiaRolesList = (RecyclerView) findViewById(R.id.selectMafiaRolesRecyclerView);
        rv_mafiaRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_mafiaRolesList = new PlayerChoosingRoleAdapter(prlist_mafiaRoles,this);
        adapter_mafiaRolesList.setRoleAmountChangedCallback(this);
        rv_mafiaRolesList.setAdapter(adapter_mafiaRolesList);

        prlist_syndicateRoles = RolesDataObjects.getSyndicateRolesList();
        rv_syndicateRolesList = (RecyclerView) findViewById(R.id.selectSyndicateRolesRecyclerView);
        rv_syndicateRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_syndicateRolesList = new PlayerChoosingRoleAdapter(prlist_syndicateRoles,this);
        adapter_syndicateRolesList.setRoleAmountChangedCallback(this);
        rv_syndicateRolesList.setAdapter(adapter_syndicateRolesList);
        //--------------------------------------------------------------------------------------------------------------------------
        //Oderanie listy imion graczy:
        slist_playersNames = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
        TextView pickedPlayersAmount = (TextView) findViewById(R.id.pickedPlayersAmount);
        pickedPlayersAmount.setText(String.valueOf(slist_playersNames.size()));
        tv_townRolesSelectedAmount = (TextView) findViewById(R.id.townSelectedRolesAmount);
        tv_mafiaRolesSelectedAmount = (TextView) findViewById(R.id.mafiaSelectedRolesAmount);
        tv_syndicateRolesSelectedAmount = (TextView) findViewById(R.id.syndicateSelectedRolesAmount);

        tv_rolesSelectedAmount = (TextView) findViewById(R.id.howMuchRolesWasSelected);

        Button assignRolesToPlayers = (Button) findViewById(R.id.assignRolesToPlayers);
        assignRolesToPlayers.setOnClickListener(new View.OnClickListener() {

            private boolean bOneFraction(){
                boolean result=false;
                if(i_selectedMafiaRoles >0&& i_selectedSyndicateRoles ==0&& i_selectedTownRoles ==0)
                    result = true;
                else if (i_selectedTownRoles >0&& i_selectedSyndicateRoles ==0&& i_selectedMafiaRoles ==0)
                    result = true;
                else if(i_selectedMafiaRoles >0&& i_selectedSyndicateRoles ==0&& i_selectedTownRoles ==0)
                    result = true;

                return result;
            }//private boolean bOneFraction()

            /**
             * DOKONCZYC!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             * @return
             */
            private boolean isFractionProportionCorrect(){
                return true;
            }

            /**
             *
             * @return
             */
            private boolean checkIfPlayersAmountIsCorrect(){
                if ((i_selectedTownRoles + i_selectedMafiaRoles + i_selectedSyndicateRoles)< slist_playersNames.size()){
                    Toast.makeText(getApplicationContext(),R.string.tooLessFuctionsSelected, Toast.LENGTH_SHORT).show();
                    return false;
                } else if ((i_selectedTownRoles + i_selectedMafiaRoles + i_selectedSyndicateRoles)> slist_playersNames.size()){
                    Toast.makeText(getApplicationContext(),R.string.tooMuchFuctionsSelected, Toast.LENGTH_SHORT).show();
                    return false;
                } else if (bOneFraction()) {
                    Toast.makeText(getApplicationContext(),R.string.onlyOneFractionSelected, Toast.LENGTH_SHORT).show();
                    return false;
                }else
                    return true;
            }// private boolean checkIfPlayersAmountIsCorrect()

            @Override
            public void onClick(View view) {
                //dodac ostrzezenia jak np. mafii jest za duzo i alertbox o zaakcpetowanie!!!

                if (checkIfPlayersAmountIsCorrect()) {
                    //przejscie do losowania ról:
                    connectSelectedRolesFromAllFractions();
                    //Tworzymy Bundle do przekazania do Activity mieszania ról
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EXTRA_SELECTED_ROLES_LIST, Parcels.wrap(prlist_allSelectedRoles)); //wszystkie wybrane role przekazujemy
                    bundle.putStringArrayList(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST, slist_playersNames); //wszystkie imiona graczy
                    Intent intent = new Intent(getApplicationContext(),ConnectPlayersToRolesActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }// if (checkIfPlayersAmountIsCorrect())

            }// public void onClick(View view)
        });

    }// protected void onCreate(Bundle savedInstanceState)

    @Override
    public void amountDecrease(PlayerRole.Fraction fraction) {

        if(fraction == PlayerRole.Fraction.TOWN)
            i_selectedTownRoles--;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            i_selectedMafiaRoles--;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            i_selectedSyndicateRoles--;

        updateFractionAmountTextViews();
    }//public void amountDecrease(PlayerRole.Fraction fraction)

    @Override
    public void amountIncrease(PlayerRole.Fraction fraction) {
        if(fraction == PlayerRole.Fraction.TOWN)
            i_selectedTownRoles++;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            i_selectedMafiaRoles++;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            i_selectedSyndicateRoles++;

        updateFractionAmountTextViews();
    }//public void amountIncrease(PlayerRole.Fraction fraction)

    private void updateFractionAmountTextViews(){
        tv_townRolesSelectedAmount.setText(String.valueOf(i_selectedTownRoles));
        tv_mafiaRolesSelectedAmount.setText(String.valueOf(i_selectedMafiaRoles));
        tv_syndicateRolesSelectedAmount.setText(String.valueOf(i_selectedSyndicateRoles));
        tv_rolesSelectedAmount.setText(String.valueOf(i_selectedTownRoles + i_selectedMafiaRoles + i_selectedSyndicateRoles));
    }//private void updateFractionAmountTextViews()

    /**
     * Zapisuje wszystkie wybrane role do jednej listy prlist_allSelectedRoles
     */
    private void connectSelectedRolesFromAllFractions(){
        prlist_allSelectedRoles = new ArrayList<>();
        for(int i = 0; i< adapter_townRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_townRolesList.getSelectedRolesList().get(i));
        for(int i = 0; i< adapter_mafiaRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_mafiaRolesList.getSelectedRolesList().get(i));
        for(int i = 0; i< adapter_syndicateRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_syndicateRolesList.getSelectedRolesList().get(i));
    }//private void connectSelectedRolesFromAllFractions()

}// SelectPlayerRolesActivity