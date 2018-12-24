package com.moneytap.network;


import com.moneytap.models.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/w/api.php")
    Observable<Response> queryRequest(@Query("action")String action,
                                      @Query("format")String format,
                                      @Query("prop")String properties,
                                      @Query("generator")String generator,
                                      @Query("formatversion")int formatVersion,
                                      @Query("piprop")String pictureProp,
                                      @Query("wbptterms")String description,
                                      @Query("gpssearch")String searchTerms,
                                      @Query("gpslimit")int limit,
                                      @Query("inprop") String inprop);



}
