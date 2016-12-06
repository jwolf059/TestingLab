package edu.uw.tacoma.jwolf059.testinglab;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tacoma.jwolf059.testinglab.autheticate.Account;
import edu.uw.tacoma.jwolf059.testinglab.autheticate.LoginFragment;
import edu.uw.tacoma.jwolf059.testinglab.util.SharedPreferenceEntry;
import edu.uw.tacoma.jwolf059.testinglab.util.SharedPreferenceHelper;

public class SignInActivity extends AppCompatActivity implements RegisterFragment.OnRegisterListener {

    public SharedPreferenceHelper mSharedPreferencesHelper;
    public Account mAccount;

    public static final String USER_ADD_URL = "http://cssgate.insttech.washington.edu/~jwolf059/reg.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        // Instantiate a SharedPreferencesHelper.
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferenceHelper(
                sharedPreferences);
        SharedPreferenceEntry entry = mSharedPreferencesHelper.getLoginInfo();

        if (entry.isLoggedIn()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragement_container, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragement_container, new RegisterFragment())
                    .commit();
        }

    }

    @Override
    public void register(Account account) {
        mAccount = account;
        String url = builderRegisterURL(account);
        RegisterAsyncTask task = new RegisterAsyncTask();
        task.execute(new String[]{url.toString()});

    }

    private String builderRegisterURL(Account account) {
        StringBuilder sb = new StringBuilder(USER_ADD_URL);
        try {
            String userID = account.getmEmail().toLowerCase();
            sb.append("email=");
            sb.append(userID);


            String pwd = account.getmPassword();
            sb.append("&pwd=");
            sb.append(pwd);


            Log.i("Register URL", sb.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    public class RegisterAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        @Override

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext()
                            , "User successfully registered!"
                            , Toast.LENGTH_LONG)
                            .show();

                    SharedPreferenceEntry entry = new SharedPreferenceEntry(
                            true, mAccount.getmEmail());
                    mSharedPreferencesHelper.savePersonalInfo(entry);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragement_container, new LoginFragment())
                            .commit();



                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                    SharedPreferenceEntry entry = new SharedPreferenceEntry(false,"");
                    mSharedPreferencesHelper.savePersonalInfo(entry);

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (id == R.id.action_logout) {
            SharedPreferenceEntry entry = new SharedPreferenceEntry(false,"");
            mSharedPreferencesHelper.savePersonalInfo(entry);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragement_container, new RegisterFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
