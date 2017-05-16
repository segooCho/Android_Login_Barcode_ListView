package joeun.bixolon.bixolonwarranty.Barcode;

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

public class BarcodeEventButtonExpiryDateFindTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String warrantyCode;
    private String goingOutDate;

    private boolean mlogin = false;

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public BarcodeEventButtonExpiryDateFindTask(MainActivity _context, String _warrantyCode, String _goingOutDate) {
        context = _context;
        warrantyCode = _warrantyCode;
        goingOutDate = _goingOutDate;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getExpiryDateUrl())
                    .addBodyParameter("warrantyCode", warrantyCode)
                    .addBodyParameter("goingOutDate", goingOutDate)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            // do anything with responseUserLoginTask
                            Log.v("ExpiryDate", "======================================");
                            Log.v("ExpiryDate", "onResponse");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    context.barcodeTextViewExpiryDate.setText(jsonObject.getString("ExpiryDate"));
                                }
                                mlogin = true;
                            }
                            catch (JSONException e){
                                Log.v("ExpiryDate", "======================================");
                                Log.v("ExpiryDate", "JSONException");
                                Log.v("ExpiryDate", String.valueOf(e));
                                mlogin = false;
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("ExpiryDate", "======================================");
                            Log.v("ExpiryDate", "ANError");
                            Log.v("ExpiryDate", String.valueOf(error));
                            mlogin = false;
                            context.alertMessage.AlertShow("Error","Communication Error(ANError)").show();
                        }
                    });
            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("ExpiryDate", "======================================");
            Log.v("ExpiryDate", "InterruptedException");
            Log.v("ExpiryDate", String.valueOf(e));
            mlogin = false;
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
