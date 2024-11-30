package com.hhplus.board.support

import org.springframework.test.context.TestExecutionListeners

@Retention(AnnotationRetention.RUNTIME)
@TestExecutionListeners(
    value = [AfterDataCleansingTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
annotation class AfterDataCleansing()
