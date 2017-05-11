package joeun.bixolon.bixolonwarranty.Login;

import android.text.TextUtils;
import android.view.View;

import joeun.bixolon.bixolonwarranty.Activity.LoginActivity;
import joeun.bixolon.bixolonwarranty.R;

/**
 * Created by admin on 2017. 5. 10..
 */

public class AttemptLogin {
    private LoginActivity context;

    /**
     *
     * @param _context
     */
    public AttemptLogin(LoginActivity _context) {
        context = _context;
    }

    /**
     *
     */
    public void Login() {
        if (context.mAuthTask != null) {
            return;
        }

        // Reset errors.
        context.mEmailView.setError(null);
        context.mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = context.mEmailView.getText().toString();
        String password = context.mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            context.mPasswordView.setError(context.getString(R.string.error_field_required));
            focusView = context.mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            context.mEmailView.setError(context.getString(R.string.error_field_required));
            focusView = context.mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            context.progress.ShowProgress(true);

            context.mAuthTask = new UserLoginTask(context, email, password);
            context.mAuthTask.execute((Void) null);
        }

    }


}
