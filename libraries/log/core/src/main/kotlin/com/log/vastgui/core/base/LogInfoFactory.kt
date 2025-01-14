/*
 * Copyright 2021-2024 VastGui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.log.vastgui.core.base

import com.log.vastgui.core.LogCat
import com.log.vastgui.core.internel.LazyMessageWrapper
import com.log.vastgui.core.plugin.LogJson
import com.log.vastgui.core.plugin.LogPretty

/**
 * [LogInfo] factory.
 *
 * @since 1.3.4
 */
class LogInfoFactory @JvmOverloads constructor(
    private val level: LogLevel,
    private val tag: String,
    content: Any,
    private val tr: Throwable? = null
) {
    internal lateinit var logInfo: LogInfo

    /**
     * [String] or lazy String (() -> String)
     *
     * @since 1.3.4
     */
    private var rawContentOrLazy: Any = content

    /** @since 1.3.4 */
    private val isFuncContent: Boolean
        get() = rawContentOrLazy is LazyMessageWrapper

    /** @since 1.3.4 */
    fun content(): Any {
        if (isFuncContent) {
            rawContentOrLazy = (rawContentOrLazy as LazyMessageWrapper).lazyMsg()
        }
        return rawContentOrLazy
    }

    /**
     * For json.
     *
     * @see LogJson
     * @since 1.3.4
     */
    fun setStringContent(content: String) {
        rawContentOrLazy = content
    }

    /**
     * For pretty.
     *
     * @see LogPretty
     * @since 1.3.4
     */
    fun getStringContent(): String {
        check(rawContentOrLazy is String) { "rawContentOrLazy should be String." }
        return rawContentOrLazy as String
    }

    /**
     * @since 1.3.4
     */
    fun build(): LogInfo {
        check(!::logInfo.isInitialized) { "logInfo has been initialized." }
        val thread = Thread.currentThread()
        val index = getStackOffset<LogCat>(thread.stackTrace)
        return LogInfo(
            thread.name,
            thread.stackTrace[index + 1],
            level,
            tag,
            System.currentTimeMillis(),
            content() as String,
            TEXT_TYPE,
            tr
        ).also { logInfo = it }
    }
}