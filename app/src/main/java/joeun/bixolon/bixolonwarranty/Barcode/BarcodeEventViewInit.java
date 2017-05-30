package joeun.bixolon.bixolonwarranty.Barcode;

import android.widget.ScrollView;

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
        context.barcodeEditTextSerialNo.setText("");
        context.barcodeTextViewSerialNo.setText("");
        context.barcodeTextViewModel.setText("");
        context.barcodeTextViewBuyerCode.setText("");
        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.barcodeTextViewGoingOutDate.setText( String.format("%d-%02d-%02d",
                                                        calendar.get(Calendar.YEAR),
                                                        calendar.get(Calendar.MONTH)+1,
                                                        calendar.get(Calendar.DAY_OF_MONTH)));
        context.barcodeButtonGoingOutDate.setEnabled(false);
        context.barcodeSpinnerWarrantyCode.setEnabled(false);
        context.barcodeTextViewExpiryDate.setText("");
        context.barcodeButtonExpiryDate.setEnabled(false);
        context.barcodeSpinnerBranchOffice.setEnabled(false);
        context.barcodeSpinnerServiceCenter.setEnabled(false);
        context.barcodeEditTextDescription.setText("");
        context.barcodeEditTextDescription.setEnabled(false);
        context.barcodeEditTextQuantity.setText("1");
        context.barcodeEditTextQuantity.setEnabled(false);
        context.barcodeButtonSave.setEnabled(false);
        context.barcodeScrollView.fullScroll(ScrollView.FOCUS_UP);
    }
}
