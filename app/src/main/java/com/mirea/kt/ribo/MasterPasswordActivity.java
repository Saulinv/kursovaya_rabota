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

public class MasterPasswordActivity extends AppCompatActivity {

    EditText editTextMasterPassword;
    Button buttonSubmit;
    private static final String MASTER_PREFS = "master_prefs";
    private static final String MASTER_PASSWORD_KEY = "master_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_password);

        editTextMasterPassword = findViewById(R.id.editTextMasterPassword);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masterPassword = editTextMasterPassword.getText().toString();
                if (!masterPassword.isEmpty()) {
                    String hashedPassword = EncryptionHelper.hashPassword(masterPassword);
                    SharedPreferences sharedPreferences = getSharedPreferences(MASTER_PREFS, MODE_PRIVATE);
                    String maspassword = sharedPreferences.getString(MASTER_PASSWORD_KEY,"");
                    if (maspassword.equals(hashedPassword)){
                        Toast.makeText(MasterPasswordActivity.this, "Master Password saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MasterPasswordActivity.this, MainApplicationActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        editTextMasterPassword.getText().clear();
                        Toast.makeText(MasterPasswordActivity.this, "Неправильный мастер-пароль", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MasterPasswordActivity.this, "Please enter the master password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}