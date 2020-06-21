package com.electroninc.londontourguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.electroninc.londontourguide.R;
import com.electroninc.londontourguide.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private Context context;
    private List<String> photos;
    private ItemClickListener itemClickListener;

    public PhotosAdapter(Context context, List<String> photos, ItemClickListener itemClickListener) {
        this.context = context;
        this.photos = photos;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.photo_item, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        String photo = photos.get(position);
        holder.getPhoto().setImageResource(Utils.getResourceFromDrawable(context, photo));
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public interface ItemClickListener {
        void onItemClicked(int itemId);
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView photo;

        PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClicked(getAdapterPosition());
        }

        ImageView getPhoto() {
            return photo;
        }
    }
}
