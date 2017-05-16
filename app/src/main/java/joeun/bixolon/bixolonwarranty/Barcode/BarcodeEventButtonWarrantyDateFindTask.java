package joeun.bixolon.bixolonwarranty.Barcode;

import android.os.AsyncTask;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 10..
 */

public class BarcodeEventButtonWarrantyDateFindTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String warrantyCode;
    private String salesDate;

    private boolean mlogin = false;
    //Model

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public BarcodeEventButtonWarrantyDateFindTask(MainActivity _context, String _warrantyCode, String _salesDate) {
        context = _context;
        warrantyCode = _warrantyCode;
        salesDate = _salesDate;
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
            AndroidNetworking.post(baseUrl.getBarcodeUrl())
                    .addBodyParameter("warrantyCode", warrantyCode)
                    .addBodyParameter("salesDate", salesDate)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            // do anything with responseUserLoginTask
                            Log.v("Save", "======================================");
                            Log.v("Save", "onResponse");
                            mlogin = true;
                            context.alertMessage.AlertShow("OK","Save Ok").show();
                            new BarcodeEventViewInit(context);
                            return;
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Save", "======================================");
                            Log.v("Save", "ANError");
                            Log.v("Save", String.valueOf(error));
                            mlogin = false;
                            context.alertMessage.AlertShow("Error","Communication Error(ANError)").show();
                            return;
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