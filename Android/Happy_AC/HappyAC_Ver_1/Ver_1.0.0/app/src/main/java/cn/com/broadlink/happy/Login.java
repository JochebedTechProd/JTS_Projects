package cn.com.broadlink.happy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends Activity {
    String cururl="",log_uname,log_pwd,json_string;
    static String global_username,global_housename,s_username,s_pwd,s_housenm,s_phone;
    Button login,Regster;
    LinearLayout login_age,register_page,signup_feature;
    EditText username,housename,password1,password2,phonenumber,login_username,login_pwd,login_hn;
    boolean login_check=false;
    SharedPreferences sharedpreferences;
    ACProgressFlower dialog;
    public static final String mypreference = "mypref";
    public static final String Name = "name_pwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getUnsafeOkHttpClient();
        init();
        listner();
    }
    protected static JSONObject roomdtails(String uname,String pwd,String hno) {
        JSONObject post_dict = new JSONObject();
/*{
  "Username": "vinay11",
  "Housename": "vancon11",
  "Password": "password1231",
}*/
        try {
            post_dict.put("Username", uname);
            post_dict.put("Housename", hno);
            post_dict.put("Password", pwd);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }
    protected static JSONObject register_json() {
        JSONObject post_dict = new JSONObject();

        try {
                 /*{
                      "Username": "vinay",
                      "Housename": "vancon",
                      "Password": "password123",
                      "Phonenumber": "7393273237239"
                    }*/
            post_dict.put("Username",s_username );
            post_dict.put("Housename", s_housenm);
            post_dict.put("Password", s_pwd);
            post_dict.put("Phonenumber", s_phone);
           // post_dict.put("Pairedvalue", "pv123");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post_dict;
    }

   public void init(){

       login_username=(EditText) findViewById(R.id.logunid);
       login_pwd=(EditText) findViewById(R.id.pwd_id);
       login_hn=(EditText) findViewById(R.id.hnid);
       login =(Button)findViewById(R.id.loginid);
       Regster =(Button)findViewById(R.id.registerid);
       signup_feature =(LinearLayout) findViewById(R.id.signupid);
       login_age =(LinearLayout) findViewById(R.id.logid);
       register_page=(LinearLayout) findViewById(R.id.regid);
       username=(EditText) findViewById(R.id.ruserid);
       housename=(EditText) findViewById(R.id.rhouseid);
       password1=(EditText) findViewById(R.id.rpwd_id1);
       password2=(EditText) findViewById(R.id.rpwd_id2);
       phonenumber=(EditText) findViewById(R.id.rphnoid);
   }


    public void Save(String save_val) {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, save_val);
       // editor.putString(Email, e);
        editor.commit();
    }

   public void listner(){


        dialog = new ACProgressFlower.Builder(this)
               .direction(ACProgressConstant.DIRECT_CLOCKWISE)
               .themeColor(Color.WHITE)
               .text("processing")
               .fadeColor(Color.DKGRAY).build();


try {


    sharedpreferences = getSharedPreferences(mypreference,
            Context.MODE_PRIVATE);
    if (sharedpreferences.contains(Name)) {

        String[] animalsArray = sharedpreferences.getString(Name, "").split(",");
        login_username.setText(animalsArray[0]);
        login_pwd.setText(animalsArray[1]);
        login_hn.setText(animalsArray[2]);
        // name.setText(sharedpreferences.getString(Name, ""));
    }
}catch (ArrayIndexOutOfBoundsException e){
    e.printStackTrace();
}
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               global_username=login_username.getText().toString();
               s_pwd= login_pwd.getText().toString();
               global_housename=login_hn.getText().toString();


               if(!s_pwd.isEmpty()&&!global_username.isEmpty()&&!global_housename.isEmpty()){
                   login_check=false;
                   /*http://123.176.44.3:5902/VankonApp/login/
                   {
                           "Username": "vinay",
                           "Password": "password123"
                   }*/
                   dialog.show();
                   cururl="http://123.176.44.3:5902/VankonApp/login/";
                   Log.d("+++ caling url",cururl);
                   Log.d("im inside jsoncall", String.valueOf(roomdtails(global_username,s_pwd,global_housename)));

                   Save(global_username+","+s_pwd+","+global_housename);
                   json_string=String.valueOf(roomdtails(global_username,s_pwd,global_housename));
                   // JSONSenderVolley(cururl, rmdt);
                   Login.OkHttpHandler okHttpHandler= new Login.OkHttpHandler();
                   okHttpHandler.execute(cururl);
               }
              /* Intent intent = new Intent(Login.this, RoomActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();*/
           }
       });
       Regster.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View view) {

                   s_username=username.getText().toString();
                   s_housenm=housename.getText().toString();
                   s_pwd=password1.getText().toString();
                   s_phone=phonenumber.getText().toString();

                   if(s_pwd.contentEquals(password2.getText().toString())){
                       register_json();
                       login_check=true;
                       login_username.setText(username.getText().toString());
                       login_pwd.setText(s_pwd);
                       dialog.show();
                       cururl="http://123.176.44.3:5902/VankonApp/register/";
                       Log.d("+++ caling url",cururl);
                       Log.d("im inside jsoncall", String.valueOf(register_json()));
                       json_string=String.valueOf(register_json());
                       // JSONSenderVolley(cururl, rmdt);
                       Login.OkHttpHandler okHttpHandler= new Login.OkHttpHandler();
                       okHttpHandler.execute(cururl);
                   }
                   else {
                       Toast.makeText(getApplicationContext(), "Password doesn't match" , Toast.LENGTH_SHORT).show();
                   }

           }
       });
       signup_feature.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               login_age.setVisibility(View.GONE);
               //register_page.setVisibility(View.VISIBLE);
               signup_feature.setVisibility(View.GONE);
               register_page.setVisibility(View.VISIBLE);
               register_page.setAlpha(0.0f);

// Start the animation
               register_page.animate()
                       .translationY(view.getHeight())
                       .alpha(1.0f)
                       .setListener(null);
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
                 //   Log.d("+++++++TAG",response.body().string());
                    final String jsonData = response.body().string();
                    Log.d("+++++++TAG",jsonData);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                    if(!login_check){
                        JSONObject jres_gs = null;
                        try {
                            jres_gs = new JSONObject(jsonData);

                        String vale = jres_gs.getString("error_code");
                        if (vale.contentEquals("200"))
                      {
                          dialog.dismiss();

                            Intent intent = new Intent(Login.this, RoomActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "please enter valide details", Toast.LENGTH_SHORT).show();

                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //finish();
                    }
                    else {
                            try {
                                JSONObject jres_gs = new JSONObject(jsonData);
                                String vale = jres_gs.getString("error_code");
                                if (vale.contentEquals("200")) {
                               /* runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // Stuff that updates the UI

                                    }
                                });*/
                                    dialog.dismiss();
                                    login_age.setVisibility(View.VISIBLE);
                                    register_page.setVisibility(View.GONE);
                                    signup_feature.setVisibility(View.VISIBLE);


                                } else if (vale.contentEquals("500")) {
                                    Toast.makeText(getApplicationContext(), "values already entered", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                }
                    });
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


}
