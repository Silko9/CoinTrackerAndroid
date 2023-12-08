package ru.shapov.cointrackerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ru.shapov.cointrackerandroid.adapters.ExchangeAdapter;
import ru.shapov.cointrackerandroid.models.Exchange;

public class ExchangeListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_list);

        DataBaseHelper db = DataBaseHelper.getInstance();
        List<Exchange> exchanges =  db.getExchanges(db.getLogin());

        ListView exchangeListView = findViewById(R.id.exchangeListView);
        ExchangeAdapter adapter = new ExchangeAdapter(this, exchanges);
        exchangeListView.setAdapter(adapter);

        TextView noExchangeTextView = findViewById(R.id.noExchangeTextView);

        if (exchangeListView.getAdapter() == null || exchangeListView.getAdapter().getCount() == 0) {
            noExchangeTextView.setVisibility(View.VISIBLE);
            exchangeListView.setVisibility(View.GONE);
        } else {
            noExchangeTextView.setVisibility(View.GONE);
            exchangeListView.setVisibility(View.VISIBLE);
        }

        exchangeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exchange exchange = exchanges.get(position);

                Intent intent = new Intent(ExchangeListActivity.this, ExchangeActivity.class);
                intent.putExtra("user_id", exchange.getIdUser());
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
