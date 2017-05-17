package joeun.bixolon.bixolonwarranty.Login;

import android.text.TextUtils;
import android.view.View;

import joeun.bixolon.bixolonwarranty.Activity.LoginActivity;
import joeun.bixolon.bixolonwarranty.R;

/**
 * Created by admin on 2017. 5. 10..
 */

public class LoginEventButtonSignIn {
    private LoginActivity context;

    /**
     *
     * @param _context
     */
    public LoginEventButtonSignIn(LoginActivity _context) {
        context = _context;
    }

    /**
     *
     */
    public void Login() {
        if (context.loginEventButtonSignInTask != null) {
            return;
        }

        // Reset errors.
        context.loginTextViewUserId.setError(null);
        context.loginTextViewPassword.setError(null);

        // Store values at the time of the login attempt.
        String userId = context.loginTextViewUserId.getText().toString();
        String password = context.loginTextViewPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            context.loginTextViewPassword.setError(context.getString(R.string.error_field_required));
            focusView = context.loginTextViewPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userId)) {
            context.loginTextViewUserId.setError(context.getString(R.string.error_field_required));
            focusView = context.loginTextViewUserId;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            context.progress.ShowProgress(true);

            context.loginEventButtonSignInTask = new LoginEventButtonSignInTask(context, userId, password);
            context.loginEventButtonSignInTask.execute((Void) null);
        }

    }


}
