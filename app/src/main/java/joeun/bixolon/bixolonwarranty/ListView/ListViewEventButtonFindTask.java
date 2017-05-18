package joeun.bixolon.bixolonwarranty.ListView;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 10..
 */

public class ListViewEventButtonFindTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String regDate;
    private boolean mlogin = false;

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public ListViewEventButtonFindTask(MainActivity _context, String _regDate) {
        context = _context;
        regDate = _regDate;
    }

    /**
     * doInBackground
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        context.listViewEventAdapter = new ListViewEventAdapter();
        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getWorkListUrl())
                    .addBodyParameter("regDate", regDate)
                    .addBodyParameter("userId", context.loginEventModel.getUserId())
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            // do anything with responseUserLoginTask
                            Log.v("Find", "======================================");
                            Log.v("Find", "onResponse");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Log.v("Find", "SerialNo : " + jsonObject.getString("SerialNo"));
                                    new ListViewEventView(context,
                                            jsonObject.getString("SerialNo"),
                                            jsonObject.getString("Model"),
                                            jsonObject.getString("UserSpec"),
                                            jsonObject.getString("GoingOutDate"));
                                }
                                mlogin = true;
                            }
                            catch (JSONException e){
                                Log.v("Find", "======================================");
                                Log.v("Find", "JSONException");
                                Log.v("Find", String.valueOf(e));
                                mlogin = false;
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            Log.v("Find", "======================================");
                            Log.v("Find", "ANError");
                            Log.v("Find", String.valueOf(error));
                            mlogin = false;
                            context.listViewEventAdapter.notifyDataSetChanged();
                            //TODO :: List 없을때 초기화 작업
                            context.alertMessage.AlertShow("Error","No such information.").show();
                        }
                    });

            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("Find", "======================================");
            Log.v("Find", "InterruptedException");
            Log.v("Find", String.valueOf(e));
            mlogin = false;
            context.alertMessage.AlertShow("Error","Communication Error(InterruptedException)").show();
        }

        if (!mlogin)
            return false;
        else
            return true;

    }

    /**
     * onPostExecute
     * @param success
     */
    @Override
    protected void onPostExecute(final Boolean success) {
        if (!success) {
            context.listView.setAdapter(context.listViewEventAdapter);
        }
        context.onTaskInit();
    }

    /**
     *
     */
    @Override
    protected void onCancelled() {
        context.onTaskInit();
    }
}
