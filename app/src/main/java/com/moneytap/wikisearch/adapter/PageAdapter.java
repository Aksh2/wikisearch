package com.moneytap.wikisearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moneytap.models.Page;
import com.moneytap.wikisearch.R;
import com.moneytap.wikisearch.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {
    final String TAG = PageAdapter.class.getSimpleName();
    private Context context;
    private List<Page> pagesList;

    public PageAdapter(Context context,List<Page> pagesList){

        this.context=context;
        this.pagesList=pagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wiki_search_item, viewGroup,false);
        return new ViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Page page = pagesList.get(position);
        if(page!=null){
            Log.d(TAG,"Page Object: " + page);
            viewHolder.itemName.setText(page.getTitle());
            viewHolder.description.setText(page.getTerms()!=null?page.getTerms().getDescription().get(0):"");

            Glide.with(context)
                    .load(page.getThumbnail()!=null?page.getThumbnail().getUrl():"http://fag.nuol.edu.la/images/no_photo.png")
                    .into(viewHolder.thumbnail);
        }


    }


    @Override
    public int getItemCount() {
        Log.d(TAG,"size:"+pagesList.size());
        return pagesList.size();
    }

    public void setPagesList(List<Page> pagesList){
        this.pagesList=pagesList;
        Log.d(TAG,"pageList: "+pagesList.toString());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,description;
        ImageView thumbnail;

        public ViewHolder(View view){
            super(view);
            itemName = view.findViewById(R.id.itemTv);
            description = view.findViewById(R.id.descriptionTv);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=pagesList.get(getAdapterPosition()).getUrl();
                    Log.d(TAG,"adapter url:"+url);
                    Intent webview =new Intent(v.getContext(),WebviewActivity.class);
                    webview.putExtra("url",url);
                    v.getContext().startActivity(webview);
                }
            });
        }
    }
}
