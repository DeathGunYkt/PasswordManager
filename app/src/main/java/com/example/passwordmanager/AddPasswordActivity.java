package com.example.passwordmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {
    private EditText editTextServiceName, editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        editTextServiceName = findViewById(R.id.editTextServiceName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSavePassword = findViewById(R.id.buttonSavePassword);

        buttonSavePassword.setOnClickListener(view -> savePassword());
    }

    private void savePassword() {
        String serviceName = editTextServiceName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (serviceName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }

        // Сохраняем данные
        SharedPreferences sharedPreferences = getSharedPreferences("passwords", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(serviceName, username + ":" + password); // Сохранение как "логин:пароль"
        editor.apply();

        Toast.makeText(this, "Пароль успешно сохранен!", Toast.LENGTH_SHORT).show();
        finish(); // Закрываем экран добавления пароля и возвращаемся на главный экран
    }
}