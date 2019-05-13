package net.simplifiedcoding.androidpaginglibrary

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    private val retrofit: Retrofit

    //returns class that can get stuff from network???
    val api: Api
        get() = retrofit.create(Api::class.java)

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    companion object {

        private val BASE_URL = "https://api.stackexchange.com/2.2/"
        private var mInstance: RetrofitClient? = null

        val insance: RetrofitClient?
           get() {
                if (mInstance == null) {
                    mInstance = RetrofitClient()
                }
                return mInstance
            }
    }
}
