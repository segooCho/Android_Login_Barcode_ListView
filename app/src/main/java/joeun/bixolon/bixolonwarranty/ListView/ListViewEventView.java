package joeun.bixolon.bixolonwarranty.ListView;

import android.app.ListActivity;
import android.support.v4.content.ContextCompat;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.R;


/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEventView extends ListActivity {
    private MainActivity context;

    /**
     * ListView Adapter에 데이터 추가
     * @param _context
     * @param _serialNo
     * @param _model
     * @param _buyerCode
     * @param _warrantyCode
     * @param _goingOutDate
     */
    public ListViewEventView(MainActivity _context, String _serialNo, String _model, String _buyerCode, String _warrantyCode,
                             String _goingOutDate) {
        context = _context;

        context.listView.setAdapter(context.listViewEventAdapter);
        context.listViewEventAdapter.addItem(
                ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp),
                _serialNo,
                _model,
                _buyerCode,
                _warrantyCode,
                _goingOutDate);
    }
}
