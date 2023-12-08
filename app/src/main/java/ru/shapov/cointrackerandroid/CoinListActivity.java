package ru.shapov.cointrackerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ru.shapov.cointrackerandroid.adapters.CoinAdapter;
import ru.shapov.cointrackerandroid.models.Coin;

public class CoinListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);

        DataBaseHelper db = DataBaseHelper.getInstance();
        List<Coin> coins =  db.getAllCoins();

        ListView coinListView = findViewById(R.id.coinListView);
        CoinAdapter adapter = new CoinAdapter(this, coins);
        coinListView.setAdapter(adapter);

        coinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coin coin = coins.get(position);

                Intent intent = new Intent(CoinListActivity.this, CoinDetailActivity.class);
                intent.putExtra("coin_id", coin.getId());
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