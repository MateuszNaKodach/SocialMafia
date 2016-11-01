package pl.nowakprojects.socialmafia.utitles;

/**
 * Created by Mateusz on 17.10.2016.
 * Klasa porównuje która rola ma się później obudzić.
 */

import java.util.Comparator;

import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;

public class GameRolesWakeHierarchyComparator implements Comparator<HumanPlayer> {

    @Override
    public int compare(HumanPlayer role1, HumanPlayer role2) {
        return (role1.getPlayerRole().getNightWakeHierarchyNumber()>role2.getPlayerRole().getNightWakeHierarchyNumber()) ? 1:(-1);
    }
}
