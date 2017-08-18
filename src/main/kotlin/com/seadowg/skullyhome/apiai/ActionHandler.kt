package com.seadowg.skullyhome.apiai

interface ActionHandler {
    fun handle(request: Request): Response
}
