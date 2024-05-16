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

package com.log.vastgui.core.base

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/5/11 9:12

/**
 * Logger.
 *
 * @since 1.3.1
 */
interface Logger {
    /** @since 1.3.1 */
    fun log(info: LogInfo)

    companion object
}