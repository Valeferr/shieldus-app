package com.example.shieldus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shieldus.R;
import com.example.shieldus.models.Article;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final List<Article> articles;
    private final OnArticleClickListener listener;

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    public ArticleAdapter(List<Article> articles, OnArticleClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_card, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ImageView articleImage;
        private final TextView articleTitle;
        private final TextView articleCategory;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image);
            articleTitle = itemView.findViewById(R.id.article_title);
            articleCategory = itemView.findViewById(R.id.article_category);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onArticleClick(articles.get(position));
                }
            });
        }

        public void bind(Article article) {
            articleTitle.setText(article.getTitle());
            articleCategory.setText(article.getCategory());
            int imageResId;
            switch(article.getId()) {
                case "1": imageResId = R.drawable.relazione_tossica; break;
                case "2": imageResId = R.drawable.diritti_lavoro; break;
                case "3": imageResId = R.drawable.sicurezza_personale; break;
                case "4": imageResId = R.drawable.denuncia_violenza; break;
                case "5": imageResId = R.drawable.storie_riscatto; break;
                default: imageResId = R.drawable.placeholder_article;
            }

            Glide.with(itemView.getContext())
                    .load(imageResId)
                    .placeholder(R.drawable.ic_article_placeholder)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(articleImage);
        }
    }
}