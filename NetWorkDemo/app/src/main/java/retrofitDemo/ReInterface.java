package retrofitDemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReInterface {
    //定义了需要实现的方法

    public static String BASE_URL = "https://api.caiyunapp.com/v2.6/TAkhjf8d1nlSlspN/101.6656,39.2072/";


    @GET("realtime")
    public Call<ReModel> getRealtime();

    //dynamic para
    @GET("{type}")
    public Call<ReModel> Dynamic_getData(@Path("type") String types);
}
