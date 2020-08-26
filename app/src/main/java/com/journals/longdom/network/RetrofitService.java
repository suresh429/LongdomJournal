package com.journals.longdom.network;

import android.content.Context;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
   public static String IMAGE_HOME_URL ="https://www.longdom.org/assets/category-images/";

   // private static Retrofit retrofit = null;




    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.longdom.org/admin/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


   /* public static Retrofit getRetrofitClient(Context mContext) {
        if (retrofit == null) {

            OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new NetworkConnectionInterceptor(mContext));
            // Adding NetworkConnectionInterceptor with okHttpClientBuilder.

           // oktHttpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.longdom.org/admin/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(oktHttpClient.build())
                    .build();

        }
        return retrofit;
    }*/

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
