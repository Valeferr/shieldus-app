package com.example.shieldus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shieldus.R;
import com.example.shieldus.models.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private static final int USER_MESSAGE = 0;
    private static final int BOT_MESSAGE = 1;

    public ChatMessageAdapter(Context context, List<ChatMessage> messages) {
        super(context, 0, messages);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isUser() ? USER_MESSAGE : BOT_MESSAGE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatMessage message = getItem(position);
        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (viewType == USER_MESSAGE) {
                convertView = inflater.inflate(R.layout.item_message_user, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.item_message_bot, parent, false);
            }
        }

        TextView textViewMessage = convertView.findViewById(R.id.textViewMessage);
        TextView textViewTime = convertView.findViewById(R.id.textViewTime);

        textViewMessage.setText(message.getMessage());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        textViewTime.setText(sdf.format(new Date(message.getTimestamp())));

        return convertView;
    }
}