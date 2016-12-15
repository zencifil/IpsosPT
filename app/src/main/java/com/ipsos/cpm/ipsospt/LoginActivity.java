package com.ipsos.cpm.ipsospt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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

import com.ipsos.cpm.ipsospt.Helper.Constants;

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

    SessionManager _sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _sessionManager = new SessionManager(getApplicationContext());

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
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@tro.ipsos-kmg.com") || email.contains("@ipsos.com");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
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

        UserLoginTask(String email, String password) {
            _email = email;
            _password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                //TODO don't forget to open
                login();
            }
            catch (InterruptedException e) {
                return false;
            }

            return true;
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
                _passwordTextBox.setError(getString(R.string.error_incorrect_password));
                _passwordTextBox.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private boolean login() {
            //HttpsURLConnection urlConnection;
            //BufferedReader reader;
            //String resultJsonStr = null;

            try {
//                URL url = new URL(Constants.BASE_URL);
//                urlConnection = (HttpsURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return false;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return false;
//                }
//                resultJsonStr = buffer.toString();
//
//                JSONObject resultJson = new JSONObject(resultJsonStr);
                //TODO result icinde adi felan gelmeli...

                _email = "savas.cilve@ipsos.com";
                _fldName = "SAVAS CILVE";
                _sessionManager.createLoginSession(getString(R.string.app_name), _email);

                return true;
            }
            catch (Exception e) {
                Log.e("Login", e.getMessage());
                return false;
            }
        }

        private void navigateToHomeActivity() {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homeIntent.putExtra(Constants.EXTRA_FLDNAME, _fldName);
            homeIntent.putExtra(Constants.EXTRA_EMAIL, _email);
            startActivity(homeIntent);
        }
    }
}

