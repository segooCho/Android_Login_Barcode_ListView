package joeun.bixolon.bixolonwarranty.ListView;

import android.app.ListActivity;
import android.support.v4.content.ContextCompat;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.R;


/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEventView extends ListActivity {
    protected MainActivity context;

    public ListViewEventView(MainActivity _context, String barcode, String id, String warrantyType, String warrantyDate) {
        context = _context;

        context.listView.setAdapter(context.listViewEventAdapter);
        context.listViewEventAdapter.addItem(   ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp),
                                                barcode,
                                                id,
                                                warrantyType,
                                                warrantyDate);
    }
}
