package joeun.bixolon.bixolonwarranty.Common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 11..
 */

public class Spinners implements AdapterView.OnItemSelectedListener {
    private MainActivity context;
    private Spinner spinner;
    private List<String> arrayList;

    /**
     * Spinners
     * @param _context
     * @param _spinner
     * @param _arrayList
     */
    public Spinners(MainActivity _context, Spinner _spinner, List<String> _arrayList) {
        context = _context;
        spinner = _spinner;
        arrayList = _arrayList;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);

        //공통으로 사용하기 위해 setOnItemSelectedListener 사용하지 않음
        //spinner.setOnItemSelectedListener(this);
    }

    /**
     * onItemSelected : 필수
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //Log.v("onItemSelected", "onItemSelected : "  + arrayList.get(arg2));
    }

    /**
     * onNothingSelected : 필수
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * setSpinnerText
     * @param _spinner
     * @param text
     */
    public void setSpinnerText(Spinner _spinner, String text) {
        for(int i= 0; i < _spinner.getAdapter().getCount(); i++) {
            if (_spinner.getAdapter().getItem(i).toString().contains(text))
                _spinner.setSelection(i);
        }

    }

}
