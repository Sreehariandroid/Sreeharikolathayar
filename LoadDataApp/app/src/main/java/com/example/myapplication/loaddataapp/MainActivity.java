package com.example.myapplication.loaddataapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.loaddataapp.adapter.ListDataAdapter;
import com.example.myapplication.loaddataapp.databinding.ActivityMainBinding;
import com.example.myapplication.loaddataapp.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.swipeContainer.setOnRefreshListener(() -> {
            activityMainBinding.swipeContainer.setRefreshing(false);
            mainViewModel.getItemList().clear();
            mainViewModel.makeApiCall();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);
        ListDataAdapter listDataAdapter = new ListDataAdapter(this);
        activityMainBinding.recycleList.setAdapter(listDataAdapter);
        activityMainBinding.recycleList.setLayoutManager(new LinearLayoutManager(this));
        mainViewModel.addObserver(this);
        mainViewModel.makeApiCall();

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

    /**
     *
     * @param observable MainViewModel observable instance
     * @param obj an argument passed to the <code>notifyObservers</code> method.
     * This method will be called once the rest api call for fetch json data is success.
     */
    @Override
    public void update(final Observable observable, final Object obj) {

        if (observable instanceof MainViewModel) {
            ListDataAdapter listDataAdapter = (ListDataAdapter) activityMainBinding.recycleList.getAdapter();
            MainViewModel mainViewModel = (MainViewModel) observable;

            if (listDataAdapter != null) {
                listDataAdapter.setListItems(mainViewModel.getItemList());
            }
            activityMainBinding.toolbar.setTitle(mainViewModel.getTitle());
        }
    }
}
