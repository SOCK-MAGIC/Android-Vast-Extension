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

package com.ave.vastgui.tools.network.response

import androidx.annotation.MainThread

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/4/11
// Documentation: https://ave.entropy2020.cn/documents/VastTools/core-topics/connectivity/performing-network-operations/response-livedata/

/**
 * Response mutable live data
 *
 * @since 0.3.0
 */
class ResponseMutableLiveData<T> : ResponseLiveData<T> {

    constructor() : super()

    constructor(value: T) : super(value)

    init {
        clearState()
    }

    fun clearState() {
        getState().clearState()
    }

    fun changeState(s: State) {
        getState().changeState(s)
    }

    fun postEmpty() {
        getState().changeEmpty()
    }

    @JvmOverloads
    fun postError(t: Throwable? = null) {
        getState().changeError(t)
    }

    @JvmOverloads
    fun postFailed(errorCode: Int? = null, errorMsg: String? = null) {
        getState().changeFailed(errorCode, errorMsg)
    }

    fun postSuccess() {
        getState().changeSuccess()
    }

    fun postValueAndSuccess(value: T) {
        super.postValue(value)
        getState().changeSuccess()
    }

    @MainThread
    fun setValueAndSuccess(value: T) {
        super.setValue(value)
        getState().changeSuccess()
    }

}