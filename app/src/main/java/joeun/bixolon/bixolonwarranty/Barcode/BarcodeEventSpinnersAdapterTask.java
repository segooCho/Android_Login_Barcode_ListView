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

import java.util.ArrayList;
import java.util.List;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 10..
 */

public class BarcodeEventSpinnersAdapterTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String url;
    List<String> arrayList = new ArrayList<>();
    private boolean mlogin = false;

    /***
     * ListViewEventButtonFindTask
     * @param _context
     */
    public BarcodeEventSpinnersAdapterTask(MainActivity _context, String _url) {
        context = _context;
        url = _url;
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
            AndroidNetworking.get(baseUrl.getBaseUrl() + url)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            // do anything with responseUserLoginTask
                            Log.v("SpinnersAdapter", "======================================");
                            Log.v("SpinnersAdapter", "jsonArray");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //Log.v("Barcode", "LastName : " + jsonObject.getString("Spinner"));
                                    arrayList.add(jsonObject.getString("Spinner"));
                                }
                                mlogin = true;
                            }
                            catch (JSONException e){
                                Log.v("SpinnersAdapter", "======================================");
                                Log.v("SpinnersAdapter", "JSONException");
                                Log.v("SpinnersAdapter", String.valueOf(e));
                                mlogin = false;
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("SpinnersAdapter", "======================================");
                            Log.v("SpinnersAdapter", "ANError");
                            Log.v("SpinnersAdapter", String.valueOf(error));
                            mlogin = false;
                            context.alertMessage.AlertShow("Error","Fail to find common information.(Spinner)").show();
                        }
                    });
            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.v("SpinnersAdapter", "======================================");
            Log.v("SpinnersAdapter", "InterruptedException");
            Log.v("SpinnersAdapter", String.valueOf(e));
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
        if (success)
            context.onSpinnersAdapterTask(arrayList, url);
        else
            context.onTaskInit();
    }

    /**
     * onCancelled
     */
    @Override
    protected void onCancelled() {
        context.onTaskInit();
    }
}
