package cn.com.broadlink.happy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.com.broadlink.happy.model.DeviceInfo;
import cn.com.broadlink.happy.model.DevicePairInfo;
import cn.com.broadlink.happy.model.param.InitParam;
import cn.com.broadlink.happy.model.param.RMDescParam;
import cn.com.broadlink.happy.model.result.PairResult;
import cn.com.broadlink.smarthome.BLSmartHomeAPI;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static cn.com.broadlink.happy.Login.global_housename;
import static cn.com.broadlink.happy.Login.global_username;
import static cn.com.broadlink.happy.RoomActivity.ctrl_pairdval;
import static cn.com.broadlink.happy.RoomActivity.rmitem_value;
import static cn.com.broadlink.happy.RoomActivity.rname;

public class MainActivity extends Activity {
   // String[] rname;
    static Boolean edt_conrt=true;
     Handler mHandler;
    boolean isRunning = false;
    String cururl="";
    NumberPicker np;
    String json_string,switch_numb;
    NumberPicker nmbr;
    TextView txt_nmber,config_indicate;
    ImageView editablemode;
    int runval=50000;
    private static final String TAG = "BROADLINK_SDK";
    private static final String LICENSE = "j2Z8lGo4H5QyY9QuLIvaArpJilB4niPX0VJ3/yJmg2uz9VLe0/3NG4ijLTtsr6cXvuoNVwAAAAC34dCX0yp96VBPcuFLL7wGcGROQUnBoZ+yWte4wQP2QBAiWsIC7HOxWMc5DPTJ6KB+CwHvkzI2xdh4hc7UtXUGsEkbxXTfoUSQjDzWcfVjcA4AAADyki8iea7A5SRnXUE3S9qWgOmS8UyHs5pISopUO3fLlWUU/3Xu40C//cc6tVOyI8bjRa8g9o3HM62zOdPHgG6/";
    static RequestQueue mRequestQueue;
    private EditText mShowMsg;
    private TextView mTvConfig,scn1,scn2,scn3,scn4,scn5,scn6,scn7,scn8,allon,alloff,l1,l2,l3,l4;
    private TextView mTvPair,it_learn,on_off;
    private TextView device_cnted,getjust_learnd,controlBtn;
    public static final String MY_PREFS_NAME = "device_paired_result";
    private BLSmartHomeAPI mNetworkAPI;
    private DeviceInfo mDeviceInfo;
    private DevicePairInfo mDevicePairInfo;
    LinearLayout resetswitch;
    String ac_temp="novalue",run_switch_auto="";
    ImageView back_val;
    boolean send=false;
    boolean getir_just_lerned=false,get_learnrespo=false,controll_panel=false;
    private final static int REQUESTCODE = 1; // 返回的结果码
    String mac_str="",ip_str="",pid_str="",did_str = "",to_get_ir_info="",cookie="",decoded_cookie="",cookie_conrol_panel="", base64="";
    GridView rmlv;
    TextView roomname;
    public Adapter_GroupSwitchesList adapter3;
    /*static String rm_swtch[]={"26002c001a1a1e193b351e1a3a351f193a191f191f353a000b0b1e191f193b341f193b351e1a3a191f191f353a000d0500000000000000000000",
        "2600ee000b00067b0b0003370c00033609000d030a00033a090006790b0003380c0006790d000cff090006750f000339090003390b0003370f0003340c0003370a00033b0c0003330d0003350900033b0b0003380b000cfe090003380c00067a0a00067a0b0003370900033b0800067c0900067d0900010b1f191f193a351f193a353b353a1a1e1a1e351f00027e0c000678080001e31f191f193a351f193b353a353b191f191e351f0001a60a0003370b000339090002ba1f191e193b351f193a353b353a191f191f351e0004110900033a0b0003380d0006780a00067a090003390b0003380b0003380d0009ba0b000d0500000000000000000000",
        "26002c001b193a1a1e351f193a361e193b353a351f191f000aed1f193b191f351e193b351e1a3a353b351e191f000d0500000000000000000000",
        "26008a010600033a0a0003370900033a0b000338090003390a000339090003390a00033a0800033a090003390e0003360800033a0b0003370a0003390900033b09000339090003390800033a0900033b0800033a0a0003380a0003380b0003390800033a0a0003380a0003380b00033909000339160002741e191f193a361e193a363a1a1e191f191e361e191f0001080a00033a0800033b0b00033609151e1a1e193a361e1a3a353a1a1e1a1e191f351e1a1e0003740b0003380b00042c1e1a1e1a3a351e1a3a353a1a1e1a1e1a1e351e1a1e00029d0a00033a09000339090003390a0003390a00033a09000339090003390900033a0900033a090003390a0003380b0003380900033b0a0003380900033a0c0003350b00033909000339090003390900033a0a0003390a0003380b0003380c0003360c000338090003390800033a0900033a0800033b0c000337090003390700033c0a0003390b0003370b0003370a0003390900033b0800033a0d000335090003390a00033a09000339090003390900033a0900033b0b000d0500000000000000000000",
        "26004200181b1d1b38371d1b39361d1b39361d1b391b1d000b0a1d1b1d1b39361d1b39361d1b39361e1a391b1d000b0b1c1b1d1a39371d1b38371d1b39361d1b391b1d000d0500000000000000000000",
        "260042001a193b191f351e193b353a1a1e191f361e193a000b091d1b391b1d361e1a3937391a1d1b1d371d1a3a000b0b1d1a391b1d371d1a3937391a1d1b1d371d1a39000d0500000000000000000000",
        "260042001b191e193a361d1b37381c1c1c1c3a353b191f000b091e191d1b39361e1a39371d1a1e1a3937381b1d000b0c1e191f193a351f193a361e191f193a353b191f000d0500000000000000000000",
        "260042001a193b191f351e1a3a353a1a1e191f353a191f000b0c1c193b191f351e1a3a353a1a1e191f353a1a1e000b0a1e193a191f351f193a353a1a1e1a1e353a1a1d000d0500000000000000000000",
        "2600f200060003380900033a0a000338090003390a0003390900033a090003380a0003380a0003390900033a090003380a0003380a0003390900033a0900033909000339090003390a0003390a0003380a0003380a0003390900033a0a0003380a0003380a0003390900033a0900033909000339090003390a00033a0900033909000339090003390900033a09000339090003390900033a0900033a0900033a08000339090003390a00033a0900033a080002291f191f193a351f1939371e19391b1d37391a1e000b0b1b1c1d1b39361d1b39371d1a391b1d37381b1d000b0d1c1a1e1a39371d1a39371d1b381b1d37391b1d000d0500000000000000000000",
        "2600ae000500033c0700033a09000339090003380a0003390900033a09000339090003390a00033a090003390a0003380a000338090003390900033a0b0003370a0003390b0003370c0003370900033a0b0003360b00067b0b0006780900067d090009bd0b000cff0700033c0b00067a0c2c1e1a1e193a361e1a3a353a353b353a351f00035a0d0007871e191f193b351e193b353a353b353a351f000aee1e1a1e193b351e1a3a353a353b353b341f000d0500000000000000000000",
        "260048001a1a3a191f351f193a351f193a351f191f191e191f000aef1e193a1a1e351f193a351f193a351f191f191f191e000af01d193b191f341f193b351e193b351e1a1e191f191f000d0500000000000000000000",
        "260042001e191f193a351f193a353b351f193a351f191e000aef1e191f193a351f193b353a351f193a351f191f000aef1d1a1e193b351e1a3a353a351f193b351e1a1e000d0500000000000000000000",
        "260046001b193b191e351f193b341f1a3a351f191e193b0005360c0005c71f193a1a1e351f193a351f193b351e191f193a000b0b1e193a1a1e351f193a351f193b351e191f193b000d0500000000000000000000",
        "26002c001b193b191f341f193b353a191f191f191f343b000b0a1f193a1a1e351f193a353b191f191f191e353b000d0500000000000000000000",
        "26004c001a1a1e1a3a351f193a351f191f193a1a1e191f351e000aef1e1a1e193a361e1a3a351e1a1e193a1a1e1a1e351f0007520f00038d1f191f193a351f193a361e191f193a1a1e1a1e351e000d0500000000000000000000",
        "26004a001f193b341f193b351e1a1e193b191f191e1a1e000b0a1f191f193a351f193a351f191f193a1a1e191f191f000b0a1e191f193b351e193b351f191e193b191f191f191e0008950b000d0500000000000000000000"};
*/

     String content_ac[] ={"on","off","18","19","20","21","22","23","24","25","26","27","28","29","30"};

    static String rm_swtch[]={"2600240100012c92151216121512163615121513151215121636151215131635153617111611173417111710171116111711161115361711171017111611171116351611173516111711163516121500029016111512151316111513151215121612141316111612151214141437141415121512161214131414141314141413141315131413141414131438141314371537140005250001299514131513141314381413141315131413143814131413153714371414141314371513141315131413141414131437151314131414141314131513143714381413141414371413150002911413141315131437141414371413153714371438141314371513143714141413141315371413141414131414141314131537143714141413143814371437151314000d0500000000000000000000",
    "2600240100012c92153616121512161215121612151216121512153715361635163614141413153614141512161215121513151214131612151215131611151315361512163615121711153615131600029016111611161117111710171117101711161117111611171116111734171116111711161117111611161216111611171116111611161216121635163517111635150005250001299514371413151314131414141314141413141414371437153714371413151314371513141314141413141315131413141414131414141314141413143716361413141415361512150002911734161215121536163615121612153615361636151216361536153616121513153615121513151216121512151214141536153715121512163615121537141315000d0500000000000000000000",
    "2600240100012c92173417111611173417111711161117101711163517111611173417111711163516111711161117111611161117351611171116111710171116351711163517111611163517111600028f171017111611171017111611171116111710171116111711161117341711171017111611171116111711161116111711161117101711171017341735173417111700052200012c9216351711161117351611171017111611171116351710171117341711161117341711171116111611171116111734171117111611171017111611173417351710171116351711160002901610171117341711171017351611173417351734171117341711163517351635173417111710171116111711161117101735163517111611173516111734173517000d0500000000000000000000",
    "2600240100012b931537151215131536151215131512151315361536151315121735151215131536151217111611151316111513153615121513151215131512153615131536151316111536151315000291151116121611161215121513151215121612151215131512161216351512151315121513161115131611151217111512151315121512161215121513151215361600052400012a9315371512151216361512151315121513153615361612151215371512151315361513151215121513151215131536151215131512151315121512163615361513151215361513150002911535161215121513151215371512153616361536151315361536163615121537153615131512151315121513151215121537153615131512153715361513153615000d0500000000000000000000",
    "2600240100012d911735161117111635171116111710171116111711163517111635171116111734171116111711161117111611163517111611171116111710173516111735161117111635161117000290171116111711161117111611161117111611171116111611171116351711161117101711161117111611171017111611171116111611171116351711161116351700052200012c92173417111710173516111711161117111611161117351611173417111711163516111711161117111611171017351611171116111611171116111734173516111711163517101700028f1611161117111611171116351611173516351734171117341711163517111635173417111611171116111711161117101735163517111611173417351611173417000d0500000000000000000000",
    "2600240100012c92173516111711163517101711161117111635171116351611173516111711163517111611171017111611171116351711161117101711161117341711173417111611173516111700028f161116111711161117101711161117111611171116111711161116351711161117111611171116111611171116111710171116111711161117111635171116351600052300012c9216351711161117351611171116111711163517101735161117351611171116351711161117101711161117111635161117111611171116111710173516351711161117341711160002911611171116111711161116351711163517341735171116351611173516111734173517101711161117111611171017111734173516111711161117111635173417000d0500000000000000000000",
    "2600240100012d921734171116111735161117111611161117111635173417111734171116111735161216111611171017111611173417111711161116111711163517111635171017111635171116000290161017111611171116111711161116111711161117111611161117351611171116111711161116111711161117111611171017111611171017351734171116351700052300012b92173516111711163517101711161117111611173417351611173516111710173517111611161117111611171116351710171117101711161117111635163517111611173516111700028f1634173517341735173417111611173516351735161117341735161117111635173516111711161117101711161117111635173417111710173516351710173516000d0500000000000000000000",
    "2600240100012d92163517111611173417111710171116111735163517341711163517111611173417111711161116111711161117341711161117111611171116351611173516111711163516111700028f161116111711161117101711161117111611171017111611171116351711161117111611171017111611171116111710171116111711161117111611163517351600052300012b92173516111711163517101711161117111635173417351611173516111711163516111711161117111611171017351611171116111611171116111734173516111711163516111700028f1634173516351735163517111611163517351635171017351635171116111734173516111711161117111611161117111635173417111710171116111734173517000d0500000000000000000000",
    "2600240100012d92163517111611173417111611171116111710171117101735163517111611173417111611171116111711161117341711161117111611171017351611173516111711163516111700028f16111611161216111611171116111611171116111711161117111635171116111612161116111711161117111611161117111611171116111734171116351735160005230001299414371513141315371413141414131512151314131414143714371513161114381413161216111611161216111635171116111711161116121611163515371512151315361512150002911512153615371536153615131512153715361536151315361513151215121636153615131512151315121512151315121536163615121513151215131536153615000d0500000000000000000000",
    "2600240100012c921734171117101735161117111611171116351611171116351734171117111635171017111611171116111710173516111711161117101711163517111635171017111734171116000290161017111611171116111710171116111711161117111611171017351611171116111710171116111710171116111711161117111611171017111635173417351600052300012b92173516111711163517101711161117111635171017111635173417111711163516111710171117111611161117341711171017111611171116111635173417111710173516111700028f1610173516351735163517111611173417351635171116351611171116111734173517101711171017111611171116111734173516111711163517111635173417000d0500000000000000000000",
    "2600240100012d91173517111611173417111611171116111710173516111734173517101711163517111611171116111611171116351711161117111611161117351611173417111711163516111700028f161116111711161117111611161117111611171116111711161116351711161116111711161117111611161117111611171116111710171116351734173516351700052200012c92173417111710173516111711161117111611173417111635173417111611173417111710171116111711161116351711161117101711161117111635173516111710173516111700028f1635161117341735163516111711163517341735161117351635163517351612163516111711161116111612161116121635163517111512163615121536163614000d0500000000000000000000",
    "2600240100012c92163517111611173417111611171116111635173516111734173516111711163517101711161117111611171017351611171116111611171116351711163516111711163517101700028f161116111711161117101711161117111611171017111611171116351711161116111711161117111611171017111611171116111711161116111711161117111600052200012c91173516111711163517111611171116111635173417111734173516111711163516111711161117111611171017351611171116111711161116111735163517101711163517111600028f1611161117351635173417111611173516351734171117341711163517341711173417111611171116111711161117101735163517111611173417111635173417000d0500000000000000000000",
    "2600240100012c92173417111710173516111711161117111611171017351635173417111711163516111711161117111611171116351710171116111711161117341711163517111611173417111700028f161116111711161117101711161117111611171116111611171116351711161117101711161117111611171116111611171116111711161117341711171017111600052500012c9217341711171116351611171117101711161117111635173516351711161117351611171117101711161117111635161216111611161216121611163517351512161216351612150002911611161115371437163614131414153614381437141414371414143714381413143814131413151314131513141314141437143814131513141315371536163616000d0500000000000000000000",
    "2600240100012c92173417111611173516111711161117101735161117351635173417111611173417111711161117101711161117341711171017111611171116351611173516111711163517101700028f161117101711161117111611171116111611171116111711161116351711161117111611161117111611171116111711161116111711161117111635161117111600052300012c9217341711171017351611171116111710173516111735163517341711171017351611171116111611171116111734171117101711161117111611173417351711161116351711160002901634173516111734173516111711163517351635161215361635171116351711163516111612161117111611161216111635173516111711161116351735163517000d0500000000000000000000",
    "2600240100012d911735161117111635171116111710171117101735163517341735161117111635171017111710171116111710173517101711161117111611173417111734171017111734171116000290161017111611171017111710171116111711161117101711161117341711171017111611171116111711161116111711161117111611161117341735171116111600052300012b92173516111711163517101711171017111611173516351734173516111711163517111611171017111611171116351710171116111711161117111635163517111611173417111700028f1634173516111735163517111611173417351635171116351734171116351711163517101711161117111611171017111635173417111611173516351734173516000d0500000000000000000000"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mHandler = new Handler();
        findView();
        init();

        creat_adater();
        getUnsafeOkHttpClient();
        setListener();


        np = (NumberPicker) findViewById(R.id.numberPicker);

        //nmbr.getValue();
        np.setMinValue(18);
        np.setMaxValue(30);

        np.setOnValueChangedListener(onValueChangeListener);

    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        isRunning=false;
        MainActivity.this.mHandler.removeCallbacks(runnable);
        run_switch_auto="";
        config_indicate.setVisibility(View.GONE);
        txt_nmber.setEnabled(true);

        super.onBackPressed();

    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            isRunning = true;

            if(!run_switch_auto.contentEquals(""))
            try {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                Log.d("++++++++value","++++++++++"+switch_numb);
                Toast.makeText(getApplication(),switch_numb, Toast.LENGTH_SHORT).show();
                // break;


                if (!TextUtils.isEmpty(ctrl_pairdval)) {
                    Log.d("++++++++ctrl_pairdval","++++++++++"+ctrl_pairdval);
                    PairResult pairResult = JSON.parseObject(ctrl_pairdval, PairResult.class);
                    if (pairResult.getStatus() == 0) {
                        mDevicePairInfo = pairResult.getDevicePairedInfo();
                    }
                }


                if(mDevicePairInfo!=null){
                    try {

                        Log.d("++++++++ctrl_pairdval","++++++++++"+mDevicePairInfo);
                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);
                        descParam.getData().getIrcode().setCode(run_switch_auto);

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(MainActivity.this, "No controllable equipment" );

                }
            }catch (Exception e){
                e.printStackTrace();
            }


            MainActivity.this.mHandler.postDelayed(runnable, runval);
        }
    };



    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                  //  Toast.makeText(getApplication(),numberPicker.getValue(), Toast.LENGTH_SHORT).show();
                    Log.d("++++++++value","++++++++++"+numberPicker.getValue());

                    txt_nmber.setText(String.valueOf(numberPicker.getValue()));
                    ac_temp=String.valueOf(numberPicker.getValue());

                }
            };
    public void creat_adater(){

        on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<rm_swtch.length;i++){
                    if(on_off.getText().toString().contentEquals("on")){
                        //rm_swtch[i];

                        on_off.setText("off");

                        try {
                            Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");

                            if (!TextUtils.isEmpty(ctrl_pairdval)) {
                                Log.d("++++++++ctrl_pairdval","++++++++++"+ctrl_pairdval);

                                PairResult pairResult = JSON.parseObject(ctrl_pairdval, PairResult.class);
                                if (pairResult.getStatus() == 0) {
                                    mDevicePairInfo = pairResult.getDevicePairedInfo();
                                }
                            }


                            if(mDevicePairInfo!=null){
                                try {


                                    String action = "RM_Panel_Test";
                                    RMDescParam descParam = new RMDescParam();
                                    descParam.setDevicePairedInfo(mDevicePairInfo);
                                    descParam.getData().setPid("000000000000000000000000c3050100");
                                    descParam.getData().setIrcodeid("2924");
                                    descParam.getData().setTimestamp(1523347479);
                                    descParam.getData().getIrcode().setCode(rm_swtch[0]);

                                    // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                                    Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                                    final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                                    Log.d(TAG, "SDK Trace:" + result);

                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                            else if (mDevicePairInfo == null) {
                                Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                                BLCommonUtils.toastShow(MainActivity.this, "No controllable equipment" );

                            }
                        }catch (Exception e){

                        }


                        break;

                    }





                    if(on_off.getText().toString().contentEquals("off")){
                        //rm_swtch[i];

                        on_off.setText("on");
                        try {
                            Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");

                            if (!TextUtils.isEmpty(ctrl_pairdval)) {
                                Log.d("++++++++ctrl_pairdval","++++++++++"+ctrl_pairdval);

                                PairResult pairResult = JSON.parseObject(ctrl_pairdval, PairResult.class);
                                if (pairResult.getStatus() == 0) {
                                    mDevicePairInfo = pairResult.getDevicePairedInfo();
                                }
                            }


                            if(mDevicePairInfo!=null){
                                try {

                                    Log.d("++++++++mDevicePairInfo","++++++++++"+mDevicePairInfo);

                                    String action = "RM_Panel_Test";
                                    RMDescParam descParam = new RMDescParam();
                                    descParam.setDevicePairedInfo(mDevicePairInfo);
                                    descParam.getData().setPid("000000000000000000000000c3050100");
                                    descParam.getData().setIrcodeid("2924");
                                    descParam.getData().setTimestamp(1523347479);
                                    descParam.getData().getIrcode().setCode(rm_swtch[1]);

                                    // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                                    Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                                    final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                                    Log.d(TAG, "SDK Trace:" + result);

                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                            else if (mDevicePairInfo == null) {
                                Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                                BLCommonUtils.toastShow(MainActivity.this, "No controllable equipment" );

                            }
                        }catch (Exception e){

                        }


                        break;
                    }
                }
            }
        });




        txt_nmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i=0;i<rm_swtch.length;i++){
                    if(ac_temp.contentEquals(String.valueOf(content_ac[i]))){
                        //rm_swtch[i];
                        Log.d("++++++++value","++++++++++"+content_ac[i]);
                        Toast.makeText(getApplication(),ac_temp, Toast.LENGTH_SHORT).show();
                       // break;


                        try {
                            Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");

                            if (!TextUtils.isEmpty(ctrl_pairdval)) {
                                Log.d("++++++++ctrl_pairdval","++++++++++"+ctrl_pairdval);
                                PairResult pairResult = JSON.parseObject(ctrl_pairdval, PairResult.class);
                                if (pairResult.getStatus() == 0) {
                                    mDevicePairInfo = pairResult.getDevicePairedInfo();
                                }
                            }


                            if(mDevicePairInfo!=null){
                                try {

                                    Log.d("++++++++mDevicePairInfo","++++++++++"+mDevicePairInfo);
                                    String action = "RM_Panel_Test";
                                    RMDescParam descParam = new RMDescParam();
                                    descParam.setDevicePairedInfo(mDevicePairInfo);
                                    descParam.getData().setPid("000000000000000000000000c3050100");
                                    descParam.getData().setIrcodeid("2924");
                                    descParam.getData().setTimestamp(1523347479);
                                    descParam.getData().getIrcode().setCode(rm_swtch[i]);

                                    // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                                    Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                                    final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                                    Log.d(TAG, "SDK Trace:" + result);

                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                            else if (mDevicePairInfo == null) {
                                Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                                BLCommonUtils.toastShow(MainActivity.this, "No controllable equipment" );

                            }
                        }catch (Exception e){

                        }




                    }
                }


            }
        });
        config_indicate.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                if(isRunning){
                    isRunning=false;
                    MainActivity.this.mHandler.removeCallbacks(runnable);
                    run_switch_auto="";
                    config_indicate.setVisibility(View.GONE);
                    txt_nmber.setEnabled(true);






                }

                return false;
            }
        });





        txt_nmber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {





                if(!isRunning){

                    for(int i=0;i<rm_swtch.length;i++){
                        if(ac_temp.contentEquals(String.valueOf(content_ac[i]))){
                            //rm_swtch[i];

                            final CharSequence[] items = {"2-minuts", "3-minuts", "5-minuts","7-minuts"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Select time in mins");
                            final int finalI = i;
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    // Do something with the selection


                                    if(item==0){
                                        Log.d("++++++++value","++++++++++"+item);

                                        runval=20000;
                                        Log.d("++++++++value","++++++++++"+content_ac[finalI]);
                                        Toast.makeText(getApplication(),ac_temp, Toast.LENGTH_SHORT).show();
                                        // break;
                                        txt_nmber.setEnabled(false);
                                        config_indicate.setVisibility(View.VISIBLE);
                                        switch_numb=content_ac[finalI];

                                        run_switch_auto=rm_swtch[finalI];
                                        runnable.run();
                                    }
                                    if(item==1){
                                        Log.d("++++++++value","++++++++++"+item);

                                        runval=30000;
                                        Log.d("++++++++value","++++++++++"+content_ac[finalI]);
                                        Toast.makeText(getApplication(),ac_temp, Toast.LENGTH_SHORT).show();
                                        // break;
                                        txt_nmber.setEnabled(false);
                                        config_indicate.setVisibility(View.VISIBLE);
                                        switch_numb=content_ac[finalI];

                                        run_switch_auto=rm_swtch[finalI];
                                        runnable.run();
                                    }
                                    if(item==2){
                                        Log.d("++++++++value","++++++++++"+item);

                                        runval=50000;
                                        Log.d("++++++++value","++++++++++"+content_ac[finalI]);
                                        Toast.makeText(getApplication(),ac_temp, Toast.LENGTH_SHORT).show();
                                        // break;
                                        txt_nmber.setEnabled(false);
                                        config_indicate.setVisibility(View.VISIBLE);
                                        switch_numb=content_ac[finalI];

                                        run_switch_auto=rm_swtch[finalI];
                                        runnable.run();
                                    }
                                    if(item==3){
                                        Log.d("++++++++value","++++++++++"+item);

                                        runval=70000;
                                        Log.d("++++++++value","++++++++++"+content_ac[finalI]);
                                        Toast.makeText(getApplication(),ac_temp, Toast.LENGTH_SHORT).show();
                                        // break;
                                        txt_nmber.setEnabled(false);
                                        config_indicate.setVisibility(View.VISIBLE);
                                        switch_numb=content_ac[finalI];

                                        run_switch_auto=rm_swtch[finalI];
                                        runnable.run();
                                    }

                                    dialog.dismiss();
                                }
                            });
                            builder.show();





                        }
                    }








                }



                return false;
            }
        });


        /*if(rname.length==0)*/

        // save switch values-------

        /*{

            send=true;
            cururl="http://123.176.44.3:5902/VankonApp/get_room_switches/";
            // JSONObject rmdt = null;
            Log.d("+++ caling url",cururl);
            Log.d("im inside jsoncall", String.valueOf(roomdtails()));
            json_string=String.valueOf(roomdtails());
            // JSONSenderVolley(cururl, rmdt);
            OkHttpHandler okHttpHandler= new OkHttpHandler();
            okHttpHandler.execute(cururl);

        }*/


        {
        /*else{
            rname=new String[rm_swtch.length];
            for (int l = 0; l < rm_swtch.length; l++) {
                rname[l] = "none,none";
                Log.d("!....rname", rname[l]);
            }

            adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
            rmlv.setAdapter(adapter3);

        }*/
        init();

           //, rname=new String[rm_swtch.length];

       /* for (int l = 0; l < rm_swtch.length; l++) {
            // rname[l] = "none,none";
            Log.d("--!starting adater", "---------------" + rname[l]+l);
        }*/

        adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
        rmlv.setAdapter(adapter3);}

        rmlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

               // Toast.makeText(getBaseContext(),rname[position],Toast.LENGTH_SHORT).show();

    try {
     Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");

    if (!TextUtils.isEmpty(ctrl_pairdval)) {
        PairResult pairResult = JSON.parseObject(ctrl_pairdval, PairResult.class);
        if (pairResult.getStatus() == 0) {
            mDevicePairInfo = pairResult.getDevicePairedInfo();
        }
    }


                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);
                        descParam.getData().getIrcode().setCode(rm_swtch[position]);

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(MainActivity.this, "No controllable equipment" );

                }
}catch (Exception e){

}
            }

        });

        resetswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rname = new String[rm_swtch.length];
                    for (int l = 0; l < rm_swtch.length; l++) {

                        rname[l] = "L"+l;

                        Log.d("!reset....rname", rname[l]);

                    }

               // init();
                adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(), rname);
                rmlv.setAdapter(adapter3);
                RoomActivity.saveArray(rname,rmitem_value,getApplicationContext());
            }
        });

        rmlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int posi, long id) {

                final int pos=posi;

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custome_switches);

                final Button fan = (Button) dialog.findViewById(R.id.fan);
                final Button tv = (Button) dialog.findViewById(R.id.tv);
                final EditText switch_name = (EditText) dialog.findViewById(R.id.value_id);

                final Button lamp = (Button) dialog.findViewById(R.id.lamp);
                final Button wm = (Button) dialog.findViewById(R.id.wm);
                final Button oven = (Button) dialog.findViewById(R.id.oven);
                final Button printer = (Button) dialog.findViewById(R.id.printer);
                final Button cleaner = (Button) dialog.findViewById(R.id.cleaner);
                final Button fridge = (Button) dialog.findViewById(R.id.fridge);
                final Button fan2 = (Button) dialog.findViewById(R.id.fan2);
                final Button ac = (Button) dialog.findViewById(R.id.ac);

                final Button button1 = (Button) dialog.findViewById(R.id.button1);
                final Button button2 = (Button) dialog.findViewById(R.id.button2);
                fan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="FAN"+","+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );

                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="TV,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );

                    }
                });
                lamp.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="LAMP,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );

                    }
                });
                wm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="WASHINGMISION,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );

                    }
                });
                oven.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="OVEN,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });
                printer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="PRINTER,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });
                cleaner.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="CLEANER,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });
                fridge.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="FRIDGE,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });
                fan2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="FAN2,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });
                ac.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(!switch_name.getText().toString().contentEquals(""))
                        rname[pos]="AC,"+switch_name.getText().toString();
                        else
                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "please enter Switch name !....." );


                    }
                });

                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try{


                            //http://123.176.44.3:5902/VankonApp/add_room_switches/

                           /* {"Username": "vinay","Housename":"vancon",
                                "Roomname":"Room1","Switch":"Switch11",
                                "Name":"Name1","Type":"Type1"}
                            */

                            try {


                                send=false;
                                cururl="http://123.176.44.3:5902/VankonApp/add_room_switches/";
                                JSONObject rmdt = null;
                                Log.d("+++ caling url",cururl);
                                Log.d("im inside jsoncall", String.valueOf(switch_upload("Switch"+pos,rname[pos])));
                                rmdt = new JSONObject(String.valueOf(roomdtails()));

                                json_string=String.valueOf(switch_upload("Switch"+(pos),rname[pos]));
                                // JSONSenderVolley(cururl, rmdt);
                                OkHttpHandler okHttpHandler= new OkHttpHandler();
                                okHttpHandler.execute(cururl);


                              /*  send=true;
                                cururl="http://123.176.44.3:5902/VankonApp/get_room_switches/";
                                // JSONObject rmdt = null;
                                Log.d("+++ caling url",cururl);
                                Log.d("im inside jsoncall", String.valueOf(roomdtails()));
                                json_string=String.valueOf(roomdtails());
                                // JSONSenderVolley(cururl, rmdt);
                               // OkHttpHandler okHttpHandler= new OkHttpHandler();
                                okHttpHandler.execute(cururl);
*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }catch (NullPointerException |ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();


                            BLCommonUtils.toastShow(MainActivity.this, "\n" +
                                    "opps something missing!....." );

                        }

                        dialog.dismiss();
                    }
                });



                button1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        init();
        setListener();
        super.onResume();
    }


    private void findView() {
        editablemode=(ImageView)findViewById(R.id.edit_id);
        mShowMsg = (EditText) findViewById(R.id.tv_show);

        txt_nmber =(TextView)findViewById(R.id.numbrtxt);
        config_indicate =(TextView)findViewById(R.id.indicateid);

        on_off=(TextView)findViewById(R.id.power);
        device_cnted=(TextView)findViewById(R.id.tv_probe);
        mTvConfig = (TextView) findViewById(R.id.tv_config);
        mTvPair = (TextView) findViewById(R.id.tv_pair);
        roomname=(TextView)findViewById(R.id.rm_name);
        it_learn = (TextView) findViewById(R.id.learn_ir);
        getjust_learnd=(TextView)findViewById(R.id.get_ir);
        controlBtn =(TextView)findViewById(R.id.control_id);
        back_val=(ImageView)findViewById(R.id.ivBack);
        resetswitch=(LinearLayout)findViewById(R.id.reset);
        scn1 =(TextView)findViewById(R.id.scn1);
        scn2=(TextView)findViewById(R.id.scn2);
        scn3=(TextView)findViewById(R.id.scn3);
        scn4=(TextView)findViewById(R.id.scn4);
        scn5=(TextView)findViewById(R.id.scn5);
        scn6=(TextView)findViewById(R.id.scn6);
        scn7=(TextView)findViewById(R.id.scn7);
        scn8=(TextView)findViewById(R.id.scn8);
        allon=(TextView)findViewById(R.id.on);
        alloff=(TextView)findViewById(R.id.off);
        l1=(TextView)findViewById(R.id.l1);
        l2=(TextView)findViewById(R.id.l2);
        l3=(TextView)findViewById(R.id.l3);
        l4=(TextView)findViewById(R.id.l4);
        rmlv = (GridView) findViewById(R.id.switches_list);
    }
public void save_shared(String data){


    SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = shared.edit();
    editor.putString("name", data);
    editor.commit();
    BLCommonUtils.toastShow(MainActivity.this, "\n" +
            data );
    //editor.putInt("idName", 12);

}

    public String retrive_shared(){
        String name ="";
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = (shared.getString("name", ""));
        BLCommonUtils.toastShow(MainActivity.this, "\n" +
                name );
        return name;
    }
    private void init()  {
        String mDevAddr = "";






        mNetworkAPI = BLSmartHomeAPI.getInstanceBLNetwork(MainActivity.this);
        InitParam param = new InitParam();
        param.setPackageName("cn.com.broadlink.SDKDemo");
        param.setLicense(LICENSE);
        param.setLoglevel(4);

    }
    public void ger_ir_code(String ir_config){

        get_learnrespo=false;

        String ir_code="";
        JSONObject myjson = null;
        try {
            myjson = new JSONObject(ir_config);

          JSONObject  event_obj=myjson.getJSONObject("event");
            // Log.d("---vinay_to_get_ir_info", "Json_string:" + to_get_ir_info);
            ir_code = event_obj.getJSONObject("payload").getString("code");
            // Log.d("---vinay_email", "Json_string:" + did_str);
          //  pid_str = myjson.getJSONObject("devicePairedInfo").getString("pid");

            getjust_learnd.setText(ir_code);
            Log.d("respo_ir oprtd--","just learned data ++++++++++"+ir_config);
            Log.d("ir_code--","ir_code ++++++++++"+ir_code);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json_ir="\"ircode\":{"+"\"ircodeid\":\"\","+"\"ircodedata\":{"+"\"functionList\":[{"+"\"code\":\""+ir_code+"\","+"\"function\":\"ON\","+"\"name\":\"\""+"}]"+"}"+"}";
        //decoded_cookie

        try {
            cookie_conrol_panel="{"+json_ir+","+"\"device\":"+decoded_cookie+"}";
            Log.d("cookie_conrol_panel", "cookie_conrol_panel not encoded----device===========:" + cookie_conrol_panel);
            byte[] data = new byte[0];
            data = cookie_conrol_panel.getBytes("UTF-8");
            base64= Base64.encodeToString(data, Base64.DEFAULT);
            Log.d("cookiee_control", "cookie of cpntroll device===========:" + base64);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void JSONSenderVolley(String url, final JSONObject json)
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());


                        Log.d("response------",response.toString());

                        if(get_learnrespo){
                            get_learnrespo=false;
                            ger_ir_code(response.toString());
                        }

                        if(controll_panel){
                            Log.d("controlpanel oprtd--",response.toString());

                        }

                        mRequestQueue.cancelAll(json);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };


        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        mRequestQueue.add(req);
    }

    private void setListener() {

        roomname.setText(rmitem_value);
        editablemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_conrt){
                    editablemode.setBackgroundResource(R.mipmap.edit_active);
                edt_conrt=false;
                    adapter3.notifyDataSetChanged();
                }
                else if(!edt_conrt){
                    edt_conrt=true;
                    editablemode.setBackgroundResource(R.mipmap.edit_inactive);
                adapter3.notifyDataSetChanged();}

            }
        });
        back_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EasyConfigActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });





        it_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    JSONObject myjson = new JSONObject(ctrl_pairdval);

                    to_get_ir_info=myjson.getString("devicePairedInfo");
                    // Log.d("---vinay_to_get_ir_info", "Json_string:" + to_get_ir_info);
                    did_str = myjson.getJSONObject("devicePairedInfo").getString("did");
                    // Log.d("---vinay_email", "Json_string:" + did_str);
                    pid_str = myjson.getJSONObject("devicePairedInfo").getString("pid");
                    // Log.d("---vinay_email2", "Json_string:" + pid_str);
                    mac_str = myjson.getJSONObject("devicePairedInfo").getString("mac");
                    // Log.d("---vinay_email2", "Json_string:" + mac_str);
                    cookie = myjson.getJSONObject("devicePairedInfo").getString("cookie");

                    Log.d("---vinay_email2", "----cookiee====:" + cookie);

                  /*  // Sending side
                                   byte[] data = text.getBytes("UTF-8");
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);*/

                    // Receiving side
                    byte[] data1 = Base64.decode(cookie, Base64.DEFAULT);
                    try {

                        decoded_cookie = new String(data1, "UTF-8");
                        Log.d("---decoded_cookie", "----decoded value==--------=====+++==:" + decoded_cookie);
                        JSONObject device = new JSONObject(decoded_cookie);
                        decoded_cookie= device.getString("device");
                        Log.d("---decoded_cookie", "----decoded_cookie++++++++++====:" + decoded_cookie);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // mShowMsg.setText(to_get_ir_info);
                    it_learn.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try{
                   // Log.d("vinay----","device pair:"+"=============debug result:"+result+"========");
                    String json="{" +
                            "\"directive\":{" +
                            "\"header\":{"+"\"namespace\":\"DNA.RMControl\","+"\"name\":\"StudyIrCode\","+"\"interfaceVersion\":\"2\","+"\"messageId\":\"1bd5d003-31b9-476f-ad03-71d471922820\"" + "},"+
                            "\"endpoint\":{" + "\"devicePairedInfo\":{\"did\":\""+did_str+"\",\"pid\":\""+"000000000000000000000000c2050100"+"\",\"mac\":\""+mac_str+"\",\"cookie\":\""+cookie+"\"},"+"\"endpointId\":\""+did_str+"\"," + "\"cookie\":{}" + "}," +
                            "\"payload\":{"+"}"+
                            "}" +
                            "}";

                    Log.d("vinay_debug", "Study ir code===========:" + json);

                    JSONObject json_srt_con=new JSONObject(json);

                    String url="http://101.124.0.208:12347/openproxy/v2/learncode?license=LdsII6Ju9tc2Ba1Ab7OPeZYoKoD8kZyE89A8wE5XUYmbx5g26bA3cokOYtiAvicw1xMyWwAAAABS37aO9pqhR/DcVUjs5QpGwZuCHRzCqgH8Jw9vNQIFcDh+6HNYuUV8Gf0B/2hunfyy05qFEfP+0jNRq8V0KCkLgu17BymmnhQy5oxtKs2CmwAAAAA=";
                    getir_just_lerned=true;
                    JSONSenderVolley(url,json_srt_con);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

        getjust_learnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{
                    // Log.d("vinay----","device pair:"+"=============debug result:"+result+"========");
                    String json="{" +
                            "\"directive\":{" +
                            "\"header\":{"+"\"namespace\":\"DNA.RMControl\","+"\"name\":\"GetIrCode\","+"\"interfaceVersion\":\"2\","+"\"messageId\":\"1bd5d003-31b9-476f-ad03-71d471922820\"" + "},"+
                            "\"endpoint\":{" + "\"devicePairedInfo\":{\"did\":\""+did_str+"\",\"pid\":\""+"000000000000000000000000c2050100"+"\",\"mac\":\""+mac_str+"\",\"cookie\":\""+cookie+"\"},"+"\"endpointId\":\""+did_str+"\"," + "\"cookie\":{}" + "}," +
                            "\"payload\":{"+"}"+
                            "}" +
                            "}";

                    Log.d("_just_reached", "----------get learned==============:" + json);

                    JSONObject json_srt_con=new JSONObject(json);

                    String url="http://101.124.0.208:12347/openproxy/v2/learncode?license=LdsII6Ju9tc2Ba1Ab7OPeZYoKoD8kZyE89A8wE5XUYmbx5g26bA3cokOYtiAvicw1xMyWwAAAABS37aO9pqhR/DcVUjs5QpGwZuCHRzCqgH8Jw9vNQIFcDh+6HNYuUV8Gf0B/2hunfyy05qFEfP+0jNRq8V0KCkLgu17BymmnhQy5oxtKs2CmwAAAAA=";
                    get_learnrespo=true;
                    JSONSenderVolley(url,json_srt_con);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


    }


        public class OkHttpHandler extends AsyncTask {


        public  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String val;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)

        @Override
        protected Object doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, json_string);

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(cururl)
                    .post(body)
                    .build();

           /* Request request = new Request.Builder()
                    .url(strings[0])
                    .build();*/

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    final String jsonData = response.body().string();

                    Log.d("+++++++response",jsonData);


                    /*runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            JSONObject jres_gs1 = null;



                        }
                    });*/


                    try {


                        if(send) {
                            JSONObject jres_gs = new JSONObject(jsonData);
                            final String respo = jres_gs.getString("Response");

                            Log.d("+++++++response","im inside send boolen");


                            if (respo.contentEquals("NO_DATA_FOUND")){
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        Toast.makeText(getApplicationContext(), respo , Toast.LENGTH_SHORT).show();

                                        rname = new String[rm_swtch.length];
                                        for (int l = 0; l < rm_swtch.length; l++) {
                                            rname[l] = "L"+l;
                                            Log.d("--!nodata found", "---------------" + rname[l]+l);
                                        }

                                        adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(), rname);
                                        rmlv.setAdapter(adapter3);

                                    }
                                });
                            }else{
                                final ArrayList<String> roomnames_value = new ArrayList<String>();

                                final ArrayList<String> swicth_num_lst = new ArrayList<String>();
                                // JSONArray new_array1 = jres_gs.getJSONArray("get_switches");
                                JSONArray new_array2 = jres_gs.getJSONArray("switches");

                                roomnames_value.clear();

                                swicth_num_lst.clear();
                        /*valuese.clear();
                        pr_value.clear();*/


                                for (int i = 0, count = new_array2.length(); i < count; i++) {
                                    swicth_num_lst.add(new_array2.getJSONObject(i).getString("switch"));

                                    String swith_name = new_array2.getJSONObject(i).getString("name");
                                    String swith_type = new_array2.getJSONObject(i).getString("type");
                                    roomnames_value.add(swith_type + "," + swith_name);
                            /*
                            valuese.add(new_array2.getJSONObject(i).getString("room_name"));
                            pr_value.add(new_array2.getJSONObject(i).getString("paired_value"));
                             */
                                }







                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            rname = new String[rm_swtch.length];


                                            for (int l = 0; l < rm_swtch.length; l++) {
                                                rname[l] = "L"+l;
                                                 Log.d("--!length0 to adapt", "---------------" + rname[l]+l);
                                            }

                                            for (int l = 0; l < roomnames_value.size(); l++) {
                                                for (int k = 0; k < rm_swtch.length; k++) {
                                                    String number = swicth_num_lst.get(l).replaceAll("\\D+","");

                                                    Log.d("--!number", "---------------" + number);

                                                    if (number.contentEquals(String.valueOf(k))) {
                                                        rname[k] = roomnames_value.get(l);
                                                        Log.d("--!inside iff server", "---------------" + rname[k] + k);


                                                    }
                                                    //rname[k] = "none,none";

                                                }
                                                // rname[l] = roomnames_value.get(l);
                                            }




                                            for (int l = 0; l < rm_swtch.length; l++) {
                                               // rname[l] = "none,none";
                                                Log.d("--!length0 to adapt", "---------------" + rname[l]+l);
                                            }

                                            RoomActivity.saveArray(rname,rmitem_value,getApplicationContext());


                                            adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
                                            rmlv.setAdapter(adapter3);
                                        }
                                    });


                                }

                            }


                        if(!send){


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    /*rname = new String[rm_swtch.length];
                                    for (int l = 0; l < rm_swtch.length; l++) {
                                        rname[l] = "none,none";
                                        Log.d("--!length0 to adapt", "---------------" + rname[l]+l);
                                    }

                                    adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(), rname);
                                    rmlv.setAdapter(adapter3);*/


                                    try {
                                       JSONObject jres_gs1 = new JSONObject(jsonData);

                                        if(jres_gs1.getString("error_code").contentEquals("200")){
                                            String respo=jres_gs1.getString("Response");
                                             Toast.makeText(getApplicationContext(), respo , Toast.LENGTH_SHORT).show();
                                        }
                                        if(jres_gs1.getString("error_code").contentEquals("500")){
                                            // send=false;
                                            //String respox=jres_gs1.getString("error_desc");
                                            Toast.makeText(getApplicationContext(), "server error - unable to upload " , Toast.LENGTH_SHORT).show();}
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }



                                   // Toast.makeText(getBaseContext(),"saved to cloud",Toast.LENGTH_SHORT).show();

                                    send=true;

                                    cururl="http://123.176.44.3:5902/VankonApp/get_room_switches/";
                                    // JSONObject rmdt = null;
                                    Log.d("+++ caling url",cururl);
                                    Log.d("im inside jsoncall", String.valueOf(roomdtails()));
                                    json_string=String.valueOf(roomdtails());
                                    // JSONSenderVolley(cururl, rmdt);
                                     OkHttpHandler okHttpHandler= new OkHttpHandler();
                                    okHttpHandler.execute(cururl);

                                }
                            });

                        }
                       /* runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                *//* adapter3 = new Adapter_RoomList(RoomActivity.this,valuese,pr_value);
                                    rmlv.setAdapter(adapter3);*//*
                                createroom_initial.setVisibility(View.GONE);
                                initViews(roomnames_value);
                                adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
                                rmlv.setAdapter(adapter3);

                            }
                        });
*/
                    }catch(JSONException e){
                        e.printStackTrace();

                           /* runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    rname=new String[rm_swtch.length];
                                    for (int l = 0; l < rm_swtch.length; l++) {
                                        rname[l] = "none,none";
                                        Log.d("!....rname", rname[l]);
                                    }

                                    adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
                                    rmlv.setAdapter(adapter3);

                                }
                            });
*/

                    }


                }
            });

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     JSONObject roomdtails() {
        JSONObject post_dict = new JSONObject();
                /* "Username": "username",
         "Housename": "housename",
         "Roomname": "Room1"*/
        //global_username,global_housename
        try {
            post_dict.put("Username",global_username);
            post_dict.put("Housename",global_housename);
            post_dict.put("Roomname",rmitem_value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }
    JSONObject switch_upload(String switcname,String name_type) {
        JSONObject post_dict = new JSONObject();

        /* {"Username": "vinay","Housename":"vancon",
                                "Roomname":"Room1","Switch":"Switch11",
                                "Name":"Name1","Type":"Type1"}*/

        String[] animalsArray = name_type.split(",");
        try {

            post_dict.put("Username",global_username);
            post_dict.put("Housename",global_housename);
            post_dict.put("Roomname",rmitem_value);
            post_dict.put("Switch",switcname);
            post_dict.put("Name",animalsArray[1]);
            post_dict.put("Type",animalsArray[0]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }

}
