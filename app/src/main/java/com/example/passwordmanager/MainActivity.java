package com.example.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView listViewPasswords;
    private EditText editTextSearch;
    private ArrayList<String> passwordList;
    private HashMap<String, String> passwordDetails;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPasswords = findViewById(R.id.listViewPasswords);
        editTextSearch = findViewById(R.id.editTextSearch);
        Button buttonAddPassword = findViewById(R.id.buttonAddPassword);

        passwordList = new ArrayList<>();
        passwordDetails = new HashMap<>();

        buttonAddPassword.setOnClickListener(view -> startActivity(new Intent(this, AddPasswordActivity.class)));

        // Загрузка сохранённых паролей
        loadPasswords();

        // Фильтрация списка по введённому тексту
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Нажатие на элемент списка для просмотра деталей
        listViewPasswords.setOnItemClickListener((adapterView, view, position, id) -> {
            String serviceName = passwordList.get(position);
            String credentials = passwordDetails.get(serviceName);
            String[] parts = credentials.split(":");
            if (parts.length == 2) {
                Toast.makeText(this, "Логин: " + parts[0] + "\nПароль: " + parts[1], Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadPasswords() {
        SharedPreferences sharedPreferences = getSharedPreferences("passwords", MODE_PRIVATE);
        passwordList.clear();
        passwordDetails.clear();

        for (String key : sharedPreferences.getAll().keySet()) {
            String[] credentials = sharedPreferences.getString(key, "default:default").split(":");
            if (credentials.length == 2) {
                passwordList.add(key);
                passwordDetails.put(key, credentials[0] + ":" + credentials[1]); // Логин:Пароль
            }
        }

        // Если паролей нет, показать сообщение
        if (passwordList.isEmpty()) {
            Toast.makeText(this, "Вам еще ничего не сохранено.", Toast.LENGTH_SHORT).show();
        }

        // Обновляем адаптер
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passwordList);
        listViewPasswords.setAdapter(arrayAdapter);
    }

    private void filterList(String query) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (String password : passwordList) {
            if (password.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(password);
            }
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        listViewPasswords.setAdapter(arrayAdapter);
    }
}