package com.hhplus.board.api.global.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestDocsController {

    @GetMapping
    fun apiDocs(): String {
        return "docs/api-docs"
    }

}