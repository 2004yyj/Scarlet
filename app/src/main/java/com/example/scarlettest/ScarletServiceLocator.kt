package com.example.scarlettest

import android.app.Application
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ScarletServiceLocator(application: Application) {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .build()

    private val scarletInstance = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory("ws://10.0.2.2:8080/ws/chat"))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
        .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
        .backoffStrategy(LinearBackoffStrategy(5000))
        .build()

    val chattingService = scarletInstance.create(ChattingService::class.java)
}