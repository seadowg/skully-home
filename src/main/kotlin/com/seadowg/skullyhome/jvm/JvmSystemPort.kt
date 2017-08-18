package com.seadowg.skullyhome.jvm

import com.seadowg.skullyhome.system.EnvPort

class JvmSystemPort : EnvPort {
    override fun get(): Int {
        return try {
            Integer.parseInt(System.getenv("PORT"))
        } catch (e: NumberFormatException) {
            8080
        }

    }

}