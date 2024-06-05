package com.mirea.kt.ribo;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddPasswordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText etUsername,etPassword, etWebsite;
    private DBHelper dbHelper;

    private Spinner spinnerCategory;

    private String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_password);
        etUsername = findViewById(R.id.et_username);
        etPassword  = findViewById(R.id.et_password);
        etWebsite = findViewById(R.id.et_wedsite);
        spinnerCategory = findViewById(R.id.spCategory);
        Button btnAdd = findViewById(R.id.btn_add);

        dbHelper = new DBHelper(getApplicationContext());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String website = etWebsite.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                try {
                    String encryptedPassword = EncryptionHelper.encrypt(password, masterPassword);
                    dbHelper.addPassword(username, encryptedPassword, website, category);
                    Toast.makeText(AddPasswordActivity.this, "Пароль успешно сохранен", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddPasswordActivity.this, "Ошибка сохранения пароля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter adapterspin = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.spincategory)){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(position == 0){
                    TextView textView = new TextView(AddPasswordActivity.this);
                    textView.setHeight(0);
                    textView.setVisibility(View.GONE);
                    convertView =textView;
                    return convertView;
                }else{
                    return super.getDropDownView(position, null, parent);
                }

            }
        };
        spinnerCategory.setAdapter(adapterspin);

        SharedPreferences sharedPreferences = getSharedPreferences("master_prefs", MODE_PRIVATE);
        masterPassword = sharedPreferences.getString("master_password", null);

        if (masterPassword == null) {
            Toast.makeText(this, "Мастер-пароль не установлен", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerAdapter.flag = true;
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
