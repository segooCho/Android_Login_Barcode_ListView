package joeun.bixolon.bixolonwarranty.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;

import joeun.bixolon.bixolonwarranty.Barcode.BarcodeCaptureActivity;
import joeun.bixolon.bixolonwarranty.Properties.BaseUrl;
import joeun.bixolon.bixolonwarranty.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent;
    View wizard;
    final Activity activity = this;
    Button buttonBarcode;
    TextView textViewBarcode;
    int navigationItemSelectedId;

    //TODO :: Barcode Event 정의
    private void onBarcodeEvent() {
        buttonBarcode = (Button) findViewById(R.id.buttonBarcode);
        buttonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setCaptureActivity(BarcodeCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        textViewBarcode = (TextView) findViewById(R.id.textViewBarcode);
        textViewBarcode.setText("Barocde");
    }

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
        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
        setTitle(R.string.title_activity_main_barcode);
        dynamicContent.removeAllViews();
        dynamicContent.addView(wizard);
        onBarcodeEvent();
        navigationItemSelectedId = R.id.nav_barcode;
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

            wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
            setTitle(R.string.title_activity_main_barcode);
            dynamicContent.removeAllViews();
            dynamicContent.addView(wizard);
            onBarcodeEvent();
            navigationItemSelectedId = R.id.nav_barcode;
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

    //TODO :: Barcode Event Scan 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        try {
            BaseUrl baseUrl = new BaseUrl();
            AndroidNetworking.post(baseUrl.getBarcodeUrl())
                    .addBodyParameter("barcode",result.getContents())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // do anything with responseUserLoginTask
                            Log.v("Login", "======================================");
                            Log.v("Login", "onResponse");

                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.v("Login", "======================================");
                            Log.v("Login", "onError");
                            Log.v("Login", String.valueOf(error));
                        }
                    });

            //TODO: 응답 시간을 강제적으로 기다린다... 추후 문제 발생 할수 있다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.v("Login", "======================================");
            Log.v("Login", "InterruptedException");
            Log.v("Login", String.valueOf(e));
        }

        textViewBarcode.setText("Barocde : " + result.getContents());


    }

}
