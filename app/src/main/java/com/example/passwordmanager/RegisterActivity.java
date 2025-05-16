package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextNewEmail, editTextNewPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = editTextNewEmail.getText().toString().trim();
        String password = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (password.equals(confirmPassword)) {
            // Сохраните логин и пароль
            getSharedPreferences("users", MODE_PRIVATE)
                    .edit()
                    .putString(email, password)
                    .apply(); // Сохраняем в SharedPreferences

            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }
}