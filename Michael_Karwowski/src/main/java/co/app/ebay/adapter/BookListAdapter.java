package co.app.ebay.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.app.ebay.R;
import co.app.ebay.model.BookModel;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private ArrayList<BookModel> mSongList;

    public BookListAdapter(ArrayList<BookModel> mSongList) {
        this.mSongList = mSongList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BookModel bookModel = mSongList.get(position);
        holder.bookTitle.setText(bookModel.getTitle());
        holder.bookAuthor.setText(bookModel.getAuthor());

        //very poor way of doing this would rather use Picasso, this will also create a memory exception error
        //but i noticed that the images are not large in sizes so it wont happen now
        //also should be done in RxJava.RxAndroid, or at least a ServiceIntent

        //not fully checked, may create memory leaks + problems on orientation change
        //poor performance lags too much and switches images on fast scrolling
        new DownloadImageTask(holder.bookImage).execute(bookModel.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bookTitle)
        TextView bookTitle;

        @BindView(R.id.bookAuthor)
        TextView bookAuthor;

        @BindView(R.id.bookImage)
        ImageView bookImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bookImage;

        public DownloadImageTask(ImageView bookImage) {
            this.bookImage = bookImage;
            bookImage.setImageBitmap(null);
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;

            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                //Should be handled, maybe some kind of default image
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bookImage.setImageBitmap(result);
        }
    }

}
