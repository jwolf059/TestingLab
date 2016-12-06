package edu.uw.tacoma.jwolf059.testinglab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tacoma.jwolf059.testinglab.autheticate.Account;

import static edu.uw.tacoma.jwolf059.testinglab.R.id.register_button;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText mUserIdText = (EditText) v.findViewById(R.id.email_text);
        final EditText mPwdText = (EditText) v.findViewById(R.id.pwd_text);
        Button reg = (Button) v.findViewById(register_button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mUserIdText.getText().toString();
                String pwd = mPwdText.getText().toString();
                try {
                    Account newAccount = new Account(userId, pwd);
                    ((SignInActivity) getActivity()).register(newAccount);

                } catch (IllegalArgumentException e) {
                    Toast.makeText(v.getContext()
                            , e.getMessage()
                            , Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        return v;
    }

    public interface OnRegisterListener {
        public void register(Account account);
    }

}
