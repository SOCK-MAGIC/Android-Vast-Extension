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

package com.log.vastgui.core.pipeline

// Author: ywnkm
// Email: https://github.com/ywnkm
// Date: 2024/6/22

internal class PhaseContent<TSubject : Any, Call : Any>(
    val phase: PipelinePhase,
    val relation: PipelinePhaseRelation,
    val interceptors: MutableList<PipelineInterceptor<TSubject, Call>>
) {
    val isEmpty: Boolean get() = interceptors.isEmpty()

    val size: Int get() = interceptors.size

    fun addInterceptor(interceptor: PipelineInterceptor<TSubject, Call>) {
        interceptors.add(interceptor)
    }

    fun addTo(destination: MutableList<PipelineInterceptor<TSubject, Call>>) {
        for (interceptor in interceptors) {
            destination.add(interceptor)
        }
    }

    fun addTo(destination: PhaseContent<TSubject, Call>) {
        if (isEmpty) return
        addTo(destination.interceptors)
    }

    override fun toString(): String {
        return "Phase `${phase.name}`, $size handlers"
    }
}