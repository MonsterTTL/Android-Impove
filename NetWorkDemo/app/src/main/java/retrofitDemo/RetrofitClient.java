package retrofitDemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //这个类是用来将请求发送给接口类实例的


    private static RetrofitClient instance = null;//单例设计模式-实例
    private ReInterface myInterface;//自己定义的方法

    //单例设计模式下的私有方法
    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ReInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(ReInterface.class);
    }

    //静态单例--设计模式
    public static synchronized RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    //返回实体接口实例
    public ReInterface getInterface(){
        return myInterface;
    }


}
