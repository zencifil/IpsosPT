package com.ipsos.cpm.ipsospt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.data.PTContract;
import com.ipsos.cpm.ipsospt.data.PTProvider;
import com.ipsos.cpm.ipsospt.helper.ConnectivityReceiver;
import com.ipsos.cpm.ipsospt.helper.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText _emailTextBox;
    private EditText _passwordTextBox;
    private View _progressView;
    private View _loginFormView;
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        _emailTextBox = (EditText) findViewById(R.id.email);
        _passwordTextBox = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        _loginFormView = findViewById(R.id.login_form);
        _progressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        _emailTextBox.setError(null);
        _passwordTextBox.setError(null);

        // Store values at the time of the login attempt.
        String email = _emailTextBox.getText().toString();
        String password = _passwordTextBox.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            _passwordTextBox.setError(getString(R.string.error_invalid_password));
            focusView = _passwordTextBox;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            _emailTextBox.setError(getString(R.string.error_field_required));
            focusView = _emailTextBox;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            _emailTextBox.setError(getString(R.string.error_invalid_email));
            focusView = _emailTextBox;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if (!ConnectivityReceiver.isConnected()) {
                Toast.makeText(LoginActivity.this, getString(R.string.internet_no_connection_login), Toast.LENGTH_LONG).show();
                return;
            }
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@tro.ipsos-kmg.com") || email.contains("@ipsos.com");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            _loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            _loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    _loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            _progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            _progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    _progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            _progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            _loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String _email;
        private final String _password;
        private String _fldName;
        private String _fldCode;
        private String _errorMessage;

        UserLoginTask(String email, String password) {
            _email = email;
            _password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return login();
            }
            catch (Exception e) {
                return false;
            }

            //return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                navigateToHomeActivity();
            }
            else {
                //_passwordTextBox.setError(getString(R.string.error_incorrect_password));
                _passwordTextBox.setError(_errorMessage);
                _passwordTextBox.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private boolean login() {
            HttpsURLConnection urlConnection;
            BufferedReader reader;
            String resultJsonStr = null;
            boolean result = false;

            try {
                URL url = new URL(Constants.BASE_URL + Constants.API_LOGIN);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);

                String params = Constants.QUERY_PARAM_USERNAME + "=" + _email +
                        "&" +
                        Constants.QUERY_PARAM_PASSWORD + "=" + _password +
                        "&" +
                        Constants.QUERY_PARAM_GRANT_TYPE + "=password";

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params);

                writer.flush();
                writer.close();
                os.close();

                if (urlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    InputStream errorStream = urlConnection.getErrorStream();
                    StringBuilder errorBuffer = new StringBuilder();
                    if (errorStream != null) {
                        reader = new BufferedReader(new InputStreamReader(errorStream));
                        String l;
                        while ((l = reader.readLine()) != null) {
                            errorBuffer.append(l + "\n");
                        }
                    }
                    JSONObject errorJson = new JSONObject(errorBuffer.toString());
                    String errorDesc = errorJson.getString(Constants.JSON_ERROR_DESCRIPTION);
                    if (errorDesc.contains("password is incorrect"))
                        _errorMessage = getString(R.string.error_incorrect_password);
                    result = false;
                }
                else {
                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();
                    if (inputStream == null) {
                        // Nothing to do.
                        return false;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return false;
                    }
                    resultJsonStr = buffer.toString();

                    JSONObject resultJson = new JSONObject(resultJsonStr);
                    String authKey = resultJson.getString(Constants.JSON_AUTH_KEY_TOKEN);

                    //TODO take these from json
                    _fldName = "TEST PT";
                    _fldCode = "G5";

                    Calendar cal = Calendar.getInstance(); // the value to be formatted
                    DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
                    formatter.setTimeZone(cal.getTimeZone());
                    String akObtain = formatter.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, Constants.AUTHKEY_VALID_DAY);
                    String akValidUntil = formatter.format(cal.getTime());
                    ContentValues values = new ContentValues();
                    values.put(PTContract.UserInfo.COLUMN_AUTH_KEY, authKey);
                    values.put(PTContract.UserInfo.COLUMN_AK_OBTAIN_DATE, akObtain);
                    values.put(PTContract.UserInfo.COLUMN_AK_VALID_UNTIL, akValidUntil);
                    getContentResolver().delete(PTContract.UserInfo.CONTENT_URI, null, null);
                    getContentResolver().insert(PTContract.UserInfo.CONTENT_URI, values);

                    IpsosPTApplication.getInstance().createLoginSession(_fldName, _email, _fldCode);

                    result = true;
                }
            }
            catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
                result = false;
            }

            return result;
        }

        private void navigateToHomeActivity() {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            homeIntent.putExtra(Constants.EXTRA_FLDNAME, _fldName);
//            homeIntent.putExtra(Constants.EXTRA_EMAIL, _email);
//            homeIntent.putExtra(Constants.EXTRA_FLDCODE, _fldCode);
            startActivity(homeIntent);
        }
    }
}

