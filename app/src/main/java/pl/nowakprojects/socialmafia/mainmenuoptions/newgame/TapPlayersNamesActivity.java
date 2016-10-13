package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.nowakprojects.socialmafia.R;

public class TapPlayersNamesActivity extends AppCompatActivity {

    static final String EXTRA_PLAYERS_NAMES_LIST = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_PLAYERS_NAMES_LIST";

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

        //przypisujemy wczesniej wybrana liczbe graczy
        pickedPlayersAmount = getIntent().getIntExtra(PickPlayersAmountActivity.EXTRA_PLAYERS_AMOUNT,5);

        tapPlayerNamesRecyclerView = (RecyclerView) findViewById(R.id.playersNamesRecyclerView);
        tapPlayerNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tapPlayerNameAdapter = new TapPlayerNameAdapter(pickedPlayersAmount,this);
        tapPlayerNamesRecyclerView.setAdapter(tapPlayerNameAdapter);

        Button goToSelectRolesButton = (Button) findViewById(R.id.goToSelectRolesButton);
        goToSelectRolesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jesli niektore imiona pozostały puste zostanie do nich wpisany numer gracza:
                fillEmptyNamesWithPlayerNumbers();
                Intent intent = new Intent(getApplicationContext(),SelectPlayerRolesActivity.class);
                intent.putStringArrayListExtra(EXTRA_PLAYERS_NAMES_LIST,makeStringArrayListFromTapPlayerNameList());
                startActivity(intent);
            }
        });
    }// protected void onCreate(Bundle savedInstanceState)

    void fillEmptyNamesWithPlayerNumbers(){
        for(int i=0;i<tapPlayerNameAdapter.namesList.size();i++)
            if(tapPlayerNameAdapter.namesList.get(i).playerName.isEmpty())
                tapPlayerNameAdapter.namesList.get(i).playerName = getString(R.string.player) +" #"+(i+1);
    }// void fillEmptyNamesWithPlayerNumbers()

    ArrayList<String> makeStringArrayListFromTapPlayerNameList(){
        ArrayList<String> playersNames = new ArrayList<>();
        for(int i=0;i<tapPlayerNameAdapter.namesList.size();i++)
            playersNames.add(tapPlayerNameAdapter.namesList.get(i).playerName);

        return playersNames;
    }// ArrayList<String> makeStringArrayListFromTapPlayerNameList()



    class TapPlayerNameAdapter extends RecyclerView.Adapter<TapPlayerNameAdapter.PlayerNameViewHolder>{

        class TapPlayerNameItem{
            private String playerName = "";
        }//class TapPlayerNameItem

        private List<TapPlayerNameItem> namesList; //lista imion graczy
        private LayoutInflater layoutInflater;

        public TapPlayerNameAdapter(int playersAmount, Context c){
            layoutInflater = LayoutInflater.from(c);
            namesList = new ArrayList<>(playersAmount);
            for(int i=0;i<playersAmount;i++)
                namesList.add(new TapPlayerNameItem()); //tworzy lista, aby byly miejsca na imiona graczy
        }//public TapPlayerNameAdapter(int playersAmount, Context c)

        @Override
        public PlayerNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.players_names_list_item, parent, false);
            return new PlayerNameViewHolder(view);
        }//public PlayerNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

        @Override
        public void onBindViewHolder(PlayerNameViewHolder holder, int position) {
            TapPlayerNameItem item = namesList.get(position);
            holder.playerNumberText.setText(getString(R.string.player) +" #"+(position+1));
            holder.playerNameEditText.setText(item.playerName);
        }//public void onBindViewHolder(PlayerNameViewHolder holder, int position)

        @Override
        public int getItemCount() {
            return namesList.size();
        }

        class PlayerNameViewHolder extends RecyclerView.ViewHolder{

            private TextView playerNumberText;
            private EditText playerNameEditText;
            //  private View container;

            public PlayerNameViewHolder(View itemView) {
                super(itemView);
                playerNumberText = (TextView) itemView.findViewById(R.id.playerNumberText);
                playerNameEditText = (EditText) itemView.findViewById(R.id.playerNameEditText);
                // container = itemView.findViewById(R.id.playernamelistitem);

                //WYMAGA POPRAWEK DLA WIEKSZEJ ILOSCI ZMIAN NIZ JEDNA (ZAMIANA JUZ ISTNIEJACEGO TEKSTU ITP!)
                playerNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus && !playerNameEditText.getText().toString().isEmpty()) {
                            namesList.get(getAdapterPosition()).playerName = playerNameEditText.getText().toString();
                            // Log.i("TEST",tapedPlayersNamesList.get(tapedPlayersNamesList.size()-1));
                        }//if (!hasFocus && !playerNameEditText.getText().toString().isEmpty())
                    }// public void onFocusChange(View v, boolean hasFocus)
                });
            }// public PlayerNameViewHolder(View itemView)

        }//class PlayerNameViewHolder

    }//class TapPlayerNameAdapter extends RecyclerView.Adapter<TapPlayerNameAdapter.PlayerNameViewHolder>

}//public class TapPlayersNamesActivity extends AppCompatActivity