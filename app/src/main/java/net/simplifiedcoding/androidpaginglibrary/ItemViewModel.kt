package net.simplifiedcoding.androidpaginglibrary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList

class ItemViewModel : ViewModel() {

    internal var itemPagedList: LiveData<PagedList<Item>>
    internal var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>

    init {

        val itemDataSourceFactory = ItemDataSourceFactory()
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE)
                .build()

        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()

    }
}
