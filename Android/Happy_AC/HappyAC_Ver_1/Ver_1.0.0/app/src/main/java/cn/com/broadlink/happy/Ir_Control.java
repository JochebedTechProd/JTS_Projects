package cn.com.broadlink.happy;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;

import cn.com.broadlink.happy.model.DeviceInfo;
import cn.com.broadlink.happy.model.DevicePairInfo;
import cn.com.broadlink.happy.model.param.InitParam;
import cn.com.broadlink.happy.model.param.ProbeParam;
import cn.com.broadlink.happy.model.param.RMDescParam;
import cn.com.broadlink.happy.model.result.PairResult;
import cn.com.broadlink.smarthome.BLSmartHomeAPI;

import static cn.com.broadlink.happy.RoomActivity.ctrl_pairdval;
import static cn.com.broadlink.happy.RoomActivity.rmitem_value;
import static cn.com.broadlink.happy.RoomActivity.rname;

public class Ir_Control extends Activity {
    GridView rmlv;
    String paird_vaal="";


    private static final String TAG = "BROADLINK_SDK";
    private static final String LICENSE = "j2Z8lGo4H5QyY9QuLIvaArpJilB4niPX0VJ3/yJmg2uz9VLe0/3NG4ijLTtsr6cXvuoNVwAAAAC34dCX0yp96VBPcuFLL7wGcGROQUnBoZ+yWte4wQP2QBAiWsIC7HOxWMc5DPTJ6KB+CwHvkzI2xdh4hc7UtXUGsEkbxXTfoUSQjDzWcfVjcA4AAADyki8iea7A5SRnXUE3S9qWgOmS8UyHs5pISopUO3fLlWUU/3Xu40C//cc6tVOyI8bjRa8g9o3HM62zOdPHgG6/";
    static RequestQueue mRequestQueue;


    private BLSmartHomeAPI mNetworkAPI;
    private DeviceInfo mDeviceInfo;
    private DevicePairInfo mDevicePairInfo;
    public Adapter_GroupSwitchesList adapter3;
   static String rm_swtch[]={"260040001e1a39361e1a3a351e1a3a1a1e1a1d363a000b0a1e191e1a3a351f193a361e193a1a1e1a1e353b000b0a1e191f193a351f193a351f193a1a1e191f353a000d0500000000000000000000"
            ,"26000e010600033a070003390a0003390a0003380a0003380a0003380a0003390a0003380a0003380a0003380a0003390b0003370b0003370a00033a090003390a0003380b0003380a0003380900033b090003390a0003370a0003390a0003390a0003380b0003370b0003380900033a0b0003370b0003370a0003390a000339090003390a0003380900033a0900033a0a0003380a0003380a0003380900033b0a0003380a0003350e0003370800033c0b0009bb0b00033a08e31e1a3a191f351e1a3a353b343b191f191f341f0005e80c0003360b0001b81f193b191f341f193a343c353a1a1e191f351e0001ce0a00067d090003390c0003360a0009be0a0003370b00067b0a00067a0b0003390a000d0500000000000000000000"
            ,"26009200040003390a0003380900033a0b00033709000339090003390b0003380e0003340c0003360a0003390a0003390a00026e1e1a1e1a3a351e1a3a361e193a363a351e1a1e0001190c0003360c0003370b0003372b1a1e1a3a351e1a3a361d1a3a3639361e1a1e420900033a0c0003370b000337090003380b0003380a0003390a0003380a000338090003390c0003380b000d0500000000000000000000"
            ,"260048001f193a1a1e351f193a353a1a1e1a1e1a1e351e1a1e000aee1e193b191e351f193a353b191f191e1a1e351f191f000aed1e193b191f341f193b343b191f191f191e351f191f000d0500000000000000000000"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir__control);

        //init();

        mNetworkAPI = BLSmartHomeAPI.getInstanceBLNetwork(Ir_Control.this);
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




        creat_adater();

    }

    @Override
    protected void onResume() {
       /* Bundle extras = getIntent().getExtras();
        paird_vaal= extras.getString("PAIRED_CONTRL");*/
        super.onResume();
    }

    public void creat_adater(){

        if(rname.length==0){
            rname=new String[rm_swtch.length];
            for (int l = 0; l < rm_swtch.length; l++) {

                rname[l] = "L";

                Log.d("!....rname", rname[l]);

            }
        }
        init();
        adapter3 = new Adapter_GroupSwitchesList(getApplicationContext(),rname);
        rmlv.setAdapter(adapter3);
        rmlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  prestationEco str = (prestationEco)o; //As you are using Default String Adapter
                Toast.makeText(getBaseContext(),rm_swtch[position],Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(),paird_vaal,Toast.LENGTH_SHORT).show();
            }
        });
        rmlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int posi, long id) {



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

                        //以下信息由云端请求返回，现写死一个空调数据
                        descParam.getData().setPid("000000000000000000000000c3050100");
                        descParam.getData().setIrcodeid("2924");
                        descParam.getData().setTimestamp(1523347479);

                        descParam.getData().getIrcode().setCode("26002800161c1c1c38371c1c383738381c1b383838000b0c1e181f193a351e1a3a353a361e1a39363a000d0500000000000000000000");

                        // descParam.getData().getIrcode().setCode("260050000001259413121213121312131312121312381312123813371337133713371337131213371411131213121312133714111312131213371337143713371312123813371337120005190001264a12000d050000000000000000");

                        Log.d(TAG, "SDK Trace:" + "==========PARAM DESC:" + JSON.toJSONString(descParam) + "==========");
                        final String result = mNetworkAPI.deviceControl(action, JSON.toJSONString(descParam));

                        Log.d(TAG, "SDK Trace:" + result);

                        /*(Ir_Control.this).runOnUiThread(new Runnable() {
                            public void run() {
                                mShowMsg.setText(result);
                            }
                        });*/
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else if (mDevicePairInfo == null) {
                    Log.d(TAG, "SDK Trace:" + "==========NO DEVICE==========");
                    BLCommonUtils.toastShow(Ir_Control.this, "No controllable equipment" );
                    //return;
                }












                final int pos=posi;

                final Dialog dialog = new Dialog(Ir_Control.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custome_switches);

                final Button fan = (Button) dialog.findViewById(R.id.fan);
                final Button light = (Button) dialog.findViewById(R.id.lamp);

                final Button tv = (Button) dialog.findViewById(R.id.tv);

                final Button button1 = (Button) dialog.findViewById(R.id.button1);
                final Button button2 = (Button) dialog.findViewById(R.id.button2);
                fan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        rname[pos]="fan";

                        //  dialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    }
                });
                light.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        rname[pos]="light";

                        // dialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        rname[pos]="tv";

                        // dialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    }
                });

                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try{
                            //
                            adapter3.notifyDataSetChanged();
                            RoomActivity.saveArray(rname,rmitem_value,getApplicationContext());

                        }catch (NullPointerException |ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });



                button1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), spinner_item + " - " + edittext1.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();

                return true;
            }
        });


    }
    public void init(){
        rmlv = (GridView) findViewById(R.id.switches_list);
    }

}
