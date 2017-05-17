package joeun.bixolon.bixolonwarranty.Login;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import joeun.bixolon.bixolonwarranty.Activity.LoginActivity;
import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class LoginAlertMessage extends AlertDialog.Builder {
    private LoginActivity context;

    public LoginAlertMessage(LoginActivity _context) {
        super(_context);
        context = _context;
    }

    /**
     * 알림 창
     * @param setTitle
     * @param setMessage
     * @return
     */
    public AlertDialog AlertShow(String setTitle, String setMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(setTitle);
        alertDialog.setMessage(setMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return alertDialog;
    }
}
