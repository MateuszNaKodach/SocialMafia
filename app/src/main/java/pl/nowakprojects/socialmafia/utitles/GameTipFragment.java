package pl.nowakprojects.socialmafia.utitles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 18.10.2016.
 */ //MOJA KONWENCJA KODOWANIA!!!
public class GameTipFragment extends Fragment {

    private boolean b_isCollapsed = false;
    private TextView textView_tipTitle;
    private TextView textView_tipContent;
    private Button button_collapseButton;
    private String s_tipTitle;
    private String s_tipContent;

    //public GameTipFragment() {
    //}

    public GameTipFragment(String sTipTitle, String sTipContent) {
        this.s_tipTitle = sTipTitle;
        this.s_tipContent = sTipContent;
    }

    public GameTipFragment(String sTipTitle, String sTipContent, boolean isCollapsed) {
        this.s_tipTitle = sTipTitle;
        this.s_tipContent = sTipContent;
        this.b_isCollapsed=isCollapsed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_game_tip, container, false);
        textView_tipTitle = (TextView) fragmentView.findViewById(R.id.textView_tipTitle);
        textView_tipContent = (TextView) fragmentView.findViewById(R.id.textView_tipContent);
        button_collapseButton = (Button) fragmentView.findViewById(R.id.button_collapseButton);

        if (s_tipTitle == null || s_tipTitle.isEmpty())
            s_tipTitle = getString(R.string.tip_title);
        if (s_tipContent == null || s_tipContent.isEmpty())
            s_tipContent = getString(R.string.tip_content);

        textView_tipTitle.setText(s_tipTitle);
        textView_tipContent.setText(s_tipContent);

        collapse(b_isCollapsed);

        button_collapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            collapse();
            }
        });


        return fragmentView;
    }

    private void collapse(boolean isCollapsed){

        if (b_isCollapsed) {
            textView_tipContent.setVisibility(View.GONE);
            button_collapseButton.setText(getString(R.string.expand));
        } else {
            textView_tipContent.setVisibility(View.VISIBLE);
            button_collapseButton.setText(getString(R.string.collapse));
        }

    }

    private void collapse(){

        b_isCollapsed = !(b_isCollapsed);

        if (b_isCollapsed) {
            textView_tipContent.setVisibility(View.GONE);
            button_collapseButton.setText(getString(R.string.expand));
        } else {
            textView_tipContent.setVisibility(View.VISIBLE);
            button_collapseButton.setText(getString(R.string.collapse));
        }

    }

    public void setS_tipContent(String s_tipContent) {
        this.s_tipContent = s_tipContent;
        this.textView_tipContent.setText(s_tipContent);
        collapse(true);
    }

}
