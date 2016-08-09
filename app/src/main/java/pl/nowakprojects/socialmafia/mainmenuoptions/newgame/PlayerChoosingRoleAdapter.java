package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;

/**
 * Created by Mateusz on 08.08.2016.
 */
public class PlayerChoosingRoleAdapter extends RecyclerView.Adapter<PlayerChoosingRoleAdapter.PlayerRoleViewHolder> {

    private ArrayList<PlayerRole> fractionRolesList;
    private ArrayList<PlayerRole> selectedRolesList;
    private LayoutInflater inflater;
    private Context context;


    public ArrayList<PlayerRole> getSelectedRolesList() {
        return selectedRolesList;
    }

    public PlayerChoosingRoleAdapter(ArrayList<PlayerRole> fractionRolesList, Context context){
        this.selectedRolesList = new ArrayList<>();
        this.fractionRolesList = fractionRolesList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public PlayerRoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.player_role_choosing_layout,parent,false);
        return new PlayerRoleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerRoleViewHolder holder, int position) {
            PlayerRole playerRole = fractionRolesList.get(position);
            holder.roleIcon.setImageResource(playerRole.getIconResourceID());
            holder.roleName.setText(playerRole.getName());
            holder.roleAmount.setText(String.valueOf(playerRole.rolePlayersAmount));
    }

    @Override
    public int getItemCount() {
        return fractionRolesList.size();
    }

    class PlayerRoleViewHolder extends RecyclerView.ViewHolder{

        private ImageView roleIcon;
        private TextView roleName;
        private TextView roleAmount;
        private ImageButton increaseRoleAmount;
        private ImageButton decreaseRoleAmount;
        private View container;
        private AlertDialog roleDescriptionDialog;

        public PlayerRoleViewHolder(View itemView) {
            super(itemView);

            roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
            roleName = (TextView) itemView.findViewById(R.id.roleName);
            roleAmount = (TextView) itemView.findViewById(R.id.roleAmount);
            increaseRoleAmount = (ImageButton) itemView.findViewById(R.id.increaseRoleAmountButton);
            decreaseRoleAmount = (ImageButton) itemView.findViewById(R.id.decreaseRoleAmountButton);
            //container = itemView.findViewById(R.layout.player_role_choosing_layout);

            increaseRoleAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedRolesList.add(fractionRolesList.get(getAdapterPosition()));
                    fractionRolesList.get(getAdapterPosition()).setRolePlayersAmount(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()+1);
                    roleAmount.setText(String.valueOf(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()));;
                }
            });

            decreaseRoleAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.valueOf(roleAmount.getText().toString())>0){
                        selectedRolesList.remove(fractionRolesList.get(getAdapterPosition()));
                        //zmienjsza liczbę wybranych o 1:
                        fractionRolesList.get(getAdapterPosition()).setRolePlayersAmount(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()-1);
                        //ustawia tekst TextView na zmienioną liczbę
                        roleAmount.setText(String.valueOf(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()));}
                }
            });

            /**
             * Przy długim naciśnięciu karty roli pojawią się jej opis
             */
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    buildRoleDescriptionDialog();
                    roleDescriptionDialog.show();
                    return false;
                }
            });

        }


        /**
         * Tworzy okienko wyświetlające opis roli
         */
        public void buildRoleDescriptionDialog() {
            final AlertDialog.Builder descriptionDialog = new AlertDialog.Builder(context);
            descriptionDialog.setTitle(context.getString(fractionRolesList.get(getAdapterPosition()).getName()));
            descriptionDialog.setMessage(context.getString(fractionRolesList.get(getAdapterPosition()).getDescription()));
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