package NetWorkUtils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UrlConnManager {
    private static final String TAG = "UrlConnManager";
    class NameValuePair{
        public String key;
        public String value;
        public NameValuePair(String key,String value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static HttpURLConnection getHttpURLConnection(String url,String Method){
        HttpURLConnection mHttpURLConnection = null;
        try{
            URL urll = new URL(url);
            //根据url打开链接 -- 还是在请求行 ： 方法+url+版本
            mHttpURLConnection = (HttpURLConnection) urll.openConnection();
            mHttpURLConnection.setConnectTimeout(15000);//设置链接超时时间
            mHttpURLConnection.setReadTimeout(15000);//设置读取超时时间
            mHttpURLConnection.setRequestMethod(Method);//设置方法--对应请求行的方法
            //添加Header
            mHttpURLConnection.setRequestProperty("Connection","KeepAlive");
            mHttpURLConnection.setDoInput(true);//开启接收输入流
            mHttpURLConnection.setDoOutput(true);//接收参数书需要开启 -- 要输出
        }catch (Exception e){
            e.printStackTrace();
        }
        return mHttpURLConnection;
    }

    //传输参数的方法
    public static void postParams(OutputStream output, List<NameValuePair> paramsList) throws IOException {
        StringBuilder builder = new StringBuilder();
        for(NameValuePair pair:paramsList){
            if(TextUtils.isEmpty(builder)){
                builder.append("&");
            }
            builder.append(URLEncoder.encode(pair.key,"UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.value,"UTF-8"));
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output,"UTF-8"));
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }

    public void useHttpUrlConnectionPost(String url){
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = getHttpURLConnection(url,"POST");
        try{
            List<NameValuePair> postParams = new ArrayList<>();
            postParams.add(new NameValuePair("ip","59.108.54.37"));
            postParams(mHttpURLConnection.getOutputStream(),postParams);
            mHttpURLConnection.connect();
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            Log.d(TAG, "useHttpUrlConnectionPost: "+code);
            String response = mInputStream.toString();
            mInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
