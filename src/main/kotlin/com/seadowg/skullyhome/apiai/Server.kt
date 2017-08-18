package com.seadowg.skullyhome.apiai

interface Server {
    fun serve(port: Int, actionHandlers: Map<String, ActionHandler>)
    fun shutdown()
}
