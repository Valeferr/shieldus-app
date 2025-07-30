package com.example.shieldus.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.shieldus.R;
import com.example.shieldus.models.Article;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ArticleDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        setupNavigationDrawer();

        Article article = getIntent().getParcelableExtra("ARTICLE");
        if (article != null) {
            setupViews(article);
        } else {
            finish();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupViews(Article article) {
        ImageView imageView = findViewById(R.id.detail_image);
        TextView titleView = findViewById(R.id.detail_title);
        TextView contentView = findViewById(R.id.detail_content);
        TextView categoryView = findViewById(R.id.detail_category);
        TextView dateView = findViewById(R.id.detail_date);

        assert article != null;
        int imageResId;
        switch(article.getId()) {
            case "1": imageResId = R.drawable.relazione_tossica; break;
            case "2": imageResId = R.drawable.diritti_lavoro; break;
            case "3": imageResId = R.drawable.sicurezza_personale; break;
            case "4": imageResId = R.drawable.denuncia_violenza; break;
            case "5": imageResId = R.drawable.storie_riscatto; break;
            default: imageResId = R.drawable.placeholder_article;
        }

        Glide.with(this)
                .load(imageResId)
                .placeholder(R.drawable.ic_article_placeholder)
                .error(R.drawable.ic_error)
                .into(imageView);

        titleView.setText(article.getTitle());
        contentView.setText(article.getContent());
        categoryView.setText(article.getCategory());
        dateView.setText(formatDate(article.getDate()));

        setToolbarTitle(article.getCategory());
    }

    private String formatDate(String rawDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN);
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return rawDate;
        }
    }
}