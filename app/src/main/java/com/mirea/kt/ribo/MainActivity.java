package com.mirea.kt.ribo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private boolean passwordShowing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userlogin = findViewById(R.id.loginName);
        final EditText userpassword = findViewById(R.id.passwordName);

        final ImageView passwordIcon = findViewById(R.id.visibility);
        final TextView signUpBtn = findViewById(R.id.btnAuthorization);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passwordShowing){
                    passwordShowing = false;

                    userpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_action_visibility);
                }else{
                    passwordShowing =true;

                    userpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ic_action_visibility_off);

                }

                userpassword.setSelection(userpassword.length());
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userlogin = findViewById(R.id.loginName);
                EditText userpassword = findViewById(R.id.passwordName);
                if(v.getId() == R.id.btnAuthorization){

                    String server = "https://android-for-students.ru";
                    String serverPath = "/coursework/login.php";
                    HashMap<String, String> map = new HashMap();
                    map.put("lgn", userlogin.getText().toString());
                    map.put("pwd", userpassword.getText().toString());
                    map.put("g", "RIBO-01-22");
                    Log.d("MY_LOG",""+userlogin.getText().toString()+" "+userpassword.getText().toString());
                    HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);
                    Thread th = new Thread(httpRunnable);
                    th.start();
                    try {
                        th.join();
                    } catch (InterruptedException ex) {
                    } finally {
                        try {
                            String otvet = httpRunnable.getResponseBody();
                            Log.d("MY_LOG","" + otvet);
                            if (otvet == null) {

                                Toast.makeText(getApplicationContext(), "Отсутствует подключение к сети", Toast.LENGTH_LONG).show();
                            }else {
                                JSONObject jSONObject = new JSONObject(httpRunnable.getResponseBody());
                                String variKurs = jSONObject.toString();
                                Log.d("MY_LOG", "" + variKurs);
                                int result = jSONObject.getInt("result_code");
                                Log.d("MY_LOG","" + result);
                                if (result == 1) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("master_prefs", MODE_PRIVATE);
                                    String masterPassword = sharedPreferences.getString("master_password", null);
                                    if (masterPassword == null) {
                                        Intent intent = new Intent(getApplicationContext(), SetMasterPasswordActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(getApplicationContext(), MasterPasswordActivity.class);
                                        intent.putExtra("datakey", variKurs);
                                        startActivity(intent);
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                                }


                            }
                        } catch (JSONException jex) {
                        }

                    }
                }

            }
        });
    }
}