package com.example.shieldus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shieldus.R;
import com.example.shieldus.models.EducationModule;
import java.util.List;

public class EducationModuleAdapter extends RecyclerView.Adapter<EducationModuleAdapter.ModuleViewHolder> {

    private final List<EducationModule> modules;
    private final OnModuleClickListener listener;
    private final boolean isAnonymous;

    public interface OnModuleClickListener {
        void onModuleClick(EducationModule module);
    }

    public EducationModuleAdapter(List<EducationModule> modules, OnModuleClickListener listener, boolean isAnonymous) {
        this.modules = modules;
        this.listener = listener;
        this.isAnonymous = isAnonymous;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        EducationModule module = modules.get(position);

        holder.title.setText(module.getTitle());
        holder.description.setText(module.getDescription());
        holder.icon.setImageResource(module.getIconResId());

        if (!isAnonymous) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressText.setVisibility(View.VISIBLE);

            if (module.isCompleted()) {
                holder.itemView.setAlpha(0.6f);
                holder.progressText.setText("Completato!");
            } else {
                holder.itemView.setAlpha(1f);
                holder.progressText.setText(module.getProgress() + "% completato");
            }

            holder.progressBar.setProgress(module.getProgress());
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.progressText.setVisibility(View.GONE);
            holder.itemView.setAlpha(1f);
        }

        holder.itemView.setOnClickListener(v -> listener.onModuleClick(module));
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public void updateModuleProgress(String moduleId, int progress) {
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(moduleId)) {
                modules.get(i).setProgress(progress);
                notifyItemChanged(i);
                break;
            }
        }
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, progressText;
        ImageView icon;
        ProgressBar progressBar;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.moduleTitle);
            description = itemView.findViewById(R.id.moduleDescription);
            icon = itemView.findViewById(R.id.moduleIcon);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressText = itemView.findViewById(R.id.progressText);
        }
    }
}