package com.moneytap.wikisearch;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.moneytap.models.Page;
import com.moneytap.network.NetworkService;
import com.moneytap.utils.Utils;
import com.moneytap.wikisearch.adapter.PageAdapter;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    static final String TAG=MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private PageAdapter pageAdapter;
    private Toolbar toolbar;
    private SearchView searchView;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compositeDisposable = new CompositeDisposable();
        initializeViews();
        initializeRecyclerView(new ArrayList<Page>());
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchQueryResults(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchQueryResults(newText);
                return false;
            }
     });
        return true;
    }

    private void initializeViews(){
        recyclerView=findViewById(R.id.recycler_view);
        toolbar=findViewById(R.id.toolbar);
    }

    private void initializeRecyclerView(List<Page> pageList){
        pageAdapter = new PageAdapter(this,pageList);
        recyclerView.setAdapter(pageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchQueryResults(final String query){
        Log.d(TAG,"Query Text:"+query);
            compositeDisposable.add(NetworkService.getInstance().queryArticle(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<com.moneytap.models.Response>() {
                        @Override
                        public void accept(com.moneytap.models.Response response) throws Exception {
                            Log.d(TAG, "response: " + response);
                            if (response != null) {
                                pageAdapter.setPagesList(response.getQuery().getPagesList());
                                pageAdapter.notifyDataSetChanged();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d(TAG, "error: " + throwable);
                        }
                    }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
                if(id==R.id.action_search)
                    return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
