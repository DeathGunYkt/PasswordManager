package com.example.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

        CheckBox checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);
        CheckBox checkBoxShowConfirmPassword = findViewById(R.id.checkBoxShowConfirmPassword);

        buttonRegister.setOnClickListener(view -> registerUser());

        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Показать пароль
                editTextNewPassword.setTransformationMethod(null);
            } else {
                // Скрыть пароль
                editTextNewPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            editTextNewPassword.setSelection(editTextNewPassword.length()); // Устанавливаем курсор в конец
        });

        checkBoxShowConfirmPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Показать подтверждение пароля
                editTextConfirmPassword.setTransformationMethod(null);
            } else {
                // Скрыть подтверждение пароля
                editTextConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            editTextConfirmPassword.setSelection(editTextConfirmPassword.length()); // Устанавливаем курсор в конец
        });
    }

    private void registerUser() {
        String email = editTextNewEmail.getText().toString().trim();
        String password = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Проверка на пустой электронный адрес
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка на совпадение паролей
        if (password.equals(confirmPassword)) {
            // Вытаскиваем SharedPreferences для проверки существующих пользователей
            SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);

            // Проверка, если email уже существует
            if (sharedPreferences.contains(email)) {
                Toast.makeText(this, "Этот логин уже существует", Toast.LENGTH_SHORT).show();
            } else {
                // Сохраняем логин и пароль
                sharedPreferences.edit()
                        .putString(email, password)
                        .apply(); // Сохраняем в SharedPreferences

                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }
}