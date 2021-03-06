package no.ntnu.sverrlof;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String  BASE_URL = "10.22.192.252:8080:Fant/api/";

    private static ApiClient SINGLETON;
     Retrofit retrofit = null;


    public Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    // Checks if an instance has been created or not
    public static synchronized ApiClient getSINGLETON() {
        if (SINGLETON == null) {
            SINGLETON = new ApiClient();
        }
        return SINGLETON;
    }

    // Connects to the FantApi.
    public FantApi getApi() {
        return retrofit.create(FantApi.class);
    }


}
