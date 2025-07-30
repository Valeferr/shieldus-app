package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shieldus.R;
import com.example.shieldus.adapters.EducationModuleAdapter;
import com.example.shieldus.models.EducationModule;
import com.example.shieldus.persistence.ProgressManager;
import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends BaseActivity {

    private static final int QUIZ_REQUEST_CODE = 1001;
    private RecyclerView modulesRecyclerView;
    private EducationModuleAdapter adapter;
    private List<EducationModule> moduleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        setupNavigationDrawer();
        setToolbarTitle("Moduli Educativi");

        modulesRecyclerView = findViewById(R.id.modulesRecyclerView);
        modulesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        createSampleModules();

        adapter = new EducationModuleAdapter(moduleList, this::onModuleClicked, isAnonymous);
        modulesRecyclerView.setAdapter(adapter);
    }

    private void createSampleModules() {
        moduleList.clear();

        String[] ids = {"1", "2", "3", "4", "5"};
        String[] titles = {
                "Consenso nelle relazioni",
                "Salute Sessuale",
                "Diritti sul Lavoro",
                "Relazioni Tossiche",
                "Sicurezza Personale"
        };
        String[] descriptions = {
                "Impara a riconoscere e rispettare i confini nelle relazioni",
                "Informazioni essenziali sulla salute e sicurezza sessuale",
                "Conosci i tuoi diritti in ambito lavorativo",
                "Riconosci e gestisci relazioni dannose",
                "Impara le strategie fondamentali per riconoscere situazioni a rischio e proteggerti efficacemente nella vita quotidiana.",
        };
        String[] details = {
                // 1. Consenso nelle relazioni
                "Il consenso è un accordo volontario, entusiasta, informato e continuo tra tutte le persone coinvolte in un’attività, specialmente nelle relazioni intime.\n\n" +
                        "È importante sapere che:\n" +
                        "- Il consenso deve essere espresso chiaramente, non presunto.\n" +
                        "- Può essere revocato in qualsiasi momento, anche se inizialmente accordato.\n" +
                        "- L’assenza di un “no” non equivale a un “sì”: il silenzio non è consenso.\n" +
                        "- Non è valido se ottenuto con pressione, manipolazione, minacce, o se la persona è sotto l’effetto di droghe o alcol.\n\n" +
                        "Il consenso non riguarda solo la sfera sessuale: si applica anche all'affetto fisico, alla comunicazione, alla condivisione di spazi e al rispetto reciproco.\n\n" +
                        "Conoscere e praticare il consenso significa costruire relazioni sane, sicure e rispettose.",

                // 2. Salute Sessuale
                "La salute sessuale è uno stato di benessere fisico, emotivo, mentale e sociale in relazione alla sessualità.\n\n" +
                        "Prendersi cura della propria salute sessuale significa:\n" +
                        "- Conoscere il proprio corpo e comprendere i cambiamenti ormonali.\n" +
                        "- Usare metodi contraccettivi sicuri e informati.\n" +
                        "- Prevenire e riconoscere le infezioni sessualmente trasmissibili (IST).\n" +
                        "- Accedere a servizi sanitari sicuri, riservati e inclusivi.\n" +
                        "- Vivere la propria sessualità con consapevolezza e libertà, senza giudizio.\n\n" +
                        "La salute sessuale è un diritto: include informazione, protezione e accesso alle cure, senza discriminazioni.\n\n" +
                        "Promuoverla significa migliorare la qualità della vita e favorire relazioni affettive sane e rispettose.",

                // 3. Diritti Legali
                "Ogni persona ha il diritto di vivere libera da violenza, discriminazione e abuso, specialmente in relazione all’identità di genere, orientamento sessuale e salute sessuale.\n\n" +
                        "Hai il diritto di:\n" +
                        "- Essere protetto/a dalla legge contro ogni forma di violenza sessuale o psicologica.\n" +
                        "- Accedere a servizi sanitari e supporto psicologico senza subire discriminazioni.\n" +
                        "- Denunciare comportamenti abusivi o non consensuali, anche se avvengono all’interno di una relazione.\n" +
                        "- Ricevere un’educazione sessuale completa, scientifica e inclusiva.\n" +
                        "- Essere ascoltato/a con rispetto e riservatezza quando cerchi aiuto o informazioni.\n\n" +
                        "Conoscere i tuoi diritti ti permette di difenderti, tutelarti e supportare anche chi ti sta vicino.\n\n" +
                        "Ricorda: nessuno ha il diritto di decidere sul tuo corpo o la tua libertà al posto tuo.",

                // 4. Relazioni Tossiche
                "Una relazione tossica si riconosce da:\n\n" +
                        "- Controllo eccessivo sulle tue attività\n" +
                        "- Svalutazione costante delle tue opinioni\n" +
                        "- Isolamento da amici e familiari\n" +
                        "- Alternanza tra affetto e punizioni\n\n" +
                        "Uscirne richiede:\n" +
                        "1. Riconoscere il problema\n" +
                        "2. Chiedere supporto a centri specializzati\n" +
                        "3. Ricostruire la propria autonomia\n" +
                        "4. Eventualmente denunciare se ci sono reati",

                // 5. Difesa Personale
                "Difesa Personale - Prevenzione e Autoprotezione\n\n" +
                        "La difesa personale inizia con la consapevolezza e la prevenzione. " +
                        "Non si tratta solo di tecniche fisiche, ma di un approccio completo per evitare situazioni pericolose.\n\n" +

                        "Perché è importante?\n" +
                        "• Prevenzione: L'80% delle aggressioni può essere evitato riconoscendo i segnali d'allarme\n" +
                        "• Tecniche base: Movimenti semplici per liberarti da prese o blocchi\n" +
                        "• Diritto alla sicurezza: Proteggere il tuo spazio personale\n\n" +

                        "Cosa imparerai:\n" +
                        "1. Valutazione del rischio in ambienti pubblici\n" +
                        "2. Posizioni di sicurezza e allontanamento\n" +
                        "3. Uso di oggetti comuni per difesa (es. chiavi, spray)\n" +
                        "4. Come reagire a diverse tipologie di aggressione\n\n" +

                        "Esempio pratico:\n" +
                        "Se qualcuno ti afferra il polso:\n" +
                        "1. Mantieni la calma\n" +
                        "2. Ruota il braccio verso il pollice (punto debole)\n" +
                        "3. Allontanati e cerca aiuto\n\n" +

                        "<small>Ricorda: La difesa personale è un diritto legittimo di autoprotezione</small>"
        };

        int[] icons = {
                R.drawable.ic_consent,
                R.drawable.ic_medical_emergency,
                R.drawable.ic_legal,
                R.drawable.ic_psychological_support,
                R.drawable.ic_default_module
        };

        for (int i = 0; i < ids.length; i++) {
            int progress = ProgressManager.getProgress(this, ids[i]);
            moduleList.add(new EducationModule(
                    ids[i],
                    titles[i],
                    descriptions[i],
                    details[i],
                    icons[i],
                    progress
            ));
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QUIZ_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String moduleId = data.getStringExtra("MODULE_ID");
            int newProgress = data.getIntExtra("NEW_PROGRESS", 0);

            if (adapter != null) {
                adapter.updateModuleProgress(moduleId, newProgress);
            }

            for (EducationModule module : moduleList) {
                if (module.getId().equals(moduleId)) {
                    module.setProgress(newProgress);
                    break;
                }
            }
        }
    }


    private void onModuleClicked(EducationModule module) {
        Intent intent = new Intent(this, ModuleIntroActivity.class);
        intent.putExtra("MODULE_ID", module.getId());
        intent.putExtra("MODULE_TITLE", module.getTitle());
        intent.putExtra("MODULE_DETAIL", module.getDetailedContent());
        intent.putExtra("MODULE_IMAGE", module.getIconResId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
