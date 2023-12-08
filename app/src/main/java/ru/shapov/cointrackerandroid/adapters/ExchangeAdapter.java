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
import ru.shapov.cointrackerandroid.models.Exchange;
import ru.shapov.cointrackerandroid.models.User;

public class ExchangeAdapter extends ArrayAdapter<Exchange> {
    private Context context;
    private List<Exchange> exchanges;
    private DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance();

    public ExchangeAdapter(Context context, List<Exchange> exchanges) {
        super(context, 0, exchanges);
        this.context = context;
        this.exchanges = exchanges;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exchange_list_item, parent, false);
        }

        Exchange exchange = exchanges.get(position);
        User user = dataBaseHelper.getUserById(exchange.getIdUser());

        TextView loginTextView = convertView.findViewById(R.id.loginTextView);
        TextView heHasForExchangeTextView = convertView.findViewById(R.id.heHasForExchangeTextView);
        TextView weHaveForExchangeTextView = convertView.findViewById(R.id.weHaveForExchangeTextView);

        loginTextView.setText(user.getLogin());
        heHasForExchangeTextView.setText("У него на обмен: " + exchange.getHeHasForExchange());
        weHaveForExchangeTextView.setText("У вас на обмен: " + exchange.getWeHaveForExchange());

        return convertView;
    }
}
