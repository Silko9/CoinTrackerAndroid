package ru.shapov.cointrackerandroid.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

import ru.shapov.cointrackerandroid.R;
import ru.shapov.cointrackerandroid.models.Coin;

public class CoinAdapter extends ArrayAdapter<Coin> {
    private Context context;
    private List<Coin> coins;

    public CoinAdapter(Context context, List<Coin> coins) {
        super(context, 0, coins);
        this.context = context;
        this.coins = coins;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.coin_list_item, parent, false);
        }

        Coin coin = coins.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        ImageView coinImageView = convertView.findViewById(R.id.coinImageView);

        TextView denominationCurrencyYearTextView = convertView.findViewById(R.id.denominationCurrencyYearTextView);
        TextView countryMintTextView = convertView.findViewById(R.id.countryMintTextView);

        nameTextView.setText(coin.getName());
        String denominationCurrencyYear = coin.getDenomination() + " " + coin.getCurrency() + " " + coin.getYearMinting();
        denominationCurrencyYearTextView.setText(denominationCurrencyYear);

        String countryMint = coin.getCountry() + " " + coin.getMint();
        countryMintTextView.setText(countryMint);


        int resID = convertView.getResources().getIdentifier(coin.getPicturePath(), "drawable", context.getPackageName());

        if (resID != 0)
            coinImageView.setImageResource(resID);
        else
            coinImageView.setImageResource(R.drawable.default_coin_image);

        return convertView;
    }
}


