package com.mirea.kt.ribo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainApplicationActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ImageButton imageButton;


    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(R.string.mankey);
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        fragmentManager = getSupportFragmentManager();

        SharedPreferences sharedPreferences = getSharedPreferences("master_prefs", MODE_PRIVATE);
        String masterPassword = sharedPreferences.getString("master_password", null);
        if (masterPassword == null) {
            Intent intent = new Intent(this, MasterPasswordActivity.class);
            startActivity(intent);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_banks) {
                    selectedFragment = PasswordListFragment.newInstance("Банки");
                } else if (itemId == R.id.navigation_mailac) {
                    selectedFragment = PasswordListFragment.newInstance("Почтовые аккаунты");
                } else if (itemId == R.id.navigation_sociaclnet) {
                    selectedFragment = PasswordListFragment.newInstance("Социальные сети");
                } else if (itemId == R.id.navigation_other) {
                    selectedFragment = PasswordListFragment.newInstance("Прочее");
                }
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });



        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_banks);
        }

        // FloatingActionButton setup
        imageButton = findViewById(R.id.btn_open_addloginactivity);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainApplicationActivity.this, AddPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("UseSupportActionBar")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.generatekeys){
            startActivity(new Intent(this, GeneratePasswordActivity.class));
            return true;
        }else{return super.onOptionsItemSelected(item);}
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
