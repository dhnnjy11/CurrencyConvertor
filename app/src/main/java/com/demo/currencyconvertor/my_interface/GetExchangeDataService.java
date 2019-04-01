package com.demo.currencyconvertor.my_interface;

import com.demo.currencyconvertor.model.ExchangeList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//API calling of Exchange rate list
public interface GetExchangeDataService {

    @GET("live")
    Call<ExchangeList> getCurrencyData(@Query("access_key") String apiKey, @Query("source") String source);
}
