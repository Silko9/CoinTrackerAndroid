package ru.shapov.cointrackerandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ru.shapov.cointrackerandroid.adapters.AlbumAdapter;
import ru.shapov.cointrackerandroid.adapters.CoinAdapter;
import ru.shapov.cointrackerandroid.models.Album;
import ru.shapov.cointrackerandroid.models.Coin;

public class AlbumsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_list);

        DataBaseHelper db = DataBaseHelper.getInstance();
        List<Album> albums = db.getAllAlbum();
        albums.add(0, new Album(0L, 0L, "", ""));

        ListView albumListView = findViewById(R.id.albumsListView);
        AlbumAdapter adapter = new AlbumAdapter(this, albums);
        albumListView.setAdapter(adapter);

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album = albums.get(position);

                if(album.getId() != 0) {
                    Intent intent = new Intent(AlbumsListActivity.this, AlbumActivity.class);
                    intent.putExtra("album_id", album.getId());
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlbumsListActivity.this);
                    final EditText input = new EditText(AlbumsListActivity.this);

                    builder.setTitle("Создание альбома")
                            .setMessage("Введите название альбома:")
                            .setView(input)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String userInput = input.getText().toString();
                                    DataBaseHelper.getInstance().deleteAlbumById(getIntent().getLongExtra("album_id", -1));
                                    db.createAlbum(userInput);
                                    dialog.cancel();

                                    Intent activityIntent = new Intent(AlbumsListActivity.this, AlbumsListActivity.class);
                                    startActivity(activityIntent);
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
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