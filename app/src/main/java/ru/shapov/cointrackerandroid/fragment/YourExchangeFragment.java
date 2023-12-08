package ru.shapov.cointrackerandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.shapov.cointrackerandroid.DataBaseHelper;
import ru.shapov.cointrackerandroid.DialogueActivity;
import ru.shapov.cointrackerandroid.ExchangeActivity;
import ru.shapov.cointrackerandroid.LoginActivity;
import ru.shapov.cointrackerandroid.MainActivity;
import ru.shapov.cointrackerandroid.R;
import ru.shapov.cointrackerandroid.adapters.ExchangeAdapter;
import ru.shapov.cointrackerandroid.adapters.ItemExchangeAdapter;
import ru.shapov.cointrackerandroid.models.Exchange;
import ru.shapov.cointrackerandroid.models.ItemExchange;

public class YourExchangeFragment extends Fragment {
    private Long idUser;

    public YourExchangeFragment(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_your_exchange, container, false);

        DataBaseHelper db = DataBaseHelper.getInstance();
        List<ItemExchange> itemExchanges =  db.getItemExchangesMe(idUser);

        ListView exchangeListView = view.findViewById(R.id.itemExchangeListView);
        ItemExchangeAdapter adapter = new ItemExchangeAdapter(getContext(), itemExchanges);
        exchangeListView.setAdapter(adapter);

        TextView noExchangeTextView = view.findViewById(R.id.noExchangeTextView);
        Button buttonMessage = view.findViewById(R.id.buttonMessage);

        if (itemExchanges == null || itemExchanges.isEmpty()) {
            noExchangeTextView.setVisibility(View.VISIBLE);
            exchangeListView.setVisibility(View.GONE);
            buttonMessage.setVisibility(View.GONE);
        } else {
            noExchangeTextView.setVisibility(View.GONE);
            exchangeListView.setVisibility(View.VISIBLE);
            buttonMessage.setVisibility(View.VISIBLE);
        }

        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(getContext(), DialogueActivity.class);
                activityIntent.putExtra("receiving_id", idUser);
                startActivity(activityIntent);
            }
        });

        return view;
    }
}