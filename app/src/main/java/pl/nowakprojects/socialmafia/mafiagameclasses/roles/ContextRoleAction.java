package pl.nowakprojects.socialmafia.mafiagameclasses.roles;

import androidx.fragment.app.Fragment;

import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;

/**
 * Created by Mateusz on 05.12.2016.
 */

public interface ContextRoleAction {
    void action(Fragment fragment, HumanPlayer actionPlayer, HumanPlayer... chosePlayers);
}
