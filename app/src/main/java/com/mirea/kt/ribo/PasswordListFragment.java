package com.mirea.kt.ribo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PasswordListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";

    private String category;
    private DBHelper dbHelper;
    private PasswordAdapter passwordAdapter;
    private RecyclerView recyclerView;

    public PasswordListFragment() {
    }

    public static PasswordListFragment newInstance(String category) {
        PasswordListFragment fragment = new PasswordListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        List<Password> passwordList = dbHelper.getPasswordsByCategory(category);
        passwordAdapter = new PasswordAdapter(passwordList, getContext());
        recyclerView.setAdapter(passwordAdapter);

        return view;
    }
}
