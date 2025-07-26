package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shieldus.R;
import com.example.shieldus.adapters.EducationModuleAdapter;
import com.example.shieldus.models.EducationModule;
import com.example.shieldus.persistence.ProgressManager;
import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends BaseActivity {

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

        adapter = new EducationModuleAdapter(moduleList, this::onModuleClicked);
        modulesRecyclerView.setAdapter(adapter);
    }

    private void createSampleModules() {
        moduleList.clear();

        String[] ids = {"1", "2", "3"};
        String[] titles = {"Consenso nelle relazioni", "Salute Sessuale", "Diritti Legali"};
        String[] descriptions = {"Impara a riconoscere e rispettare i confini nelle relazioni",
                "Informazioni essenziali sulla salute e sicurezza sessuale",
                "Conosci i tuoi diritti in caso di discriminazione o violenza", };
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
                        "Ricorda: nessuno ha il diritto di decidere sul tuo corpo o la tua libertà al posto tuo."
        };

        int[] icons = {R.drawable.ic_consent, R.drawable.ic_health, R.drawable.ic_legal};

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

    public void updateModuleProgress(String moduleId, int newProgress) {
        for (EducationModule module : moduleList) {
            if (module.getId().equals(moduleId)) {
                EducationModule updatedModule = new EducationModule(
                        module.getId(),
                        module.getTitle(),
                        module.getDescription(),
                        module.getDetailedContent(),
                        module.getIconResId(),
                        newProgress
                );

                int position = moduleList.indexOf(module);
                moduleList.set(position, updatedModule);

                if (adapter != null) {
                    adapter.notifyItemChanged(position);
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String moduleId = data.getStringExtra("MODULE_ID");
            int newProgress = data.getIntExtra("NEW_PROGRESS", 0);
            updateModuleProgress(moduleId, newProgress);
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
