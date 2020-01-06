package cn.com.broadlink.happy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static cn.com.broadlink.happy.Login.global_housename;
import static cn.com.broadlink.happy.Login.global_username;

public class RoomActivity extends Activity {
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    protected static final String TAG = "NukeSSLCerts";
    String json_string;
    Boolean send=false;
    static boolean config_bul=false;
    // String TAG = "vancon";
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE};
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    // static RequestQueue mRequestQueue;
    GridView rmlv;
    static String cururl="";
    LinearLayout add_button,configer,createroom_initial,room,device,home;
    ArrayList Recycler_room;
    static String romname="",piredstring="";
    boolean homw_bool = false,device_bool=false,room_bool=false;
    public static ArrayList<String> roomlist;
    public Adapter_RoomList adapter3;
    static String rname[];
    static String rmitem_value="",ctrl_pairdval="";
    List<String> li;
    String roomname[];
    String paired_value[];
    ArrayList<String> valuese = new ArrayList<String>();
    ArrayList<String> pr_value = new ArrayList<String>();
    final static int REQUESTCODE = 1;
    ImageView roomicon,configericon,deviceicon,homeicon;
    TextView roomtext,configertext,devicetext,hometext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // setUserTypeOnButtonClick();

       /* roomname=new String[valuese.size()];
        for (int l = 0; l < valuese.size(); l++) {

            roomname[l] = valuese.get(l);
            Log.d("!....roomname", roomname[l]);

        }*/

        getUnsafeOkHttpClient();

        nuke();

        init();

        troom();

        paired_room();

        filesave2();

        retrive_filename();

        checkpermisions();
        Log.d("+++ caling url","------------------from start ");
        cururl="http://123.176.44.3:5902/VankonApp/get_rooms/";
        JSONObject rmdt = null;
        Log.d("+++ caling url",cururl);
        Log.d("im inside jsoncall", String.valueOf(roomdtails()));
        json_string=String.valueOf(roomdtails());
        // JSONSenderVolley(cururl, rmdt);
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(cururl);
        if(valuese.isEmpty()||pr_value.isEmpty()){
            createroom_initial.setVisibility(View.VISIBLE);
            send = false;

               /* Log.d("+++ caling url","------------------from start ");
                cururl="http://123.176.44.3:5902/VankonApp/get_rooms/";
                JSONObject rmdt = null;
                Log.d("+++ caling url",cururl);
                Log.d("im inside jsoncall", String.valueOf(roomdtails()));
                json_string=String.valueOf(roomdtails());
                // JSONSenderVolley(cururl, rmdt);
                OkHttpHandler okHttpHandler= new OkHttpHandler();
                okHttpHandler.execute(cururl);*/



        }else{
            initViews(valuese);
            adapter3 = new Adapter_RoomList(RoomActivity.this,valuese);
            rmlv.setAdapter(adapter3);

        }

        rmlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                rmitem_value = (String) rmlv.getItemAtPosition(position);
                saveme(rmitem_value);
                ctrl_pairdval=pr_value.get(position);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                // roomname=new String[svswnm.length];

            }
        });

       /* rmlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
               *//* pr_value.set(pos,"1");

                paired_value=new String[pr_value.size()];
                for (int l = 0; l < pr_value.size(); l++) {
                    paired_value[l] = pr_value.get(l);
                    Log.d("!....troom", paired_value[l]);
                }
                adapter3.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Paired to the ir blaster succsfully:"+paired_value[pos] , Toast.LENGTH_SHORT).show();
                MainActivity.saveArray(paired_value,"proom",getApplicationContext());*//*

                return true;
            }
        });*/

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  int sz=valuese.size()+1;
                valuese.add("room"+sz);
                pr_value.add("0");*/

                Intent i = new Intent(getApplicationContext(),EasyConfigActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        configer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  int sz=valuese.size()+1;
                valuese.add("room"+sz);
                pr_value.add("0");*/

                Intent i = new Intent(getApplicationContext(),EasyConfigActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homw_bool = !homw_bool;
                homeicon.setBackgroundResource(homw_bool ? R.mipmap.home_icon_active : R.mipmap.home_icon_inactive);

                if(homw_bool)
                    hometext.setTextColor(Color.parseColor("#F20000"));
                else
                    hometext.setTextColor(Color.parseColor("#000000"));

            }
        });
        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                device_bool = !device_bool;
                deviceicon.setBackgroundResource(device_bool ? R.mipmap.device_icon_active : R.mipmap.device_icon_inactive);
                if(device_bool)
                    devicetext.setTextColor(Color.parseColor("#F20000"));
                else
                    devicetext.setTextColor(Color.parseColor("#000000"));

            }
        });
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                room_bool = !room_bool;
                roomicon.setBackgroundResource(room_bool ? R.mipmap.rooms_icon_active : R.mipmap.rooms_icon_inactive);

                if(room_bool)
                    roomtext.setTextColor(Color.parseColor("#F20000"));
                else
                    roomtext.setTextColor(Color.parseColor("#000000"));


            }
        });




        createroom_initial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EasyConfigActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);


            }
        });
    }
    public void checkpermisions(){

        if(ActivityCompat.checkSelfPermission(RoomActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(RoomActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(RoomActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(RoomActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs storage and network permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(RoomActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            // txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }

    }

    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        Toast.makeText(getBaseContext(), "We got the  Permission", Toast.LENGTH_LONG).show();
    }

    private void initViews( ArrayList<String> recycler_value){
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        RecyclerView.Adapter adapter = new Recycler_adapter(recycler_value);
        recyclerView.setAdapter(adapter);
        if(!recycler_value.isEmpty())
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    Toast.makeText(getApplicationContext(), valuese.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    String base_encode(String value){

        String base64 = " ";

        try {

            byte[] data = value.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64;
    }
    String base_decode(String value){

        String text = " ";

        try {

            byte[] data = Base64.decode(value, Base64.DEFAULT);
            text = new String(data, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
    public void init(){

        add_button=(LinearLayout)findViewById(R.id.add);
        rmlv = (GridView) findViewById(R.id.switches_list);
        configer=(LinearLayout)findViewById(R.id.config);

        room=(LinearLayout)findViewById(R.id.room);
        device=(LinearLayout)findViewById(R.id.device);
        home=(LinearLayout)findViewById(R.id.home);

        createroom_initial=(LinearLayout)findViewById(R.id.crtroom);
        roomicon=(ImageView) findViewById(R.id.rm_icon);
        configericon=(ImageView)findViewById(R.id.confg_icon);
        deviceicon=(ImageView)findViewById(R.id.dev_icon);
        homeicon=(ImageView)findViewById(R.id.hm_icon);

        roomtext=(TextView) findViewById(R.id.rm_txt);
        configertext=(TextView)findViewById(R.id.confg_text);
        devicetext=(TextView)findViewById(R.id.dev_text);
        hometext=(TextView)findViewById(R.id.hm_txt);

    }
    public String[] retar(String arrayName, Context mContext) {
        Log.d("!==retar", arrayName);
        SharedPreferences prefs = mContext.getSharedPreferences("preference1", 0);
        int size = prefs.getInt(arrayName + "_size", 0);

        String array[] = new String[size];
        for(int i=0;i<size;i++){
            array[i] = prefs.getString(arrayName + "_" + i, null);
            Log.d("!==retar2", array[i]);
        }
        return array;
    }
    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == 2) {
             if (requestCode == REQUESTCODE) {

             }
         }
     }*/
    @Override
    protected void onResume() {
        /*String newString,roomnm_strng;
        Intent i = getIntent();*/

        try

        {
            Log.d("+++ checking rnm,paired",romname+"------"+piredstring);
            if(config_bul) {
                if (!romname.contentEquals("") && !piredstring.contentEquals("")) {


                    config_bul = false;
                    createroom_initial.setVisibility(View.GONE);

                    send = true;
                    cururl = "http://123.176.44.3:5902/VankonApp/add_rooms/";
                    JSONObject rmdt = null;
                    Log.d("+++ caling url", cururl);
                    piredstring=base_encode(piredstring);
                    json_string = String.valueOf(save_room(global_username, global_housename, romname, piredstring));
                    Log.d("im inside jsoncall", json_string);
                    // JSONSenderVolley(cururl, rmdt);
                    OkHttpHandler okHttpHandler = new OkHttpHandler();
                    okHttpHandler.execute(cururl);


//temporarily ristricting shared preference for time being

               /* valuese.add(romname);

                Log.d("pr_value_in config", String.valueOf(valuese));

                pr_value.add(piredstring);

                Log.d("pr_value_in config", String.valueOf(pr_value));

                Log.d("valuese_in main", String.valueOf(valuese));*/


                    // Log.d("pr_value_in main", String.valueOf(pr_value));

                /*adapter3 = new Adapter_RoomList(RoomActivity.this, valuese);

                rmlv.setAdapter(adapter3);*/

                /*roomname = new String[valuese.size()];

                for (int l = 0; l < valuese.size(); l++) {
                    roomname[l] = valuese.get(l);
                    Log.d("!....troom", roomname[l]);
                }

                paired_value = new String[pr_value.size()];

                for (int l = 0; l < pr_value.size(); l++) {
                    paired_value[l] = pr_value.get(l);
                    Log.d("!....troom", paired_value[l]);
                }

                RoomActivity.saveArray(roomname, "troom", getApplicationContext());

                RoomActivity.saveArray(paired_value, "proom", getApplicationContext());

                romname="";

                piredstring="";*/




                /*getIntent().removeExtra("STRING_I_NEED");

                getIntent().removeExtra("PAIRED_RNAME");*/
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        super.onResume();
    }
    public void saveme(String selectmulti ){
        Log.d(selectmulti,"started");
        li= new ArrayList<String>();
        try {
            if ((retar(selectmulti, getApplicationContext()).length <= 0)) {
                Log.d(selectmulti, "started");
                retar(selectmulti, getApplicationContext());
                Log.d("!==saveme", String.valueOf((retar(selectmulti, getApplicationContext())).length));
            } else {
                Log.d("!==selectmulti", "im in my else func");
                li.clear();
                // boolean[] cstate = new boolean[(loadArray("bolnarray",getApplicationContext())).length] ;
                for (String b : (retar(selectmulti, getApplicationContext()))) {
                    if (b != null && b.length() > 0)
                        li.add(b);
                    Log.d(selectmulti, String.valueOf(li));
                }
            }
            //  cswitchid = new String[newList3.size()];
            rname = new String[li.size()];
            for (int l = 0; l < li.size(); l++) {

                rname[l] = li.get(l);
                //  Logic_check();
                Log.d("!....rome names", String.valueOf(rname[l]));
            }



        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static boolean deletearray(String[] array, String arrayName, Context mContext) {

        SharedPreferences prefs = mContext.getSharedPreferences("preference1", 0);
        SharedPreferences.Editor editor = prefs.edit();
        try {

            for (int i = 0; i < array.length; i++) {
                Log.d("!==array", array[i]);
            }

            editor.remove(arrayName + "_size");
            for (int i = 0; i < array.length; i++)
                editor.remove(arrayName + "_" + i);
            Log.d("in svae", "saved");

        }catch (NullPointerException n){
            n.printStackTrace();
        }
        return editor.commit();
    }

    public static boolean saveArray(String[] array, String arrayName, Context mContext) {

        SharedPreferences prefs = mContext.getSharedPreferences("preference1", 0);
        SharedPreferences.Editor editor = prefs.edit();
        try {

            for (int i = 0; i < array.length; i++) {
                Log.d("!==array saved", array[i]);
            }

            editor.putInt(arrayName + "_size", array.length);
            for (int i = 0; i < array.length; i++)
                editor.putString(arrayName + "_" + i, array[i]);
            Log.d("in svae", "saved");

        }catch (NullPointerException n){
            n.printStackTrace();
        }
        return editor.commit();
    }

    public void troom(){
        Log.d("troom","troom");
        li= new ArrayList<String>();

        try{
            if((retar("troom",getApplicationContext()).length<=0)){
                Log.d("troom","started");

                retar("troom",getApplicationContext());
                Log.d("!==troom", String.valueOf((retar("troom",getApplicationContext())).length));
            }
            else{
                Log.d("!==troom","im in my else func");
                li.clear();
                // boolean[] cstate = new boolean[(loadArray("bolnarray",getApplicationContext())).length] ;
                for(String b:(retar("troom",getApplicationContext()))){
                    if(b != null && b.length() > 0)
                        li.add(b);



                    Log.d("!==troom", String.valueOf(li));
                }
            }

            valuese.clear();
            for(String s: li) {
                if (!s.trim().isEmpty()) {
                    valuese.add(s);
                    Log.d("!....troom array string", s);
                    Log.d("!....troom array string", valuese.toString());

                }
            }
            //  cswitchid = new String[newList3.size()];
           /* roomname=new String[li.size()];
            for (int l = 0; l < li.size(); l++) {

                roomname[l] = li.get(l);
                //  Logic_check();
                Log.d("!....troom array string", String.valueOf(roomname[l]));
            }
            Log.d("....svdrmli", String.valueOf(li));*/
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException t){
            t.printStackTrace();
        }
    }
    public void paired_room(){
        Log.d("proom","proom");
        li= new ArrayList<String>();

        try{
            if((retar("proom",getApplicationContext()).length<=0)){
                Log.d("proom","started");

                retar("proom",getApplicationContext());
                Log.d("!==proom", String.valueOf((retar("proom",getApplicationContext())).length));
            }
            else{
                Log.d("!==proom","im in my else func");
                li.clear();
                // boolean[] cstate = new boolean[(loadArray("bolnarray",getApplicationContext())).length] ;
                for(String b:(retar("proom",getApplicationContext()))){
                    if(b != null && b.length() > 0)
                        li.add(b);



                    Log.d("!==proom", String.valueOf(li));
                }
            }

            pr_value.clear();
            for(String s: li) {
                if (!s.trim().isEmpty()) {
                    pr_value.add(s);
                    Log.d("!....proom array string", s);
                    Log.d("!....proom array string", pr_value.toString());

                }
            }
            //  cswitchid = new String[newList3.size()];
           /* roomname=new String[li.size()];
            for (int l = 0; l < li.size(); l++) {

                roomname[l] = li.get(l);
                //  Logic_check();
                Log.d("!....troom array string", String.valueOf(roomname[l]));
            }
            Log.d("....svdrmli", String.valueOf(li));*/
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException t){
            t.printStackTrace();
        }
    }

    public void create(View view) {
        Intent i = new Intent(getApplicationContext(),EasyConfigActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(RoomActivity.this,permissionsRequired[2])){
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(RoomActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(RoomActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(RoomActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                //proceedAfterPermission();
            }
        }
    }

    protected static JSONObject roomdtails() {
        JSONObject post_dict = new JSONObject();


        try {
            post_dict.put("Username", global_username);
            post_dict.put("Housename", global_housename);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }
    protected static JSONObject save_room(String uname,String housename,String roomname,String room_pairedvalue) {

       /*  http://123.176.44.3:5902/VankonApp/add_rooms/
         {"Username": "vinay","Housename":"vancon","Roomname":"Room1","Roompairedvalue":"fan"}*/
        JSONObject post_dict = new JSONObject();


        try {
            post_dict.put("Username", uname);
            post_dict.put("Housename", housename);
            post_dict.put("Roomname", roomname);
            post_dict.put("Roompairedvalue", room_pairedvalue);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }



    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }




    public class OkHttpHandler extends AsyncTask {


        public  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String val;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)



        @Override
        protected Object doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, json_string);

            Request request = new Request.Builder()
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
                public void onResponse(Call call, Response response) throws IOException {
                    final String jsonData = response.body().string();
                    Log.d("+++++++TAG",jsonData);
                    /*response : {"error_code":"200","Response":"Successfully got 1 rooms ", "username":"vinay", "housename":"vancon",
                    "room_value":[{"room_name":"Room1","paired_value":"fan"}]
                    }*/

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            JSONObject jres_gs1 = null;
                            try {
                                jres_gs1 = new JSONObject(jsonData);
                                if(jres_gs1.getString("error_code").contentEquals("200")){
                                    String respo1=jres_gs1.getString("response");
                                    Toast.makeText(getApplicationContext(), respo1 , Toast.LENGTH_SHORT).show();

                                    Log.d("+++++++TAG",respo1);
                                    if(send)
                                    {
                                        send=false;
                                        cururl="http://123.176.44.3:5902/VankonApp/get_rooms/";
                                        JSONObject rmdt1 = null;
                                        Log.d("+++ caling url",cururl);
                                        Log.d("im inside jsoncall", String.valueOf(roomdtails()));
                                        json_string=String.valueOf(roomdtails());
                                        // JSONSenderVolley(cururl, rmdt);
                                        OkHttpHandler okHttpHandler= new OkHttpHandler();
                                        okHttpHandler.execute(cururl);
                                    }

                                }
                                if(jres_gs1.getString("error_code").contentEquals("500")){
                                    // send=false;
                                    String respo=jres_gs1.getString("error_desc");

                                    Toast.makeText(getApplicationContext(), respo , Toast.LENGTH_SHORT).show();}

                                //  Toast.makeText(getApplicationContext(), "cannot upload to server" , Toast.LENGTH_SHORT).show();}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });




                    try {
                        final ArrayList<String> roomnames_value = new ArrayList<String>();


                        JSONObject jres_gs = new JSONObject(jsonData);

                        // String respo=jres_gs.getString("Response");



                        JSONArray new_array2 = jres_gs.getJSONArray("room_value");
                        roomnames_value.clear();
                        valuese.clear();
                        pr_value.clear();
                        /*valuese.clear();
                        pr_value.clear();*/
                        for (int i = 0, count = new_array2.length(); i < count; i++) {
                            // roomnames_value.add(new_array2.getJSONObject(i).getString("room_name"));


                            valuese.add(new_array2.getJSONObject(i).getString("room_name"));
                            pr_value.add(base_decode(new_array2.getJSONObject(i).getString("paired_value")));




                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                /* adapter3 = new Adapter_RoomList(RoomActivity.this,valuese,pr_value);
                                    rmlv.setAdapter(adapter3);*/
                                createroom_initial.setVisibility(View.GONE);
                                initViews(valuese);


                                roomname = new String[valuese.size()];

                                for (int l = 0; l < valuese.size(); l++) {
                                    roomname[l] = valuese.get(l);
                                    Log.d("!....troom", roomname[l]);
                                }

                                paired_value = new String[pr_value.size()];

                                for (int l = 0; l < pr_value.size(); l++) {
                                    paired_value[l] = pr_value.get(l);
                                    Log.d("!....proom", paired_value[l]);
                                }
                                adapter3 = new Adapter_RoomList(RoomActivity.this, valuese);

                                rmlv.setAdapter(adapter3);
                                RoomActivity.saveArray(roomname, "troom", getApplicationContext());

                                RoomActivity.saveArray(paired_value, "proom", getApplicationContext());

                                romname="";

                                piredstring="";

                            }
                        });


                    }catch(JSONException e){
                        e.printStackTrace();
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

    void filesave2(){
        try {
            FileOutputStream mOutput = openFileOutput(FILENAME, Activity.MODE_PRIVATE);
            String data = "THIS DATA WRITTEN TO A FILE";
            mOutput.write(data.getBytes());
            mOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void retrive_filename(){
        try {
            FileInputStream mInput = openFileInput(FILENAME);
            byte[] data = new byte[128];
            mInput.read(data);
            mInput.close();

            String display = new String(data);
            Toast.makeText(getApplicationContext(), display , Toast.LENGTH_SHORT).show();

            // tv.setText(display.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void filesave() throws FileNotFoundException {
        File file = null;
        FileOutputStream fos = null;
        final String str_path = Environment.getExternalStorageDirectory().toString() + "/CogentIBS";
        File dir=new File(str_path);
        if(!dir.exists())dir.mkdir();
        if(dir.exists()) {
            file = new File(str_path, "sample.txt");
            file.delete();
            fos = new FileOutputStream(file);

            // wb.write(fos);
            //wb1.write(fos);

            /*final File finalFile = file;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!str_path.equals(""))
                        FileUtils.openExcelFile(finalFile.getAbsolutePath(), getBaseContext());
                    hideProgressDialog();

                }
            });*/

        }
    }
    void file_retrive(){
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        File file = new File(sdcard,"file.txt");

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
    }
}
