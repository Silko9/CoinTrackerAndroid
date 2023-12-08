package ru.shapov.cointrackerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.shapov.cointrackerandroid.models.Coin;
import ru.shapov.cointrackerandroid.models.RelUserCoin;
import ru.shapov.cointrackerandroid.models.User;


public class CoinDetailActivity extends AppCompatActivity {
    RelUserCoin relUserCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

        Long coinId = getIntent().getLongExtra("coin_id", -1);
        DataBaseHelper db = DataBaseHelper.getInstance();
        if (coinId == -1) return;

        Coin coin = db.getCoinById(coinId);
        User user = db.getUserByLogin(db.getLogin());
        showCoin(db, coin, user.getId());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button decreaseButton1 = findViewById(R.id.decreaseButton1);
        Button increaseButton1 = findViewById(R.id.increaseButton1);
        Button decreaseButton2 = findViewById(R.id.decreaseButton2);
        Button increaseButton2 = findViewById(R.id.increaseButton2);
        Button decreaseButton3 = findViewById(R.id.decreaseButton3);
        Button increaseButton3 = findViewById(R.id.increaseButton3);

        decreaseButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relUserCoin.getStockAmount() == 0) return;
                db.editAmountRelUserCoin(user.getId(), coinId, 1, -1);
                showCoin(db, coin, user.getId());
            }
        });
        increaseButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.editAmountRelUserCoin(user.getId(), coinId, 1, 1);
                showCoin(db, coin, user.getId());
            }
        });
        decreaseButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relUserCoin.getExchangeAmount() == 0 || relUserCoin.getNeedAmount() > 0) return;
                db.editAmountRelUserCoin(user.getId(), coinId, 2, -1);
                showCoin(db, coin, user.getId());
            }
        });
        increaseButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( relUserCoin.getNeedAmount() > 0) return;
                db.editAmountRelUserCoin(user.getId(), coinId, 2, 1);
                showCoin(db, coin, user.getId());
            }
        });
        decreaseButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relUserCoin.getNeedAmount() == 0 || relUserCoin.getExchangeAmount() > 0) return;
                db.editAmountRelUserCoin(user.getId(), coinId, 3, -1);
                showCoin(db, coin, user.getId());
            }
        });
        increaseButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relUserCoin.getExchangeAmount() > 0) return;
                db.editAmountRelUserCoin(user.getId(), coinId, 3, 1);
                showCoin(db, coin, user.getId());
            }
        });
    }

    private void showCoin(DataBaseHelper db, Coin coin, Long userId){
        relUserCoin = db.getRelUserCoin(userId, coin.getId());

        ImageView detailCoinImageView = findViewById(R.id.detailCoinImageView);
        TextView detailNameTextView = findViewById(R.id.detailNameTextView);
        TextView detailDenominationTextView = findViewById(R.id.detailDenominationTextView);
        TextView detailCurrencyTextView = findViewById(R.id.detailCurrencyTextView);
        TextView detailCountryTextView = findViewById(R.id.detailCountryTextView);
        TextView detailMintTextView = findViewById(R.id.detailMintTextView);
        TextView detailYearTextView = findViewById(R.id.detailYearTextView);
        TextView detailDescriptionTextView = findViewById(R.id.detailDescriptionTextView);

        TextView detailStockAmountView = findViewById(R.id.detailStockAmountTextView);
        TextView detailExchangeAmountView = findViewById(R.id.detailExchangeAmountTextView);
        TextView detailNeedAmountView = findViewById(R.id.detailNeedTextView);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) detailCoinImageView.getLayoutParams();

        layoutParams.height = (int) getResources().getDimension(R.dimen.max_image_height);
        detailCoinImageView.setLayoutParams(layoutParams);

        detailCoinImageView.setImageResource(getResources().getIdentifier(coin.getPicturePath(), "drawable", getPackageName()));
        detailNameTextView.setText("Название: " + coin.getName());
        detailDenominationTextView.setText("Номинал: " + coin.getDenomination());
        detailCurrencyTextView.setText("Валюта: " + coin.getCurrency());
        detailCountryTextView.setText("Страна: " + coin.getCountry());
        detailMintTextView.setText("Монетный двор: " + coin.getMint());
        detailYearTextView.setText("Год выпуска: " + coin.getYearMinting());
        detailDescriptionTextView.setText("Описание: " + coin.getDescription());

        detailStockAmountView.setText("В наличие: " + relUserCoin.getStockAmount());
        detailExchangeAmountView.setText("На обмен: " + relUserCoin.getExchangeAmount());
        detailNeedAmountView.setText("Нуждаюсь: " + relUserCoin.getNeedAmount());
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

