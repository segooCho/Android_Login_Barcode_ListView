package joeun.bixolon.bixolonwarranty.Common;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class ButtonDatePicker implements View.OnClickListener {
    private MainActivity context;
    private TextView textView;

    public ButtonDatePicker(MainActivity _context, TextView _textView) {
        context = _context;
        textView = _textView;
    }

    /**
     * DatePickerDialog 보기
     * @param v
     */
    @Override
    public void onClick(View v) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //기본 화면의 날짜를 기본으로 설정
                String yyyyMMdd = textView.getText().toString();
                //Log.v("yyyyMMdd", yyyyMMdd);
                int year = Integer.parseInt(yyyyMMdd.substring(0,4));
                int moon = Integer.parseInt(yyyyMMdd.substring(5,7))-1;
                int day = Integer.parseInt(yyyyMMdd.substring(8,10));
                DatePickerDialog dialog = new DatePickerDialog(context, listener, year, moon, day);
                dialog.show();
            }
        });
    }

    /**
     * 적용 처리
     */
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //listener 이벤트는 모든 포인트에서 발생하여 view.isShown() 롤 화면 상태를 Check
            if (view.isShown()) {
                //Log.v("DatePickerDialog", "DatePickerDialog : "  + year + "-" + monthOfYear+1 + "- + dayOfMonth +"일");
                textView.setText(String.format("%d-%02d-%02d", year, monthOfYear+1 , dayOfMonth));

                if (context.barcodeTextViewGoingOutDate.getId() == textView.getId())
                    context.barcodeButtonExpiryDate.callOnClick();
            }
        }
    };
}
