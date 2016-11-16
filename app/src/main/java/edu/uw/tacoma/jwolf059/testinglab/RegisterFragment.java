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
        final EditText mUserIdText = (EditText) v.findViewById(R.id.editText);
        final EditText mPwdText = (EditText) v.findViewById(R.id.editText2);
        Button reg = (Button) v.findViewById(R.id.reg_button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = mUserIdText.getText().toString();
                String pass = mPwdText.getText().toString();
                Account newACT = new Account(userID, pass);
                if (newACT.getmEmail() == null || newACT.getmPassword() == null) {
                    newACT = null;
                    Toast.makeText(getContext(), "Invalid Input DO IT AGAIN", Toast.LENGTH_SHORT) .show();
                } else {
                    ((SignInActivity) getActivity()).register(newACT);
                }
            }
        });
        return v;
    }

    public interface OnRegisterListener {
        public void register(Account account);
    }

}
