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
import joeun.bixolon.bixolonwarranty.Common.Spinners;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;

import static joeun.bixolon.bixolonwarranty.R.id.barcodeSpinnerServiceCenter;

/**
 * Created by admin on 2017. 5. 10..
 */

public class BarcodeEventButtonBarcodeFindTask extends AsyncTask<Void, Void, Boolean> {
    private MainActivity context;
    private String barcode;
    private boolean mlogin = false;

    /**
     * BarcodeEventButtonBarcodeFindTask
     * @param _context
     * @param _barcode
     */
    public BarcodeEventButtonBarcodeFindTask(MainActivity _context, String _barcode) {
        context = _context;
        barcode = _barcode;
    }

    /**
     * doInBackground
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getBarcodeUrl())
                    .addBodyParameter("barcode", barcode)
                    .addBodyParameter("id", context.loginEventModel.getUserId())
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "onResponse");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject.getString("RTN").equals("-1")) {
                                        mlogin = false;
                                        context.alertMessage.AlertShow("Error", jsonObject.getString("MSG")).show();
                                    } else if (jsonObject.getString("RTN").equals("1")) {
                                        /**
                                         * 이미 등록된 Serial No.
                                         */
                                        context.barcodeEventModel.setBarcode(barcode);
                                        context.barcodeTextViewModel.setText(jsonObject.getString("Model"));
                                        context.barcodeTextViewBuyerCode.setText(jsonObject.getString("BuyerCode"));
                                        context.barcodeButtonGoingOutDate.setEnabled(true);
                                        context.barcodeTextViewGoingOutDate.setText(jsonObject.getString("GoingOutDate"));
                                        if (!jsonObject.getString("WarrantyCode").isEmpty()) {
                                            context.barcodeSpinnerWarrantyCode.setEnabled(true);
                                            context.spinners.setSpinnerText(context.barcodeSpinnerWarrantyCode, jsonObject.getString("WarrantyCode"));
                                        }
                                        context.barcodeButtonExpiryDate.setEnabled(true);
                                        context.barcodeTextViewExpiryDate.setText(jsonObject.getString("ExpiryDate"));
                                        if (!jsonObject.getString("BranchOffice").isEmpty()) {
                                            context.barcodeSpinnerBranchOffice.setEnabled(true);
                                            context.spinners.setSpinnerText(context.barcodeSpinnerBranchOffice, jsonObject.getString("BranchOffice"));
                                        }
                                        if (!jsonObject.getString("ServiceCenter").isEmpty()) {
                                            context.barcodeSpinnerServiceCenter.setEnabled(true);
                                            context.spinners.setSpinnerText(context.barcodeSpinnerServiceCenter, jsonObject.getString("ServiceCenter"));
                                        }
                                        context.barcodeEditTextDescription.setText(jsonObject.getString("Description"));
                                        context.barcodeEditTextDescription.setEnabled(true);
                                        context.barcodeButtonSave.setEnabled(true);

                                        mlogin = true;
                                        context.alertMessage.AlertShow("Ok", jsonObject.getString("MSG")).show();
                                    } else {
                                        /**
                                         * 정상 Serial No.
                                         */
                                        context.barcodeEventModel.setBarcode(barcode);
                                        context.barcodeTextViewModel.setText(jsonObject.getString("Model"));
                                        context.barcodeTextViewBuyerCode.setText(jsonObject.getString("BuyerCode"));
                                        context.barcodeButtonGoingOutDate.setEnabled(true);
                                        context.barcodeSpinnerWarrantyCode.setEnabled(true);
                                        context.barcodeButtonExpiryDate.setEnabled(true);
                                        context.barcodeSpinnerBranchOffice.setEnabled(true);
                                        context.barcodeSpinnerServiceCenter.setEnabled(true);
                                        context.barcodeEditTextDescription.setEnabled(true);
                                        context.barcodeEditTextQuantity.setEnabled(true);
                                        context.barcodeButtonSave.setEnabled(true);

                                        mlogin = true;
                                    }
                                }
                            }
                            catch (JSONException e){
                                Log.v("Barcode", "======================================");
                                Log.v("Barcode", "JSONException");
                                Log.v("Barcode", String.valueOf(e));
                                mlogin = false;
                                context.alertMessage.AlertShow("Error","Communication Error(JSONException)").show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "ANError");
                            Log.v("Barcode", String.valueOf(error));
                            mlogin = false;
                            context.alertMessage.AlertShow("Error","Communication Error(ANError).").show();
                        }
                    });
            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.v("Barcode", "======================================");
            Log.v("Barcode", "InterruptedException");
            Log.v("Barcode", String.valueOf(e));
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
