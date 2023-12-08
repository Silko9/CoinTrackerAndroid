package ru.shapov.cointrackerandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ru.shapov.cointrackerandroid.adapters.AlbumAdapter;
import ru.shapov.cointrackerandroid.adapters.CoinAdapter;
import ru.shapov.cointrackerandroid.models.Album;
import ru.shapov.cointrackerandroid.models.Coin;

public class AlbumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);

        DataBaseHelper db = DataBaseHelper.getInstance();
        Long albumId = getIntent().getLongExtra("album_id", -1);
        List<Coin> coins =  db.getAllCoinByAlbum(albumId);
        Album album = db.getAlbumById(albumId);
        setTitle(album.getName());
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

                Intent intent = new Intent(AlbumActivity.this, CoinDetailActivity.class);
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
        inflater.inflate(R.menu.menu_album, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent activityIntent = null;

        if (item.getItemId() == R.id.add_coin) {
            activityIntent = new Intent(this, AddCoinToAlbumActivity.class);
            activityIntent.putExtra("album_id", getIntent().getLongExtra("album_id", -1));
            activityIntent.putExtra("is_add", 1);
        }

        if (item.getItemId() == R.id.remove_coin) {
            activityIntent = new Intent(this, AddCoinToAlbumActivity.class);
            activityIntent.putExtra("album_id", getIntent().getLongExtra("album_id", -1));
            activityIntent.putExtra("is_add", 0);
        }

        if (item.getItemId() == R.id.delete_album) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Удалить альбом")
                    .setMessage("Вы точно хотите удалить альбом?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHelper.getInstance().deleteAlbumById(getIntent().getLongExtra("album_id", -1));
                            Intent activityIntent = new Intent(AlbumActivity.this, AlbumsListActivity.class);
                            startActivity(activityIntent);
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        if (item.getItemId() == R.id.comeback)
            activityIntent = new Intent(this, AlbumsListActivity.class);

        if(activityIntent != null){
            startActivity(activityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
