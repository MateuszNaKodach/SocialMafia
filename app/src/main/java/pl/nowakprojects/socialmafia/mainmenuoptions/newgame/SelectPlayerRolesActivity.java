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

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

/**
 * Wybór ról jakie znajdą się w aktualnej grze
 */
public class SelectPlayerRolesActivity extends AppCompatActivity implements PlayerChoosingRoleAdapter.RoleAmountChangedCallback {

    static final String EXTRA_SELECTED_ROLES_LIST = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_SELECTED_ROLES_LIST";
    static final String LOG_TAG = "SelectPlayersRolesActivity.class";

    private PlayerChoosingRoleAdapter adapter_townRolesList;
    private PlayerChoosingRoleAdapter adapter_mafiaRolesList;
    private PlayerChoosingRoleAdapter adapter_syndicateRolesList;

    private ArrayList<PlayerRole> prlist_townRoles;
    private ArrayList<PlayerRole> prlist_mafiaRoles;
    private ArrayList<PlayerRole> prlist_syndicateRoles;


    private ArrayList<PlayerRole> prlist_allSelectedRoles; //selected roles from all fractions
    private ArrayList<String> slist_playersNames; //lista imion graczy

    private int i_selectedTownRoles = 0;
    private int i_selectedMafiaRoles = 0;
    private int i_selectedSyndicateRoles = 0;


    @BindView(R.id.selectTownRolesRecyclerView) RecyclerView mTownRolesRecyclerView;
    @BindView(R.id.selectMafiaRolesRecyclerView)    RecyclerView mMafiaRolesRecyclerView;
    @BindView(R.id.selectSyndicateRolesRecyclerView)    RecyclerView mSyndicateRolesRecyclerView;
    @BindView(R.id.howMuchRolesWasSelected) TextView mSelectedRolesAmountTextView;
    @BindView(R.id.townSelectedRolesAmount) TextView mSelectedTownRolesAmountTextView;
    @BindView(R.id.mafiaSelectedRolesAmount)    TextView mSelectedMafiaRolesAmountTextView;
    @BindView(R.id.syndicateSelectedRolesAmount)    TextView mSelectedSyndicateRolesAmountTextView;
    @BindView(R.id.pickedPlayersAmount) TextView mPickedPlayersAmountTextView;
    @BindView(R.id.assignRolesToPlayers) Button mAssignRolesButton;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);

        ButterKnife.bind(this);

        vUiSetupUserInterface();

        vReceiveExtraObjects();


    }// protected void onCreate(Bundle savedInstanceState)

    @Override
    public void amountDecrease(PlayerRole.Fraction fraction) {

        if(fraction == PlayerRole.Fraction.TOWN)
            i_selectedTownRoles--;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            i_selectedMafiaRoles--;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            i_selectedSyndicateRoles--;

        vUiUpdateSelectedRolesTextView();
    }//public void amountDecrease(PlayerRole.Fraction fraction)

    @Override
    public void amountIncrease(PlayerRole.Fraction fraction) {
        if(fraction == PlayerRole.Fraction.TOWN)
            i_selectedTownRoles++;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            i_selectedMafiaRoles++;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            i_selectedSyndicateRoles++;

        vUiUpdateSelectedRolesTextView();
    }//public void amountIncrease(PlayerRole.Fraction fraction)

    /**
     * Zapisuje wszystkie wybrane role do jednej listy prlist_allSelectedRoles
     */
    private void vConnectSelectedRolesFromAllFractions(){
        prlist_allSelectedRoles = new ArrayList<>();
        for(int i = 0; i< adapter_townRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_townRolesList.getSelectedRolesList().get(i));
        for(int i = 0; i< adapter_mafiaRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_mafiaRolesList.getSelectedRolesList().get(i));
        for(int i = 0; i< adapter_syndicateRolesList.getSelectedRolesList().size(); i++)
            prlist_allSelectedRoles.add(adapter_syndicateRolesList.getSelectedRolesList().get(i));
    }//private void vConnectSelectedRolesFromAllFractions()

    private void vReceiveExtraObjects(){
        slist_playersNames = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
        mPickedPlayersAmountTextView.setText(String.valueOf(slist_playersNames.size()));
    };

    //Setup User Interface Methods:-----------------------------------------------------------------

    private void vUiUpdateSelectedRolesTextView(){
        mSelectedTownRolesAmountTextView.setText(String.valueOf(i_selectedTownRoles));
        mSelectedMafiaRolesAmountTextView.setText(String.valueOf(i_selectedMafiaRoles));
        mSelectedSyndicateRolesAmountTextView.setText(String.valueOf(i_selectedSyndicateRoles));
        mSelectedRolesAmountTextView.setText(String.valueOf(i_selectedTownRoles + i_selectedMafiaRoles + i_selectedSyndicateRoles));
    }//private void vUiUpdateSelectedRolesTextView()

    private void vUiSetupUserInterface(){
        vUiSetupToolbar();
        vUiSetupRecyclerView();
        vUiSetupButtonListener();
    }

    private void vUiSetupRecyclerView(){
        prlist_townRoles = RolesDataObjects.getTownRolesList();
        mTownRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_townRolesList = new PlayerChoosingRoleAdapter(prlist_townRoles,this);
        adapter_townRolesList.setRoleAmountChangedCallback(this);
        mTownRolesRecyclerView.setAdapter(adapter_townRolesList);

        prlist_mafiaRoles = RolesDataObjects.getMafiaRolesList();
        mMafiaRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_mafiaRolesList = new PlayerChoosingRoleAdapter(prlist_mafiaRoles,this);
        adapter_mafiaRolesList.setRoleAmountChangedCallback(this);
        mMafiaRolesRecyclerView.setAdapter(adapter_mafiaRolesList);

        prlist_syndicateRoles = RolesDataObjects.getSyndicateRolesList();
        mSyndicateRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_syndicateRolesList = new PlayerChoosingRoleAdapter(prlist_syndicateRoles,this);
        adapter_syndicateRolesList.setRoleAmountChangedCallback(this);
        mSyndicateRolesRecyclerView.setAdapter(adapter_syndicateRolesList);
    }

    private void vUiSetupToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void vUiSetupButtonListener(){
        mAssignRolesButton.setOnClickListener(new View.OnClickListener() {

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
                    vConnectSelectedRolesFromAllFractions();
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
    }


}// SelectPlayerRolesActivity