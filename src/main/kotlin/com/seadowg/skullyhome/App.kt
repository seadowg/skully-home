package com.seadowg.skullyhome

import com.seadowg.skullyhome.apiai.Server
import com.seadowg.skullyhome.handler.EpisodeLookup
import com.seadowg.skullyhome.jvm.JvmSystemPort
import com.seadowg.skullyhome.jvm.SparkServer
import com.seadowg.skullyhome.system.EnvPort

fun main(args: Array<String>) {
    App().run()
}

class App {
    private var server: Server = SparkServer()

    fun run() {
        bootServer(server, JvmSystemPort())
    }

    fun kill() {
        server.shutdown()
    }

    private fun bootServer(server: Server, envPort: EnvPort) {
        server.serve(envPort.get(), hashMapOf(
                "episode_lookup" to EpisodeLookup()
        ))
    }
}