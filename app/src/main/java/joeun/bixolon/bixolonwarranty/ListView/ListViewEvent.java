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
    ListViewEventAdapter listViewEventAdapter;

    public ListViewEvent(MainActivity _context) {
        context = _context;

        listViewEventAdapter = new ListViewEventAdapter();
        context.listView.setAdapter(listViewEventAdapter);
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Box", "Account Box Black 36dp","aaaaaaaaa");
        listViewEventAdapter.addItem(ContextCompat.getDrawable(context, R.mipmap.ic_assignment_black_36dp), "Circle", "Account Circle Black 36dp","aaaaaaaaa");
    }
}
