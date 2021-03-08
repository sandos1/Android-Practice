package com.example.applemusicapicall;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

//is the client (like our virtual web browser)
public class RetrofitCLient {

    public static final String BASE_URL = "https://itunes.apple.com/";
    private static retrofit2.Retrofit retrofit = null;

    private RetrofitCLient(){

    }

    public static retrofit2.Retrofit setRetrofit() {

        if(retrofit ==null){
            // LOG HTTP REQUEST & RESPONSE WITH LOGGING INTERCEPTOR

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            // //OkHttp’s logging interceptor has four log levels: NONE, BASIC, HEADERS, BODY

            //Level.BODY This is the most complete log level and will print out every related
            // information for your request and response.
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging) // add logging as last interceptor
                    .build();
             retrofit= new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)  //base url
                    .addConverterFactory(GsonConverterFactory.create()) //where we define our gson library to parse object
                     .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
