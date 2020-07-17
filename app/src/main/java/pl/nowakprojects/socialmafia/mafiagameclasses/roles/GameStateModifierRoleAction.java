package pl.nowakprojects.socialmafia.mafiagameclasses.roles;

import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;

/**
 * Created by Mateusz on 05.12.2016.
 */

public interface GameStateModifierRoleAction {
    void action(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers);
    //void commitNotDealedRole(TheGame theGame, HumanPlayer actionPlayer, HumanPlayer... chosePlayers);
}
