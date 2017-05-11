package joeun.bixolon.bixolonwarranty.Barcode;

import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventButtonBarcode implements View.OnClickListener {
    private MainActivity context;

    public BarcodeEventButtonBarcode(MainActivity _context) {
        context = _context;
    }

    /**
     * Barcode Scan 버튼 이벤트
     * @param v
     */
    @Override
    public void onClick(View v) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new BarcodeEventViewInit(context);
                IntentIntegrator integrator = new IntentIntegrator(context);
                integrator.setCaptureActivity(BarcodeCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
    }
}
