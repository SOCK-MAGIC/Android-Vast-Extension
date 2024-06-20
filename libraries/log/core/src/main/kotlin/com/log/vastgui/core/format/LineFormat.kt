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

package com.log.vastgui.core.format

import com.log.vastgui.core.base.LogFormat
import com.log.vastgui.core.base.LogFormat.Companion.timeSdf
import com.log.vastgui.core.base.LogInfo

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/6/20 23:27
// Description: 
// Documentation:
// Reference:

/**
 * Line format of [LogInfo].
 *
 * @see <img
 *     src="https://github.com/SakurajimaMaii/Android-Vast-Extension/blob/develop/libraries/log/desktop/image/log.png?raw=true"/>
 * @since 1.3.4
 */
object LineFormat : LogFormat {
    override fun format(logInfo: LogInfo): String = logInfo.let {
        "${timeSdf.format(it.mTime)} [${it.mLevel}|${it.mTag}|${it.mThreadName}] (${it.mStackTrace?.fileName}:${it.mStackTrace?.lineNumber}) ${it.mContent}"
    }
}