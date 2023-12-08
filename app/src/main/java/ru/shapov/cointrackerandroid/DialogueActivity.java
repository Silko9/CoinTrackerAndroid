package ru.shapov.cointrackerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ru.shapov.cointrackerandroid.adapters.MessageAdapter;
import ru.shapov.cointrackerandroid.models.Message;
import ru.shapov.cointrackerandroid.models.User;

public class DialogueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages;
    private DataBaseHelper db = DataBaseHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);

        recyclerView = findViewById(R.id.recyclerViewMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        User user = db.getUserByLogin(db.getLogin());
        messages = db.getAllMessages(user.getId(), getIntent().getLongExtra("receiving_id", -1));
        messageAdapter = new MessageAdapter(messages);
        recyclerView.setAdapter(messageAdapter);

        User userReceiving = db.getUserById(getIntent().getLongExtra("receiving_id", -1));
        setTitle("Диалог " + userReceiving.getLogin());

        EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSendMessage = findViewById(R.id.buttonSendMessage);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    Message message = new Message(messageText, user.getId());
                    sendMessage(message);
                    editTextMessage.setText("");
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void sendMessage(Message message) {
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
        scrollToBottom();

        User user = db.getUserByLogin(db.getLogin());
        db.sendMessage(user.getId(), getIntent().getLongExtra("receiving_id", -1), message.getMessage());
    }

    private void scrollToBottom() {
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent activityIntent = null;

        if (item.getItemId() == R.id.action_coin_list)
            activityIntent = new Intent(this, CoinListActivity.class);

        if (item.getItemId() == R.id.action_exit) {
            DataBaseHelper.getInstance().getUserByLogin("");
            activityIntent = new Intent(this, MainActivity.class);
        }
        if (item.getItemId() == R.id.exchange)
            activityIntent = new Intent(this, ExchangeListActivity.class);

        if (item.getItemId() == R.id.dialogue)
            activityIntent = new Intent(this, DialoguesListActivity.class);

        if (item.getItemId() == R.id.albums)
            activityIntent = new Intent(this, AlbumsListActivity.class);

        if(activityIntent != null){
            startActivity(activityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
