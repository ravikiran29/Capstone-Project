package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ravikiran.pathade.ravikiranpathade.newstrends.R;
import ravikiran.pathade.ravikiranpathade.newstrends.activities.NewsDetailActivity;

import java.util.List;

import models.Articles;

/**
 * Created by ravikiranpathade on 12/13/17.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsListViewHolder> {

    List<Articles> articles;

    Context holderContext;
    Context recyclerContext;

    public NewsRecyclerAdapter(List<Articles> articlesList) {
        this.articles = articlesList;

    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.news_item_cardview;
        recyclerContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(recyclerContext);

        View view = inflater.inflate(layoutId, parent, false);

        NewsListViewHolder holder = new NewsListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setDataArticles(List<Articles> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    class NewsListViewHolder extends RecyclerView.ViewHolder {
        ImageView newsCardImage;
        TextView headline;
        TextView author;
        CardView cardView;

        public NewsListViewHolder(View itemView) {
            super(itemView);
            holderContext = itemView.getContext();

            newsCardImage = itemView.findViewById(R.id.news_card_image);
            headline = itemView.findViewById(R.id.headline_card);
            author = itemView.findViewById(R.id.author_card);

            cardView = itemView.findViewById(R.id.news_card);
        }

        public void bind(final int position) {
            final Articles article = articles.get(position);


            Glide.with(holderContext).load(article.getUrlToImage()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).error(R.drawable.noimageavailable).into(newsCardImage);


            headline.setText(article.getTitle());

            //String dateString = article.getPublishedAt();
//
//        if(!dateString.equals("")||!dateString.isEmpty()){
//            Date date = DateTimeUtils.formatDate(dateString);
//            if(date!=null){
//                Log.d("Check Date",date.toString());
//            }}


            final String title_string = article.getTitle();
            final String desc_string = article.getDescription();
            final String imageUrl_string = article.getUrlToImage();
            final String urlArticle_string = article.getUrl();
            final String author_string = article.getAuthor();
            final String publishedAt_string = article.getPublishedAt();
            final String source_id_string = article.getSource().getId();
            final String source_name_string = article.getSource().getName();
            if (article.getAuthor() != null) {
                author.setText(holderContext.getResources().getString(R.string.by) + author_string + holderContext.getResources().getString(R.string.at) + source_name_string);
            }

            if (!desc_string.equals(holderContext.getResources().getString(R.string.empty_string))) {
                newsCardImage.setContentDescription(desc_string);
            } else {
                newsCardImage.setContentDescription(title_string);
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holderContext, NewsDetailActivity.class);

                    i.putExtra(holderContext.getResources().getString(R.string.list_id), position);
                    i.putExtra(holderContext.getResources().getString(R.string.title_cursor_adapter), title_string);
                    i.putExtra(holderContext.getResources().getString(R.string.description_cursor_adapter), desc_string);
                    i.putExtra(holderContext.getResources().getString(R.string.urlToImage_cursor_adapter), imageUrl_string);
                    i.putExtra(holderContext.getResources().getString(R.string.url_cursor_adapter), urlArticle_string);
                    i.putExtra(holderContext.getResources().getString(R.string.author_cursor_adapter), author_string);
                    i.putExtra(holderContext.getResources().getString(R.string.publishedAt_cursor_adapter), publishedAt_string);
                    i.putExtra(holderContext.getResources().getString(R.string.source_id_cursor_adapter), source_id_string);
                    i.putExtra(holderContext.getResources().getString(R.string.source_name_cursor_adapter), source_name_string);

                    holderContext.startActivity(i);

                }
            });

        }
    }
}
