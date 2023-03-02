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

package com.ave.vastgui.app.viewmodel

import com.ave.vastgui.app.network.NetworkRetrofitBuilder
import com.ave.vastgui.app.network.service.QRCodeKey
import com.ave.vastgui.app.network.service.QRService
import com.ave.vastgui.tools.network.response.ResponseLiveData
import com.ave.vastgui.tools.viewModel.VastViewModel


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/2/22
// Description: 
// Documentation:
// Reference:

class NetVM: VastViewModel() {

    val qRCodeKey = ResponseLiveData<QRCodeKey>()

    /**
     * 自动检测数据请求的状态
     * 目前支持 Error Failed Successful Empty 四种状态
     *
     * @param timestamp 时间戳
     */
    fun getQRCode_1(timestamp:String){
        NetworkRetrofitBuilder().create(QRService::class.java).generateQRCode(timestamp).request(qRCodeKey)
    }

    /**
     * 手动检测数据请求的状态
     *
     * @param timestamp 时间戳
     */
    fun getQRCode_2(timestamp:String){
        NetworkRetrofitBuilder().create(QRService::class.java).generateQRCode(timestamp)
            .request{
                onSuccess = {
                    qRCodeKey.postValueAndSuccess(it)
                }
                onFailed = { errorCode, errorMsg ->
                    qRCodeKey.postFailed(errorCode, errorMsg)
                }
                onError = {
                    qRCodeKey.postError(it)
                }
            }
    }

}