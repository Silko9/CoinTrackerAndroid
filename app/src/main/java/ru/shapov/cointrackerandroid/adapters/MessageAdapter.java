package ru.shapov.cointrackerandroid.adapters;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ru.shapov.cointrackerandroid.CoinListActivity;
import ru.shapov.cointrackerandroid.DataBaseHelper;
import ru.shapov.cointrackerandroid.DialogueActivity;
import ru.shapov.cointrackerandroid.R;
import ru.shapov.cointrackerandroid.models.Message;
import ru.shapov.cointrackerandroid.models.User;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView dateText;
        Long userId;
        Context context;


        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.textMessage);
            dateText = itemView.findViewById(R.id.textTime);
            this.context = itemView.getContext();
        }

        public void bind(Message message) {
            DataBaseHelper db = DataBaseHelper.getInstance();
            User user = db.getUserByLogin(db.getLogin());
            if(Objects.equals(user.getId(), message.getUserId()))
                messageText.setBackground(ContextCompat.getDrawable(context, R.drawable.message_background));
            else
                messageText.setBackground(ContextCompat.getDrawable(context, R.drawable.message_background_stranger));
            messageText.setText(message.getMessage());
            dateText.setText(message.getTime());
            userId = message.getUserId();
        }
    }
}

