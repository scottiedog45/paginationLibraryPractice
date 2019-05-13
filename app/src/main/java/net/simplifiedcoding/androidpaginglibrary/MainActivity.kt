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
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    //HIERARCHY IS THUS:
    //itemdatasource is closest to the network or database, and actually fetches the data, it has a
    //interface that it conforms to that a "chunking" protocol, based on items or pages or w/e, which provideds
    //the basis for the unit of data loaded. The factory makes this, and is repsonsible for querying
    //the views. The item view model is the top of this data chain and is responsible for declaring when the data should
    //be fetched (like how for the scrolling should be, placeholders, etc.). On the other side, the adapter
    //is 100% responsible for the layout, and grabbing the necessary views, maybe also fetching and image with
    //glide or something. The in the main activity, the adapter and view model is set. the created view
    //model is responsible for observing the adapter and checking if it needs more data. The data "inside"
    // the view model is live data, so it's dynamic. To "refresh", you call invalidate on the item live data source.
    //


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

        //this is where the magic happens, view model observes the layout of the recycler view, and
        //then tells the adapter what to do
        itemViewModel.itemPagedList.observe(this, Observer { items -> adapter.submitList(items) })


        Timer("SettingUp", false).schedule(5000) {
            itemViewModel.refresh?.invoke()
        }
    }
}
