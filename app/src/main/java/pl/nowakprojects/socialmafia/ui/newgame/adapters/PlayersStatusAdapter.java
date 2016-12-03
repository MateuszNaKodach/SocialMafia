package pl.nowakprojects.socialmafia.ui.newgame.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.ui.newgame.dialogfragments.PlayersStatusDialogFragment;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.ui.newgame.interfaces.OnPlayerKilledListener;

/**
 * Created by Mateusz on 01.11.2016.
 */
public class PlayersStatusAdapter extends RecyclerView.Adapter<PlayersStatusAdapter.PlayerStatusViewHolder> {

    private PlayersStatusDialogFragment playersStatusDialogFragment;
    private TheGame mTheGame;
    private ArrayList<HumanPlayer> humanPlayersList;
    private LayoutInflater inflater;
    private Context context;
    private OnPlayerKilledListener mPlayerKillCallback;

    public PlayersStatusAdapter(PlayersStatusDialogFragment playersStatusDialogFragment, Context context, TheGame theGame) {
        this.playersStatusDialogFragment = playersStatusDialogFragment;
        mTheGame = theGame;
        this.humanPlayersList = mTheGame.getPlayersInfoList();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        /*try {
            mPlayerKillCallback = (OnPlayerKilledListener) context
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnPlayerKilledListener");
        }*/

    }

    @Override
    public PlayerStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.player_game_status_layout, parent, false);
        return new PlayerStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayerStatusViewHolder holder, final int position) {
        HumanPlayer humanPlayer = humanPlayersList.get(position);
        holder.playerName.setText(humanPlayer.getPlayerName());
        holder.playerRoleIcon.setImageResource(humanPlayer.getPlayerRole().getIconResourceID());
        holder.roleName.setText(context.getString(humanPlayer.getRoleName()));
        holder.fractionName.setText(context.getString(humanPlayer.getPlayerRole().getFractionNameStringID()));
        showProperlyPlayerStatus(humanPlayer, holder);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showPlayerStatusPopupMenu(holder.itemView, position, holder);
           }
       });
    }//public void onBindViewHolder(final PlayersStatusAdapter.PlayerStatusViewHolder holder, final int position)


    @Override
    public int getItemCount() {
        return humanPlayersList.size();
    }

    private void showPlayerStatusPopupMenu(View view, final int position, final PlayerStatusViewHolder playerStatusViewHolder) {
        PopupMenu popupMenu = new PopupMenu(playersStatusDialogFragment.getActivity(), view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.player_status_menu, popupMenu.getMenu());
        //ustawianie kill albo revive
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.statusmenu_player_info:{
                        humanPlayersList.get(position).showPlayerInfoDialog(context);
                        break;
                    }

                    case R.id.statusmenu_kill_the_player: {

                        if (humanPlayersList.get(position).isAlive())
                            humanPlayersList.get(position).setNotAlive();
                        else
                            humanPlayersList.get(position).reviveThePlayer();

                        notifyDataSetChanged();
                        break;
                    }

                    case R.id.statusmenu_kill_the_player_during_the_game: {

                        if (humanPlayersList.get(position).isAlive())
                            mTheGame.kill(humanPlayersList.get(position));

                        notifyDataSetChanged();
                        break;
                    }
                }

                return false;
            }
        });
        popupMenu.show();
    }//private void showPlayerStatusPopupMenu(View view, final int position, final PlayersStatusAdapter.PlayerStatusViewHolder playerStatusViewHolder)


    private void showProperlyPlayerStatus(HumanPlayer humanPlayer, PlayerStatusViewHolder playerStatusViewHolder) {
        if (humanPlayer.isAlive())
            playerStatusViewHolder.playerStatus.setImageResource(R.drawable.icon_heart);
        else {
            playerStatusViewHolder.playerStatus.setImageResource(R.drawable.icon_ghost);
            // playerStatusViewHolder.playerRoleIcon.setImageResource(R.drawable.icon_ghost);
        }
    }

    class PlayerStatusViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerRoleIcon;
        private TextView playerName;
        private TextView roleName;
        private TextView fractionName;
        private ImageView playerStatus;

        public PlayerStatusViewHolder(View itemView) {
            super(itemView);

            playerRoleIcon = (ImageView) itemView.findViewById(R.id.roleIco);
            playerName = (TextView) itemView.findViewById(R.id.playerName);
            roleName = (TextView) itemView.findViewById(R.id.roleName);
            fractionName = (TextView) itemView.findViewById(R.id.fractionName);
            playerStatus = (ImageView) itemView.findViewById(R.id.playerStatusIcon);

        }


    }//class PlayerStatusViewHolder extends RecyclerView.ViewHolder
}//public class PlayersStatusAdapter extends RecyclerView.Adapter<PlayersStatusAdapter.PlayerStatusViewHolder>
