package com.demo.currencyconvertor.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

//POJO for recieving response for Currency
public class CurrencyList {

    @SerializedName("success")
    private  boolean success;

    @SerializedName("currencies")
    private JsonElement currencies;

    public JsonElement getCurrencies() {
        return currencies;
    }

    public void setCurrencies(JsonElement currencies) {
        this.currencies = currencies;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
