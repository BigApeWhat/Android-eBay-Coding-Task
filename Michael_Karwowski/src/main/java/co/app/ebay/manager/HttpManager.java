package co.app.ebay.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.app.ebay.interfaces.IHttpJson;
import co.app.ebay.model.BookModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    //Not the best place for the url
    @NonNull
    private final String mApiURL = "http://de-coding-test.s3.amazonaws.com/";

    @NonNull
    private final Retrofit mRetrofit;

    @NonNull
    private JsonResponseCallback mJsonResponseCallback;

    @NonNull
    private final IHttpJson.IGetBookJson mApiService;

    public HttpManager(JsonResponseCallback callback) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mApiURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mJsonResponseCallback = callback;
        mApiService = mRetrofit.create(IHttpJson.IGetBookJson.class);

    }

    public void loadJsonBookList() {

        Call<ArrayList<BookModel>> call = mApiService.getJson();
        call.enqueue(new Callback<ArrayList<BookModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BookModel>> call, Response<ArrayList<BookModel>> response) {
                mJsonResponseCallback.jsonResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<BookModel>> call, Throwable t) {
                //Something went wrong, handle it
            }
        });
    }

    //should be done in a mvp way, through an interface
    public void onStop() {
        mJsonResponseCallback = null;
    }

    public interface JsonResponseCallback {
        void jsonResponseReceived(ArrayList<BookModel> bookModelList);
    }
}
