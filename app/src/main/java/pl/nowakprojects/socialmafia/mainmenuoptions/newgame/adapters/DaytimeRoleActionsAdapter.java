package pl.nowakprojects.socialmafia.mainmenuoptions.newgame.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.fragments.RoleActionsFragment;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.ShowingLoversRolesDialog;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.ShowingPlayerGoodOrBadDialog;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.dialogfragments.ShowingPlayerRoleDialog;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Adapter do wykonywania roli graczy
 */
public class DaytimeRoleActionsAdapter extends RecyclerView.Adapter<DaytimeRoleActionsAdapter.PlayerRoleActionViewHolder> {

    private RoleActionsFragment roleActionsFragment;
    private TheGame mTheGame;
    private ArrayList<HumanPlayer> actionPlayers;
    private LayoutInflater inflater;
    private Context context;

    public DaytimeRoleActionsAdapter(RoleActionsFragment roleActionsFragment, Context context, TheGame theGame) {
        this.roleActionsFragment = roleActionsFragment;
        mTheGame = theGame;
        if(mTheGame.isSpecialZeroNightNow()) //OGARNAC CI CO SIE BUDZA O DANEJ PORZE DNIA
            this.actionPlayers = mTheGame.getZeroNightHumanPlayers();
        else if(mTheGame.isNightDaytimeNow())
            this.actionPlayers = mTheGame.getAllNightsBesideZeroHumanPlayers();
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
        holder.roleName.setText(playerRole.getName());
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
            if (playerRole.getName() == R.string.priest) {
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
                        mTheGame.iActionMadeThisTime();
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
                                    makePriestAction(mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), mTheGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString()));
                                    roleActionWasMade();
                                }
                            } else if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.mafiaKill) {
                                if(mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).getRoleName()==R.string.priest&&mTheGame.isMafiaBossAlive()){
                                    Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), R.string.cantKillPriestWhileBossAlive, Toast.LENGTH_LONG).show();
                                }else {
                                    makeMafiaAction(mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), null);
                                    roleActionWasMade();
                                }
                                } else {
                                makeRoleAction(actionPlayers.get(getAdapterPosition()), mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()));
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
        void makeRoleAction(HumanPlayer actionPlayer, HumanPlayer choosenPlayer) {
            switch (actionPlayer.getRoleName()) {
                case R.string.policeman:
                    makePolicemanAction(choosenPlayer);
                    break;
                case R.string.prostitute:
                    makeProstituteAction(choosenPlayer);
                    break;
                case R.string.medic:
                    makeMedicAction(choosenPlayer);
                    break;
                case R.string.black:
                    makeBlackManAction(choosenPlayer);
                    break;
                case R.string.blackJudge:
                    makeBlackManAction(choosenPlayer);
                    break;
                case R.string.blackmailer:
                    makeBlackmailerAction(choosenPlayer);
                    break;
                case R.string.blackmailerBoss:
                    makeBlackmailerAction(choosenPlayer);
                    break;
                case R.string.darkmedic:
                    makeDarkMedicAction(choosenPlayer);
                    break;
                case R.string.dealer:
                    makeDealerAction(choosenPlayer);
                    break;
                case R.string.rapist:
                    makeProstituteAction(choosenPlayer);
                    break;
                case R.string.hitler:
                    makeProstituteAction(choosenPlayer);
                    break;
                case R.string.deathAngel:
                    makeDeathAngelAction(choosenPlayer);
                    break;
                case R.string.bartender:
                    //makeBartenderAction(choosenPlayer);
                    break;
            }
        }

        private void makeProstituteAction(HumanPlayer choosenPlayer) {
            FragmentManager fragmentManager = roleActionsFragment.getFragmentManager();
            ShowingPlayerRoleDialog showingPlayerRoleDialog = new ShowingPlayerRoleDialog(choosenPlayer);
            showingPlayerRoleDialog.show(fragmentManager, "ProstituteAction");
        }

        private void makePolicemanAction(HumanPlayer choosenPlayer) {
            FragmentManager fragmentManager = roleActionsFragment.getFragmentManager();
            ShowingPlayerGoodOrBadDialog showingPlayerGoodOrBadDialog = new ShowingPlayerGoodOrBadDialog(choosenPlayer);
            showingPlayerGoodOrBadDialog.show(fragmentManager, "PolicemanAction");
        }

        private void makeBlackManAction(HumanPlayer choosenPlayer) {
            if (!(choosenPlayer.getGuardsList().contains(actionPlayers.get(getAdapterPosition()))))
                choosenPlayer.addGuard(actionPlayers.get(getAdapterPosition()));

            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
        }

        private void makeBlackmailerAction(HumanPlayer choosenPlayer) {
            if (!(choosenPlayer.getBlackMailersList().contains(actionPlayers.get(getAdapterPosition()))))
                choosenPlayer.addBlackMailer(actionPlayers.get(getAdapterPosition()));

            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
        }

        private void makePriestAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
            choosenPlayer1.addLover(choosenPlayer2);
            choosenPlayer2.addLover(choosenPlayer1);
            FragmentManager fragmentManager = roleActionsFragment.getFragmentManager();
            ShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new ShowingLoversRolesDialog(choosenPlayer1, choosenPlayer2);
            theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
        }

        //DODAC MOZLIWOSC BYCIA DILOWANYM DO WSZYSTKICH FUNKCJI!!!!
        //DOKONCZYC GDY ZABIJE SIE WIELE SKLEPÓW Z BRONIĄ!
        private void makeMafiaAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
            mTheGame.addLastNightHittingByMafiaPlayer(choosenPlayer1);
            if(choosenPlayer2!=null){
                mTheGame.addLastNightHittingByMafiaPlayer(choosenPlayer2);}

            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer1.getPlayerName() + " " + roleActionsFragment.getString(R.string.hadHitByMafiaNow), Toast.LENGTH_LONG).show();
        }

        private void makeMedicAction(HumanPlayer choosenPlayer) {
            mTheGame.addLastNightHealingByMedicPlayers(choosenPlayer);
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isHealingThisNight), Toast.LENGTH_LONG).show();

        }

        private void makeDarkMedicAction(HumanPlayer choosenPlayer) {
            if(choosenPlayer.isNotDealed()){
                mTheGame.addLastNightHeatingByDarkMedicPlayers(choosenPlayer);
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isHeatingThisNight), Toast.LENGTH_LONG).show();}

        }

        private void makeDealerAction(HumanPlayer choosenPlayer) {
            mTheGame.addLastNightDealingByDealerPlayers(choosenPlayer);
            mTheGame.findHumanPlayerByName(choosenPlayer.getPlayerName()).setDealed(true);
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isDealingThisNight), Toast.LENGTH_LONG).show();

        }

        private void makeDeathAngelAction(HumanPlayer choosenPlayer) {
            choosenPlayer.addStigma();
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isSignedThisNight), Toast.LENGTH_LONG).show();

        }

        private void makeBartenderAction() {
            //przusawnie strzalu, wybiera stronę i ilość
        }

        private void makeHitlerAction() {
            FragmentManager fragmentManager = roleActionsFragment.getFragmentManager();
            //TheGameActionShowingJewPlayerName theGameActionShowingJewPlayerName = new TheGameActionShowingJewPlayerName(theGameActionActivity.mTheGame.findHumanPlayerByRoleName(getString(R.string.jew)));
            //theGameActionShowingJewPlayerName.show(fragmentManager, "ProstituteAction");
        }

        //----daytime::
        private void makeJudgeDecisionAction() {
        }

        ;

        //-----


    }


}
