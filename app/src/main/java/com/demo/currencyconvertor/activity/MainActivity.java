package com.demo.currencyconvertor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.demo.currencyconvertor.Config;
import com.demo.currencyconvertor.R;
import com.demo.currencyconvertor.adapter.ExchangeAdapter;
import com.demo.currencyconvertor.model.CurrencyList;
import com.demo.currencyconvertor.model.ExchangeList;
import com.demo.currencyconvertor.model.ExchangeRate;
import com.demo.currencyconvertor.my_interface.GetCurrencyDataService;
import com.demo.currencyconvertor.my_interface.GetExchangeDataService;
import com.demo.currencyconvertor.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ExchangeAdapter adapter;
    private ArrayList<String> currencies;
    private String SelectedCurrency;
    private RecyclerView recyclerView;
    private Spinner spin_currency;
    int inputVal = 1;
    private TextInputEditText inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spin_currency = (Spinner) findViewById(R.id.currency_spinner);
        recyclerView = findViewById(R.id.recycler_view_notice_list);
        inputLayout = (TextInputEditText)findViewById(R.id.input_val);

       final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_SHORT).show();
                callCurrencyListAPI();
                handler.postDelayed(this, 1800000);
            }
        }, 1000);


    }

    /* Button click for convert value to all exchange rates*/
    public void onBtnConvert(View view){
        loadExchangeRateList(spin_currency.getSelectedItem().toString());

    }
    /* Invoke api for loading currency list*/
    public void callCurrencyListAPI(){
        GetCurrencyDataService service = RetrofitInstance.getRetrofitInstance().create(GetCurrencyDataService.class);
        Call<CurrencyList> call = service.getCurrencyData(Config.API_KEY);
        call.enqueue(new Callback<CurrencyList>() {
            @Override
            public void onResponse(Call<CurrencyList> call, Response<CurrencyList> response) {

                JsonElement jsonElement = response.body().getCurrencies();
                currencies = new ArrayList<>();
                final JsonObject jsonObject = jsonElement.getAsJsonObject();
                for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    currencies.add(entry.getKey());
                }
                loadCurrencyList(currencies);
                Toast.makeText(MainActivity.this, "Successfully Done", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<CurrencyList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*Load currency list into spinner*/
    public void loadCurrencyList(ArrayList<String> currencies){

        spin_currency.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, currencies));
        spin_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
                String source = parent.getItemAtPosition(position).toString();
                loadExchangeRateList(source);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //didn't implement
            }
        });
    }

    /* Call api and load Exchange Rate List*/
    private void loadExchangeRateList(final String source){
        GetExchangeDataService exchangeService = RetrofitInstance.getRetrofitInstance().create(GetExchangeDataService.class);
        Call<ExchangeList> exchangeRateCall = exchangeService.getCurrencyData(Config.API_KEY,source );
        exchangeRateCall.enqueue(new Callback<ExchangeList>() {
            @Override
            public void onResponse(Call<ExchangeList> call, Response<ExchangeList> response) {
                Double inputValue = Double.parseDouble(inputLayout.getText().toString());
                JsonElement jsonElement = response.body().getQuotes();
                ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
                final JsonObject jsonObject = jsonElement.getAsJsonObject();
                inputValue = Double.parseDouble(inputLayout.getText().toString());
                for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    ExchangeRate exchangeRate = new ExchangeRate();
                    exchangeRate.setTargetCurrency(entry.getKey().substring(3));
                    exchangeRate.setRate(entry.getValue().getAsDouble()*inputValue);
                    exchangeRate.setSourceCurrency(source);
                    exchangeRates.add(exchangeRate);
                }


                adapter = new ExchangeAdapter(exchangeRates);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);


                Toast.makeText(MainActivity.this, "Successfully Done", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<ExchangeList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}
