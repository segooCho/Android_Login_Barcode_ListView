package joeun.bixolon.bixolonwarranty.ListView;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventViewInit;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;
import joeun.bixolon.bixolonwarranty.R;

/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEventButtonFind implements View.OnClickListener {
    protected MainActivity context;

    public ListViewEventButtonFind(MainActivity _context){
        context = _context;
    }

    @Override
    public void onClick(View v) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseUrl baseUrl = new BaseUrl();
                    AndroidNetworking.post(baseUrl.getListViewUrl())
                            .addBodyParameter("id", "LONDON")
                            .addBodyParameter("date", "20170504")
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
                                            //Log.v("Find", "Barcode : " + jsonObject.getString("Barcode"));
                                            //Log.v("Find", "Id : " + jsonObject.getString("Id"));
                                            //Log.v("Find", "WarrantyType : " + jsonObject.getString("WarrantyType"));
                                            new ListViewEventView(context, jsonObject.getString("Barcode"), jsonObject.getString("Id"), jsonObject.getString("WarrantyType"));
                                        }
                                    }
                                    catch (JSONException e){
                                        Log.v("Find", "======================================");
                                        Log.v("Find", "JSONException");
                                        Log.v("Find", String.valueOf(e));
                                        context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                                    }
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Log.v("Find", "======================================");
                                    Log.v("Find", "ANError");
                                    Log.v("Find", String.valueOf(error));
                                    context.alertMessage.AlertShow("Error","No such information.").show();
                                }
                            });

                    //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.v("Find", "======================================");
                    Log.v("Find", "InterruptedException");
                    Log.v("Find", String.valueOf(e));
                    context.alertMessage.AlertShow("Error","Communication Error(InterruptedException)").show();
                }
            }
        });
    }

}
