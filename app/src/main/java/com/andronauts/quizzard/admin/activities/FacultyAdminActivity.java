package com.andronauts.quizzard.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.andronauts.quizzard.R;
import com.andronauts.quizzard.admin.adapters.FacultyAdminRecycler;
import com.andronauts.quizzard.api.responseModels.faculty.FacultyGetListResponse;
import com.andronauts.quizzard.api.retrofit.RetrofitClient;
import com.andronauts.quizzard.dataModels.Faculty;
import com.andronauts.quizzard.databinding.ActivityFacultiesBinding;
import com.andronauts.quizzard.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class FacultyAdminActivity extends AppCompatActivity {
    //Using activity_faculty
    private ActivityFacultiesBinding binding;
    private SharedPrefs prefs;
    private FacultyAdminRecycler adapter;
    private List<Faculty> faculties;
    private List<Faculty> sortedFaculties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacultiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Changing toolbar color
        binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorAdmin));
        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sortList(editable.toString());
            }
        });

        loadData();

    }

    private void loadData(){
        RetrofitClient.getClient().adminGetFaculties(prefs.getToken(),true).enqueue(new Callback<FacultyGetListResponse>() {
            @Override
            public void onResponse(Call<FacultyGetListResponse> call, Response<FacultyGetListResponse> response) {
                if(response.isSuccessful()){
                    faculties = response.body().getFaculties();
                    sortedFaculties = new ArrayList<>(faculties);
                    setUpRecyclerView();
                }
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<FacultyGetListResponse> call, Throwable t) {

            }
        });
    }

    private void sortList(String key){
        sortedFaculties.clear();
        for(Faculty faculty:faculties){
            if(faculty.getName().toLowerCase().contains(key) ||
                    faculty.getFacultyId().toLowerCase().contains(key)){
                sortedFaculties.add(faculty);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FacultyAdminRecycler(this,sortedFaculties,true);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}