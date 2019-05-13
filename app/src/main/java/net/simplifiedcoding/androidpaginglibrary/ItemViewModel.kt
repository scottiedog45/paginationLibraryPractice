package net.simplifiedcoding.androidpaginglibrary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList

class ItemViewModel : ViewModel() {

    var itemPagedList: LiveData<PagedList<Item>>
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>

    var refresh : (() -> Unit)



    init {
        val itemDataSourceFactory = ItemDataSourceFactory()
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE)
                .build()

        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()

        var refreshl : () -> Unit = {
            itemDataSourceFactory.itemLiveDataSource.value?.invalidate()
        }

        refresh = refreshl

    }
}
