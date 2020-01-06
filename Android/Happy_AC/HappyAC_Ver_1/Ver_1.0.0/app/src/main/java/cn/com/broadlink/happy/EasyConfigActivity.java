package cn.com.broadlink.happy;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cn.com.broadlink.happy.model.DeviceInfo;
import cn.com.broadlink.happy.model.DevicePairInfo;
import cn.com.broadlink.happy.model.param.ConfigParam;
import cn.com.broadlink.happy.model.param.PairDescParam;
import cn.com.broadlink.happy.model.param.ProbeParam;
import cn.com.broadlink.happy.model.result.ConfigResult;
import cn.com.broadlink.happy.model.result.PairResult;
import cn.com.broadlink.happy.model.result.ProbeResult;
import cn.com.broadlink.smarthome.BLSmartHomeAPI;

import static cn.com.broadlink.happy.RoomActivity.config_bul;
import static cn.com.broadlink.happy.RoomActivity.piredstring;
import static cn.com.broadlink.happy.RoomActivity.romname;

public class EasyConfigActivity extends Activity {
    private static final String TAG = "BROADLINK_SDK";

    private EditText mEcSsidText;
    private EditText mEcPasswordText;
    private TextView mEcStartConfig,testpopup;
    private TextView mEcCancelConfig;
    private DevicePairInfo mDevicePairInfo;
    private ProgressDialog progressDialog;
    private BLSmartHomeAPI mNetworkAPI;
    private String mDevAddr = "";
    private boolean isFound = false;
    static String saved_prob="";
    private final static int REQUESTCODE = 1; // 返回的结果码
    static String cururl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_config);
        //getUnsafeOkHttpClient();
        findView();
        init();
        setListener();



    }

    private void findView() {

        mEcSsidText = (EditText) findViewById(R.id.ec_ssid);
        mEcPasswordText = (EditText) findViewById(R.id.ec_password);
        mEcStartConfig = (TextView) findViewById(R.id.ec_config);
        mEcCancelConfig = (TextView) findViewById(R.id.ec_cancel_config);
       // testpopup= (TextView) findViewById(R.id.ec_config);
    }

    private void init() {

        mNetworkAPI = BLSmartHomeAPI.getInstanceBLNetwork(EasyConfigActivity.this);
        String ssid = BLCommonUtils.getWIFISSID(EasyConfigActivity.this);
        mEcSsidText.setText(ssid.toCharArray(), 0, ssid.length());
        mEcPasswordText.setFocusable(true);
        mEcPasswordText.setFocusableInTouchMode(true);
        mEcPasswordText.requestFocus();
       // mEcPasswordText.setText("1234567890");
        /*rmName.setFocusable(true);
        rmName.setFocusableInTouchMode(true);
        mEcPasswordText.requestFocus();*/

    }
    protected static org.json.JSONObject roomdtails() {
        org.json.JSONObject post_dict = new JSONObject();

        /*{"Username": "vinay","Housename":"vancon"}*/
        try {
            post_dict.put("Username", "vinay");
            post_dict.put("Housename", "vancon");

//http://123.176.44.3:5902/VankonApp/get_rooms/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }


    private void setListener() {
        /**
         * 将设备复位后通过此方法将设备配置到相应地路由器上
         * 方法是个耗时操作，需要放到Thread中执行（请勿使用AsyncTask，部分手机会导致配置不上）
         */



// needed to uncoment the cloud configure code
       mEcStartConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(EasyConfigActivity.this);
                progressDialog.setMessage("\n" +
                        "Configuration...");
                progressDialog.show();
// generate random dummy string withouth ir blaster connectivity-----------:)
              /*  final Dialog dialog = new Dialog(EasyConfigActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.addroom);

                final Button button2 = (Button) dialog.findViewById(R.id.button2);
                final Spinner rmName = (Spinner)dialog.findViewById(R.id.rm_name);

                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {



                        if(!rmName.getSelectedItem().toString().contentEquals("")) {
                            config_bul=true;
                            // mDevicePairInfo = pairResult.getDevicePairedInfo();
                            romname=rmName.getSelectedItem().toString();

                            piredstring=random();
                            Log.d("PAIRED_VALUE:--",piredstring);
                            Log.d("PAIRED_VALUE:--", rmName.getSelectedItem().toString());
                            Intent i = new Intent(getApplicationContext(), RoomActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mEcStartConfig.setEnabled(false);
                            dialog.dismiss();
                            startActivity(i);
                            finish();

                        }
                        else {
                            Toast.makeText(getBaseContext(),"please enter room",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                dialog.show();

            }*/
    // starting from here to
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 部分android手机默认停止了组播，需要使用以下办法开启
                        Log.d(TAG, "SDK Trace:" + "==========SDK CONFIG==========");
                        ConfigParam param = new ConfigParam();
                        param.setSsid(mEcSsidText.getText().toString());
                        param.setPassword(mEcPasswordText.getText().toString());
                        param.setTimeout(60);
                        param.setCfgversion(2);
                        Log.d(TAG, "SDK Trace:" + "==========PARAM:" + JSON.toJSONString(param) + "==========");
                        String action = "ConfigStart";
                        String result = mNetworkAPI.deviceEasyConfig(action, JSON.toJSONString(param));

                        Log.d(TAG, "SDK Trace:" + "==========SDK CONFIG RESULT:" + result + "==========");
                        if (result != null) {
                            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
                            if (configResult.getStatus() == 0) {
                                mDevAddr = configResult.getDevaddr() + "@80";
                            }
                        }
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DeviceInfo configDevice = null;
                        int i;
                        isFound = false;
                        for (i = 0; i < 60; i++) {
                            //自动搜索设备50次，若没找到设备则认为搜索失败
                            ProbeParam probeParam = new ProbeParam();
                            probeParam.setScantime(2000); // 扫描时间
                            probeParam.setVersion(1);
                            String deviceProbe = "DeviceProbe";
                            String probeRet = mNetworkAPI.deviceProbe(deviceProbe, JSON.toJSONString(probeParam));
                            Log.d(TAG, "SDK Trace:" + "==========SDK PROBE " + String.valueOf(i) + " RESULT:" + probeRet + "==========");
                            Log.d("vinay--", "SDK Trace:" + "==========SDK PROBE " + String.valueOf(i) + " RESULT:" + probeRet + "==========");

                            if (!TextUtils.isEmpty(probeRet)) {
                                ProbeResult probeResult = JSON.parseObject(probeRet, ProbeResult.class);
                                if (probeResult.getStatus() == 0 && probeResult.getList() != null) {

                                    for (DeviceInfo device : probeResult.getList()) {

                                        if (device.isNewconfig() || device.getLanaddr().equalsIgnoreCase(mDevAddr)) {
                                            configDevice = device;
                                            isFound = true;
                                            saved_prob = probeRet;
                                            break;
                                        }
                                    }
                                    if (isFound)
                                        break;
                                }
                            }
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        if (configDevice != null) {
                            BLCommonUtils.toastShow(EasyConfigActivity.this, "\n" +
                                    "Device configuration succeeded！Mac：" + configDevice.getMac());

                            final DeviceInfo deviceInfo = configDevice;
                            EasyConfigActivity.this.runOnUiThread(new Runnable() {
                                public void run() {


                                    PairDescParam descParam = new PairDescParam();
                                    descParam.setDeviceInfo(deviceInfo);
                                    String action = "DevicePair";
                                    final String result = mNetworkAPI.devicePair(action, JSON.toJSONString(descParam));
                                    Log.d("paired_result:--", result);
                                    if (!TextUtils.isEmpty(result)) {
                                        // save_shared(result);
                                        PairResult pairResult = JSON.parseObject(result, PairResult.class);
                                        if (pairResult.getStatus() == 0) {
                                            final Dialog dialog = new Dialog(EasyConfigActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setContentView(R.layout.addroom);

                                            final Button button2 = (Button) dialog.findViewById(R.id.button2);
                                            final EditText rmName = (EditText) dialog.findViewById(R.id.rm_name);

                                            button2.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    if (!rmName.getText().toString().contentEquals("")) {
                                                        // mDevicePairInfo = pairResult.getDevicePairedInfo();
                                                        romname = rmName.getText().toString();
                                                        config_bul = true   ;
                                                        piredstring = result;
                                                        Log.d("PAIRED_VALUE:--", result);
                                                        Log.d("roomname:--", romname);
                                                        Intent i = new Intent(getApplicationContext(), RoomActivity.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        mEcStartConfig.setEnabled(false);
                                                        dialog.dismiss();
                                                        startActivity(i);
                                                        finish();

                                                    } else {
                                                        Toast.makeText(getBaseContext(), "please enter room", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });


                                            dialog.show();

                                        }
                                    }
                                }
                            });
                        } else {
                            BLCommonUtils.toastShow(EasyConfigActivity.this, "No newly configured devices found");
                        }
                    }
                }).start();
            }

                // till here needed to comment
        });
        mEcCancelConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFound = true;
                Log.d(TAG, "SDK Trace:" + "==========SDK Cancel CONFIG==========");
                String action = "ConfigCancel";
                String result = mNetworkAPI.deviceEasyConfig(action, "{}");
                Log.d(TAG, "SDK Trace:" + "==========SDK Cancel CONFIG RESULT:" + result + "==========");
                BLCommonUtils.toastShow(EasyConfigActivity.this, "\n" +
                        "Cancel the distribution network！" );
            }
        });

    }

    public static String random() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output;
    }


   /* public class OkHttpHandler extends AsyncTask {


        public  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String val;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)



        @Override
        protected Object doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, String.valueOf(roomdtails()));

            Request request = new Request.Builder()
                    .url(cururl)
                    .post(body)
                    .build();


           *//* Request request = new Request.Builder()
                    .url(strings[0])
                    .build();*//*


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("+++++++TAG",response.body().string());
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
*/
}
