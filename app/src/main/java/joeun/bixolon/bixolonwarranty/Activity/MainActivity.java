package joeun.bixolon.bixolonwarranty.Activity;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonBarcodeFindTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonSaveTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonWarrantyDateFindTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventSpinnersAdapterTask;
import joeun.bixolon.bixolonwarranty.Common.AlertMessage;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonBarcode;
import joeun.bixolon.bixolonwarranty.Common.ButtonDatePicker;
import joeun.bixolon.bixolonwarranty.Common.Progress;
import joeun.bixolon.bixolonwarranty.Common.Spinners;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventAdapter;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventButtonFindTask;
import joeun.bixolon.bixolonwarranty.ListView.ListViewEventViewInit;
import joeun.bixolon.bixolonwarranty.Model.BarcodeEventModel;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventViewInit;
import joeun.bixolon.bixolonwarranty.Model.LoginEventModel;
import joeun.bixolon.bixolonwarranty.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout dynamicContent;
    View wizard;
    Spinners spinners;
    public AlertMessage alertMessage;

    //MainActivity 설정
    int navigationItemSelectedId;

    //Progress
    Progress progress;
    View formView, progressView;

    //Model
    public LoginEventModel loginEventModel = new LoginEventModel();
    public BarcodeEventModel barcodeEventModel = new BarcodeEventModel();

    //Barcode UI
    Button barcodeButtonBarcode, barcodeButtonSalesDatePicker, barcodeButtonWarrantyDate, barcodeButtonSave;
    public TextView barcodeTextViewBarcode, barcodeTextViewUserSpec, barcodeTextViewUserSpecName,
                    barcodeTextViewModelName, barcodeTextViewCustomer, barcodeTextViewSalesDatePicker, barcodeTextViewWarrantyDatePicker;
    public Spinner barcodeSpinnerWarrantyCode, barcodeSpinnerCorporationInfo, barcodeSpinnerServiceCenter;

    BarcodeEventSpinnersAdapterTask barcodeEventSpinnersAdapterTask = null;
    BarcodeEventButtonBarcodeFindTask barcodeEventButtonBarcodeFindTask = null;
    BarcodeEventButtonWarrantyDateFindTask barcodeEventButtonWarrantyDateFindTask = null;
    BarcodeEventButtonSaveTask barcodeEventButtonSaveTask = null;


    //Spinners 처리
    List<String> arrayListWarrantyCode = new ArrayList<>();
    List<String> arrayListCorporationInfo = new ArrayList<>();
    List<String> arrayListServiceCenter = new ArrayList<>();
    List<String> arrayListUserSpec = new ArrayList<>();
    String modeWarrantyCode = "spinnerWarrantyCode";
    String modeCorporationInfo = "spinnerCorporationInfo";
    String modeServiceCenter = "spinnerServiceCenter";
    String modeUserSpec = "spinnerUserSpec";

    //ListView UI
    public TextView lvTextViewDatePicker;
    Button lvButtonDatePicker, lvButtonFind;
    public ListView listView;
    public ListViewEventAdapter listViewEventAdapter;
    ListViewEventButtonFindTask listViewEventButtonFindTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * User 별 기본 세팅 정보를 가져온다
         * LoginID, CorporationInfo(법인 정보), ServiceCenter
         */
        Bundle bundle = getIntent().getExtras();
        loginEventModel.setId(bundle.getString("LoginID"));
        loginEventModel.setCorporationInfo(bundle.getString("CorporationInfo"));
        loginEventModel.setServiceCenter(bundle.getString("ServiceCenter"));

        /**
         * 기본 구성 화면
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        /**
         * Barcode (content_main_barcode)를 기본 화면으로 처리
         */
        navigationItemSelectedId = R.id.nav_barcode;
        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.content_main_barcode, null);
        setTitle(R.string.title_activity_main_barcode);
        dynamicContent.removeAllViews();
        dynamicContent.addView(wizard);

        //Message
        alertMessage = new AlertMessage(MainActivity.this);

        //Progress
        progressView = findViewById(R.id.dynamicProgress);
        formView = findViewById(R.id.dynamicContent);
        progress = new Progress(formView, progressView, getResources().getInteger(android.R.integer.config_shortAnimTime));


        //1.WarrantyCode 가져오기 -> onSpinnersAdapterTask
        progress.ShowProgress(true);
        barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeWarrantyCode, "");
        barcodeEventSpinnersAdapterTask.execute((Void) null);

        /**
         * 이부분에서 Barcode Event 기본 설정을 하였으나..
         * Spinner 바인딩 데이터 시작이 필요하여 onSpinnersAdapterTask를 활용하여 처리
         */
    }

    /**
     * onSpinnersAdapterTask
     * @param arrayList
     * @param mode
     */
    public void onSpinnersAdapterTask(List<String> arrayList, String mode) {
        if (mode.equals(modeWarrantyCode)) {
            arrayListWarrantyCode.addAll(arrayList);
            //2.CorporationInfo 가져오기
            barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeCorporationInfo, "");
            barcodeEventSpinnersAdapterTask.execute((Void) null);
        } else if (mode.equals(modeCorporationInfo)) {
            arrayListCorporationInfo.addAll(arrayList);
            //2.CorporationInfo 가져오기
            barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeServiceCenter, "");
            barcodeEventSpinnersAdapterTask.execute((Void) null);
        } else if (mode.equals(modeServiceCenter)) {
            arrayListServiceCenter.addAll(arrayList);

            //TODO :: Barcode Event 기본 설정
            barcodeEventModel.setBarcode(null);
            findViewByIdBarcodeView();
            onBarcodeEventInit();
            onTaskInit();
        } else if (mode.equals(modeUserSpec)) {
            arrayListUserSpec.addAll(arrayList);

            //TODO :: arrayListUserSpec UI 변경 필요
            spinners = new Spinners(MainActivity.this, barcodeSpinnerServiceCenter, arrayListUserSpec);
            barcodeSpinnerServiceCenter.setSelection(0);
            onTaskInit();
        }
    }

    /**
     * onBarcodeFindTask
     * @param itemId
     */
    public void onBarcodeFindTask(String itemId) {
        barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeUserSpec, itemId);
        barcodeEventSpinnersAdapterTask.execute((Void) null);
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 메뉴 클릭
     * @param menuItem
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

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

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Barcode Event View ID 찾기
     */
    private void findViewByIdBarcodeView() {
        barcodeButtonBarcode = (Button) findViewById(R.id.barcodeButtonBarcode);
        barcodeTextViewBarcode = (TextView) findViewById(R.id.barcodeTextViewBarcode);
        barcodeTextViewUserSpec = (TextView) findViewById(R.id.barcodeTextViewUserSpec);
        barcodeTextViewUserSpecName = (TextView) findViewById(R.id.barcodeTextViewUserSpecName);
        barcodeTextViewModelName = (TextView) findViewById(R.id.barcodeTextViewModelName);
        barcodeTextViewCustomer = (TextView) findViewById(R.id.barcodeTextViewCustomer);
        barcodeButtonSalesDatePicker = (Button) findViewById(R.id.barcodeButtonSalesDatePicker);
        barcodeTextViewSalesDatePicker = (TextView) findViewById(R.id.barcodeTextViewSalesDatePicker);
        barcodeSpinnerWarrantyCode = (Spinner)findViewById(R.id.barcodeSpinnerWarrantyCode);
        barcodeButtonWarrantyDate = (Button) findViewById(R.id.barcodeButtonWarrantyDate);
        barcodeTextViewWarrantyDatePicker = (TextView) findViewById(R.id.barcodeTextViewWarrantyDatePicker);
        barcodeSpinnerCorporationInfo = (Spinner)findViewById(R.id.barcodeSpinnerCorporationInfo);
        barcodeSpinnerServiceCenter = (Spinner)findViewById(R.id.barcodeSpinnerServiceCenter);


        barcodeButtonSave = (Button) findViewById(R.id.barcodeButtonSave);
    }

    /**
     * Barcode Event 정의
     */
    private void onBarcodeEventInit() {
        new BarcodeEventViewInit(MainActivity.this);
        barcodeButtonBarcode.setOnClickListener(new BarcodeEventButtonBarcode(MainActivity.this));
        barcodeButtonSalesDatePicker.setOnClickListener(new ButtonDatePicker(MainActivity.this, barcodeTextViewSalesDatePicker));
        barcodeButtonWarrantyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barcodeEventModel.getBarcode() == null) {
                    alertMessage.AlertShow("Error","Barcode Scan No").show();
                    return;
                }
                progress.ShowProgress(true);
                barcodeEventButtonWarrantyDateFindTask = new BarcodeEventButtonWarrantyDateFindTask(MainActivity.this,
                        barcodeSpinnerWarrantyCode.getSelectedItem().toString(),
                        barcodeTextViewSalesDatePicker.getText().toString().replace("-",""));
                barcodeEventButtonSaveTask.execute((Void) null);
            }
        });
        barcodeButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barcodeEventModel.getBarcode() == null) {
                    alertMessage.AlertShow("Error","Barcode Scan No").show();
                    return;
                }
                progress.ShowProgress(true);
                barcodeEventButtonSaveTask = new BarcodeEventButtonSaveTask(MainActivity.this,
                                                                            barcodeSpinnerWarrantyCode.getSelectedItem().toString(),
                                                                            barcodeTextViewSalesDatePicker.getText().toString().replace("-",""));
                barcodeEventButtonSaveTask.execute((Void) null);
            }
        });

        //WarrantyCode
        spinners = new Spinners(MainActivity.this, barcodeSpinnerWarrantyCode, arrayListWarrantyCode);
        barcodeSpinnerWarrantyCode.setSelection(0);
        //Corporation Info(법인 정보)
        spinners = new Spinners(MainActivity.this, barcodeSpinnerCorporationInfo, arrayListCorporationInfo);
        if (loginEventModel.getCorporationInfo() != null) {
            spinners.setSpinnerText(barcodeSpinnerCorporationInfo, loginEventModel.getCorporationInfo());
        } else {
            barcodeSpinnerCorporationInfo.setSelection(0);
        }
        //Service Center
        spinners = new Spinners(MainActivity.this, barcodeSpinnerServiceCenter, arrayListServiceCenter);
        if (loginEventModel.getServiceCenter() != null) {
            spinners.setSpinnerText(barcodeSpinnerServiceCenter, loginEventModel.getServiceCenter());
        } else {
            barcodeSpinnerServiceCenter.setSelection(0);
        }
    }

    /**
     * ListView Event View ID 찾기
     */
    private void findViewByIdListView() {
        lvButtonDatePicker = (Button) findViewById(R.id.lvButtonDatePicker);
        lvTextViewDatePicker = (TextView) findViewById(R.id.lvTextViewDatePicker);
        lvButtonFind = (Button) findViewById(R.id.lvButtonFind);
        listView = (ListView) findViewById(R.id.lvListView);
    }

    /**
     * ListView Event 정의
     */
    private void onListViewEventInit() {
        new ListViewEventViewInit(MainActivity.this);
        lvButtonDatePicker.setOnClickListener(new ButtonDatePicker(MainActivity.this, lvTextViewDatePicker));
        lvButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.ShowProgress(true);
                listViewEventButtonFindTask = new ListViewEventButtonFindTask(MainActivity.this, lvTextViewDatePicker.getText().toString().replace("-",""));
                listViewEventButtonFindTask.execute((Void) null);
            }
        });
    }

    /**
     * onTask Return
     */
    public void onTaskInit() {
        barcodeEventSpinnersAdapterTask = null;
        barcodeEventButtonBarcodeFindTask = null;
        barcodeEventButtonWarrantyDateFindTask = null;
        barcodeEventButtonSaveTask = null;

        listViewEventButtonFindTask = null;
        progress.ShowProgress(false);
    }

    /**
     * onActivityResult(Barcode Event Scan 처리)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() == null) return;
        //Log.v("requestCode", "requestCode : " + requestCode);
        //Log.v("resultCode", "resultCode : " + resultCode);
        switch (requestCode) {
            // TODO :: IntentIntegrator : REQUEST_CODE = 0x0000c0de;
            case 49374: {
                progress.ShowProgress(true);
                barcodeTextViewBarcode.setText("Barocde : " + result.getContents());
                barcodeEventModel.setBarcode(null);
                barcodeEventButtonBarcodeFindTask = new BarcodeEventButtonBarcodeFindTask(MainActivity.this, result.getContents());
                barcodeEventButtonBarcodeFindTask.execute((Void) null);
            }
        }
    }

}
