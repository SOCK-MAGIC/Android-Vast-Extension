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

import com.log.vastgui.core.annotation.LogApi
import java.text.SimpleDateFormat
import java.util.Locale

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/6/20 22:23
// Description: 
// Documentation:
// Reference:

/**
 * Log formatter.
 *
 * @since 1.3.4
 */
interface LogFormat {
    /**
     * Convert [logInfo] to [String] according to a certain format.
     *
     * @since 1.3.4
     */
    fun format(logInfo: LogInfo): String

    companion object {
        /** @since 1.3.4 */
        @LogApi val timeSdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    }
}