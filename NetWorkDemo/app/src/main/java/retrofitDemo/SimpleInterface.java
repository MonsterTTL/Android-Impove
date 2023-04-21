package retrofitDemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SimpleInterface {

    @GET("https://api.caiyunapp.com/v2.6/TAkhjf8d1nlSlspN/101.6656,39.2072/realtime")
    public Call<ReModel> getSimpleRealtime();
}
