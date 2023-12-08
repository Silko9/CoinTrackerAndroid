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

import ru.shapov.cointrackerandroid.adapters.CoinAdapter;
import ru.shapov.cointrackerandroid.models.Album;
import ru.shapov.cointrackerandroid.models.Coin;

public class AddCoinToAlbumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);

        DataBaseHelper db = DataBaseHelper.getInstance();
        Long albumId = getIntent().getLongExtra("album_id", -1);
        boolean isAdd = getIntent().getIntExtra("is_add", -1) == 1;
        List<Coin> coins;
        if(isAdd)
            coins =  db.getAllCoinNoTheAlbum(albumId);
        else
            coins = db.getAllCoinByAlbum(albumId);
        TextView emptyTextView = findViewById(R.id.emptyCoinTextView);

        ListView coinListView = findViewById(R.id.coinListView);
        CoinAdapter adapter = new CoinAdapter(this, coins);
        coinListView.setAdapter(adapter);

        if (coinListView.getCount() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }

        coinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coin coin = coins.get(position);
                if(isAdd)
                    db.addCoinToAlbum(coin.getId(), albumId);
                else
                    db.removeCoinToAlbum(coin.getId(), albumId);

                Intent intent = new Intent(AddCoinToAlbumActivity.this, AlbumActivity.class);
                intent.putExtra("album_id", albumId);
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
