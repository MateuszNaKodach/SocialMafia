package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.TheGame;

public class ConnectPlayersToRolesActivity extends AppCompatActivity {

    static final String EXTRA_NEW_GAME = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_NEW_GAME";

    ArrayList<HumanPlayer> playersInfoList;
    ArrayList<PlayerRole> selectedGameRoles;
    private ArrayList<String> playersNamesList; //lista imion graczy

    private int showedRolesAmount = 0;

    PlayerShowingRoleAdapter playerShowingRoleAdapter;
    RecyclerView allPlayersRolesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_players_to_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // cos nie dziala!!!

        selectedGameRoles = Parcels.unwrap(getIntent().getParcelableExtra(SelectPlayerRolesActivity.EXTRA_SELECTED_ROLES_LIST));
        playersNamesList = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
        makeHumanPlayerWithRolesList(); //utowrzenie już listy graczy z przydzielonymi rolami
        playerShowingRoleAdapter = new PlayerShowingRoleAdapter(playersInfoList, this);
        allPlayersRolesRecyclerView = (RecyclerView) findViewById(R.id.allPlayersRolesRecyclerView);
        allPlayersRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        allPlayersRolesRecyclerView.setAdapter(playerShowingRoleAdapter);

        Button startTheGameButton = (Button) findViewById(R.id.startTheGameButton);
        startTheGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showedRolesAmount < playersInfoList.size())
                    Toast.makeText(getApplicationContext(), R.string.tooLessPlayersRolesShowed, Toast.LENGTH_LONG).show();
                else {
                    //tworzenie nowej gry:
                    TheGame newGame = new TheGame();
                    newGame.setPlayersInfoList(playersInfoList);
                    Bundle bundle = new Bundle();
                    Log.i("SRATATA", "Dajemy parcele.");
                    bundle.putParcelable(EXTRA_NEW_GAME, Parcels.wrap(newGame));
                    Log.i("SRATATA", "Dajemy parcele.");
                    Intent intent = new Intent(getApplicationContext(), TheGameActionActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Log.i("SRATATA", "Startujemy activity.");
                }
            }
        });
    }

    void makeHumanPlayerWithRolesList() {
        playersInfoList = new ArrayList<>();
        Collections.shuffle(this.selectedGameRoles); // pomieszanie roli, żeby nie były według wyboru
        // przydzielenie pomieszanych ról do graczy:
        //tworzymy nowe obiekty z imienia i roli gracza
        for (int i = 0; i < this.selectedGameRoles.size(); i++) {
            playersInfoList.add(new HumanPlayer(playersNamesList.get(i), selectedGameRoles.get(i)));
        }
    }

    public class PlayerShowingRoleAdapter extends RecyclerView.Adapter<PlayerShowingRoleAdapter.HumanPlayerViewHolder> {

        private ArrayList<HumanPlayer> humanPlayersList;
        private LayoutInflater inflater;
        private Context context;

        public PlayerShowingRoleAdapter(ArrayList<HumanPlayer> humanPlayersList, Context context) {
            this.humanPlayersList = humanPlayersList;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public HumanPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_role_showing_layout, parent, false);
            return new HumanPlayerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HumanPlayerViewHolder holder, int position) {
            HumanPlayer humanPlayer = humanPlayersList.get(position);
            holder.playerName.setText(humanPlayer.getPlayerName());
            holder.roleName.setText(getString(R.string.questionMarks));
            holder.showRoleButton.setText(getString(R.string.show_role));
            holder.playerRoleIcon.setImageResource(R.drawable.image_template);
            holder.wasRoleShowed.setChecked(humanPlayersList.get(position).isWasRoleShowed());
            holder.isRoleShowed = false;
        }

        @Override
        public int getItemCount() {
            return humanPlayersList.size();
        }

        class HumanPlayerViewHolder extends RecyclerView.ViewHolder {

            private ImageView playerRoleIcon;
            private TextView playerName;
            private TextView roleName;
            private Button showRoleButton;
            private CheckBox wasRoleShowed;
            private View container;

            private AlertDialog roleDescriptionDialog;
            private boolean isRoleShowed = false;

            public HumanPlayerViewHolder(View itemView) {
                super(itemView);

                playerRoleIcon = (ImageView) itemView.findViewById(R.id.playerStatusIcon);
                playerName = (TextView) itemView.findViewById(R.id.playerName);
                roleName = (TextView) itemView.findViewById(R.id.roleName);
                showRoleButton = (Button) itemView.findViewById(R.id.show_hide_button);
                wasRoleShowed = (CheckBox) itemView.findViewById(R.id.wasRoleShowedCheckBox);

                showRoleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (humanPlayersList.get(getAdapterPosition()).isWasRoleShowed() == false)
                            showedRolesAmount++;

                        humanPlayersList.get(getAdapterPosition()).setWasRoleShowed(true);
                        wasRoleShowed.setChecked(humanPlayersList.get(getAdapterPosition()).isWasRoleShowed());
                        if (isRoleShowed) {
                            isRoleShowed = false;
                            showRoleButton.setText(getString(R.string.show_role));
                            roleName.setText(getString(R.string.questionMarks));
                            playerRoleIcon.setImageResource(R.drawable.image_template);
                        } else {
                            isRoleShowed = true;
                            showRoleButton.setText(getString(R.string.hide_role));
                            roleName.setText(getString(humanPlayersList.get(getAdapterPosition()).getRoleName()));
                            playerRoleIcon.setImageResource(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getIconResourceID());
                        }
                    }
                });

                /**
                 * Przy naciśnięciu karty roli pojawią się jej opis
                 */
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isRoleShowed) {
                            buildRoleDescriptionDialog();
                            roleDescriptionDialog.show();
                        }
                    }
                });

            }

            /**
             * Tworzy okienko wyświetlające opis roli
             */
            public void buildRoleDescriptionDialog() {
                final AlertDialog.Builder descriptionDialog = new AlertDialog.Builder(context);
                descriptionDialog.setTitle(context.getString(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getName()));
                descriptionDialog.setMessage(context.getString(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getDescription()));
                descriptionDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     * Zamyka okno z opisem roli
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roleDescriptionDialog.cancel();
                    }
                });

                roleDescriptionDialog = descriptionDialog.create();
            }

        }
    }

}
