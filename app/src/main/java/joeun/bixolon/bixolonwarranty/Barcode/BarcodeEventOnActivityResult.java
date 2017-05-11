package joeun.bixolon.bixolonwarranty.Barcode;

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
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventOnActivityResult {
    private MainActivity context;

    public BarcodeEventOnActivityResult(MainActivity _context, String barcode) {
        context = _context;
        context.barcodeTextViewBarcode.setText("Barocde : " + barcode);
        context.barcodeEventModel.setBarcode(null);

        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getBarcodeUrl())
                    .addBodyParameter("barcode", barcode)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            // do anything with responseUserLoginTask
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "onResponse");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //Log.v("Barcode", "LastName : " + jsonObject.getString("LastName"));
                                    //Log.v("Barcode", "Email : " + jsonObject.getString("Email"));
                                    context.barcodeEventModel.setBarcode(jsonObject.getString("FirstName"));
                                    context.barcodeTextViewProductName.setText("ProductName : " + jsonObject.getString("LastName"));
                                    context.barcodeTextViewEmail.setText("E-Mail : " + jsonObject.getString("Email"));
                                }
                            }
                            catch (JSONException e){
                                Log.v("Barcode", "======================================");
                                Log.v("Barcode", "JSONException");
                                Log.v("Barcode", String.valueOf(e));
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "ANError");
                            Log.v("Barcode", String.valueOf(error));
                            context.alertMessage.AlertShow("Error","It is not a product barcode.").show();
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
}
