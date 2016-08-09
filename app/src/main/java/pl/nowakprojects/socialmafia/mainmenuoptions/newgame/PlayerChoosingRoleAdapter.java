package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;

/**
 * Created by Mateusz on 08.08.2016.
 */
public class PlayerChoosingRoleAdapter extends RecyclerView.Adapter<PlayerChoosingRoleAdapter.PlayerRoleViewHolder> {

    private ArrayList<PlayerRole> fractionRolesList;
    private LayoutInflater inflater;

    public PlayerChoosingRoleAdapter(ArrayList<PlayerRole> fractionRolesList, Context context){
        this.fractionRolesList = fractionRolesList;
        this.inflater = LayoutInflater.from(context);
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

        public PlayerRoleViewHolder(View itemView) {
            super(itemView);

            roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
            roleName = (TextView) itemView.findViewById(R.id.roleName);
            roleAmount = (TextView) itemView.findViewById(R.id.roleAmount);
            increaseRoleAmount = (ImageButton) itemView.findViewById(R.id.increaseRoleAmountButton);
            decreaseRoleAmount = (ImageButton) itemView.findViewById(R.id.decreaseRoleAmountButton);
            container = itemView.findViewById(R.layout.player_role_choosing_layout);

            increaseRoleAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    roleAmount.setText(String.valueOf(Integer.valueOf(roleAmount.getText().toString())+1));
                }
            });

            decreaseRoleAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.valueOf(roleAmount.getText().toString())>0)
                        roleAmount.setText(String.valueOf(Integer.valueOf(roleAmount.getText().toString())-1));
                }
            });

        }
    }
}
