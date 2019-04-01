package com.demo.currencyconvertor.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

//POJO for recieving response for Exchange Rate Object
public class ExchangeList {
    @SerializedName("success")
    private  boolean success;

    @SerializedName("source")
    private String source;

    @SerializedName("quotes")
    private JsonElement quotes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public JsonElement getQuotes() {
        return quotes;
    }

    public void setQuotes(JsonElement quotes) {
        this.quotes = quotes;
    }
}
