package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.nowakprojects.socialmafia.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameFinishedActivityFragment extends Fragment {

    public GameFinishedActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_finished, container, false);
    }
}
