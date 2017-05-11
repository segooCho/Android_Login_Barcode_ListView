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
    private boolean mlogin = false;

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public ListViewEventButtonFindTask(MainActivity _context) {
        context = _context;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        context.listViewEventAdapter = new ListViewEventAdapter();
        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getListViewUrl())
                    .addBodyParameter("id", context.loginEventModel.getId())
                    .addBodyParameter("date", context.lvTextViewDatePicker.getText().toString().replace("-",""))
                    .setPriority(Priority.LOW)
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
                                    Log.v("Find", "Barcode : " + jsonObject.getString("Barcode"));
                                    Log.v("Find", "Id : " + jsonObject.getString("Id"));
                                    Log.v("Find", "WarrantyType : " + jsonObject.getString("WarrantyType"));
                                    new ListViewEventView(  context,
                                            jsonObject.getString("Barcode"),
                                            jsonObject.getString("Id"),
                                            jsonObject.getString("WarrantyType"),
                                            jsonObject.getString("WarrantyDate"));
                                }
                                mlogin = true;
                            }
                            catch (JSONException e){
                                Log.v("Find", "======================================");
                                Log.v("Find", "JSONException");
                                Log.v("Find", String.valueOf(e));
                                mlogin = true;
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Find", "======================================");
                            Log.v("Find", "ANError");
                            Log.v("Find", String.valueOf(error));
                            mlogin = true;
                            context.alertMessage.AlertShow("Error","No such information.").show();
                        }
                    });

            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("Find", "======================================");
            Log.v("Find", "InterruptedException");
            Log.v("Find", String.valueOf(e));
            mlogin = true;
            context.alertMessage.AlertShow("Error","Communication Error(InterruptedException)").show();
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
