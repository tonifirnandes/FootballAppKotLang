package com.muslimlife.tf.footballappkotlang.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FootBallRestService {
    companion object {
        private fun httpClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(FootBallRestConstant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun instance(): FootBallRest {
            return httpClient().create(FootBallRest::class.java)
        }
    }
}