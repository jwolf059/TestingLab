package edu.uw.tacoma.jwolf059.testinglab;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tacoma.jwolf059.testinglab.autheticate.Account;

public class SignInActivity extends AppCompatActivity implements RegisterFragment.OnRegisterListener {

    public static final String USER_ADD_URL = "http://cssgate.insttech.washington.edu/~jwolf059/register.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragement_container, new RegisterFragment())
                .commit();
    }

    @Override
    public void register(Account account) {
        String url = builderRegisterURL(account);
        RegisterAsyncTask task = new RegisterAsyncTask();
        task.execute(new String[]{url.toString()});

    }

    private String builderRegisterURL(Account account) {
        StringBuilder sb = new StringBuilder(USER_ADD_URL);
        try {
            String userID = account.getmEmail().toLowerCase();
            sb.append("id='");
            sb.append(userID);
            sb.append("'");

            String pwd = account.getmPassword();
            sb.append("&pass='");
            sb.append(pwd);
            sb.append("'");

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
                if (status.equals("sucess")) {
                    Toast.makeText(getApplicationContext()
                            , "User successfully registered!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }
}
