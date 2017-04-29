package joeun.bixolon.bixolonwarranty.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import joeun.bixolon.bixolonwarranty.Barcode.BarcodeCaptureActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;
import joeun.bixolon.bixolonwarranty.R;

import static joeun.bixolon.bixolonwarranty.R.id.datepicker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent;
    View wizard;
    int navigationItemSelectedId;
    String ScanBarcode;

    //Barcode UI
    final Activity activity = this;
    Button buttonBarcode, buttonSave;
    TextView textViewBarcode,textViewProductName, textViewEmail;
    Spinner spinnerWarrantyType;
    DatePicker datePicker;

    //ListView UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO :: 기본 구성 화면
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TODO :: content_main_barcode를 기본 화면으로 처리
        ScanBarcode = null;
        navigationItemSelectedId = R.id.nav_barcode;
        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
        setTitle(R.string.title_activity_main_barcode);
        dynamicContent.removeAllViews();
        dynamicContent.addView(wizard);
        onBarcodeEvent();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //TODO :: 우측상단 Setting
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    //TODO :: 메뉴 클릭
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //이전과 같은 navigationItemSelectedId 선택시 처리하지 않음
        if (id == navigationItemSelectedId) return false;

        if (id == R.id.nav_barcode) {
            //Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
            //startActivity(intent);
            //finish();

            navigationItemSelectedId = R.id.nav_barcode;
            wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
            setTitle(R.string.title_activity_main_barcode);
            dynamicContent.removeAllViews();
            dynamicContent.addView(wizard);
            onBarcodeEvent();
        } else if (id == R.id.nav_list) {
            wizard = getLayoutInflater().inflate(R.layout.content_main_listview, null);
            setTitle(R.string.title_activity_main_list);
            dynamicContent.removeAllViews();
            dynamicContent.addView(wizard);
            navigationItemSelectedId = R.id.nav_list;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    //TODO :: Barcode Event 정의
    private void onBarcodeEvent() {
        buttonBarcode = (Button) findViewById(R.id.buttonBarcode);
        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanBarcode = null;
                textViewBarcode.setText("Barocde");
                textViewProductName.setText("ProductName");
                textViewEmail.setText("E-Mail");
                spinnerWarrantyType.setSelection(0);
                Calendar calendar = Calendar.getInstance(); // date 초기화
                datePicker.init(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                        , null);

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setCaptureActivity(BarcodeCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        textViewBarcode = (TextView) findViewById(R.id.textViewBarcode);
        textViewBarcode.setText("Barocde");
        textViewProductName = (TextView) findViewById(R.id.textViewProductName);
        textViewProductName.setText("ProductName");
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewEmail.setText("E-Mail");
        spinnerWarrantyType = (Spinner)findViewById(R.id.spinnerWarrantyType);
        datePicker = (DatePicker) findViewById(R.id.datepicker);

        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText("position : " + position +
                        parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        /*
        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        */

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScanBarcode == null) {

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Barcode Scan No");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }

                String id = "Test";
                Log.v("Save", "ScanBarcode : " + ScanBarcode);
                Log.v("Save", "WarrantyType : " + spinnerWarrantyType.getSelectedItem());
                Log.v("Save", "WarrantyDate : " + String.format("%d%02d%02d", datePicker.getYear(),datePicker.getMonth()+1, datePicker.getDayOfMonth()));

                try {
                    BaseUrl baseUrl = new BaseUrl();
                    AndroidNetworking.put(baseUrl.getBarcodeUrl())
                            .addBodyParameter("id",id)
                            .addBodyParameter("barcode",ScanBarcode)
                            .addBodyParameter("warrantyType",spinnerWarrantyType.getSelectedItem().toString())
                            .addBodyParameter("warrantyDate",String.format("%d%02d%02d", datePicker.getYear(),datePicker.getMonth()+1, datePicker.getDayOfMonth()))
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    // do anything with responseUserLoginTask
                                    Log.v("Save", "======================================");
                                    Log.v("Save", "onResponse");

                                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setTitle("OK");
                                    alertDialog.setMessage("Save Ok");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                    return;

                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Log.v("Save", "======================================");
                                    Log.v("Save", "onError");
                                    Log.v("Save", String.valueOf(error));
                                }
                            });

                    //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.v("Barcode", "======================================");
                    Log.v("Barcode", "InterruptedException");
                    Log.v("Barcode", String.valueOf(e));
                }
            }
        });

    }

    //TODO :: Barcode Event Scan 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() == null) return;

        textViewBarcode.setText("Barocde : " + result.getContents());
        ScanBarcode = null;
        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getBarcodeUrl())
                    .addBodyParameter("barcode",result.getContents())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            // do anything with responseUserLoginTask
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "onResponse");
                            try{
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    //Log.v("Barcode", "LastName : " + jsonObject.getString("LastName"));
                                    //Log.v("Barcode", "Email : " + jsonObject.getString("Email"));
                                    ScanBarcode = jsonObject.getString("FirstName");
                                    textViewProductName.setText("ProductName : " + jsonObject.getString("LastName"));
                                    textViewEmail.setText("E-Mail : " + jsonObject.getString("Email"));
                                }
                            }
                            catch (JSONException e){
                                Log.v("Barcode", "======================================");
                                Log.v("Barcode", "JSONException");
                                Log.v("Barcode", String.valueOf(e));
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Barcode", "======================================");
                            Log.v("Barcode", "onError");
                            Log.v("Barcode", String.valueOf(error));
                        }
                    });

            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("Barcode", "======================================");
            Log.v("Barcode", "InterruptedException");
            Log.v("Barcode", String.valueOf(e));
        }
    }

    /*
    //TODO :: DatePicker Event 처리
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */


}
