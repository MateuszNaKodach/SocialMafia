package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;

/**
 * Created by Mateusz on 19.10.2016.
 */
public class RoleActionsFragment extends Fragment {

    private TheGameActionActivity theGameActionActivity;
    DayOrNightRoleActionsAdapter dayOrNightRoleActionsAdapter;
    ArrayList<HumanPlayer> dayOrNightHumanPlayers;


    public RoleActionsFragment(TheGameActionActivity theGameActionActivity) {
        this.theGameActionActivity = theGameActionActivity;
        // Required empty public constructor
    }

    public RoleActionsFragment(TheGameActionActivity theGameActionActivity, ArrayList<HumanPlayer> dayOrNightHumanPlayers) {
        this.theGameActionActivity = theGameActionActivity;
        this.dayOrNightHumanPlayers = dayOrNightHumanPlayers;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dayOrNightRoleActionsAdapter = new DayOrNightRoleActionsAdapter(theGameActionActivity.getApplicationContext(), dayOrNightHumanPlayers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_time_role_actions, null, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView playersActionsRecyclerView = (RecyclerView) view.findViewById(R.id.playersActionsRecyclerView);
        playersActionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL, false));
        playersActionsRecyclerView.setAdapter(dayOrNightRoleActionsAdapter);
    }

    /**
     * Adapter do wykonywania roli graczy
     */
    public class DayOrNightRoleActionsAdapter extends RecyclerView.Adapter<DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder> {

        private ArrayList<HumanPlayer> actionPlayers;

        private LayoutInflater inflater;
        private Context context;

        public DayOrNightRoleActionsAdapter(Context context, ArrayList<HumanPlayer> actionPlayers) {
            this.actionPlayers = actionPlayers; //ogarnac zeby tylko te co się budzą danej porze dnia!!
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_role_action_layout, null); //moze tutaj null zamiast parent
            return new DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DayOrNightRoleActionsAdapter.PlayerRoleActionViewHolder holder, int position) {
            PlayerRole playerRole = actionPlayers.get(position).getPlayerRole();
            //  holder.roleIcon.setImageResource(playerRole.getIconResourceID());
            holder.roleName.setText(playerRole.getName());
            holder.playerName.setText(actionPlayers.get(position).getPlayerName());

            //Dodawanie opcji do Spinnera
            ArrayList<String> playerNames = new ArrayList<String>();
            for (HumanPlayer humanPlayer : theGameActionActivity.theGame.getLiveHumanPlayers()) {
                if (!(humanPlayer.getPlayerName().equals(actionPlayers.get(position).getPlayerName()))) //wszystkich oprócz samego gracza
                    playerNames.add(humanPlayer.getPlayerName());
            }
            ArrayAdapter<String> choosingSpinnerAdapter = new ArrayAdapter<String>(theGameActionActivity.getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
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
                        return theGameActionActivity.theGame.iGetActionsMadeThisTime() == actionPlayers.size();
                    }

                    private void enableEndOfDayTimeButton() {
                        if (isAllActionsMade())
                            theGameActionActivity.nightTimeFragment.finishTheNightButton.setEnabled(true);
                    }

                    /**
                     * Rola została wykonana
                     * Nie jest już możliwa zmiana wybranych graczy
                     * Dodajemy do sumy wykonanych ról
                     */
                    void roleActionWasMade() {
                        if (!(actionPlayers.get(getAdapterPosition()).getPlayerRole().is_actionMade())) {
                            theGameActionActivity.theGame.iActionMadeThisTime();
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
                        if (confirmButton.isEnabled() && confirmButton.getText() != getString(R.string.roleActionDone)) {
                            //sprawdza czy to ksiądz, aby wykonać odpowiednią funkcję
                            if (actionPlayers.get(getAdapterPosition()).getRoleName() == R.string.priest) {
                                if (theGameActionActivity.theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()).equals(theGameActionActivity.theGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString())))
                                    Toast.makeText(theGameActionActivity.getApplicationContext(), getString(R.string.theSameLovers), Toast.LENGTH_LONG).show();
                                else {
                                    makePriestAction(theGameActionActivity.theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()), theGameActionActivity.theGame.findHumanPlayerByName(choosingSpinner2.getSelectedItem().toString()));
                                    roleActionWasMade();
                                }
                            } else {
                                makeRoleAction(actionPlayers.get(getAdapterPosition()), theGameActionActivity.theGame.findHumanPlayerByName(choosingSpinner.getSelectedItem().toString()));
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
                FragmentManager fragmentManager = getFragmentManager();
                TheGameActionShowingPlayerRoleDialog theGameActionShowingPlayerRoleDialog = new TheGameActionShowingPlayerRoleDialog(choosenPlayer);
                theGameActionShowingPlayerRoleDialog.show(fragmentManager, "ProstituteAction");
            }

            private void makePolicemanAction(HumanPlayer choosenPlayer) {
                FragmentManager fragmentManager = getFragmentManager();
                TheGameActionShowingPlayerGoodOrBad theGameActionShowingPlayerGoodOrBad = new TheGameActionShowingPlayerGoodOrBad(choosenPlayer);
                theGameActionShowingPlayerGoodOrBad.show(fragmentManager, "PolicemanAction");
            }

            private void makeBlackManAction(HumanPlayer choosenPlayer) {
                if (!(choosenPlayer.getGuard().contains(actionPlayers.get(getAdapterPosition()))))
                    choosenPlayer.setGuard(actionPlayers.get(getAdapterPosition()));

                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackNow), Toast.LENGTH_LONG).show();
            }

            private void makeBlackmailerAction(HumanPlayer choosenPlayer) {
                if (!(choosenPlayer.getBlackMailer().contains(actionPlayers.get(getAdapterPosition()))))
                    choosenPlayer.setBlackMailer(actionPlayers.get(getAdapterPosition()));

                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.hasBlackmailerNow), Toast.LENGTH_LONG).show();
            }

            private void makePriestAction(HumanPlayer choosenPlayer1, HumanPlayer choosenPlayer2) {
                choosenPlayer1.setLover(choosenPlayer2);
                choosenPlayer2.setLover(choosenPlayer1);
                FragmentManager fragmentManager = getFragmentManager();
                TheGameActionShowingLoversRolesDialog theGameActionShowingLoversRolesDialog = new TheGameActionShowingLoversRolesDialog(this, choosenPlayer1, choosenPlayer2);
                theGameActionShowingLoversRolesDialog.show(fragmentManager, "PriestAction");
            }

            private void makeMedicAction(HumanPlayer choosenPlayer) {
                theGameActionActivity.theGame.addLastNightHealingByMedicPlayers(choosenPlayer);
                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isHealingThisNight), Toast.LENGTH_LONG).show();

            }

            private void makeDarkMedicAction(HumanPlayer choosenPlayer) {
                theGameActionActivity.theGame.addLastNightHeatingByDarkMedicPlayers(choosenPlayer);
                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isHeatingThisNight), Toast.LENGTH_LONG).show();

            }

            private void makeDealerAction(HumanPlayer choosenPlayer) {
                theGameActionActivity.theGame.addLastNightDealingByDealerPlayers(choosenPlayer);
                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isDealingThisNight), Toast.LENGTH_LONG).show();

            }

            private void makeDeathAngelAction(HumanPlayer choosenPlayer) {
                choosenPlayer.addStigma();
                Toast.makeText(theGameActionActivity.getApplicationContext(), choosenPlayer.getPlayerName() + " " + getString(R.string.isSignedThisNight), Toast.LENGTH_LONG).show();

            }

            private void makeBartenderAction() {
                //przusawnie strzalu, wybiera stronę i ilość
            }

            private void makeHitlerAction() {
                FragmentManager fragmentManager = getFragmentManager();
                //TheGameActionShowingJewPlayerName theGameActionShowingJewPlayerName = new TheGameActionShowingJewPlayerName(theGameActionActivity.theGame.findHumanPlayerByRoleName(getString(R.string.jew)));
                //theGameActionShowingJewPlayerName.show(fragmentManager, "ProstituteAction");
            }

            //----daytime::
            private void makeJudgeDecisionAction() {
            }

            ;

            //-----


        }


    }


}
