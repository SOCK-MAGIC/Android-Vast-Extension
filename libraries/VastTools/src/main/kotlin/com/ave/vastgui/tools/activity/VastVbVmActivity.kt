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

package com.ave.vastgui.tools.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.ave.vastgui.tools.activity.delegate.ActivityVbVmDelegate
import com.ave.vastgui.core.extension.NotNUllVar

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/3/10 16:13
// Description: Please make sure that the activity extends VastVbVmActivity when the activity using viewBinding and viewModel.
// Documentation: [VastBaseActivity](https://sakurajimamaii.github.io/VastDocs/document/en/VastBaseActivity.html)

/**
 * VastVbVmActivity.
 *
 * ```kotlin
 * class MainActivity : VastVbVmActivity<ActivityMainBinding,MainViewModel>() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *          super.onCreate(savedInstanceState)
 *          // Something to do
 *     }
 * }
 * ```
 *
 * @param VB [ViewBinding] of the activity layout.
 * @param VM [ViewModel] of the activity.
 */
abstract class VastVbVmActivity<VB : ViewBinding, VM : ViewModel> : VastActivity() {

    // Activity Delegate
    protected inner class AVVD: ActivityVbVmDelegate<VB, VM>(this){
        override fun createViewModel(modelClass: Class<out ViewModel>): ViewModel {
            return this@VastVbVmActivity.createViewModel(modelClass)
        }
    }
    private var mActivityDelegate by NotNUllVar<AVVD>()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDelegate = AVVD()
        setContentView(mActivityDelegate.getBinding().root)
    }

    protected fun getBinding(): VB {
        return mActivityDelegate.getBinding()
    }

    protected fun getViewModel(): VM {
        return mActivityDelegate.getViewModel()
    }

    protected open fun createViewModel(modelClass: Class<out ViewModel>): ViewModel {
        return modelClass.newInstance()
    }

    final override fun createActivityDelegate(): ActivityVbVmDelegate<VB, VM> {
        return mActivityDelegate
    }

}