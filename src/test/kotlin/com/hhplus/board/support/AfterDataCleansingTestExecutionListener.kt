package com.hhplus.board.support

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import java.util.function.Consumer

class AfterDataCleansingTestExecutionListener : AbstractTestExecutionListener() {
    override fun afterTestMethod(testContext: TestContext) {
        val jdbcTemplate = getJdbcTemplate(testContext)
        val truncateQueries = getTruncateQueries(jdbcTemplate)
        truncateTables(jdbcTemplate, truncateQueries)
    }

    private fun getTruncateQueries(jdbcTemplate: JdbcTemplate): List<String> {
        return jdbcTemplate.queryForList(
            "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
            String::class.java
        )
    }

    private fun getJdbcTemplate(testContext: TestContext): JdbcTemplate {
        return testContext.applicationContext.getBean(JdbcTemplate::class.java)
    }

    private fun truncateTables(jdbcTemplate: JdbcTemplate, truncateQueries: List<String>) {
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY FALSE")
        truncateQueries.forEach(Consumer { v: String -> execute(jdbcTemplate, v) })
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY TRUE")
    }

    private fun execute(jdbcTemplate: JdbcTemplate, query: String) {
        jdbcTemplate.execute(query)
    }
}