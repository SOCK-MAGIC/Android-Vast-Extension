/*
 * Copyright 2021-2024 VastGui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

@file:JvmName("FactoryKt")

import com.log.vastgui.core.LogFactory
import com.log.vastgui.core.base.LogLevel
import com.log.vastgui.core.base.Logger
import com.log.vastgui.core.getLogFactory
import com.log.vastgui.core.json.GsonConverter
import com.log.vastgui.core.plugin.LogJson
import com.log.vastgui.core.plugin.LogPretty
import com.log.vastgui.core.plugin.LogPrinter
import com.log.vastgui.core.plugin.LogSwitch
import com.log.vastgui.desktop.desktop
import com.log.vastgui.desktop.format.LineColorfulFormat

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/7/5
// Documentation: https://ave.entropy2020.cn/documents/log/log-core/setting-up-logfactory/

private val gson = GsonConverter.getInstance(true)

val logFactory: LogFactory = getLogFactory {
    install(LogSwitch) {
        open = true
    }
    install(LogJson) {
        converter = gson
    }
    install(LogPretty) {
        converter = gson
    }
    install(LogPrinter) {
        levelSet = setOf(LogLevel.DEBUG)
        logger = Logger.desktop(LineColorfulFormat)
    }
}