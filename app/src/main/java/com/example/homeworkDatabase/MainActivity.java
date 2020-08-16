package com.example.homeworkDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialize variable
    EditText editText;
    Button btAdd,btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList= database.mainDao().getAll();
        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        //Set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get string from edit text
                String sText = editText.getText().toString().trim();
                //Check condition
                if (!sText.equals("")){
                    //When text is not empty
                    //Initialize main data

                    MainData data = new MainData();
                    //Set text on main data
                    data.setText(sText);
                    //Insert text in database
                    database.mainDao().insert(data);
                    //Clear edit text
                    editText.setText("");
                    //Notify when data is inserted
                    dataList.clear();;
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();

                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete all data from database
                database.mainDao().reset(dataList);
                //Notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}