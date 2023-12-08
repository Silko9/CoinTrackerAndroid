package ru.shapov.cointrackerandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.shapov.cointrackerandroid.DataBaseHelper;
import ru.shapov.cointrackerandroid.R;
import ru.shapov.cointrackerandroid.models.Coin;
import ru.shapov.cointrackerandroid.models.Dialogue;
import ru.shapov.cointrackerandroid.models.User;

public class DialogueAdapter extends ArrayAdapter<Dialogue> {
    private Context context;
    private List<Dialogue> dialogues;

    public DialogueAdapter(Context context, List<Dialogue> dialogues) {
        super(context, 0, dialogues);
        this.context = context;
        this.dialogues = dialogues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dialogue_list_item, parent, false);
        }

        DataBaseHelper db = DataBaseHelper.getInstance();

        Dialogue dialogue = dialogues.get(position);

        TextView userTextView = convertView.findViewById(R.id.userTextView);
        TextView countMessagesTextView = convertView.findViewById(R.id.countMessagesTextView);
        TextView dateLastMessageTextView = convertView.findViewById(R.id.dateLastMessageTextView);

        User user = db.getUserById(dialogue.getUserId());
        userTextView.setText(user.getLogin());
        countMessagesTextView.setText("Кол-во сообщений: " + dialogue.getCountMessages());
        dateLastMessageTextView.setText("Последнее сообщение: " + dialogue.getLastDateMessage());

        return convertView;
    }
}