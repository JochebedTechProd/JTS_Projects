package cn.com.broadlink.happy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.com.broadlink.happy.model.DeviceInfo;
import cn.com.broadlink.happy.model.DevicePairInfo;
import cn.com.broadlink.happy.model.param.InitParam;
import cn.com.broadlink.happy.model.param.PairDescParam;
import cn.com.broadlink.happy.model.param.ProbeParam;
import cn.com.broadlink.happy.model.param.RMDescParam;
import cn.com.broadlink.happy.model.result.PairResult;
import cn.com.broadlink.smarthome.BLSmartHomeAPI;

public class Main2Activity extends Activity {
    private static final String TAG = "BROADLINK_SDK";
    private static final String LICENSE = "j2Z8lGo4H5QyY9QuLIvaArpJilB4niPX0VJ3/yJmg2uz9VLe0/3NG4ijLTtsr6cXvuoNVwAAAAC34dCX0yp96VBPcuFLL7wGcGROQUnBoZ+yWte4wQP2QBAiWsIC7HOxWMc5DPTJ6KB+CwHvkzI2xdh4hc7UtXUGsEkbxXTfoUSQjDzWcfVjcA4AAADyki8iea7A5SRnXUE3S9qWgOmS8UyHs5pISopUO3fLlWUU/3Xu40C//cc6tVOyI8bjRa8g9o3HM62zOdPHgG6/";
    static RequestQueue mRequestQueue;
    private EditText mShowMsg;
    private TextView mTvConfig,scn1,scn2,scn3,scn4,scn5,scn6,scn7,scn8,allon,alloff,l1,l2,l3,l4;
    private TextView mTvPair,it_learn;
    private TextView device_cnted,getjust_learnd,controlBtn;
    public static final String MY_PREFS_NAME = "device_paired_result";
    private BLSmartHomeAPI mNetworkAPI;
    private DeviceInfo mDeviceInfo;
    private DevicePairInfo mDevicePairInfo;
    boolean getir_just_lerned=false,get_learnrespo=false,controll_panel=false;
    private final static int REQUESTCODE = 1; // 返回的结果码
    String mac_str="",ip_str="",pid_str="",did_str = "",to_get_ir_info="",cookie="",decoded_cookie="",cookie_conrol_panel="", base64="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findView();
        init();
        setListener();







    }

    @Override
    protected void onResume() {
        init();
        setListener();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                Bundle bundle= data.getExtras();
                mDeviceInfo = (DeviceInfo) bundle.getSerializable("deviceInfo");
                Log.d("vinay----debug", mDeviceInfo.getMac());
            }
        }
    }

    private void findView() {
        mShowMsg = (EditText) findViewById(R.id.tv_show);
        device_cnted=(TextView)findViewById(R.id.tv_probe);
        mTvConfig = (TextView) findViewById(R.id.tv_config);
        mTvPair = (TextView) findViewById(R.id.tv_pair);

        it_learn = (TextView) findViewById(R.id.learn_ir);
        getjust_learnd=(TextView)findViewById(R.id.get_ir);
        controlBtn =(TextView)findViewById(R.id.control_id);


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

    }
    public void save_shared(String data){


        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("name", data);
        editor.commit();
        BLCommonUtils.toastShow(Main2Activity.this, "\n" +
                data );
        //editor.putInt("idName", 12);

    }

    public String retrive_shared(){
        String name ="";
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = (shared.getString("name", ""));
        BLCommonUtils.toastShow(Main2Activity.this, "\n" +
                name );
        return name;
    }
    private void init()  {
        String mDevAddr = "";





        mNetworkAPI = BLSmartHomeAPI.getInstanceBLNetwork(Main2Activity.this);
        InitParam param = new InitParam();
        param.setPackageName("cn.com.broadlink.SDKDemo");
        param.setLicense(LICENSE);
        param.setLoglevel(4);
        Log.d(TAG, "vinay_____+++++SDK Trace:" + "==========PARAM:" + JSON.toJSONString(param) + "==========");
        Log.d(TAG, "SDK Trace:" + "==========PARAM:" + JSON.toJSONString(param) + "==========");
        String result = mNetworkAPI.sdkInit(JSON.toJSONString(param));
        Log.d(TAG, "SDK Trace:" + "==========SDK INIT RESULT:" + result + "==========");
        Log.d("vinay----debug", "SDK Trace:" + "==========SDK INIT RESULT:" + result + "==========");

        ProbeParam probeParam = new ProbeParam();
        probeParam.setScantime(2000); // 扫描时间
        probeParam.setVersion(1);
        String deviceProbe = "DeviceProbe";
        String probeRet = mNetworkAPI.deviceProbe(deviceProbe, JSON.toJSONString(probeParam));
        Log.d(TAG, "SDK Trace:" + "==========SDK PROBE ******vinay debug*** " + " RESULT:" + probeRet + "==========");
        try {

            JSONObject myjson = new JSONObject(probeRet);
            JSONArray new_array = myjson.getJSONArray("list");
            for (int i = 0, count = new_array.length(); i < count; i++) {
                Log.d("vinay----debug", new_array.getJSONObject(i).getString("mac")+'\n' + new_array.getJSONObject(i).getString("mac"));

                device_cnted.setText("mac addr: "+new_array.getJSONObject(i).getString("mac")
                        + '\n' + "lan addrs: "+new_array.getJSONObject(i).getString("lanaddr")
                        + '\n' + "password: "+new_array.getJSONObject(i).getString("password"));

                mac_str=new_array.getJSONObject(i).getString("mac");
                ip_str=new_array.getJSONObject(i).getString("lanaddr");
                pid_str=new_array.getJSONObject(i).getString("pid");
                did_str=new_array.getJSONObject(i).getString("did");

            }



        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void ger_ir_code(String ir_config){

        get_learnrespo=false;





                            /*{
                        "context": {},
                        "event": {
                            "header": {
                                "namespace": "DNA.RMControl",
                                "name": "Response",
                                "interfaceVersion": "2",
                                "messageId": "1bd5d003-31b9-476f-ad03-71d471922820"
                            },
                            "payload": {
                                "code": "26004200191b391a1d371d1b38371d1b3a191e1a1f353a000b091d1a391b1d361d1b39371d1a391b1d1b1d3639000b0b1c1b391b1d361d1b39361d1b391b1d1b1d3639000d0500000000000000000000"
                            },
                            "endpoint": {
                                "scope": {},
                                "endpointId": "00000000000000000000c8f742631c5c"
                            }
                        }
                    }*/

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

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        // progressDialog.dismiss();
                        //String tag_json_arry = "json_req";
                        //  getInstance().getRequestQueue().cancelAll("feed_request");

                        Log.d("response------",response.toString());

                        if(get_learnrespo){
                            get_learnrespo=false;
                            ger_ir_code(response.toString());
                        }
                        //getir_enable=false,get_learnrespo=false,controll_panel=false
                   /*     if(getir_just_lerned){



                            getir_just_lerned=false;
                        }*/

                        if(controll_panel){
                            Log.d("controlpanel oprtd--",response.toString());

                        }

                        mRequestQueue.cancelAll(json);
                        //msgResponse.setText(response.toString());
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

        // Adding request to request queue
        // jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        mRequestQueue.add(req);
//        getRequestQueue().add(req);
    }

    private void setListener() {

        /**
         * 将设备复位后通过此方法将设备配置到相应地路由器上
         * 方法是个耗时操作，需要放到Thread中执行（请勿使用AsyncTask，部分手机会导致配置不上）
         */
        mTvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, EasyConfigActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        /**
         * 对设备进行配对
         */


       /* try{
            String receid=retrive_shared();
            BLCommonUtils.toastShow(Main2Activity.this, "\n" +
                    receid );
            if(receid!=null){
            PairResult pairResult = JSON.parseObject(receid, PairResult.class);
            if (pairResult.getStatus() == 0) {
                mShowMsg.setText(" vinay paired");

                mDevicePairInfo = pairResult.getDevicePairedInfo();
                mTvPair.setBackgroundResource(R.drawable.btn);

                try{
                JSONObject myjson = new JSONObject(receid);

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

                // Sending side
                                  *//*  byte[] data = text.getBytes("UTF-8");
                                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);*//*

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



            }}
        }catch (NullPointerException e){
            e.printStackTrace();

        }*/


        mTvPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "SDK Trace:" + "==========SDK PAIR==========");
                        if (mDeviceInfo == null) {
                            Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                            BLCommonUtils.toastShow(Main2Activity.this, "\n" +
                                    "No devices to pair with" );
                            return;
                        }

                        DeviceInfo info = mDeviceInfo;
                        PairDescParam descParam = new PairDescParam();
                        descParam.setDeviceInfo(info);

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        String action = "DevicePair";
                        final String result = mNetworkAPI.devicePair(action, JSON.toJSONString(descParam));

                        if (!TextUtils.isEmpty(result)) {
                            save_shared(result);
                            PairResult pairResult = JSON.parseObject(result, PairResult.class);
                            if (pairResult.getStatus() == 0) {
                                mDevicePairInfo = pairResult.getDevicePairedInfo();
                            }
                        }


                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                                Log.d("---vinay_result", "Json_string======++++++:" + result);
                                try {
                                    JSONObject myjson = new JSONObject(result);

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

                                    // Sending side
                                  /*  byte[] data = text.getBytes("UTF-8");
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


                            }
                        });

                    }
                }).start();


            }
        });

        /**
         * 对设备进行控制，测试红外码是否生效
         */

        it_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



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
        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String json="{" +
                            "\"directive\":{" +
                            "\"header\":{"+"\"namespace\":\"DNA.CustomFunctionControl\","+"\"name\":\"IrcodeFunction\","+"\"interfaceVersion\":\"2\","+"\"messageId\":\"1bd5d003-31b9-476f-ad03-71d471922820\"" + "},"+
                            "\"endpoint\":{" + "\"devicePairedInfo\":{\"did\":\""+did_str+"\",\"pid\":\""+"000000000000000000000000c2050100"+"\",\"mac\":\""+mac_str+"\",\"cookie\":\""+base64+"\"},"+"\"endpointId\":\""+did_str+"\"," + "\"cookie\":{}" + "}," +
                            "\"payload\":{"+"\"function\":\"ON\"}"+
                            "}" +
                            "}";

                    Log.d("vinay_debug", "control code===========:" + json);

                    JSONObject json_srt_con= null;
                    json_srt_con = new JSONObject(json);
                    String url="http://101.124.0.208:12347/openproxy/v2/opencontrol?license=LdsII6Ju9tc2Ba1Ab7OPeZYoKoD8kZyE89A8wE5XUYmbx5g26bA3cokOYtiAvicw1xMyWwAAAABS37aO9pqhR/DcVUjs5QpGwZuCHRzCqgH8Jw9vNQIFcDh+6HNYuUV8Gf0B/2hunfyy05qFEfP+0jNRq8V0KCkLgu17BymmnhQy5oxtKs2CmwAAAAA=";
                    //getir_just_lerned=true;
                    JSONSenderVolley(url,json_srt_con);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        scn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("26002800161c1c1c38371c1c383738381c1b383838000b0c1e181f193a351e1a3a353a361e1a39363a000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        scn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("26002c001a193b191f3420183a363a351e1a3a1a1d361e000aef1b1b381c1c381c1b3a3639361c1c3b191c371d000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });
        scn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("2600e6010a0003370b0003380a0003390b0003370a0003380900033a0900033a0b0003370b0003370b0003380a0003390a0003380b0003370b0003380900033a0a0003380b0003370b0003380a000339090003390a0003380b0003380a0003390a000338090003390b0003380a00033a0a0003370b0003370a0003390900033c090003370a0003380b0003380a00033a0a0003370b0003370a0003390900033b080003390b0003370b0003380a00033a09000339090003390a0003380b00033a09000338090003390900033a0a0003390b0006790b0003380900033b0a0003380a0003380b0003370a00033b080003390a0003380a0003390900033a090003390a0003380afd1d1a1d1b3a361d1a3a363a351e1a3a1a1e191f0002a80b0003370c0003370a0001d41c1c1c1c37381c1c383738381c1c381b1d1b1c0001d30d0003350a0003380a0002ac1c1c1c1c38371c1c383738381c1c371c1c1c1ef90d0003350a0003370900033a0900033a0a0003380a0003380f0003330a000339090003390a0003380900033a0900033a0a000338090003390b0003380900033a0a0003360b0003390900033a0900033a0a000338090003390a0003380b000338090003390b0003380b0003370900033b0a000338090003390b0003370800033b0b0003380b0003370d000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        scn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("2600ca020500033b080003390900033a0800033a0b0003370b0003380900033c0b0009bb0800033b0a0003380a0003380a0003380900033b0a0003370c0003360b0003380a000339090003390a0003380a0003380b0003380b0003370b0003370900033a0a0003390a0003380a0003380a0003380a00033b080003380b0003370b0003370a00033a0a0003370a0003380b0003380900033a0a00033809000339090003390a000339090003390b0003370b0003380a00033a090003380a000338090003390b0003390a0003370b0003370b0003370b0003390a0003380a0003370b0003380a00033a090003380a0003380a0003380b0003390a0003370a0003380a0003380b0003390a000338090003390a0003380a0002461f193a191f3520173b353a353b351e191f191f000142090003390a0003390900031e1e193b191f351e193b353a353b351e191f191f6b09000339090003390a00033a0ab11e193b191e361e193a363a353a361e191f191e0002d60b0003380a0003390a0003380a0003380a0003380a00033a0a0003380a0003380a0003380900033a0b000337090003390b0003380a0003390a0003380a0003380a0003380a00033b090003370b0003380a0003380b0003390a0003370a0003380a0003390a0003390a0003380b0003370b0003380a0003390a0003380a0003380a0003380a00033a0a0003380a0003380a0003380a0003390b0003370b0003370a0003390a0003390a000338090003390a0003380b0003390a0003380a0003380a0003380a00033a0a0003380a0003380a0003390a0003390a000338090003390a0003380a00033b090003380a0003380a0003380a00033a0a0003370b0003370b0003380a00033a0b0003360b0009bf0d0006750800033b0900033b0c000335090003390c0003370b0003390800033a0a0003370a0003390a0003390a0003380a0003380a0003380a0003390b0003370b0003370a0003390a000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });

        scn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("26003c001d1a1d1a38381c1c3738383738381c1c37000b091c1b1f1938371f193936393738371f1938000b091e191e193b351e193b353a353b351e1a3a000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        scn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("26003c001d193b191e351f193a353b353a353b191f000b051f193a191f351f193a353a353b353a191f000b081d193b191f351e193b353a353b353a191f000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });
        scn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("260040001e1a3a351e1a3a353a363a1a1e351e1a1e000aef1c1b1d1b3a351d1b3a353937391b1d361d1b1d000aef1c1b1c1c38381c1b383838373a1a1e351f191f000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        scn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("26003c001a193a1a1e351f193a353a363a1a1e353a000b0a1d1a3a191f351e1a3a353a353b191e363a000b091d1a3a1a1e351e1a3a353a363a191f353a000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });
        allon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("26001a01050009c10b000d020a0006770900067d0b0003370a00033a0c0003340b0003390a0003380b00067a0cae1f191f193b341f193b353a191f351f193a351f0002d7110003320c0003360b0001881f191f193a351f193b353a191f351f193a351f0001ff0b0003370a0003390800033b0b0003370a0003390d0003360b00033809000339090003390c0003360800033c090003390a0003390d0003350900067d0b0003370b0003390b0003380a0003370c0003360b0003380c0003390a00067a090003390a00033a090003380b0003370c0003380b0003380a0003380b0003370900067e0a0003370800067d0b0003380c0003370b00067b0e0009b90a00033a0b0003380a0003390a00067b0600033f06000d02090009be0b000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        alloff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("260002020700033b06000339090003390a0003380a00033a090003380c000336090003390a00033b08000339090003390a00033810000334090003380a0003380900033a0900033b090003380b0003370c0003360b000339090003380a0003380800033b0c0003370a0003380a0003380a0003380a0003390c0003360a0003380a0003390a0003390a0003380b0003370b0003380b000338090003390c0003360b00026f1f193a1a1e351f193a353b191f341f193b191f000134090003390a000339100003312e193b191f351e193b353a1a1e351f193a1a1e5d0c0003360b0003360a0003390bdc1f193a191f351f193a353b191f341f193b191e0002c70c0003360c0003370a0003390800033a10000332090003390d0003370a000337090003390a0003390b0003380a000338090003390a0003380b0003380b0003380a0003370a0003390a0003390900033a08000339090003390b0003390a0003380a0003370b0003370b0003390b000337090003390a0003380b0003390a0003380a0003370b0003380c0003370900033a0800033a0c0003360a0003390c0003360a000338090003390b0003380c00033609000339090003390d0003370a0003370a0003380b0003380a0003390a0003380b0003370b0003370900033a090003390b0003370a0003380a000339090003390c0003360a0003380b0003390b0006790a0003370d000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("260040001e1a39361e1a3a351e1a3a1a1e1a1d363a000b0a1e191e1a3a351f193a361e193a1a1e1a1e353b000b0a1e191f193a351f193a351f193a1a1e191f353a000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("26000e010600033a070003390a0003390a0003380a0003380a0003380a0003390a0003380a0003380a0003380a0003390b0003370b0003370a00033a090003390a0003380b0003380a0003380900033b090003390a0003370a0003390a0003390a0003380b0003370b0003380900033a0b0003370b0003370a0003390a000339090003390a0003380900033a0900033a0a0003380a0003380a0003380900033b0a0003380a0003350e0003370800033c0b0009bb0b00033a08e31e1a3a191f351e1a3a353b343b191f191f341f0005e80c0003360b0001b81f193b191f341f193a343c353a1a1e191f351e0001ce0a00067d090003390c0003360a0009be0a0003370b00067b0a00067a0b0003390a000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");




                if(mDevicePairInfo!=null){
                    try {


                        String action = "RM_Panel_Test";
                        RMDescParam descParam = new RMDescParam();
                        descParam.setDevicePairedInfo(mDevicePairInfo);

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("26009200040003390a0003380900033a0b00033709000339090003390b0003380e0003340c0003360a0003390a0003390a00026e1e1a1e1a3a351e1a3a361e193a363a351e1a1e0001190c0003360c0003370b0003372b1a1e1a3a351e1a3a361d1a3a3639361e1a1e420900033a0c0003370b000337090003380b0003380a0003390a0003380a000338090003390c0003380b000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        (Main2Activity.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }



            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SDK Trace:" + "==========SDK RM Test==========");
                if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Main2Activity.this, "No controllable equipment" );
                    return;
                }


                String action = "RM_Panel_Test";
                RMDescParam descParam = new RMDescParam();
                descParam.setDevicePairedInfo(mDevicePairInfo);

                //以下信息由云端请求返回，现写死一个空调数据
                descParam.getData().setPid("000000000000000000000000c3050100");
                descParam.getData().setIrcodeid("2924");
                descParam.getData().setTimestamp(1523347479);

                descParam.getData().getIrcode().setCode("260048001f193a1a1e351f193a353a1a1e1a1e1a1e351e1a1e000aee1e193b191e351f193a353b191f191e1a1e351f191f000aed1e193b191f341f193b343b191f191f191e351f191f000d0500000000000000000000");

                // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                Log.d(TAG, "SDK Trace:" + result);

                (Main2Activity.this).runOnUiThread(new Runnable() {
                    public void run() {
                        mShowMsg.setText(result);
                    }
                });
            }
        });
    }



    /*public String json_data(){
        try {
            JSONObject parent = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            JSONObject directive_json = new JSONObject();

            JSONObject endpoint_json = new JSONObject();
            JSONObject devicepaired_json = new JSONObject();
            JSONObject cookie = new JSONObject();

            JSONObject header_json = new JSONObject();

            directive_json.put("header", header_json);

           // jsonObject.put("mk1", "mv1");
            jsonObject.put("directive", directive_json);
            parent.put("k2", jsonObject);
            Log.d("output", parent.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

}
