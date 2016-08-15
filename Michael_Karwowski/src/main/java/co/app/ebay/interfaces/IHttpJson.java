package co.app.ebay.interfaces;

import java.util.ArrayList;

import co.app.ebay.model.BookModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IHttpJson {
    interface IGetBookJson {
        @GET("books.json")
        Call<ArrayList<BookModel>> getJson();
    }

}
