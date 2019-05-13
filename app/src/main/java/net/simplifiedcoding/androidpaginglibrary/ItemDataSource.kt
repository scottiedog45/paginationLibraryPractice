package net.simplifiedcoding.androidpaginglibrary

import android.arch.paging.PageKeyedDataSource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDataSource : PageKeyedDataSource<Int, Item>() {


    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, Item>) {

        println("FIRING LOAD INITIAL")

        RetrofitClient.insance!!
                .api
                .getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(object : Callback<StackApiResponse> {
                    override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                        if (response.body() != null) {

                            callback.onResult(response.body()!!.items!!.toMutableList(), null, FIRST_PAGE + 1)

                        }

                    }

                    override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {

                    }
                })

    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Item>) {

        println("FIRING LOAD BEFORE")

        RetrofitClient.insance!!
                .api
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(object : Callback<StackApiResponse> {
                    override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {


                        if (response.body() != null) {
                            val key = if (params.key > 1) params.key - 1 else null
                            callback.onResult(response.body()!!.items!!.toMutableList(), key)
                        }
                    }

                    override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {

                    }
                })

    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Item>) {

        println("FIRING LOAD AFTER")

        RetrofitClient.insance!!
                .api
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(object : Callback<StackApiResponse> {
                    override fun onResponse(call: Call<StackApiResponse>, response: Response<StackApiResponse>) {

                        if (response.body() != null) {
                            val key = if (response.body()!!.has_more) params.key + 1 else null
                            callback.onResult(response.body()!!.items!!.toMutableList(), key)
                        }

                    }

                    override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {

                    }
                })


    }

    companion object {
        val PAGE_SIZE = 50
        private val FIRST_PAGE = 1
        private val SITE_NAME = "stackoverflow"
    }
}
