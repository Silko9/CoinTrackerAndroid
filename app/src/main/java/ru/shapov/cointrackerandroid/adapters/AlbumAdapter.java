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
import ru.shapov.cointrackerandroid.models.Album;
import ru.shapov.cointrackerandroid.models.Coin;

public class AlbumAdapter extends ArrayAdapter<Album> {
    private Context context;
    private List<Album> albums;

    public AlbumAdapter(Context context, List<Album> albums) {
        super(context, 0, albums);
        this.context = context;
        this.albums = albums;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.albums_list_item, parent, false);
        }

        DataBaseHelper db = DataBaseHelper.getInstance();

        Album album = albums.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        ImageView albumImageView = convertView.findViewById(R.id.albumImageView);

        TextView countCoinTextView = convertView.findViewById(R.id.countCoinTextView);
        TextView dateCreateTextView = convertView.findViewById(R.id.dateCreateTextView);

        nameTextView.setText(album.getName());
        int countCoin = db.getCountCoinByAlbum(album.getId());
        if(album.getId() != 0) {
            countCoinTextView.setText("Кол-во монет: " + db.getCountCoinByAlbum(album.getId()));
            dateCreateTextView.setText("Дата создания: " + album.getDateCreate());
            albumImageView.setImageResource(R.drawable.album);
        }
        else {
            countCoinTextView.setText("Создать альбом");
            dateCreateTextView.setText("");
            albumImageView.setImageResource(R.drawable.add_album);
        }

        return convertView;
    }
}


