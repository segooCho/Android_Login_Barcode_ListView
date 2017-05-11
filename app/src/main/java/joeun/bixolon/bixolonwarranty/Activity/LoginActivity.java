package joeun.bixolon.bixolonwarranty.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import joeun.bixolon.bixolonwarranty.Login.LoaderCallback;
import joeun.bixolon.bixolonwarranty.Login.LoginEventButtonSignIn;
import joeun.bixolon.bixolonwarranty.Login.LoginTask;
import joeun.bixolon.bixolonwarranty.R;
import joeun.bixolon.bixolonwarranty.Common.Progress;


import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    public LoginTask loginTask = null;

    // UI references.
    public AutoCompleteTextView loginTextViewId;
    public EditText loginTextViewPassword;

    //Progress
    public Progress progress;
    private View formView, progressView;

    LoaderCallback loaderCallback;
    LoginEventButtonSignIn loginEventButtonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //TODO: 임시 시작 처리
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("LoginID","1001");
        startActivity(intent);
        finish();
        */

        setContentView(R.layout.activity_login);
        findViewByIdView();

        loginEventButtonSignIn = new LoginEventButtonSignIn(this);
        loginTextViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    loginEventButtonSignIn.Login();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEventButtonSignIn.Login();
            }
        });

        progress = new Progress(formView, progressView, getResources().getInteger(android.R.integer.config_shortAnimTime));
    }

    /**
     * View ID 찾기
     */
    private void findViewByIdView() {
        loginTextViewId = (AutoCompleteTextView) findViewById(R.id.loginTextViewId);
        // Set up the login form.
        populateAutoComplete();
        loginTextViewPassword = (EditText) findViewById(R.id.loginTextViewPassword);
        //Progress
        formView = findViewById(R.id.loginformScrollView);
        progressView = findViewById(R.id.loginProgress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        loaderCallback = new LoaderCallback(this);
        getLoaderManager().initLoader(0, null, loaderCallback);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(loginTextViewId, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    /**
     * LoginTask PostExecute의 결과를 받아서 처리 한다
     * @param success
     */
    public void onLoginTaskPostExecuteResult(final Boolean success) {
        loginTask = null;
        progress.ShowProgress(false);

        if (success) {
            //TODO: MainActivity 시작
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("LoginID",loginTextViewId.getText().toString());
            startActivity(intent);
            //TODO: LoginActivity 죽이는건데.. 이 위치는 뒤로가기시 종료?? 위쪽은 로그인으로 이동 뭐지??
            finish();
        } else {
            loginTextViewPassword.setError(getString(R.string.error_incorrect_password));
            loginTextViewPassword.requestFocus();
        }
    }

    /**
     * LoginTask Cancelled 처리
     */
    public void onLoginTaskCancelledResult() {
        loginTask = null;
        progress.ShowProgress(false);
    }

}

