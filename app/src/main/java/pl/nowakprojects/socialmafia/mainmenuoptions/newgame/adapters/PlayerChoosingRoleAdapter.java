package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.BuildConfig;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.PlayerRole;

/**
 * Created by Mateusz on 08.08.2016.
 */
public class PlayerChoosingRoleAdapter extends RecyclerView.Adapter<PlayerChoosingRoleAdapter.PlayerRoleViewHolder> {

    private ArrayList<PlayerRole> fractionRolesList;
    private ArrayList<PlayerRole> selectedRolesList;
    private LayoutInflater inflater;
    private Context context;

    public ArrayList<PlayerRole> getFractionRolesList() {
        return fractionRolesList;
    }

    public ArrayList<PlayerRole> getSelectedRolesList() {
        return selectedRolesList;
    }

    private RoleAmountChangedCallback roleAmountChangedCallback;

    public interface RoleAmountChangedCallback{
        void amountDecrease(PlayerRole.Fraction fraction);
        void amountIncrease(PlayerRole.Fraction fraction);
    }

    public void setRoleAmountChangedCallback(RoleAmountChangedCallback roleAmountChangedCallback) {
        this.roleAmountChangedCallback = roleAmountChangedCallback;
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
        holder.roleAmount.setText(String.valueOf(playerRole.getRolePlayersAmount()));
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
        private AlertDialog roleDescriptionDialog;


        public PlayerRoleViewHolder(View itemView) {
            super(itemView);

            roleIcon = (ImageView) itemView.findViewById(R.id.roleIco);
            roleName = (TextView) itemView.findViewById(R.id.roleName);
            roleAmount = (TextView) itemView.findViewById(R.id.roleAmount);
            increaseRoleAmount = (ImageButton) itemView.findViewById(R.id.increaseRoleAmountButton);
            decreaseRoleAmount = (ImageButton) itemView.findViewById(R.id.decreaseRoleAmountButton);
            //container = itemView.findViewById(R.layout.player_role_choosing_layout);

            increaseRoleAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!BuildConfig.PREMIUM_VERSION && !fractionRolesList.get(getAdapterPosition()).isBasicMafiaRole() && fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()>0)
                        Toast.makeText(context,R.string.premiu_need,Toast.LENGTH_SHORT).show();
                    else{
                    selectedRolesList.add(fractionRolesList.get(getAdapterPosition()));
                    fractionRolesList.get(getAdapterPosition()).setRolePlayersAmount(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()+1);
                    roleAmount.setText(String.valueOf(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()));
                    roleAmountChangedCallback.amountIncrease(fractionRolesList.get(getAdapterPosition()).getFraction());
                    }
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
                        roleAmount.setText(String.valueOf(fractionRolesList.get(getAdapterPosition()).getRolePlayersAmount()));
                        roleAmountChangedCallback.amountDecrease(fractionRolesList.get(getAdapterPosition()).getFraction());}
                }
            });

            /**
             * Przy naciśnięciu karty roli pojawią się jej opis
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildRoleDescriptionDialog();
                    roleDescriptionDialog.show();
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
