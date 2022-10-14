package com.example.scarlettest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val chattingService: ChattingService
): ViewModel() {
    init {
        socketConnection()
    }

    private val _message: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val message = _message.asStateFlow()

    private fun socketConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            chattingService.observeWebSocketEvent()
                .receiveAsFlow()
                .filter { event -> event is WebSocket.Event.OnConnectionOpened<*> }
                .collectLatest {
                    getMessageList()
                }
        }
    }

    private fun getMessageList() {
        viewModelScope.launch(Dispatchers.IO) {
            chattingService.observeChat()
                .receiveAsFlow()
                .collectLatest {
                    val messageList = _message.value.toMutableList()
                    messageList.add(it)
                    _message.emit(messageList)
                }
        }
    }
}