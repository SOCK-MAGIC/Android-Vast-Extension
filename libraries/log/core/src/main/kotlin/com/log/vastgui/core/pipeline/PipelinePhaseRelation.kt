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

/**
 * [PipelinePhaseRelation].
 *
 * @since 1.3.4
 */
internal sealed class PipelinePhaseRelation {

    /**
     * After.
     *
     * @since 1.3.4
     */
    class After(val relativeTo: PipelinePhase) : PipelinePhaseRelation()

    /**
     * Before.
     *
     * @since 1.3.4
     */
    class Before(val relativeTo: PipelinePhase) : PipelinePhaseRelation()

    /**
     * Last.
     *
     * @since 1.3.4
     */
    data object Last : PipelinePhaseRelation()
}