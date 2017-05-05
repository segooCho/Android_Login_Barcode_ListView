package joeun.bixolon.bixolonwarranty.ListView;

import android.app.ListActivity;
import android.support.v4.content.ContextCompat;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;
import joeun.bixolon.bixolonwarranty.R;


/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEvent extends ListActivity {
    protected MainActivity context;
    ListViewAdapter adapter;

    public ListViewEvent(MainActivity _context) {
        context = _context;

        adapter = new ListViewAdapter() ;
        context.listView.setAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        adapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
    }
}
