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

@file:JvmName("ResUtilsKt")

package com.ave.vastgui.tools.utils

import android.content.Context
import android.content.res.Resources
import com.ave.vastgui.tools.content.ContextHelper

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/6/19
// Documentation: https://ave.entropy2020.cn/documents/VastTools/core-topics/app-resources/resource-utils/

/**
 * Using the context provided by [ContextHelper.getAppContext] and [method]
 * to find the required resources.
 *
 * ```kotlin
 * val drawable = findByContext {
 *     AppCompatResources.getDrawable(this, R.drawable.android_logo)
 * }
 * ```
 *
 * @param callback The default value if [findByContext] can't get the
 *     resource by method.
 * @param method Method of providing resources
 * @param T Resource Type.
 * @since 0.5.2
 */
@Throws(Exception::class)
@JvmOverloads
inline fun <T : Any> findByContext(callback: T? = null, method: Context.() -> T?): T =
    try {
        method(ContextHelper.getAppContext()) ?: callback
        ?: throw NullPointerException("Can't get the resource and the callback is null.")
    } catch (exception: Exception) {
        callback ?: throw exception
    }

/**
 * Using the resources and theme provided by [ContextHelper.getAppContext]
 * and [method] to find the required resources.
 *
 * ```kotlin
 * val drawable = findByResources { res, theme ->
 *     ResourcesCompat.getDrawable(res, R.drawable.android_logo, theme)
 * }
 * ```
 *
 * @param callback The default value if [findByResources] can't get the
 *     resource by method.
 * @param method Method of providing resources
 * @param T Resource Type.
 * @since 0.5.2
 */
@Throws(Exception::class)
@JvmOverloads
inline fun <T : Any> findByResources(
    callback: T? = null,
    method: (Resources, Resources.Theme) -> T?
): T = try {
    val resources = ContextHelper.getAppContext().resources
    val theme = ContextHelper.getAppContext().theme
    method(resources, theme) ?: callback
    ?: throw NullPointerException("Can't get the resource and the callback is null.")
} catch (exception: Exception) {
    callback ?: throw exception
}