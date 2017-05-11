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
        if (context.loginTask != null) {
            return;
        }

        // Reset errors.
        context.loginTextViewId.setError(null);
        context.loginTextViewPassword.setError(null);

        // Store values at the time of the login attempt.
        String id = context.loginTextViewId.getText().toString();
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
        if (TextUtils.isEmpty(id)) {
            context.loginTextViewId.setError(context.getString(R.string.error_field_required));
            focusView = context.loginTextViewId;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            context.progress.ShowProgress(true);

            context.loginTask = new LoginTask(context, id, password);
            context.loginTask.execute((Void) null);
        }

    }


}
