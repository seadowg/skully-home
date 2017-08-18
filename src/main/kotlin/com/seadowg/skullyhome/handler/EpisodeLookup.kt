package com.seadowg.skullyhome.handler

import com.google.gson.Gson
import com.seadowg.skullyhome.apiai.ActionHandler
import com.seadowg.skullyhome.apiai.Request
import com.seadowg.skullyhome.apiai.Response
import okhttp3.OkHttpClient

class EpisodeLookup : ActionHandler {
    override fun handle(request: Request): Response {
        val httpClient = OkHttpClient()
        val skullyRequest = okhttp3.Request.Builder()
                .get()
                .url("https://skully.herokuapp.com/episodes/search?name=${request.params.getArgument("any")}")
                .build()

        val skullResponse = httpClient.newCall(skullyRequest).execute()

        val episode = Gson().fromJson(skullResponse.body()!!.string(), Episode::class.java)
        return Response("Skip to ${episode.next}", emptyList())
    }
}

private data class Episode(val next: String)
