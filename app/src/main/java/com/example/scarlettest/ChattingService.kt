package com.example.scarlettest

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

interface ChattingService {
    @Receive
    fun observeWebSocketEvent(): ReceiveChannel<WebSocket.Event>
    @Send
    fun sendChat(message: Message): Boolean
    @Receive
    fun observeChat(): ReceiveChannel<Message>
}