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
        context.barcodeTextViewUserSpec.setText("User Spec");
        context.barcodeTextViewUserSpecName.setText("User Spec Name");
        context.barcodeTextViewModelName.setText("Model Name");
        context.barcodeTextViewCustomer.setText("Customer");

        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.barcodeTextViewSalesDatePicker.setText( String.format("%d-%02d-%02d",
                                                        calendar.get(Calendar.YEAR),
                                                        calendar.get(Calendar.MONTH)+1,
                                                        calendar.get(Calendar.DAY_OF_MONTH)));
    }
}
