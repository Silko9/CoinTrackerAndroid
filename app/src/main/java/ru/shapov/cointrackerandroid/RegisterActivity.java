package ru.shapov.cointrackerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ru.shapov.cointrackerandroid.models.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DataBaseHelper db = DataBaseHelper.getInstance();

        Button buttonRegister = findViewById(R.id.buttonRegister);
        TextInputEditText textViewLogin = findViewById(R.id.editTextUsername);
        TextInputEditText textViewPassword = findViewById(R.id.editTextPassword);
        TextInputEditText textViewRepeatPassword = findViewById(R.id.editTextRepeatPassword);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRepeatPassword.setError(null);
                textViewPassword.setError(null);
                textViewLogin.setError(null);

                String login = textViewLogin.getText().toString();
                String password = textViewPassword.getText().toString();
                String repeatPassword = textViewRepeatPassword.getText().toString();

                if(login.equals("")){
                    textViewLogin.setError("Введите логин");
                    return;
                }

                if(password.equals("")){
                    textViewPassword.setError("Введите пароль");
                    return;
                }

                if(repeatPassword.equals("")){
                    textViewRepeatPassword.setError("Введите пароль");
                    return;
                }

                User user = db.getUserByLogin(login);
                if (user != null) {
                    textViewLogin.setError("Данный логин занят");
                    return;
                }

                if(!password.equals(repeatPassword)){
                    textViewRepeatPassword.setError("Пароли не совпадают");
                    return;
                }

                db.createUser(login, password);
                db.setLogin(login);

                Intent activityIntent = new Intent(RegisterActivity.this, CoinListActivity.class);
                startActivity(activityIntent);
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