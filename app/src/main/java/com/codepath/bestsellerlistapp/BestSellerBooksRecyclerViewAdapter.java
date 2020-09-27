package com.codepath.bestsellerlistapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.bestsellerlistapp.models.BestSellerBook;
import com.codepath.bestsellerlistapp.networking.CallbackResponse;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BestSellerBook} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class BestSellerBooksRecyclerViewAdapter extends RecyclerView.Adapter<BestSellerBooksRecyclerViewAdapter.BookViewHolder> {

    private final List<BestSellerBook> books;
    private final OnListFragmentInteractionListener mListener;

    public BestSellerBooksRecyclerViewAdapter(List<BestSellerBook> items, OnListFragmentInteractionListener listener) {
        this.books = items;
        this.mListener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_best_seller_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        holder.mItem = books.get(position);
        holder.mBookTitle.setText(holder.mItem.title);
        holder.mBookAuthor.setText(holder.mItem.author);
        holder.mRanking.setText(String.valueOf(holder.mItem.rank));
        holder.mBookDescription.setText(holder.mItem.description);
        Glide.with(holder.mView)
                .load(holder.mItem.bookImageUrl)
                .centerInside()
                .into(holder.mBookImage);
        holder.mBuyBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open Amazon URL
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.amazonUrl));
                holder.mView.getContext().startActivity(browserIntent);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.mItem);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBookTitle;
        public final TextView mBookAuthor;
        public BestSellerBook mItem;
        public TextView mRanking;
        public TextView mBookDescription;
        public ImageView mBookImage;
        public Button mBuyBookButton;

        public BookViewHolder(View view) {
            super(view);
            mView = view;
            mBookTitle = (TextView) view.findViewById(R.id.book_title);
            mBookAuthor = (TextView) view.findViewById(R.id.book_author);
            mRanking = (TextView) view.findViewById(R.id.ranking);
            mBookDescription = (TextView) view.findViewById(R.id.book_description);
            mBookImage = (ImageView) view.findViewById(R.id.book_image);
            mBuyBookButton = (Button) view.findViewById(R.id.buy_button);
        }

        @Override
        public String toString() {
            return mBookTitle.toString() + " '" + mBookAuthor.getText() + "'";
        }
    }
}
