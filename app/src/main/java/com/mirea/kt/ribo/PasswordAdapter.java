package com.mirea.kt.ribo;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private List<Password> passwordList;
    private DBHelper dbHelper;
    private Context context;

    private static final String MASTER_PREFS = "master_prefs";
    private static final String MASTER_PASSWORD_KEY = "master_password";

    public PasswordAdapter(List<Password> passwordList, Context context) {
        this.passwordList = passwordList;
        this.dbHelper = new DBHelper(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_item, parent, false);
        return new PasswordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.loginTextView.setText(password.getUsername());
        holder.websiteTextView.setText(password.getWebsite());
        holder.categoryTextView.setText(password.getCategory());

        holder.deleteButton.setOnClickListener(v -> {
            int passwordId = password.getId();
            if (dbHelper.deletePassword(passwordId)) {
                passwordList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, passwordList.size());
                Toast.makeText(v.getContext(), "Пароль успешно удалён", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Ошибка удаления пароля", Toast.LENGTH_SHORT).show();
            }
        });

        holder.viewEncryptedPasswordButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(MASTER_PREFS, v.getContext().MODE_PRIVATE);
            String masterpassword = sharedPreferences.getString(MASTER_PASSWORD_KEY,"");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.Encrpassword);
            try {
                builder.setMessage(EncryptionHelper.decrypt(password.getPassword(),masterpassword));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            builder.setPositiveButton("OK", null);
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public static class PasswordViewHolder extends RecyclerView.ViewHolder {
        public TextView loginTextView, websiteTextView, categoryTextView;
        public Button deleteButton, viewEncryptedPasswordButton;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            loginTextView = itemView.findViewById(R.id.loginTextView);
            websiteTextView = itemView.findViewById(R.id.websiteTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewEncryptedPasswordButton = itemView.findViewById(R.id.viewEncryptedPasswordButton);
        }
    }
}
