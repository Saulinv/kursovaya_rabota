package com.mirea.kt.ribo;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetMasterPasswordActivity extends AppCompatActivity {

    EditText editTextMasterPassword;
    Button buttonSubmit;
    private static final String MASTER_PREFS = "master_prefs";
    private static final String MASTER_PASSWORD_KEY = "master_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_master_password);

        editTextMasterPassword = findViewById(R.id.editTextSetMasterPassword);
        buttonSubmit = findViewById(R.id.btnSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masterPassword = editTextMasterPassword.getText().toString();
                if (!masterPassword.isEmpty()) {
                    String hashedPassword = EncryptionHelper.hashPassword(masterPassword);
                    SharedPreferences sharedPreferences = getSharedPreferences(MASTER_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(MASTER_PASSWORD_KEY, hashedPassword);
                    editor.apply();
                    Toast.makeText(SetMasterPasswordActivity.this, "Мастер-пароль успешно сохранен", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SetMasterPasswordActivity.this, MainApplicationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SetMasterPasswordActivity.this, "Пожалуйста введите мастер-пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}