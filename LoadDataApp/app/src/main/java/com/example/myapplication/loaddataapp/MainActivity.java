package com.example.myapplication.loaddataapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.loaddataapp.Util.NetworkUtil;
import com.example.myapplication.loaddataapp.adapter.ListDataAdapter;
import com.example.myapplication.loaddataapp.databinding.ActivityMainBinding;
import com.example.myapplication.loaddataapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setErrorMessage(getString(R.string.error_message));

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.swipeContainer.setOnRefreshListener(() -> {
            activityMainBinding.swipeContainer.setRefreshing(false);

            mainViewModel.makeApiCall(NetworkUtil.isNetworkAvailable(this));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        activityMainBinding.setMainViewModel(mainViewModel);
        ListDataAdapter listDataAdapter = new ListDataAdapter(this);
        activityMainBinding.recycleList.setAdapter(listDataAdapter);
        activityMainBinding.recycleList.setLayoutManager(new LinearLayoutManager(this));

        mainViewModel.makeApiCall(NetworkUtil.isNetworkAvailable(this));
        mainViewModel.getItemsList().observe(this, itemElements -> {
               if (itemElements != null && itemElements.size() > 0) {
                   ListDataAdapter adapter = (ListDataAdapter) activityMainBinding.recycleList.getAdapter();

                   if (adapter != null) {
                       adapter.setListItems(itemElements);
                   }
                   activityMainBinding.toolbar.setTitle(mainViewModel.getTitle());
               }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.dispose();
    }
}
