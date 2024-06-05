package com.mirea.kt.ribo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GeneratePasswordActivity extends AppCompatActivity {
    TextView tv_passowrd;
    Button btn_generate, btn_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        tv_passowrd = findViewById(R.id.tvPassword);
        btn_generate = findViewById(R.id.btnGenerate);
        btn_copy = findViewById(R.id.btnCopy);


        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = 12;

                tv_passowrd.setText(GetPassword(length));

            }
        });
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text password", tv_passowrd.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(GeneratePasswordActivity.this, "Пароль сохранен в буфер обмена", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String GetPassword(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < length; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }
}