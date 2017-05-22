package joeun.bixolon.bixolonwarranty.Barcode;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 10..
 */

public class BarcodeEventButtonSaveTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String userSpec;
    private String goingOutDate;
    private String warrantyCode;
    private String buyer;
    private String serviceCenter;
    private String description;

    private boolean mlogin = false;

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public BarcodeEventButtonSaveTask(MainActivity _context
                                    ,String _userSpec
                                    ,String _goingOutDate
                                    ,String _warrantyCode
                                    ,String _buyer
                                    ,String _serviceCenter
                                    ,String _description) {

        context = _context;
        userSpec = _userSpec;
        goingOutDate = _goingOutDate;
        warrantyCode = _warrantyCode;
        buyer = _buyer;
        serviceCenter = _serviceCenter;
        description = _description;
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
            AndroidNetworking.put(baseUrl.getBarcodSaveUrl())
                    .addBodyParameter("serialNo", context.barcodeEventModel.getBarcode())
                    .addBodyParameter("userSpec", userSpec)
                    .addBodyParameter("goingOutDate", goingOutDate)
                    .addBodyParameter("warrantyCode", warrantyCode)
                    .addBodyParameter("buyer", buyer)
                    .addBodyParameter("serviceCenter", serviceCenter)
                    .addBodyParameter("description", description)
                    .addBodyParameter("fileName1", "")
                    .addBodyParameter("filePath1", "")
                    .addBodyParameter("fileName2", "")
                    .addBodyParameter("filePath2", "")
                    .addBodyParameter("createdBy", context.loginEventModel.getUserId())
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            Log.v("Save", "======================================");
                            Log.v("Save", "onResponse");
                            try {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject.getString("RTN").equals("-1")) {
                                        mlogin = false;
                                        context.alertMessage.AlertShow("Error", jsonObject.getString("MSG")).show();
                                    } else {
                                        mlogin = true;
                                        context.alertMessage.AlertShow("Ok", "Saved.").show();
                                        new BarcodeEventViewInit(context);
                                    }
                                }
                            } catch (JSONException e) {
                                Log.v("Save", "======================================");
                                Log.v("Save", "JSONException");
                                Log.v("Save", String.valueOf(e));
                                mlogin = false;
                                context.alertMessage.AlertShow("Error", "Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Save", "======================================");
                            Log.v("Save", "ANError");
                            Log.v("Save", String.valueOf(error));
                            mlogin = false;
                            context.alertMessage.AlertShow("Error","Communication Error(ANError)").show();
                        }
                    });
            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("Save", "======================================");
            Log.v("Save", "InterruptedException");
            Log.v("Save", String.valueOf(e));
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
