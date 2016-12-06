package edu.uw.tacoma.jwolf059.testinglab.autheticate;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.uw.tacoma.jwolf059.testinglab.R;
import edu.uw.tacoma.jwolf059.testinglab.menu.MenuActivity;
import edu.uw.tacoma.jwolf059.testinglab.util.SharedPreferenceEntry;
import edu.uw.tacoma.jwolf059.testinglab.util.SharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferenceHelper sharedPreferencesHelper = new SharedPreferenceHelper(
                sharedPreferences);
        SharedPreferenceEntry entry = sharedPreferencesHelper.getLoginInfo();

        Button menuButton = (Button) v.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });


        if (entry.isLoggedIn()){
            TextView email = (TextView) v.findViewById(R.id.email_login_text);
            email.setText("You are logged in as " + entry.getEmail());

        }
        return v;
    }


}
