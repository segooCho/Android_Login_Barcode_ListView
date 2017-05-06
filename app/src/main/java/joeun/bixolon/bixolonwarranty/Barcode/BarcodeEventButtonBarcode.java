package joeun.bixolon.bixolonwarranty.Barcode;

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventButtonBarcode implements View.OnClickListener {
    protected MainActivity context;

    public BarcodeEventButtonBarcode(MainActivity _context) {
        context = _context;
    }

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
