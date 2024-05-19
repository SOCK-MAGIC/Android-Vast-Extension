/*
 * Copyright 2024 VastGui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ave.vastgui.tools.log

import android.annotation.SuppressLint
import android.content.ComponentCallbacks2
import android.util.Log
import com.log.vastgui.core.LogUtil
import com.log.vastgui.core.base.JSON_TYPE
import com.log.vastgui.core.base.LogDivider
import com.log.vastgui.core.base.LogInfo
import com.log.vastgui.core.base.LogLevel
import com.log.vastgui.core.base.Logger
import com.log.vastgui.core.base.TEXT_TYPE
import com.log.vastgui.core.base.cutStr
import com.log.vastgui.core.base.needCut
import com.ave.vastgui.tools.content.ContextHelper
import com.ave.vastgui.tools.log.base.LogHeader
import com.ave.vastgui.tools.utils.DateUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.properties.Delegates

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/5/13 20:48
// Description: 
// Documentation:
// Reference:

/**
 * Default maximum length of chars printed of a single log.
 * **Notes:Considering fault tolerance, 1000 is set here instead of 1024.**
 *
 * @since 1.3.1
 */
private const val DEFAULT_MAX_SINGLE_LOG_LENGTH = 1000

/**
 * Default max print repeat times.
 *
 * @since 1.3.1
 */
private const val DEFAULT_MAX_PRINT_TIMES = Int.MAX_VALUE

/** @since 1.3.1 */
@SuppressLint("ConstantLocale")
private val timeSdf = SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.getDefault())

/**
 * [Logger] default implementation.
 *
 * @param maxSingleLogLength The max length of single line of log. every
 *     char in line is calculated as 4 bytes.
 * @param maxPrintTimes The total repeat number of prints. For example, the
 *     log content is divided into ten lines depending on
 *     maxSingleLogLength. If you set maxPrintTimes to 5, only the first
 *     five lines will be printed.
 * @since 1.3.1
 */
fun Logger.Companion.android(
    maxSingleLogLength: Int = DEFAULT_MAX_SINGLE_LOG_LENGTH,
    maxPrintTimes: Int = DEFAULT_MAX_PRINT_TIMES,
    header: LogHeader = LogHeader(thread = true, tag = true, level = true, time = true)
): AndroidLogger = AndroidLogger(maxSingleLogLength, maxPrintTimes, header)

/**
 * Default logger.
 *
 * @since 1.3.1
 */
class AndroidLogger internal constructor(
    private val mMaxSingleLogLength: Int,
    private val mMaxPrintTimes: Int,
    private val mHeader: LogHeader
) : Logger {
    private var mLogInfo by Delegates.notNull<LogInfo>()

    /** @since 1.3.1 */
    private val mHandler = CoroutineExceptionHandler { _, exception ->
        val logInfo = LogInfo(
            Thread.currentThread(),
            LogLevel.ERROR,
            LogUtil.TAG,
            System.currentTimeMillis(),
            exception.stackTraceToString(),
            TEXT_TYPE,
            null
        )
        log(logInfo)
    }

    /** @since 1.3.1 */
    private val mLogScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName("LogScope") + mHandler)

    /**
     * A channel of [LogInfo].
     *
     * @since 1.3.1
     */
    private val mLogChannel: Channel<LogInfo> = Channel()

    override fun log(info: LogInfo) {
        mLogScope.launch { mLogChannel.send(info) }
    }

    /**
     * Print [logInfo].
     *
     * @since 1.3.1
     */
    private fun printTextLog(logInfo: LogInfo) {
        mLogInfo = logInfo
        // The length of the log content is less than mMaxSingleLogLength
        if (!mLogInfo.needCut(mMaxSingleLogLength)) {
            printLog { content ->
                printLog(LogDivider.getInfo(content))
            }
        }
        // The length of the log content is greater than mMaxSingleLogLength
        else {
            // Segment printing count
            var count = 0
            var printTheRest = true
            printLog(mMaxSingleLogLength * 4) { content ->
                // FIX: DEAL LINESEPARATOR THAT EXIST WITHIN THE LOG CONTENT
                val patterns = content.split(System.lineSeparator())
                patterns.forEachIndexed { index, pattern ->
                    var bytes = pattern.toByteArray()
                    if (mMaxSingleLogLength * 4 < bytes.size) {
                        do {
                            val subStr = bytes.cutStr(mMaxSingleLogLength)
                            printLog(LogDivider.getInfo(String.format("%s", subStr)))
                            bytes = bytes.copyOfRange(subStr.toByteArray().size, bytes.size)
                            count++
                            if (count == mMaxPrintTimes) {
                                printTheRest = false
                                break
                            }
                        } while (mMaxSingleLogLength * 4 < bytes.size)
                    } else {
                        count++
                    }

                    if (printTheRest && count <= mMaxPrintTimes) {
                        printLog(LogDivider.getInfo(String.format("%s", String(bytes))))
                    }
                }
            }
        }
    }

    /**
     * Print [logInfo].
     *
     * @since 1.3.1
     */
    @Throws(RuntimeException::class)
    private fun printJsonLog(logInfo: LogInfo) {
        mLogInfo = logInfo
        printLog {
            for (line in it.split(System.lineSeparator())) {
                printLog(LogDivider.getInfo(line))
            }
        }
    }

    /**
     * Print log.
     *
     * @since 1.3.1
     */
    private fun printLog(len: Int = mLogInfo.mPrintBytesLength, customScope: (String) -> Unit) {
        val mContent = mLogInfo.mContent
        printLog(LogDivider.getTop(len))
        val thread = if (mHeader.thread) {
            "Thread: ${mLogInfo.mThreadName}"
        } else {
            ""
        }
        val tag = if (mHeader.tag) {
            "Tag: ${mLogInfo.mTag}"
        } else {
            ""
        }
        val level = if (mHeader.level) {
            "Level: ${mLogInfo.mLevel}"
        } else {
            ""
        }
        val time = if (mHeader.time) {
            "Time: ${timeSdf.format(mLogInfo.mTime)}"
        } else {
            ""
        }
        printLog(LogDivider.getInfo("$thread $tag $level $time"))
        printLog(LogDivider.getDivider(len))
        printLog(LogDivider.getInfo("${mLogInfo.mMethodStackTrace}"))
        printLog(LogDivider.getDivider(len))
        customScope(mContent)
        mLogInfo.mThrowable?.apply {
            printLog(LogDivider.getDivider(len))
            printLog(LogDivider.getInfo("$this"))
            for (item in this.stackTrace) {
                printLog(LogDivider.getInfo("  at $item"))
            }
        }
        printLog(LogDivider.getBottom(len))
    }

    /** @since 1.3.1 */
    private fun printLog(content: String) {
        Log.println(mLogInfo.mLevelPriority, mLogInfo.mTag, content)
    }

    init {
        ContextHelper.getApp().registerComponentCallbacks(object : ComponentCallbacks2 {
            override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {

            }

            override fun onLowMemory() {
                mLogScope.cancel("Cancel mLogScope onLowMemory.")
            }

            override fun onTrimMemory(level: Int) {

            }
        })
        mLogScope.launch {
            while (isActive) {
                val info = mLogChannel.receive()
                if (TEXT_TYPE == info.mType) {
                    printTextLog(info)
                } else if (JSON_TYPE == info.mType) {
                    printJsonLog(info)
                }
            }
        }
    }
}