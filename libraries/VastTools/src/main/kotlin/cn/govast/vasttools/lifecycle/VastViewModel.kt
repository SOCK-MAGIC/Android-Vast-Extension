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

package cn.govast.vasttools.lifecycle

import androidx.lifecycle.ViewModel
import cn.govast.vasttools.lifecycle.delegate.ViewModelDelegate
import cn.govast.vasttools.network.ApiRspListener
import cn.govast.vasttools.network.RequestBuilder
import cn.govast.vasttools.network.base.BaseApiRsp
import kotlinx.coroutines.CoroutineScope

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/7/6
// Description: 
// Documentation:

/**
 * Base ViewModel
 *
 * @since 0.0.9
 */
abstract class VastViewModel : ViewModel() {

    private val viewModelDelegate by lazy {
        ViewModelDelegate(this)
    }

    protected open fun getDefaultTag(): String = viewModelDelegate.getDefaultTag()

    protected open fun getRequestBuilder(): RequestBuilder {
        return viewModelDelegate.getRequestBuilder()
    }

    protected open fun createMainScope(): CoroutineScope {
        return viewModelDelegate.createMainScope()
    }

    protected fun <T : BaseApiRsp> defaultNetStateListener(value: NetStateLiveData<T>):
            ApiRspListener<T>.() -> Unit = {
        onStart = {
            value.postStart()
        }
        onSuccess = {
            value.postValueAndSuccess(it)
        }
        onEmpty = {
            value.postEmpty()
        }
        onError = {
            value.postError()
        }
        onFailed = { _, _ ->
            value.postFailed()
        }
        onCompletion = {
            value.postCompletion()
        }
    }
}