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

package cn.govast.vasttools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.govast.vasttools.delegate.FragmentDelegate
import cn.govast.vasttools.extension.NotNUllVar
import cn.govast.vasttools.extension.cast
import cn.govast.vasttools.extension.reflexViewBinding

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/3/11 22:58
// Description: Please make sure that the fragment extends VastVbFragment when the fragment using viewBinding.
// Documentation: [VastBaseFragment](https://sakurajimamaii.github.io/VastDocs/document/en/VastBaseFragment.html)

/**
 * VastVbFragment.
 *
 * ```kotlin
 * // Use in kotlin
 * class SampleVbFragment : VastVbFragment<FragmentSampleVbBinding>() {
 *      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *          // Something to do
 *      }
 * }
 * ```
 *
 * @param VB [ViewBinding] of the fragment layout.
 * @since 0.0.6
 */
abstract class VastVbFragment<VB : ViewBinding> : VastFragment() {

    /**
     * The viewBinding of the fragment, it will be initialized in
     * [Fragment.onCreateView].
     */
    private var mBinding by NotNUllVar<VB>()
    // Fragment Delegate
    private var mFragmentDelegate by NotNUllVar<FragmentDelegate>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = reflexViewBinding(javaClass, layoutInflater)
        mFragmentDelegate = object :FragmentDelegate(this){
            override fun getBinding(): ViewBinding {
                return mBinding
            }
        }
        return mBinding.root
    }

    final override fun setVmBySelf(): Boolean = false

    protected fun getBinding(): VB {
        return cast(mFragmentDelegate.getBinding())
    }

    final override fun createFragmentDelegate(): FragmentDelegate {
        return mFragmentDelegate
    }

}