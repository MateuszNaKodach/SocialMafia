package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;

public class SelectPlayerRoles extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    FractionPagerAdapter fractionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fractionPagerAdapter = new FractionPagerAdapter(getSupportFragmentManager());
        fractionPagerAdapter.addFragments(new SelectPlayerRolesTownFragment(), getString(R.string.town));
        viewPager.setAdapter(fractionPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class FractionPagerAdapter extends FragmentPagerAdapter{

        ArrayList<Fragment> fractionFragments = new ArrayList<>();
        ArrayList<String> fractionTabsTitles = new ArrayList<>();

        public void addFragments(Fragment fragments, String titles){
            this.fractionFragments.add(fragments);
            this.fractionTabsTitles.add(titles);
        }

        public FractionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fractionFragments.get(position);
        }

        @Override
        public int getCount() {
            return fractionFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fractionTabsTitles.get(position);
        }
    }
}
