package com.seadowg.skullyhome.jvm

import com.google.gson.Gson
import com.seadowg.skullyhome.apiai.*
import spark.kotlin.Http
import spark.kotlin.ignite

class SparkServer : Server {
    private var http: Http? = null

    override fun serve(port: Int, actionHandlers: Map<String, ActionHandler>) {
        http = ignite().port(port)

        http?.post("/") {
            val gson = Gson()
            val jsonRequest = gson.fromJson(request.body(), JsonRequest::class.java)

            val apiAiRequest = Request(MapParams(jsonRequest.result.parameters), jsonRequest.result.contexts.map { Context(it.name, it.lifespan) })
            val apiAiResponse = actionHandlers[jsonRequest.result.action]!!.handle(apiAiRequest)

            response.header("Content-Type", "application/json")
            gson.toJson(JsonResponse(apiAiResponse.prompt, apiAiResponse.contexts.map { JsonContext(it.name, it.requestsToLive) }))
        }
    }

    override fun shutdown() {
        http?.stop()
    }
}

private class MapParams(private val map: Map<String, String>) : Params {
    override fun getArgument(name: String): String? {
        return map[name]
    }

}

private data class JsonContext(val name: String, val lifespan: Int)

private data class JsonRequest(val result: Result) {
    data class Result(val parameters: Map<String, String>, val contexts: List<JsonContext>, val action: String)
}


private data class JsonResponse(val speech: String, val contextOut: List<JsonContext>)