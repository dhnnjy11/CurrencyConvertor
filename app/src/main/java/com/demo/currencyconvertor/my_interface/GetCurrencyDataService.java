package com.demo.currencyconvertor.my_interface;

import com.demo.currencyconvertor.model.CurrencyList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//API calling of Currency calling
public interface GetCurrencyDataService {

    @GET("list")
    Call<CurrencyList> getCurrencyData(@Query("access_key") String apiKey);
}
