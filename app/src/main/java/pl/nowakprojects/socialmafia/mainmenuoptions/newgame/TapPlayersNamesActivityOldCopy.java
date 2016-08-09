package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.nowakprojects.socialmafia.R;

public class TapPlayersNamesActivityOldCopy extends AppCompatActivity {

    ArrayList<String> tapedPlayersNamesList = new ArrayList<String>();
    private TapPlayerNameAdapter tapPlayerNameAdapter;
    private RecyclerView tapPlayerNamesRecyclerView;
    private int pickedPlayersAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_players_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickedPlayersAmount = getIntent().getIntExtra(PickPlayersAmountActivity.EXTRA_PLAYERS_AMOUNT,5);

        tapPlayerNamesRecyclerView = (RecyclerView) findViewById(R.id.playersNamesRecyclerView);
        tapPlayerNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tapPlayerNameAdapter = new TapPlayerNameAdapter(pickedPlayersAmount,this);
        tapPlayerNamesRecyclerView.setAdapter(tapPlayerNameAdapter);

        Button goToSelectRolesButton = (Button) findViewById(R.id.goToSelectRolesButton);
        goToSelectRolesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // for(int i=0;i<pickedPlayersAmount;i++)
               //     tapPlayerNamesRecyclerView.findViewHolderForAdapterPosition(i).
                //Jesli niektore imiona pozostały puste zostanie do nich wpisany numer gracza:
                fillEmptyNamesWithPlayerNumbers();
                Intent intent = new Intent(getApplicationContext(),SelectPlayerRolesActivity.class);
                startActivity(intent);
            }
        });
    }

    void fillEmptyNamesWithPlayerNumbers(){
        for(int i=0;i<tapPlayerNameAdapter.namesList.size();i++)
            if(tapPlayerNameAdapter.namesList.get(i).playerName.isEmpty())
                tapPlayerNameAdapter.namesList.get(i).playerName = "Player #"+(i+1);
    }

    class TapPlayerNameAdapter extends RecyclerView.Adapter<TapPlayerNameAdapter.PlayerNameViewHolder>{

        class TapPlayerNameItem{
            private int playerNumber;
            private String playerName = "";
        }

        private List<TapPlayerNameItem> namesList;
        private LayoutInflater layoutInflater;

        public TapPlayerNameAdapter(int playersAmount, Context c){
            layoutInflater = LayoutInflater.from(c);
            namesList = new ArrayList<>(playersAmount);
            for(int i=0;i<playersAmount;i++)
                namesList.add(new TapPlayerNameItem());
        }

        @Override
        public PlayerNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.players_names_list_item, parent, false);
            return new PlayerNameViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlayerNameViewHolder holder, int position) {
                TapPlayerNameItem item = namesList.get(position);
                holder.playerNumberText.setText("Player #"+(position+1));
        }

        @Override
        public int getItemCount() {
            return namesList.size();
        }

        class PlayerNameViewHolder extends RecyclerView.ViewHolder{

            private TextView playerNumberText;
            private EditText playerName;
          //  private View container;

            public PlayerNameViewHolder(View itemView) {
                super(itemView);
                playerNumberText = (TextView) itemView.findViewById(R.id.playerNumberText);
                playerName = (EditText) itemView.findViewById(R.id.playerNameEditText);
               // container = itemView.findViewById(R.id.playernamelistitem);

                //WYMAGA POPRAWEK DLA WIEKSZEJ ILOSCI ZMIAN NIZ JEDNA (ZAMIANA JUZ ISTNIEJACEGO TEKSTU ITP!)
                playerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus && !playerName.getText().toString().isEmpty()) {
                            tapedPlayersNamesList.add(playerName.getText().toString());
                            Log.i("TEST",tapedPlayersNamesList.get(tapedPlayersNamesList.size()-1));
                        }
                    }
                });

            }
        }

    }
}
