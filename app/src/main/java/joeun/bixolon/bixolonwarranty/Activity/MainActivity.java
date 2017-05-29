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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonBarcodeFindTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonExpiryDateFindTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonSaveTask;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventSpinnersAdapterTask;
import joeun.bixolon.bixolonwarranty.Common.AlertMessage;
import joeun.bixolon.bixolonwarranty.Barcode.BarcodeEventButtonBarcode;
import joeun.bixolon.bixolonwarranty.Common.ButtonDatePicker;
import joeun.bixolon.bixolonwarranty.Common.PatternCode;
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

    //Common
    LinearLayout dynamicContent;
    View wizard;
    public Spinners spinners;
    public AlertMessage alertMessage;
    public TextView headerTextViewUserName;

    //MainActivity layout ID
    int navigationItemSelectedId;

    //Progress
    Progress progress;
    View formView, progressView;

    //Model
    public LoginEventModel loginEventModel = new LoginEventModel();
    public BarcodeEventModel barcodeEventModel = new BarcodeEventModel();

    //Barcode UI
    Button barcodeButtonBarcode;
    public ScrollView barcodeScrollView;
    public Button barcodeButtonGoingOutDate, barcodeButtonExpiryDate, barcodeButtonSave;
    public TextView barcodeTextViewSerialNo, barcodeTextViewModel, barcodeTextViewUserSpec,
                    barcodeTextViewGoingOutDate, barcodeTextViewExpiryDate, barcodeEditTextQuantity;
    public Spinner barcodeSpinnerWarrantyCode, barcodeSpinnerBuyer, barcodeSpinnerServiceCenter;
    public EditText barcodeEditTextDescription;

    //Barcode Task
    BarcodeEventSpinnersAdapterTask barcodeEventSpinnersAdapterTask = null;
    BarcodeEventButtonBarcodeFindTask barcodeEventButtonBarcodeFindTask = null;
    BarcodeEventButtonExpiryDateFindTask barcodeEventButtonExpiryDateFindTask = null;
    BarcodeEventButtonSaveTask barcodeEventButtonSaveTask = null;

    //Barcode Pattern
    PatternCode patternCode = new PatternCode();

    //Spinners 처리
    List<String> arrayListWarrantyCode = new ArrayList<>();
    List<String> arrayListBuyer = new ArrayList<>();
    List<String> arrayListServiceCenter = new ArrayList<>();
    String modeWarrantyCode = "spinnerWarrantyCode";
    String modeBuyer = "spinnerBuyer";
    String modeServiceCenter = "spinnerServiceCenter";

    //ListView UI
    public TextView lvTextViewDate;
    Button lvButtonDate, lvButtonFind;
    public ListView listView;
    public Spinner lvSpinnerDate;

    //ListView Adapter
    public ListViewEventAdapter listViewEventAdapter;

    //ListView Task
    ListViewEventButtonFindTask listViewEventButtonFindTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * User 별 기본 세팅 정보를 가져온다
         * LoginID, Buyer(법인 정보), ServiceCenter
         */
        Bundle bundle = getIntent().getExtras();
        loginEventModel.setUserId(bundle.getString("userId"));
        loginEventModel.setUserName(bundle.getString("userName"));
        loginEventModel.setBuyer(bundle.getString("buyer"));
        loginEventModel.setServiceCenter(bundle.getString("serviceCenter"));

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
            //2.Buyer 가져오기
            barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeBuyer, "");
            barcodeEventSpinnersAdapterTask.execute((Void) null);
        } else if (mode.equals(modeBuyer)) {
            arrayListBuyer.addAll(arrayList);
            //3.ServiceCenter 가져오기
            barcodeEventSpinnersAdapterTask = new BarcodeEventSpinnersAdapterTask(MainActivity.this, modeServiceCenter, "");
            barcodeEventSpinnersAdapterTask.execute((Void) null);
        } else if (mode.equals(modeServiceCenter)) {
            arrayListServiceCenter.addAll(arrayList);
            /**
             *  onCreate                에서 1.WarrantyCode
             *  onSpinnersAdapterTask   에서 2.Buyer 가져오기
             *  onSpinnersAdapterTask   에서 3.ServiceCenter 가져오기
             *  onSpinnersAdapterTask   에서 모든 spinner 데이터 생성 완료 후 Barcode Event 기본 설정
             */
            headerViewInit();
            barcodeEventModel.setBarcode(null);
            findViewByIdBarcodeView();
            onBarcodeEventInit();
            onTaskInit();
        }
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


    private void headerViewInit() {
        //header UserName
        headerTextViewUserName = (TextView) findViewById(R.id.headerTextViewUserName);
        headerTextViewUserName.setText(loginEventModel.getUserName());
    }

        /**
         * Barcode Event View ID 찾기
         */
    private void findViewByIdBarcodeView() {
        barcodeScrollView = (ScrollView) findViewById(R.id.barcodeScrollView);
        barcodeButtonBarcode = (Button) findViewById(R.id.barcodeButtonBarcode);
        barcodeTextViewSerialNo = (TextView) findViewById(R.id.barcodeTextViewSerialNo);
        barcodeTextViewModel = (TextView) findViewById(R.id.barcodeTextViewModel);
        barcodeTextViewUserSpec = (TextView) findViewById(R.id.barcodeTextViewUserSpec);
        barcodeButtonGoingOutDate = (Button) findViewById(R.id.barcodeButtonGoingOutDate);
        barcodeTextViewGoingOutDate = (TextView) findViewById(R.id.barcodeTextViewGoingOutDate);
        barcodeSpinnerWarrantyCode = (Spinner)findViewById(R.id.barcodeSpinnerWarrantyCode);
        barcodeButtonExpiryDate = (Button) findViewById(R.id.barcodeButtonExpiryDate);
        barcodeTextViewExpiryDate = (TextView) findViewById(R.id.barcodeTextViewExpiryDate);
        barcodeSpinnerBuyer = (Spinner)findViewById(R.id.barcodeSpinnerBuyer);
        barcodeSpinnerServiceCenter = (Spinner)findViewById(R.id.barcodeSpinnerServiceCenter);
        barcodeEditTextDescription = (EditText) findViewById(R.id.barcodeEditTextDescription);
        barcodeEditTextQuantity = (EditText) findViewById(R.id.barcodeEditTextQuantity);
        barcodeButtonSave = (Button) findViewById(R.id.barcodeButtonSave);
    }

    /**
     * Barcode Event 정의
     */
    private void onBarcodeEventInit() {
        //화면 초기화
        new BarcodeEventViewInit(MainActivity.this);
        //Barcode Scan 버튼
        barcodeButtonBarcode.setOnClickListener(new BarcodeEventButtonBarcode(MainActivity.this));
        /**
         * 장원씨 Test
        barcodeButtonBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.ShowProgress(true);

                //TODO :: 임시
                String barcode = "BEG7DKA15090002";

                barcodeTextViewSerialNo.setText(barcode);
                barcodeEventModel.setBarcode(null);
                barcodeEventButtonBarcodeFindTask = new BarcodeEventButtonBarcodeFindTask(MainActivity.this, barcode);
                barcodeEventButtonBarcodeFindTask.execute((Void) null);

            }
        });
        */

        //GoingOutDate 버튼
        barcodeButtonGoingOutDate.setOnClickListener(new ButtonDatePicker(MainActivity.this, barcodeTextViewGoingOutDate));
        //ExpiryDate 버튼
        barcodeButtonExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barcodeEventModel.getBarcode() == null) {
                    alertMessage.AlertShow("Error","Barcode Scan No").show();
                    return;
                }

                String warrantyCode = patternCode.PatternCodeMatcher(barcodeSpinnerWarrantyCode.getSelectedItem().toString());
                if (warrantyCode == "") {
                    alertMessage.AlertShow("Error","Spinner Warranty Code Error(Pattern)").show();
                    return;
                }

                progress.ShowProgress(true);
                barcodeEventButtonExpiryDateFindTask = new BarcodeEventButtonExpiryDateFindTask(MainActivity.this,
                                                                                                warrantyCode,
                                                                                                barcodeTextViewGoingOutDate.getText().toString().replace("-",""));
                barcodeEventButtonExpiryDateFindTask.execute((Void) null);
            }
        });
        //Save 버튼
        barcodeButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barcodeEventModel.getBarcode() == null) {
                    alertMessage.AlertShow("Error","Barcode Scan No").show();
                    return;
                }

                String warrantyCode = patternCode.PatternCodeMatcher(barcodeSpinnerWarrantyCode.getSelectedItem().toString());
                if (warrantyCode == "") {
                    alertMessage.AlertShow("Error","Spinner Warranty Code Error(Pattern)").show();
                    return;
                }
                String buyer = patternCode.PatternCodeMatcher(barcodeSpinnerBuyer.getSelectedItem().toString());
                if (buyer == "") {
                    alertMessage.AlertShow("Error","Spinner Buyer Error(Pattern)").show();
                    return;
                }
                String serviceCenter = patternCode.PatternCodeMatcher(barcodeSpinnerServiceCenter.getSelectedItem().toString());
                if (serviceCenter == "") {
                    alertMessage.AlertShow("Error","Spinner Buyer Error(Pattern)").show();
                    return;
                }

                if (barcodeEditTextQuantity.getText().toString() == "")
                    barcodeEditTextQuantity.setText("1");

                progress.ShowProgress(true);
                barcodeEventButtonSaveTask = new BarcodeEventButtonSaveTask(MainActivity.this,
                                                                            barcodeTextViewGoingOutDate.getText().toString(),
                                                                            warrantyCode,
                                                                            buyer,
                                                                            serviceCenter,
                                                                            barcodeEditTextDescription.getText().toString(),
                                                                            barcodeEditTextQuantity.getText().toString());
                barcodeEventButtonSaveTask.execute((Void) null);
            }
        });

        /**
         * spinner 데이터 설정
         */
        //WarrantyCode
        spinners = new Spinners(MainActivity.this, barcodeSpinnerWarrantyCode, arrayListWarrantyCode);
        barcodeSpinnerWarrantyCode.setSelection(0);
        //Corporation Info(법인 정보)
        spinners = new Spinners(MainActivity.this, barcodeSpinnerBuyer, arrayListBuyer);
        spinners.setSpinnerText(barcodeSpinnerBuyer, loginEventModel.getBuyer());
        //Service Center
        spinners = new Spinners(MainActivity.this, barcodeSpinnerServiceCenter, arrayListServiceCenter);
        spinners.setSpinnerText(barcodeSpinnerServiceCenter, loginEventModel.getServiceCenter());
    }

    /**
     * ListView Event View ID 찾기
     */
    private void findViewByIdListView() {
        lvSpinnerDate = (Spinner) findViewById(R.id.lvSpinnerDate);
        lvButtonDate = (Button) findViewById(R.id.lvButtonDate);
        lvTextViewDate = (TextView) findViewById(R.id.lvTextViewDate);
        lvButtonFind = (Button) findViewById(R.id.lvButtonFind);
        listView = (ListView) findViewById(R.id.lvListView);
    }

    /**
     * ListView Event 정의
     */
    private void onListViewEventInit() {
        //화면 초기화
        new ListViewEventViewInit(MainActivity.this);
        //RegDate 버튼
        lvButtonDate.setOnClickListener(new ButtonDatePicker(MainActivity.this, lvTextViewDate));
        //Find 버튼
        lvButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.ShowProgress(true);
                //listViewEventAdapter = new ListViewEventAdapter();
                listViewEventButtonFindTask = new ListViewEventButtonFindTask(MainActivity.this
                        ,lvSpinnerDate.getSelectedItem().toString(), lvTextViewDate.getText().toString());
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
        barcodeEventButtonExpiryDateFindTask = null;
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
        switch (requestCode) {
            /**
             * IntentIntegrator : REQUEST_CODE = 0x0000c0de(49374) 고정됨;
             */
            case 49374: {
                progress.ShowProgress(true);
                barcodeTextViewSerialNo.setText(result.getContents());
                barcodeEventModel.setBarcode(null);
                barcodeEventButtonBarcodeFindTask = new BarcodeEventButtonBarcodeFindTask(MainActivity.this, result.getContents());
                barcodeEventButtonBarcodeFindTask.execute((Void) null);
            }
        }
    }

}
