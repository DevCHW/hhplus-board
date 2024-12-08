package com.hhplus.board.api.global.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RestDocsController {

    @GetMapping("/docs")
    fun apiDocs(): String {
        return "docs/api-docs"
    }

}