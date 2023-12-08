package ru.shapov.cointrackerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ru.shapov.cointrackerandroid.models.Coin;
import ru.shapov.cointrackerandroid.models.User;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseHelper db = DataBaseHelper.getInstance();

        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextInputEditText textViewLogin = findViewById(R.id.editTextUsername);
        TextInputEditText textViewPassword = findViewById(R.id.editTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLogin.setError(null);
                textViewPassword.setError(null);

                String login = textViewLogin.getText().toString();
                String password = textViewPassword.getText().toString();

                if(login.equals("")){
                    textViewLogin.setError("Введите логин");
                    return;
                }

                if(password.equals("")){
                    textViewPassword.setError("Введите пароль");
                    return;
                }

                User user = db.getUserByLogin(login);
                if(user != null && Objects.equals(user.getPassword(), password)){
                    db.setLogin(login);
                    Intent activityIntent = new Intent(LoginActivity.this, CoinListActivity.class);
                    startActivity(activityIntent);
                    return;
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Неверный логин или пароль", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button buttonExit = findViewById(R.id.buttonReturn);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

