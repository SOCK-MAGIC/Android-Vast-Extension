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

package com.ave.vastgui.app

import android.app.Application
import android.content.Intent
import com.ave.vastgui.app.activity.FileActivity
import com.ave.vastgui.app.log.mLogFactory
import com.ave.vastgui.app.theme.OptionFactory
import com.ave.vastgui.app.theme.compat.FabExecutorBuilder
import com.ave.vastgui.app.theme.compat.FabFactory
import com.ave.vastgui.tools.content.ContextHelper
import com.ave.vastgui.tools.exception.AppCrashHandler
import org.alee.component.skin.compat.ConstraintLayoutCompat
import org.alee.component.skin.page.WindowManager
import org.alee.component.skin.service.Config
import org.alee.component.skin.service.ThemeSkinService
import kotlin.system.exitProcess

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/12/28

private val crashConfig =
    AppCrashHandler.Configuration(mLogFactory.getLog(App::class.java)) { _, _ ->
        val intent = Intent(ContextHelper.getAppContext(), FileActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        ContextHelper.getAppContext().startActivity(intent)
        exitProcess(0)
    }

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        WindowManager.getInstance().init(this, OptionFactory())
        ThemeSkinService.getInstance().apply {
            createViewInterceptor.add(FabFactory())
            addThemeSkinExecutorBuilder(FabExecutorBuilder())
        }
        ConstraintLayoutCompat.init()
        Thread.setDefaultUncaughtExceptionHandler(AppCrashHandler.getInstance(crashConfig))
    }

    init {
        Config.getInstance().skinMode = Config.SkinMode.REPLACE_ALL
        Config.getInstance().isEnableDebugMode = true
        Config.getInstance().isEnableStrictMode = true
        Config.getInstance().performanceMode = Config.PerformanceMode.EXPERIENCE_FIRST
    }

    companion object {
        const val KEY = "530e4e6c66ec464385f5a794b44cf89a"
    }

}