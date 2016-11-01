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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.RoleActionsFragment;
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
        PlayerRole playerRole = actionPlayers.get(position).getPlayerRole();
        //  holder.roleIcon.setImageResource(playerRole.getIconResourceID());
        holder.roleName.setText(playerRole.getName());
        holder.playerName.setText(actionPlayers.get(position).getPlayerName());

        //Dodawanie opcji do Spinnera
        ArrayList<String> playerNames = new ArrayList<String>();
        for (HumanPlayer humanPlayer : mTheGame.getLiveHumanPlayers()) {
            if (!(humanPlayer.getPlayerName().equals(actionPlayers.get(position).getPlayerName()))) //wszystkich oprócz samego gracza
                playerNames.add(humanPlayer.getPlayerName());
        }
        ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(roleActionsFragment.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
        choosingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.choosingSpinner.setAdapter(choosingSpinnerAdapter);
        //-------------------------------------------------------

        if (playerRole.getName() == R.string.priest) {
            holder.choosingSpinner2.setVisibility(View.VISIBLE);
            holder.choosingSpinner2.setAdapter(choosingSpinnerAdapter);
        } else
            holder.choosingSpinner2.setVisibility(View.GONE);

        //sprawdza czy akcja została wykoanna
        holder.choosingSpinner.setEnabled(false);
        holder.choosingSpinner2.setEnabled(false);

        if (playerRole.isB_isRoleTurn()) {
            holder.itemView.findViewById(R.id.confirmButton).setEnabled(true);
            holder.choosingSpinner.setEnabled(!playerRole.is_actionMade());
            holder.choosingSpinner2.setEnabled(!playerRole.is_actionMade());
        } else
            holder.itemView.findViewById(R.id.confirmButton).setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return actionPlayers.size();
    }

    class PlayerRoleActionViewHolder extends RecyclerView.ViewHolder {

        // private ImageView roleIcon;
        private TextView roleName;
        private TextView playerName;
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
                    if (!(actionPlayers.get(getAdapterPosition()).getPlayerRole().is_actionMade())) {
                        mTheGame.iActionMadeThisTime();
                        choosingSpinner.setEnabled(false);
                        choosingSpinner2.setEnabled(false);
                        confirmButton.setText(R.string.roleActionDone);

                        enableEndOfDayTimeButton();
                        actionPlayers.get(getAdapterPosition()).getPlayerRole().set_bActionMade(true);
                    }
                    if ((getAdapterPosition() + 1) < actionPlayers.size()) {
                        actionPlayers.get(getAdapterPosition() + 1).getPlayerRole().setB_isRoleTurn(true);
                        notifyItemChanged(getAdapterPosition() + 1);
                    }

                    //notifyItemChanged(getAdapterPosition());
                }


                @Override
                public void onClick(View view) {
                    if (confirmButton.isEnabled() && confirmButton.getText() != roleActionsFragment.getString(R.string.roleActionDone)) {
                        //sprawdza czy to ksiądz, aby wykonać odpowiednią funkcję
                        if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest) {
                            if (mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).equals(mTheGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString())))
                                Toast.makeText(context.getApplicationContext(), roleActionsFragment.getString(R.string.theSameLovers), Toast.LENGTH_LONG).show();
                            else {
                                makePriestAction(mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), mTheGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString()));
                                roleActionWasMade();
                            }
                        } else {
                            makeRoleAction(actionPlayers.get(getAdapterPosition()), mTheGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()));
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
                choosenPlayer.setGuard(actionPlayers.get(getAdapterPosition()));

            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
        }

        private void makeBlackmailerAction(HumanPlayer choosenPlayer) {
            if (!(choosenPlayer.getBlackMailer().contains(actionPlayers.get(getAdapterPosition()))))
                choosenPlayer.setBlackMailer(actionPlayers.get(getAdapterPosition()));

            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
        }

        private void makePriestAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
            choosenPlayer1.setLover(choosenPlayer2);
            choosenPlayer2.setLover(choosenPlayer1);
            FragmentManager fragmentManager = roleActionsFragment.getFragmentManager();
            ShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new ShowingLoversRolesDialog(choosenPlayer1, choosenPlayer2);
            theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
        }

        private void makeMedicAction(HumanPlayer choosenPlayer) {
            mTheGame.addLastNightHealingByMedicPlayers(choosenPlayer);
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isHealingThisNight), Toast.LENGTH_LONG).show();

        }

        private void makeDarkMedicAction(HumanPlayer choosenPlayer) {
            mTheGame.addLastNightHeatingByDarkMedicPlayers(choosenPlayer);
            Toast.makeText(roleActionsFragment.getActivity().getApplicationContext(), choosenPlayer.getPlayerName() + " " + roleActionsFragment.getString(R.string.isHeatingThisNight), Toast.LENGTH_LONG).show();

        }

        private void makeDealerAction(HumanPlayer choosenPlayer) {
            mTheGame.addLastNightDealingByDealerPlayers(choosenPlayer);
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
