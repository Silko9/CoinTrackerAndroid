package ru.shapov.cointrackerandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.shapov.cointrackerandroid.R;
import ru.shapov.cointrackerandroid.models.Coin;
import ru.shapov.cointrackerandroid.models.ItemExchange;

public class ItemExchangeAdapter extends ArrayAdapter<ItemExchange> {
    private Context context;
    private List<ItemExchange> itemExchanges;

    public ItemExchangeAdapter(Context context, List<ItemExchange> itemExchanges) {
        super(context, 0, itemExchanges);
        this.context = context;
        this.itemExchanges = itemExchanges;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exchange_list_item, parent, false);
        }

        ItemExchange itemExchange = itemExchanges.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        ImageView coinImageView = convertView.findViewById(R.id.coinImageView);

        TextView denominationCurrencyYearTextView = convertView.findViewById(R.id.denominationCurrencyYearTextView);
        TextView countryMintTextView = convertView.findViewById(R.id.countryMintTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);

        nameTextView.setText(itemExchange.getCoin().getName());
        String denominationCurrencyYear = itemExchange.getCoin().getDenomination() + " " + itemExchange.getCoin().getCurrency() + " " + itemExchange.getCoin().getYearMinting();
        denominationCurrencyYearTextView.setText(denominationCurrencyYear);

        String countryMint = itemExchange.getCoin().getCountry() + " " + itemExchange.getCoin().getMint();
        countryMintTextView.setText(countryMint);

        amountTextView.setText("Кол-во: " + itemExchange.getAmount());


        int resID = convertView.getResources().getIdentifier(itemExchange.getCoin().getPicturePath(), "drawable", context.getPackageName());

        if (resID != 0)
            coinImageView.setImageResource(resID);
        else
            coinImageView.setImageResource(R.drawable.default_coin_image);

        return convertView;
    }
}


