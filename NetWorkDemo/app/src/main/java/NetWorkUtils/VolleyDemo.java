package NetWorkUtils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.networkdemo.R;

import org.json.JSONObject;

public class VolleyDemo {

    /** Volley的缓存调度线程 和 网络调度线程 ：
     * 缓存调度主要是用来缓存，避免重复加载  **/
    private static final String TAG = "VolleyDemo";

    public static void StringRequestDemo(RequestQueue queue){
        StringRequest r = new StringRequest(Request.Method.GET, "https://www.baidu.com/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage(),error);
            }
        });

        queue.add(r);
    }

    public static void JsonRequestDemo(RequestQueue queue){
        JsonObjectRequest request = new JsonObjectRequest("https://news-at.zhihu.com/api/4/news/latest",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: "+response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage(),error);
            }
        });

        queue.add(request);
    }

    public static void ImageRequestDemo(RequestQueue queue, ImageView imageView){
        ImageRequest request = new ImageRequest(
                "https://images.unsplash.com/photo-1680208281019-8642d20405f9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjAzNTV8MHwxfGFsbHwxOXx8fHx8fDJ8fDE2ODAyNjIyNzQ&ixlib=rb-4.0.3&q=80&w=1080);,%20rgba(255,%20255,%20255,%201)",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage(),error);
            }
        });

        queue.add(request);
    }

    public static void ImageLoaderDemo(RequestQueue queue,ImageView imageView){
        ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background);
        imageLoader.get("https://images.unsplash.com/photo-1680208281019-8642d20405f9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjAzNTV8MHwxfGFsbHwxOXx8fHx8fDJ8fDE2ODAyNjIyNzQ&ixlib=rb-4.0.3&q=80&w=1080);,%20rgba(255,%20255,%20255,%201)",
                listener);
    }

    public static void NetWkPIC_Demo(RequestQueue queue,NetworkImageView networkImageView){
        networkImageView.setDefaultImageResId(R.drawable.baseline_adb_24);
        networkImageView.setErrorImageResId(R.drawable.baseline_adb_24);
        networkImageView.setImageUrl("https://img.redbull.com/images/w_1620/q_auto,f_auto/redbullcom/2016/09/20/1331818966444_2/pok%C3%A9mon-super-mystery-dungeon",
                new ImageLoader(queue,new BitmapCache()));
    }


}
