package joeun.bixolon.bixolonwarranty.Barcode;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.Common.Spinners;

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

        context.barcodeTextViewSerialNo.setText("Serial No");
        context.barcodeTextViewModel.setText("Model");

        context.arrayListUserSpec.clear();
        context.spinners = new Spinners(context, context.barcodeSpinnerUserSpec, context.arrayListUserSpec);

        //TODO :: UserSpec spinner 초기화
        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.barcodeTextViewGoingOutDate.setText( String.format("%d-%02d-%02d",
                                                        calendar.get(Calendar.YEAR),
                                                        calendar.get(Calendar.MONTH)+1,
                                                        calendar.get(Calendar.DAY_OF_MONTH)));

        context.barcodeTextViewExpiryDate.setText("1901-01-01");
        context.barcodeEditTextDescription.setText("");
    }
}
