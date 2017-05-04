package joeun.bixolon.bixolonwarranty.Barcode;

import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventViewInit {
    protected MainActivity context;

    public BarcodeEventViewInit(MainActivity _context) {
        context = _context;
        context.scanBarcode = null;
        context.textViewBarcode.setText("Barocde");
        context.textViewProductName.setText("ProductName");
        context.textViewEmail.setText("E-Mail");
        context.spinnerWarrantyType.setSelection(0);
        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
