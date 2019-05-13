package net.simplifiedcoding.androidpaginglibrary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //basic layout setup
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        //need to make a property here because it's used in setting the adapter for the recycler view and also for
        //the item view model to observe it
        val adapter = ItemAdapter(this)
        recyclerView!!.adapter = adapter

        //means adapter content changes don't change the recycler view
        recyclerView!!.setHasFixedSize(true)


        val itemViewModel = ViewModelProviders.of(this).get<ItemViewModel>(ItemViewModel::class.java)

        itemViewModel.itemPagedList.observe(this, Observer { items -> adapter.submitList(items) })
    }
}
