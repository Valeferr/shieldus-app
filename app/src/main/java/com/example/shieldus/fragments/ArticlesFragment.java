package com.example.shieldus.fragments;

import android.content.Intent;
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

        articles.add(new Article(
                "1",
                "Come riconoscere una relazione tossica",
                "Segnali e comportamenti da non sottovalutare",
                "Le relazioni tossiche spesso iniziano in modo subdolo. Ecco i campanelli d'allarme:\n\n" +
                        "• Isolamento dagli amici e familiari\n" +
                        "• Controllo eccessivo sulle tue attività\n" +
                        "• Critiche costanti e umiliazioni\n" +
                        "• Gelosia patologica\n\n" +
                        "Ricorda: l'amore dovrebbe farti sentire al sicuro, non in trappola.",
                "https://example.com/relazione-tossica.jpg",
                "Relazioni",
                "2023-05-15"
        ));

        articles.add(new Article(
                "2",
                "Diritti sul lavoro: cosa devi sapere",
                "Protezioni legali contro le discriminazioni",
                "Le lavoratrici hanno diritto a:\n\n" +
                        "• Parità di retribuzione a parità di mansioni\n" +
                        "• Maternità protetta (art. 2110 Codice Civile)\n" +
                        "• Protezione da molestie sessuali (D.Lgs. 198/2006)\n" +
                        "• Rifiutare trasferimenti ingiustificati\n\n" +
                        "Se subisci discriminazioni, contatta un sindacato o l'ispettorato del lavoro.",
                "https://example.com/diritti-lavoro.jpg",
                "Legale",
                "2023-06-20"
        ));

        articles.add(new Article(
                "3",
                "Sicurezza personale: consigli pratici",
                "10 strategie per proteggerti nella vita quotidiana",
                "1. Condividi sempre la tua posizione con una persona fidata\n" +
                        "2. Impara le tecniche base di autodifesa\n" +
                        "3. Fidati del tuo istinto - se una situazione ti mette a disagio, allontanati\n" +
                        "4. Tieni sempre carico il telefono\n" +
                        "5. Evita percorsi isolati di notte\n\n" +
                        "La prevenzione è la migliore difesa.",
                "https://example.com/sicurezza-personale.jpg",
                "Sicurezza",
                "2023-04-10"
        ));

        articles.add(new Article(
                "4",
                "Come denunciare violenza domestica",
                "La guida passo-passo per chiedere aiuto",
                "1. Chiama il 1522 (numero antiviolenza)\n" +
                        "2. Rivolgiti a un centro antiviolenza\n" +
                        "3. Fatti accompagnare in questura per sporgere denuncia\n" +
                        "4. Fatti visitare per accertare eventuali lesioni\n" +
                        "5. Chiedi una misura protettiva (ammonimento o allontanamento)\n\n" +
                        "Non sei sola, esistono percorsi di uscita dalla violenza.",
                "https://example.com/denuncia-violenza.jpg",
                "Emergenza",
                "2023-07-05"
        ));

        articles.add(new Article(
                "5",
                "Empowerment femminile: storie di riscatto",
                "Donne che hanno trasformato il dolore in forza",
                "Storie vere di donne che hanno superato:\n\n" +
                        "• Maria: vittima di stalking, oggi consulente per altre donne\n" +
                        "• Aisha: fuggita da matrimonio forzato, ora mediatrice culturale\n" +
                        "• Elena: sopravvissuta a violenza, fondatrice di un centro di accoglienza\n\n" +
                        "Queste storie dimostrano che il cambiamento è possibile.",
                "https://example.com/storie-riscatto.jpg",
                "Storie",
                "2023-03-08"
        ));

        return articles;
    }
}