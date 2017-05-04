package joeun.bixolon.bixolonwarranty.Barcode;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONObject;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

/**
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventButtonSave implements View.OnClickListener {
    protected MainActivity context;

    public BarcodeEventButtonSave(MainActivity _context){
        context = _context;
    }

    @Override
    public void onClick(View v) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (context.scanBarcode == null) {
                    context.alertMessage.AlertShow("Error","Barcode Scan No").show();
                    return;
                }

                //TODO :: Login ID 적용이 필요
                String id = "Test";
                Log.v("Save", "ScanBarcode : " + context.scanBarcode);
                Log.v("Save", "WarrantyType : " + context.spinnerWarrantyType.getSelectedItem());
                Log.v("Save", "WarrantyDate : " + String.format("%d%02d%02d", context.datePicker.getYear(),context.datePicker.getMonth()+1, context.datePicker.getDayOfMonth()));

                try {
                    BaseUrl baseUrl = new BaseUrl();
                    AndroidNetworking.put(baseUrl.getBarcodeUrl())
                            .addBodyParameter("id",id)
                            .addBodyParameter("barcode",context.scanBarcode)
                            .addBodyParameter("warrantyType",context.spinnerWarrantyType.getSelectedItem().toString())
                            .addBodyParameter("warrantyDate",String.format("%d%02d%02d", context.datePicker.getYear(),context.datePicker.getMonth()+1, context.datePicker.getDayOfMonth()))
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    // do anything with responseUserLoginTask
                                    Log.v("Save", "======================================");
                                    Log.v("Save", "onResponse");
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
                                    context.alertMessage.AlertShow("Error","Communication Error(ANError)").show();
                                }
                            });
                    //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.v("Barcode", "======================================");
                    Log.v("Barcode", "InterruptedException");
                    Log.v("Barcode", String.valueOf(e));
                    context.alertMessage.AlertShow("Error","Communication Error(InterruptedException)").show();
                }
            }
        });
    }

}
