package co.app.ebay.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.app.ebay.R;
import co.app.ebay.adapter.BookListAdapter;
import co.app.ebay.manager.HttpManager;
import co.app.ebay.model.BookModel;

public class MainActivity extends AppCompatActivity implements HttpManager.JsonResponseCallback{
    //MVP injection with dagger

    //Should be injected with dagger
    private HttpManager mHttpManager;

    @BindView(R.id.recyclerView)
    RecyclerView mBookRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState == null && mHttpManager == null) {
            mHttpManager = new HttpManager(this);
            //would be nice for a spinner object while user is waiting for list to load
            mHttpManager.loadJsonBookList();
        }
    }

    @Override
    public void jsonResponseReceived(ArrayList<BookModel> bookModelList) {
        //cache images

        BookListAdapter adapter = new BookListAdapter(bookModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mBookRecyclerView.setLayoutManager(mLayoutManager);
        mBookRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBookRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHttpManager.onStop();
    }
}
