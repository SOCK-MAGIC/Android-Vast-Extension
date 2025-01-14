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

package com.log.vastgui.core.model

import java.util.Date

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2024/6/23 18:21

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val registrationDate: Date,
    val isActive: Boolean
)

val user = User(
    id = 1L,
    name = "Alice Smith",
    email = "alice.smith@example.com",
    registrationDate = Date(), // 假设当前日期
    isActive = true
)