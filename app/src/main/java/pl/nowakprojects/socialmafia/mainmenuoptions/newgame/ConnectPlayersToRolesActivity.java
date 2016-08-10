package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;

public class ConnectPlayersToRolesActivity extends AppCompatActivity {

    ArrayList<HumanPlayer> playersInfoList;
    ArrayList<PlayerRole> selectedGameRoles;
    private ArrayList<String> playersNamesList; //lista imion graczy

    PlayerShowingRoleAdapter playerShowingRoleAdapter;
    RecyclerView allPlayersRolesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_players_to_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        selectedGameRoles = Parcels.unwrap(getIntent().getParcelableExtra(SelectPlayerRolesActivity.EXTRA_SELECTED_ROLES_LIST));
        playersNamesList = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
        makeHumanPlayerWithRolesList(); //utowrzenie już listy graczy z przydzielonymi rolami

        allPlayersRolesRecyclerView = (RecyclerView) findViewById(R.id.allPlayersRolesRecyclerView);
        allPlayersRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        playerShowingRoleAdapter = new PlayerShowingRoleAdapter(playersInfoList,this);
        allPlayersRolesRecyclerView.setAdapter(playerShowingRoleAdapter);

    }

    void makeHumanPlayerWithRolesList(){
            Collections.shuffle(this.selectedGameRoles); // pomieszanie roli, żeby nie były według wyboru
            // przydzielenie pomieszanych ról do graczy:
            //tworzymy nowe obiekty z imienia i roli gracza
            for (int i = 0; i < this.playersNamesList.size(); i++) {
                playersInfoList.add(new HumanPlayer(playersNamesList.get(i)),selectedGameRoles.get(i));
            }
    }

    public class PlayerShowingRoleAdapter extends RecyclerView.Adapter<PlayerShowingRoleAdapter.HumanPlayerViewHolder> {

        private ArrayList<HumanPlayer> humanPlayersList;
        private LayoutInflater inflater;
        private Context context;

        public PlayerShowingRoleAdapter(ArrayList<HumanPlayer> humanPlayersList, Context context){
            this.humanPlayersList = humanPlayersList;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public HumanPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_role_showing_layout,parent,false);
            return new HumanPlayerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HumanPlayerViewHolder holder, int position) {
            HumanPlayer humanPlayer = humanPlayersList.get(position);

        }

        @Override
        public int getItemCount() {
            return humanPlayersList.size();
        }

        class HumanPlayerViewHolder extends RecyclerView.ViewHolder{

            private ImageView roleIcon;
            private TextView roleName;
            private View container;

            public HumanPlayerViewHolder(View itemView) {
                super(itemView);

                roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
                roleName = (TextView) itemView.findViewById(R.id.roleName);
                //container = itemView.findViewById(R.layout.player_role_choosing_layout);

            }


        }
    }

}
