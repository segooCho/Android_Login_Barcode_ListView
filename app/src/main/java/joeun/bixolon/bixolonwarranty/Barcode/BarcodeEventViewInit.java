package joeun.bixolon.bixolonwarranty.Barcode;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class BarcodeEventViewInit {
    private MainActivity context;

    /**
     * Barcode View 초기화
     * @param _context
     */
    public BarcodeEventViewInit(MainActivity _context) {
        context = _context;

        context.barcodeEventModel.setBarcode(null);

        context.barcodeTextViewBarcode.setText("Barocde");
        context.barcodeTextViewProductName.setText("ProductName");
        context.barcodeTextViewEmail.setText("E-Mail");
        context.barcodeSpinnerWarrantyType.setSelection(0);
        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.barcodeTextViewDatePicker.setText( String.format("%d-%02d-%02d",
                                            calendar.get(Calendar.YEAR),
                                            calendar.get(Calendar.MONTH)+1,
                                            calendar.get(Calendar.DAY_OF_MONTH)));
    }
}
