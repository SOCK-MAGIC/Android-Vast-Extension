/*
 * Copyright 2021-2024 VastGui
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

package com.ave.vastgui.tools.manager.mediafilemgr

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.ave.vastgui.tools.manager.filemgr.FileMgr
import com.ave.vastgui.tools.utils.AppUtils
import com.ave.vastgui.tools.utils.DateUtils
import java.io.File

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/11/2

sealed class MediaFileMgr : MediaFileProperty {

    /**
     * Get default file name.
     *
     * @param extension The media file extension.
     * @return For example, 20230313_234940_455_com_ave_vastgui_app.jpg.
     */
    final override fun getDefaultFileName(extension: String): String {
        val timeStamp: String = DateUtils.getCurrentTime("yyyyMMdd_HHmmss_SSS")
        return try {
            "${timeStamp}_${AppUtils.getPackageName().replace(".", "_")}$extension"
        } catch (exception: Exception) {
            "${timeStamp}_media_file_mgr$extension"
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    final override fun getFileUriAboveApi24(file: File, authority: String): Uri {
        return FileMgr.getFileUriAboveApi24(file, authority)
    }

    final override fun getFileUriOnApi23(file: File): Uri {
        return FileMgr.getFileUriOnApi23(file)
    }

}