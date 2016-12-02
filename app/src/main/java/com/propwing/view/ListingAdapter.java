package com.propwing.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propwing.R;
import com.propwing.model.Item;
import com.propwing.model.Property;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.PropertyViewHolder> {

    private Property mProperty;
    private Context mContext;

    public ListingAdapter(final Context context, final Property property) {
        mContext = context;
        mProperty = property;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_property, null);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        final Item item = mProperty.getItems().get(position);
        holder.mTitle.setText(item.getPropertyTitle());
        holder.mSiteUrl.setText(item.getDetailsSiteUrl());
        holder.mStateUrl.setText(item.getShortenerStatUrl());
        holder.mSummary.setText(item.getSummaryText());

        holder.mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, item.getPropertyTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getDetailsSiteUrl());
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using..."));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProperty.getItems().size();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mSiteUrl;
        private TextView mStateUrl;
        private TextView mSummary;
        private TextView mShareBtn;

        PropertyViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSiteUrl = (TextView) itemView.findViewById(R.id.site_url);
            mStateUrl = (TextView) itemView.findViewById(R.id.stat_url);
            mSummary = (TextView) itemView.findViewById(R.id.summary);
            mShareBtn = (TextView) itemView.findViewById(R.id.share_btn);
        }
    }
}
