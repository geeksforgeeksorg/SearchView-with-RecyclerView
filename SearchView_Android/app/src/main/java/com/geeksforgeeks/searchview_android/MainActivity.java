package com.geeksforgeeks.searchview_android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // creating variables for UI components
    private RecyclerView courseRV;
    private Toolbar toolbar;

    // variable for our adapter class and array list
    private CourseAdapter adapter;
    private ArrayList<CourseModel> courseModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing variables
        courseRV = findViewById(R.id.idRVCourses);
        toolbar = findViewById(R.id.toolbar);

        // setting the toolbar as the ActionBar
        setSupportActionBar(toolbar);

        // calling method to build recycler view
        buildRecyclerView();
    }

    // calling onCreateOptionsMenu to inflate our menu file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get the MenuInflater
        MenuInflater inflater = getMenuInflater();

        // inflate the menu
        inflater.inflate(R.menu.search, menu);

        // get the search menu item
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // get the SearchView from the menu item
        SearchView searchView = (SearchView) searchItem.getActionView();

        // set the on query text listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // call a method to filter your RecyclerView
                filter(newText);
                return false;
            }
        });
        return true;
    }

    // method to filter data based on query
    private void filter(String text) {
        // creating a new array list to filter data
        ArrayList<CourseModel> filteredlist = new ArrayList<>();

        // running a for loop to compare elements
        for (CourseModel item : courseModelArrayList) {
            // checking if the entered string matches any item of our recycler view
            if (item.getCourseName().toLowerCase().contains(text.toLowerCase())) {
                // adding matched item to the filtered list
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            // displaying a toast message if no data found
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // passing the filtered list to the adapter class
            adapter.filterList(filteredlist);
        }
    }

    // method to build RecyclerView
    private void buildRecyclerView() {
        // creating a new array list
        courseModelArrayList = new ArrayList<>();

        // adding data to the array list
        courseModelArrayList.add(new CourseModel("DSA", "DSA Self Paced Course"));
        courseModelArrayList.add(new CourseModel("JAVA", "JAVA Self Paced Course"));
        courseModelArrayList.add(new CourseModel("C++", "C++ Self Paced Course"));
        courseModelArrayList.add(new CourseModel("Python", "Python Self Paced Course"));
        courseModelArrayList.add(new CourseModel("Fork CPP", "Fork CPP Self Paced Course"));

        // initializing the adapter class
        adapter = new CourseAdapter(courseModelArrayList, MainActivity.this);

        // adding layout manager to the RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        // setting layout manager to the RecyclerView
        courseRV.setLayoutManager(manager);

        // setting adapter to the RecyclerView
        courseRV.setAdapter(adapter);
    }
}
