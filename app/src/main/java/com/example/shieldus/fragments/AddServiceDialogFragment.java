package com.example.shieldus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shieldus.R;
import com.example.shieldus.activities.MapActivity;

public class AddServiceDialogFragment extends DialogFragment {

    private EditText nameInput, addressInput, phoneInput;
    private Spinner typeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nameInput = view.findViewById(R.id.editTextName);
        addressInput = view.findViewById(R.id.editTextAddress);
        phoneInput = view.findViewById(R.id.editTextPhone);
        typeSpinner = view.findViewById(R.id.spinnerType);

        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        String[] options = getResources().getStringArray(R.array.service_filters);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item, options);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> dismiss());

        btnSave.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String address = addressInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String type = typeSpinner.getSelectedItem().toString().toLowerCase();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (type.equalsIgnoreCase("Tutti")) {
                Toast.makeText(getContext(), "Seleziona un tipo di servizio valido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getActivity() instanceof MapActivity) {
                ((MapActivity) getActivity()).addNewService(name, address, phone, type);
            }

            dismiss();
        });
    }
}

