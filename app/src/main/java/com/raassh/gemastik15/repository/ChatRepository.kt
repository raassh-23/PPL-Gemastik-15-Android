package com.raassh.gemastik15.repository

import com.raassh.gemastik15.api.ApiService
import com.raassh.gemastik15.api.request.SendChatRequest
import com.raassh.gemastik15.api.request.StartChatRequest
import com.raassh.gemastik15.local.db.ChatDao
import com.raassh.gemastik15.local.db.ChatEntity
import com.raassh.gemastik15.local.db.MessageEntity
import com.raassh.gemastik15.utils.callApi

class ChatRepository(private val apiService: ApiService, private val chatDao: ChatDao) {

    suspend fun insertChats(chats: List<ChatEntity>) = chatDao.insertChats(chats)

    suspend fun insertMessages(messages: List<MessageEntity>) = chatDao.insertMessages(messages)

    suspend fun insertChat(chat: ChatEntity) = chatDao.insertChat(chat)

    suspend fun insertMessage(message: MessageEntity) = chatDao.insertMessage(message)

    fun getChats() = chatDao.getChats()

    fun getMessages(chatId: String) = chatDao.getMessages(chatId)

    fun getLastMessage(chatId: String) = chatDao.getLastMessage(chatId)

    fun searchChat(query: String) = chatDao.searchChat(query)

    fun getChatById(chatIds: List<String>) = chatDao.getChatByIds(chatIds)

    fun getChatByUserId(userId: String) = chatDao.getChatByUserId(userId)

    fun startChat(users: List<String>, user: String, message: String) = callApi {
        val req = StartChatRequest(
            users = users,
            user = user,
            message = message
        )

        apiService.startChat(req).data
    }

    fun sendChatMessage(chatId: String, user: String, message: String) = callApi {
        val req = SendChatRequest(
            chatId = chatId,
            user = user,
            message = message,
        )

        apiService.sendChatMessage(req).data
    }

}