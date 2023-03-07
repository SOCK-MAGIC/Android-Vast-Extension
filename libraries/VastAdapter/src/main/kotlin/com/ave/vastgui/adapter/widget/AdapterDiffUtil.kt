/*
 * Copyright 2022 VastGui guihy2019@gmail.com
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

package com.ave.vastgui.adapter.widget

import androidx.recyclerview.widget.DiffUtil

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/2/27
// Documentation: [AdapterDiffUtil](https://ave.entropy2020.cn/documents/VastAdapter/Widget/)

/**
 * Because of the use of [AdapterItemWrapper], the original [DiffUtil] will
 * be inconvenient to use, so [AdapterDiffUtil] is designed to replace it.
 *
 * @param T The class of the list item.
 */
abstract class AdapterDiffUtil<T, R : AdapterItemWrapper<T>> : DiffUtil.ItemCallback<R>() {

    /** @see newAreContentsTheSame */
    final override fun areContentsTheSame(oldItem: R, newItem: R): Boolean {
        return newAreContentsTheSame(oldItem.getData(), newItem.getData())
    }

    /** @see newAreItemsTheSame */
    final override fun areItemsTheSame(oldItem: R, newItem: R): Boolean {
        return newAreItemsTheSame(oldItem.getData(), newItem.getData())
    }

    abstract fun newAreContentsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun newAreItemsTheSame(oldItem: T, newItem: T): Boolean

}