package net.simplifiedcoding.androidpaginglibrary

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource

class ItemDataSourceFactory : DataSource.Factory<*, *>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()


    override fun create(): DataSource<*, *> {
        val itemDataSource = ItemDataSource()
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}
