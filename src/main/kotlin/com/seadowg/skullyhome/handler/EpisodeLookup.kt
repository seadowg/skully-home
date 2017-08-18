package com.seadowg.skullyhome.handler

import com.google.gson.Gson
import com.seadowg.skullyhome.apiai.ActionHandler
import com.seadowg.skullyhome.apiai.Request
import com.seadowg.skullyhome.apiai.Response
import okhttp3.OkHttpClient

class EpisodeLookup : ActionHandler {
    override fun handle(request: Request): Response {
        val currentEpisodeName = request.params.getArgument("any")!!

        val httpClient = OkHttpClient()
        val skullyRequest = okhttp3.Request.Builder()
                .get()
                .url("https://skully.herokuapp.com/episodes/search?name=$currentEpisodeName")
                .build()

        val skullyResponse = httpClient.newCall(skullyRequest).execute()

        if (skullyResponse.isSuccessful) {
            val episode = Gson().fromJson(skullyResponse.body()!!.string(), Episode::class.java)

            return if (currentEpisodeName.toLowerCase() == episode.next.toLowerCase()) {
                Response("That's a good one! Stick it on.", emptyList())
            } else {
                Response("Skip to ${episode.next}", emptyList())
            }
        } else {
            return Response("Hmmmm... I'm not sure that's an episode... in fact I'm not sure I can trust you.", emptyList())
        }
    }
}

private data class Episode(val next: String)
