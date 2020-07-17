package pl.nowakprojects.socialmafia.ui.newgame.adapters;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.ContextRoleAction;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.GameStateModifierRoleAction;
import pl.nowakprojects.socialmafia.ui.newgame.fragments.RoleActionsFragment;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Adapter do wykonywania roli graczy
 */
public class DaytimeRoleActionsAdapter extends RecyclerView.Adapter<DaytimeRoleActionsAdapter.PlayerRoleActionViewHolder> {

    private RoleActionsFragment roleActionsFragment;
    private TheGame mTheGame;
    private List<HumanPlayer> actionPlayers;
    private LayoutInflater inflater;
    private Context context;

    public DaytimeRoleActionsAdapter(RoleActionsFragment roleActionsFragment, Context context, TheGame theGame) {
        this.roleActionsFragment = roleActionsFragment;
        mTheGame = theGame;
        this.actionPlayers = mTheGame.getGamePlayersManager().getThisNightHumanPlayers();
        //else if(mTheGame.isDayDaytimeNow())
        //    this.actionPlayers = mTheGame.getAllDaysHumanPlayers();

        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerRoleActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.player_role_action_layout, null); //moze tutaj null zamiast parent
        return new PlayerRoleActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerRoleActionViewHolder holder, int position) {
        HumanPlayer humanPlayer = actionPlayers.get(position);
        PlayerRole playerRole = actionPlayers.get(position).getPlayerRole();
        //  holder.roleIcon.setImageResource(playerRole.getIconResourceID());
        holder.roleName.setText(playerRole.getNameId());
        holder.playerName.setText(actionPlayers.get(position).getPlayerName());

        if(humanPlayer.isAlive()) {
            holder.killedIcon.setVisibility(View.GONE);
            //Dodawanie opcji do Spinnera
            ArrayList<String> playerNames = new ArrayList<String>();
            for (HumanPlayer hp : mTheGame.getLiveHumanPlayers()) {
                if(actionPlayers.get(position).getRoleName()!=(R.string.medic)||actionPlayers.get(position).getRoleName()!=(R.string.darkmedic)) {
                    if (!(hp.getPlayerName().equals(actionPlayers.get(position).getPlayerName()))) //wszystkich oprócz samego gracza
                        playerNames.add(hp.getPlayerName());
                }else
                        playerNames.add(hp.getPlayerName());
            }
            ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(roleActionsFragment.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
            choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.choosingSpinner.setAdapter(choosingSpinnerAdapter);
            //-------------------------------------------------------

            //SPRAWDZANIE CZY MAFIA MA 2 STRAZALY ITP!!!
            if (playerRole.getNameId() == R.string.priest) {
                holder.choosingSpinner2.setVisibility(View.VISIBLE);
                holder.choosingSpinner2.setAdapter(choosingSpinnerAdapter);
            } else
                holder.choosingSpinner2.setVisibility(View.GONE);

            //sprawdza czy akcja została wykoanna
            holder.choosingSpinner.setEnabled(false);
            holder.choosingSpinner2.setEnabled(false);

            if (humanPlayer.isPlayerTurn()) {
                holder.itemView.findViewById(R.id.confirmButton).setEnabled(!humanPlayer.isRoleActionMade());
                holder.choosingSpinner.setEnabled(!humanPlayer.isRoleActionMade());
                holder.choosingSpinner2.setEnabled(!humanPlayer.isRoleActionMade());
            } else
                holder.itemView.findViewById(R.id.confirmButton).setEnabled(humanPlayer.isRoleActionMade());
        }else if(humanPlayer.isNotAlive()){
            holder.killedIcon.setVisibility(View.VISIBLE);
            holder.choosingSpinner2.setVisibility(View.GONE);
            holder.choosingSpinner.setVisibility(View.GONE);
            if (humanPlayer.isPlayerTurn()) { //powielony kod!!!
                holder.itemView.findViewById(R.id.confirmButton).setEnabled(!humanPlayer.isRoleActionMade());
                holder.choosingSpinner.setEnabled(!humanPlayer.isRoleActionMade());
                holder.choosingSpinner2.setEnabled(!humanPlayer.isRoleActionMade());
            } else
                holder.itemView.findViewById(R.id.confirmButton).setEnabled(humanPlayer.isRoleActionMade());
        }

    }

    @Override
    public int getItemCount() {
        return actionPlayers.size();
    }

    class PlayerRoleActionViewHolder extends RecyclerView.ViewHolder {

        // private ImageView roleIcon;
        private TextView roleName;
        private TextView playerName;
        private ImageView killedIcon;
        private Button confirmButton;
        private Spinner choosingSpinner;
        private Spinner choosingSpinner2;

        private AlertDialog roleDescriptionDialog;

        public PlayerRoleActionViewHolder(View itemView) {

            super(itemView);

            // roleIcon = (ImageView) itemView.findViewById(R.id.roleIcon);
            roleName = (TextView) itemView.findViewById(R.id.roleName);
            playerName = (TextView) itemView.findViewById(R.id.playerName);
            confirmButton = (Button) itemView.findViewById(R.id.confirmButton);
            choosingSpinner = (Spinner) itemView.findViewById(R.id.playersNamesSpinner);
            killedIcon = (ImageView) itemView.findViewById(R.id.killedIcon);

            //Jeśli graczem jest ksiądz to używamy innego layoutu, dla wyboru 2 graczy do kochanków
//                    if(actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest)
            choosingSpinner2 = (Spinner) itemView.findViewById(R.id.playersNamesSpinner2);

            //zatwierdzenie wykonania roli gracza
            confirmButton.setOnClickListener(new View.OnClickListener() {

                private boolean isAllActionsMade() {
                    return mTheGame.iGetActionsMadeThisTime() == actionPlayers.size();
                }

                private void enableEndOfDayTimeButton() {
                    if (isAllActionsMade()) {
                        EventBus eventBus = EventBus.getDefault();
                        eventBus.post(true);
                    }
                    //TYMCZASOWE ROZWIAZANIE - ZMIENIC!!!!
                }

                /**
                 * Rola została wykonana
                 * Nie jest już możliwa zmiana wybranych graczy
                 * Dodajemy do sumy wykonanych ról
                 */
                void roleActionWasMade() {
                    if (!(actionPlayers.get(getAdapterPosition()).isRoleActionMade())) {
                        mTheGame.getAmountActionsMadeThisTime();
                        choosingSpinner.setEnabled(false);
                        choosingSpinner2.setEnabled(false);
                        confirmButton.setText(R.string.roleActionDone);

                        enableEndOfDayTimeButton();
                        actionPlayers.get(getAdapterPosition()).setRoleActionMade(true);
                    }
                    if ((getAdapterPosition() + 1) < actionPlayers.size()) {
                        actionPlayers.get(getAdapterPosition() + 1).setPlayerTurn(true);
                        notifyItemChanged(getAdapterPosition() + 1);
                    }

                    //notifyItemChanged(getAdapterPosition());
                }


                @Override
                public void onClick(View view) {
                    if (confirmButton.isEnabled() && confirmButton.getText() != roleActionsFragment.getString(R.string.roleActionDone)) {
                        //sprawdza czy to ksiądz, aby wykonać odpowiednią funkcję
                        if(actionPlayers.get(getAdapterPosition()).isAlive()) {
                            if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest) {
                                if (mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).equals(mTheGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString())))
                                    Toast.makeText(context.getApplicationContext(), roleActionsFragment.getString(R.string.theSameLovers), Toast.LENGTH_LONG).show();
                                else {
                                    makeRoleAction(actionPlayers.get(getAdapterPosition()),mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), mTheGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString()));
                                    roleActionWasMade();
                                }
                            } else if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.mafiaKill) {
                                if(mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).getRoleName()==R.string.priest&&mTheGame.getGamePlayersManager().isMafiaBossAlive()){
                                    Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), R.string.cantKillPriestWhileBossAlive, Toast.LENGTH_LONG).show();
                                }else {
                                    makeRoleAction(actionPlayers.get(getAdapterPosition()),mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), null);
                                    roleActionWasMade();
                                }
                                } else {
                                makeRoleAction(actionPlayers.get(getAdapterPosition()), mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()),null);
                                roleActionWasMade();
                            }
                        }else{
                            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), R.string.thisPlayerIsDeadBut, Toast.LENGTH_LONG).show();
                            roleActionWasMade();
                        }
                    }
                }
            });

        }

        /**
         * Wykonywanie roli graczy
         *
         * @param actionPlayer
         * @param choosenPlayer
         */
        void makeRoleAction(HumanPlayer actionPlayer, HumanPlayer choosenPlayer, HumanPlayer choosenPlayer2) {
         //MOZLIWOSC BYCIA DILOWANYM DO WSZYSTKICH FUNKCJI!!!
            if(actionPlayer.getPlayerRole() instanceof ContextRoleAction) {
                ContextRoleAction playerRole = (ContextRoleAction) actionPlayer.getPlayerRole();
                playerRole.action(roleActionsFragment, actionPlayer, choosenPlayer, choosenPlayer2);
                //actionPlayer.getPlayerRole().action(roleActionsFragment, actionPlayer, choosenPlayer, choosenPlayer2);
            }else if(actionPlayer.getPlayerRole() instanceof GameStateModifierRoleAction) {
                //actionPlayer.getPlayerRole().action(mTheGame, actionPlayer, choosenPlayer, choosenPlayer2);
                GameStateModifierRoleAction playerRole = (GameStateModifierRoleAction) actionPlayer.getPlayerRole();
                playerRole.action(mTheGame, actionPlayer, choosenPlayer, choosenPlayer2);
            }
           // else
                //throw new IllegalClassException("There is necessary class implements ContextRoleAction or GameStateModifierRoleAction!");
        }
    }


}
