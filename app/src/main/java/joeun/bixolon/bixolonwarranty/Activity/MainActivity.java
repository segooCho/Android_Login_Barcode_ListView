package joeun.bixolon.bixolonwarranty.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import joeun.bixolon.bixolonwarranty.AlertMessage.AlertMessage;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonBarcode;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonSave;
import joeun.bixolon.bixolonwarranty.Common.ButtonDatePicker;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventAdapter;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventButtonFind;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventViewInit;
import joeun.bixolon.bixolonwarranty.Model.BarcodeEventModel;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventOnActivityResult;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventViewInit;
import joeun.bixolon.bixolonwarranty.Model.LoginEventModel;
import joeun.bixolon.bixolonwarranty.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent;
    View wizard;

    //MainActivity 설정
    int navigationItemSelectedId;
    public AlertMessage alertMessage;

    //Barcode UI
    //public String scanBarcode;
    Button barcodeButtonBarcode, barcodeButtonDatePicker, barcodeButtonSave;
    public TextView barcodeTextViewBarcode, barcodeTextViewProductName, barcodeTextViewEmail, barcodeTextViewDatePicker;
    public Spinner barcodeSpinnerWarrantyType;

    //ListView UI
    public TextView lvTextViewDatePicker;
    Button lvButtonDatePicker, lvButtonFind;

    public ListView listView;
    public ListViewEventAdapter listViewEventAdapter = new ListViewEventAdapter();

    //Model
    public LoginEventModel loginEventModel = new LoginEventModel();
    public BarcodeEventModel barcodeEventModel = new BarcodeEventModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login ID
        Bundle bundle = getIntent().getExtras();
        loginEventModel.setId(bundle.getString("LoginID"));
        //Log.v("LoginID", bundle.getString("LoginID"));

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
        //scanBarcode = null;
        navigationItemSelectedId = R.id.nav_barcode;
        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
        setTitle(R.string.title_activity_main_barcode);
        dynamicContent.removeAllViews();
        dynamicContent.addView(wizard);

        //Message
        alertMessage = new AlertMessage(this);

        //TODO :: Barcode Event 기본 설정
        barcodeEventModel.setBarcode(null);
        findViewByIdBarcodeView();
        onBarcodeEventInit();
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

    //TODO :: 메뉴 클릭
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //이전과 같은 navigationItemSelectedId 선택시 처리하지 않음
        if (id == navigationItemSelectedId) return false;

        if (id == R.id.nav_barcode) {
            navigationItemSelectedId = R.id.nav_barcode;
            wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
            setTitle(R.string.title_activity_main_barcode);
            dynamicContent.removeAllViews();
            dynamicContent.addView(wizard);
            findViewByIdBarcodeView();
            onBarcodeEventInit();
        } else if (id == R.id.nav_list) {
            navigationItemSelectedId = R.id.nav_list;
            wizard = getLayoutInflater().inflate(R.layout.content_main_listview, null);
            setTitle(R.string.title_activity_main_list);
            dynamicContent.removeAllViews();
            dynamicContent.addView(wizard);
            findViewByIdListView();
            onListViewEventInit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //TODO :: Barcode Event View ID 찾기
    private void findViewByIdBarcodeView() {
        barcodeButtonBarcode = (Button) findViewById(R.id.barcodeButtonBarcode);
        barcodeTextViewBarcode = (TextView) findViewById(R.id.barcodeTextViewBarcode);
        barcodeTextViewProductName = (TextView) findViewById(R.id.barcodeTextViewProductName);
        barcodeTextViewEmail = (TextView) findViewById(R.id.barcodeTextViewEmail);
        barcodeSpinnerWarrantyType = (Spinner)findViewById(R.id.bSpinnerWarrantyType);
        barcodeButtonDatePicker = (Button) findViewById(R.id.barcodeButtonDatePicker);
        barcodeTextViewDatePicker = (TextView) findViewById(R.id.barcodeTextViewDatePicker);
        barcodeButtonSave = (Button) findViewById(R.id.barcodeButtonSave);
    }

    //TODO :: Barcode Event 정의
    private void onBarcodeEventInit() {
        new BarcodeEventViewInit(this);
        barcodeButtonBarcode.setOnClickListener(new BarcodeEventButtonBarcode(this));
        barcodeButtonDatePicker.setOnClickListener(new ButtonDatePicker(this, barcodeTextViewDatePicker));
        barcodeButtonSave.setOnClickListener(new BarcodeEventButtonSave(this));
    }

    //TODO :: ListView Event View ID 찾기
    private void findViewByIdListView() {
        lvButtonDatePicker = (Button) findViewById(R.id.lvButtonDatePicker);
        lvTextViewDatePicker = (TextView) findViewById(R.id.lvTextViewDatePicker);
        lvButtonFind = (Button) findViewById(R.id.lvButtonFind);
        listView = (ListView) findViewById(R.id.lvListView);
    }

    //TODO :: ListView Event 정의
    private void onListViewEventInit() {
        new ListViewEventViewInit(this);
        lvButtonDatePicker.setOnClickListener(new ButtonDatePicker(this, lvTextViewDatePicker));
        lvButtonFind.setOnClickListener(new ListViewEventButtonFind(this));
    }


    //TODO :: onActivityResult(Barcode Event Scan 처리)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() == null) return;
        //Log.v("requestCode", "requestCode : " + requestCode);
        //Log.v("resultCode", "resultCode : " + resultCode);
        switch (requestCode) {
            // TODO :: IntentIntegrator : REQUEST_CODE = 0x0000c0de;
            case 49374: {
                new BarcodeEventOnActivityResult(this, result.getContents());
            }
        }
    }

}
