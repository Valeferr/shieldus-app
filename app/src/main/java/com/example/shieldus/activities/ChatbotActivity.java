package com.example.shieldus.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import com.example.shieldus.R;
import com.example.shieldus.adapters.ChatMessageAdapter;
import com.example.shieldus.models.ChatMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatbotActivity extends BaseActivity {

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private Spinner questionSpinner;
    private List<ChatMessage> chatMessages;
    private ChatMessageAdapter chatAdapter;

    private static final Map<String, String> LEGAL_RESPONSES = new HashMap<String, String>() {{
        put("discriminazione sul lavoro", "In caso di discriminazione sul lavoro, puoi procedere con:\n\n1. Raccolta di prove (email, messaggi, testimonianze)\n2. Invio di una lettera di diffida\n3. Denuncia all'ispettorato del lavoro\n\nEcco i contatti utili:\n- Centro Antidiscriminazione: 800 123 456\n- Patronato INCA: 800 987 654");
        put("molestie sessuali", "Per casi di molestie sessuali sul lavoro:\n\n1. Documenta ogni episodio con data e dettagli\n2. Rivolgiti al responsabile HR\n3. Se non risolto, denuncia alle autorità\n\nSupporto psicologico: 800 555 123");
        put("licenziamento ingiusto", "Per licenziamento ingiusto:\n\n1. Verifica se hai ricevuto il preavviso\n2. Controlla le motivazioni nella lettera\n3. Rivolgiti a un sindacato o avvocato del lavoro\n\nConsulta gratuita con:\n- CGIL: 800 111 222");
        put("stipendio non pagato", "Se lo stipendio non viene pagato:\n\n1. Richiedi formalmente il pagamento\n2. Se non ricevuto in 15 giorni, denuncia all'Ispettorato del Lavoro\n\nAssistenza legale gratuita: 800 333 444");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        setupNavigationDrawer();
        setToolbarTitle("Chatbot Legale");

        initializeViews();
        setupQuestionSpinner();
        setupChatList();
        setupSendButton();

        addBotMessage(getString(R.string.chatbot_welcome_message));
    }

    private void initializeViews() {
        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        questionSpinner = findViewById(R.id.questionSpinner);
    }

    private void setupQuestionSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.predifined_legal_questions,
                R.layout.spinner_item
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        questionSpinner.setAdapter(spinnerAdapter);

        questionSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedQuestion = parent.getItemAtPosition(position).toString();
                    messageEditText.setText(selectedQuestion);
                    sendMessage();
                    questionSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private void setupChatList() {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatMessageAdapter(this, chatMessages);
        chatListView.setAdapter(chatAdapter);
    }

    private void setupSendButton() {
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            addUserMessage(message);
            messageEditText.setText("");

            messageEditText.postDelayed(() -> processUserMessage(message), 1000);
        }
    }

    private void processUserMessage(String userMessage) {
        String lowerCaseMsg = userMessage.toLowerCase();

        String response = getString(R.string.chatbot_default_response);

        for (Map.Entry<String, String> entry : LEGAL_RESPONSES.entrySet()) {
            if (lowerCaseMsg.contains(entry.getKey())) {
                response = entry.getValue();
                break;
            }
        }

        addBotMessage(response);

        if (response.equals(getString(R.string.chatbot_default_response))) {
            addBotMessage(getString(R.string.chatbot_suggestions));
        }
    }

    private void addUserMessage(String message) {
        chatMessages.add(new ChatMessage(message, true, System.currentTimeMillis()));
        chatAdapter.notifyDataSetChanged();
        scrollChatToBottom();
    }

    private void addBotMessage(String message) {
        chatMessages.add(new ChatMessage(message, false, System.currentTimeMillis()));
        chatAdapter.notifyDataSetChanged();
        scrollChatToBottom();
    }

    private void scrollChatToBottom() {
        chatListView.post(() -> chatListView.smoothScrollToPosition(chatAdapter.getCount() - 1));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_chatbot) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }
}