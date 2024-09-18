import com.log.vastgui.core.base.LogFormat
import com.log.vastgui.core.base.LogInfo
import com.log.vastgui.core.base.Logger
import com.log.vastgui.core.format.TableFormat
import com.log.vastgui.core.getLogFactory
import com.log.vastgui.core.plugin.LogPrinter
import com.log.vastgui.core.plugin.LogSwitch
import com.log.vastgui.okhttp.Okhttp3Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test


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

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/9/18
// Description: 
// Documentation:
// Reference: 

val logFactory = getLogFactory {
    install(LogSwitch) {
        open = true
    }
    install(LogPrinter) {
        logger = object : Logger {
            override val logFormat: LogFormat
                get() = TableFormat(1000, Int.MAX_VALUE, TableFormat.LogHeader.default)

            override fun log(logInfo: LogInfo) {
                println(logFormat.format(logInfo))
            }
        }
    }
}
val logcat = logFactory("global")
val okhttp = OkHttpClient
    .Builder()
    .apply { addInterceptor(Okhttp3Interceptor(logcat)) }
    .build()
var request: Request = Request.Builder()
    .url("http://127.0.0.1:7777/stream")
    .build()

class Stream {
    @Test
    fun getStream() {
        logcat.d("Start")
        val response = okhttp.newCall(request).execute()
        logcat.d(response.code)
    }
}