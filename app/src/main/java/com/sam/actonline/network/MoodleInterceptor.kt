package com.sam.actonline.network

import com.sam.actonline.utils.PrefHelper
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by Dinh Sam Vu on 4/15/2021.
 */

class MoodleInterceptor(private val pref: PrefHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.token

        if (token.isNotBlank()) {
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("wstoken", token)
                .addQueryParameter("moodlewsrestformat", "json")
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            return chain.proceed(request)
        }

        return chain.proceed(chain.request())
    }

}