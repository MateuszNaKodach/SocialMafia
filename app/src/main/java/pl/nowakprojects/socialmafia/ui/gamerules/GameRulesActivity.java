package pl.nowakprojects.socialmafia.ui.gamerules;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.nowakprojects.socialmafia.R;

public class GameRulesActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().dispatchMenuVisibilityChanged(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }



    public static class GeneralRules extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View framgentLayout = inflater.inflate(R.layout.fragment_game_rules_general, container, false);
            return framgentLayout;
        }
    }

    public static class TownRolesExplanation extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View framgentLayout = inflater.inflate(R.layout.fragment_game_rules_town, container, false);
            return framgentLayout;
        }
    }

    public static class MafiaRolesExplanation extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View framgentLayout = inflater.inflate(R.layout.fragment_game_rules_mafia, container, false);
            return framgentLayout;
        }
    }

    public static class SyndicateRolesExplanation extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View framgentLayout = inflater.inflate(R.layout.fragment_game_rules_syndicate, container, false);
            return framgentLayout;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GeneralRules();
                case 1:
                    return new TownRolesExplanation();
                case 2:
                    return new MafiaRolesExplanation();
                case 3:
                    return new SyndicateRolesExplanation();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.generalRules);
                case 1:
                    return getString(R.string.town);
                case 2:
                    return getString(R.string.mafia);
                case 3:
                    return getString(R.string.syndicate);
            }
            return null;
        }
    }
}
