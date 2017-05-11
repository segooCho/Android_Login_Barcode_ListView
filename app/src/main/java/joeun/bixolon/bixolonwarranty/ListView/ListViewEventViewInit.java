package joeun.bixolon.bixolonwarranty.ListView;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEventViewInit {
    private MainActivity context;

    /**
     * 조회 화면 초기화
     * @param _context
     */
    public ListViewEventViewInit(MainActivity _context) {
        context = _context;

        Calendar calendar = Calendar.getInstance(); // date 초기화
        context.lvTextViewDatePicker.setText( String.format("%d-%02d-%02d",
                                            calendar.get(Calendar.YEAR),
                                            calendar.get(Calendar.MONTH)+1,
                                            calendar.get(Calendar.DAY_OF_MONTH)));
    }
}
