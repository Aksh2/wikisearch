package com.moneytap.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.moneytap.utils.Utils;
import com.moneytap.wikisearch.WikiApplication;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "https://en.wikipedia.org/";
    private static Retrofit retrofit = null;
    private RetrofitInterface retrofitInterface;
    public static NetworkService INSTANCE;
    private Context context;

    public static NetworkService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkService();
        }
        return INSTANCE;
    }



    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (Utils.isNetworkAvailable(WikiApplication.getAppContext())) {
                int maxAge = 60*5; // read from cache for 5 minutes
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7; // tolerate 1-week stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };




    public static Retrofit createApiClient() {

        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

            //setup cache
            File httpCacheDirectory = new File(WikiApplication.getAppContext().getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            okHttpClient.cache(cache);
            okHttpClient.addInterceptor(loggingInterceptor);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }

   private NetworkService() {
        retrofitInterface = createApiClient().create(RetrofitInterface.class);
    }

    public Observable<com.moneytap.models.Response> queryArticle(String query) {
        return retrofitInterface.queryRequest("query", "json",
                "info|pageimages|pageterms", "prefixsearch",
                2, "thumbnail", "description", query, 10, "url");
    }


}
