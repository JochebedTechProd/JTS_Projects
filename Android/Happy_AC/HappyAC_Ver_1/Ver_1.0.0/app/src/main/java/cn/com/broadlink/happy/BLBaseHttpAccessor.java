package cn.com.broadlink.happy;

import android.text.TextUtils;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class BLBaseHttpAccessor {
    public static int HTTP_TIMEOUT = 15000;

    public static String HTTP_GET = "get";

    public static String HTTP_POST = "post";

    private static String HTTP_ERROR_MSG = "Http error: ";

    private static String HTTP_ERROR_TOO_FAST = "HTTP request too fast!";

    private static void addCommondToRequest(HttpURLConnection urlConnection) {
        long nowTime = System.currentTimeMillis() / 1000;

        urlConnection.setRequestProperty("system", "android");
        urlConnection.setRequestProperty("appPlatform", "android");
        urlConnection.setRequestProperty("timestamp", String.valueOf(nowTime));
    }

    public static String get(String addr, String param, Map<String, String> mapHead, int httpTimeOut, TrustManager... trustManagers){

        URL url = null;
        try {
            /** 如果有参数，添加到url后 **/

            if(!TextUtils.isEmpty(param)){
                addr += param;
            }
            url = new URL(addr);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 请求类型
            urlConnection.setRequestMethod("GET");
            // 不使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(httpTimeOut);
            urlConnection.setReadTimeout(httpTimeOut);

            /** 安全认证 **/
            if ("https".equals(url.getProtocol().toLowerCase())) {
                SSLContext sc = SSLContext.getInstance("TLS");

                if(trustManagers == null){
                    trustManagers = new TrustManager[]{new BLTrustManager()};
                }
                sc.init(null, trustManagers, new java.security.SecureRandom());
                ((HttpsURLConnection)urlConnection).setSSLSocketFactory(sc.getSocketFactory());

                ((HttpsURLConnection)urlConnection).setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            }

            /** http头 **/
            addCommondToRequest(urlConnection);
            if(mapHead != null){
                for(String key : mapHead.keySet()){
                    urlConnection.setRequestProperty(key, mapHead.get(key));
                }
            }

            /** 连接 **/
            urlConnection.connect();
            int respCode = urlConnection.getResponseCode();

            /** 请求服务器并返回结果 **/
            InputStream is = urlConnection.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            String resultData = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            is.close();

            return resultData;
        } catch (Exception e) {

            try {
                JSONObject jobect = new JSONObject();
                jobect.put("error", -1);
                jobect.put("msg", HTTP_ERROR_MSG + e.toString());

                return jobect.toString();
            } catch (Exception err) {

            }
            return null;
        }

    }

    /**
     * 普通post
     * @param addr
     * @param mapHead
     * @param dataBytes
     * @return
     */
    public static String post(String addr, Map<String, String> mapHead, byte[] dataBytes, int httpTimeOut, TrustManager... trustManagers) {

        URL url = null;
        try {
            url = new URL(addr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 请求类型
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // 不使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            urlConnection.setConnectTimeout(httpTimeOut);
            urlConnection.setReadTimeout(httpTimeOut);

            /** 安全认证 **/
            if ("https".equals(url.getProtocol().toLowerCase())) {
                SSLContext sc = SSLContext.getInstance("TLS");
                if(trustManagers == null){
                    trustManagers = new TrustManager[]{new BLTrustManager()};
                }
                sc.init(null, trustManagers, new java.security.SecureRandom());
                ((HttpsURLConnection)urlConnection).setSSLSocketFactory(sc.getSocketFactory());
                ((HttpsURLConnection)urlConnection).setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            }

            /** http头 **/
            addCommondToRequest(urlConnection);
            if(mapHead != null){
                for(String key : mapHead.keySet()){
                    urlConnection.setRequestProperty(key, mapHead.get(key));
                }
            }

            /** 写入数据 **/
            OutputStream os = urlConnection.getOutputStream();
            os.write(dataBytes);

            os.flush();
            os.close();

            /** 请求服务器并返回结果 **/
            InputStream is = urlConnection.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            String resultData = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            is.close();

            return resultData;
        } catch (Exception e) {
            return null;
        }
    }
}
