package com.journals.longdom.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
   public static String IMAGE_URL ="https://www.longdom.org/assets/category-images/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.longdom.org/admin/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
