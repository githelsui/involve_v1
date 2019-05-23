package com.example.android.recyclerviewproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecycler;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ArrayList<ExampleItem> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = new ArrayList<>();
        addItemToList();
        initRecyclerView();
    }

    private void addItemToList(){
        ExampleItem temp = new ExampleItem(R.drawable.ic_imageview, "temp1", "temp2");
                for(int i = 0; i < 10; i++){
                    myList.add(temp);
                }
    }

    private void initRecyclerView(){
        //initialize all objects
        myRecycler = findViewById(R.id.recycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList);

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);

    }
}
