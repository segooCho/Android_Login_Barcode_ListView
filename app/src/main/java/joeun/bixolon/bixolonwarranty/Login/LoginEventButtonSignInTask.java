package joeun.bixolon.bixolonwarranty.Login;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import joeun.bixolon.bixolonwarranty.Activity.LoginActivity;
import joeun.bixolon.bixolonwarranty.Model.LoginEventModel;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 10..
 */

public class LoginEventButtonSignInTask extends AsyncTask<Void, Void, Boolean> {
    private LoginActivity context;
    private final String id;
    private final String password;
    private boolean mlogin = false;

        /***
     * LoginEventButtonSignInTask
     * @param _context
     * @param _id
     * @param _password
     */
    public LoginEventButtonSignInTask(LoginActivity _context, String _id, String _password) {
        context = _context;
        id = _id;
        //TODO :: Hash 처리
        //Hash hash = new Hash();
        //password = hash.SHA256(_password);
        password = _password;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Simulate network access.
            // TODO: 로그인 처리 : State Code = 200 로 처리 하고 싶지만 못하고 있음
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getLoginUrl())
                    .addBodyParameter("id",id)
                    .addBodyParameter("password",password)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            Log.v("Login", "======================================");
                            Log.v("Login", "jsonArray");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    context.loginEventModel.setId(id);
                                    context.loginEventModel.setCorporationInfo(jsonObject.getString("CorporationInfo"));
                                    context.loginEventModel.setServiceCenter(jsonObject.getString("ServiceCenter"));
                                }
                                mlogin = true;
                            }
                            catch (JSONException e){
                                Log.v("Login", "======================================");
                                Log.v("Login", "JSONException");
                                Log.v("Login", String.valueOf(e));
                                mlogin = false;
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Login", "======================================");
                            Log.v("Login", "NG");
                            Log.v("Login", String.valueOf(error));
                            mlogin = false;
                        }
                    });

            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        if (!mlogin)
            return false;
        else
            return true;

    }

    /**
     *
     * @param success
     */
    @Override
    protected void onPostExecute(final Boolean success) {
        context.onLoginTaskPostExecuteResult(success);
    }

    /**
     *
     */
    @Override
    protected void onCancelled() {
        context.onLoginTaskCancelledResult();
    }
}
