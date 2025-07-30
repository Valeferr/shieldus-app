package com.example.shieldus.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shieldus.R;
import com.example.shieldus.activities.ArticleDetailActivity;
import com.example.shieldus.adapters.ArticleAdapter;
import com.example.shieldus.models.Article;
import java.util.ArrayList;
import java.util.List;

public class ArticlesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ArticleAdapter adapter = new ArticleAdapter(getRealisticArticles(), article -> {
            if (getActivity() != null) {
                Log.d("ArticleClick", "Article clicked: " + article.getTitle());
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra("ARTICLE", article);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private List<Article> getRealisticArticles() {
        List<Article> articles = new ArrayList<>();

        Resources res = this.getResources();
        String[] ids = res.getStringArray(R.array.article_ids);
        String[] titles = res.getStringArray(R.array.article_titles);
        String[] subtitles = res.getStringArray(R.array.article_subtitles);
        String[] contents = res.getStringArray(R.array.article_contents);
        String[] imageUrls = res.getStringArray(R.array.article_image_urls);
        String[] categories = res.getStringArray(R.array.article_categories);
        String[] dates = res.getStringArray(R.array.article_dates);

        for (int i = 0; i < ids.length; i++) {
            articles.add(new Article(
                    ids[i],
                    titles[i],
                    subtitles[i],
                    contents[i],
                    imageUrls[i],
                    categories[i],
                    dates[i]
            ));
        }

        return articles;
    }
}