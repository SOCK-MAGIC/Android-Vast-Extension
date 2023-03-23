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

package com.ave.vastgui.tools.manager.mediafilemgr

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.ave.vastgui.tools.helper.ContextHelper
import java.io.File

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/10/21
// Description: 
// Documentation:
// Reference:

object ImageMgr : MediaFileMgr() {

    /**
     * @return The default image file path :
     *     /storage/emulated/0/Android/data/packageName/files/Pictures.
     */
    override fun getDefaultRootDirPath(): String? {
        return getDefaultRootDirPath(MediaType.Images)
    }

    override fun getFileByUri(uri: Uri): File? {
        return getFileByUri(uri, MediaType.Images)
    }

    /**
     * Get the uri by [file].
     *
     * [getFileUriAboveApi30] will insert a new row into a table at
     * the given URL [MediaStore.Images.Media.EXTERNAL_CONTENT_URI].
     * So when you want to delete the image file, please also run
     * [android.content.ContentResolver.delete] to make sure that the
     * file-related information is completely deleted. For example:
     * ```kotlin
     * contentResolver.delete(
     *      MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
     *      MediaStore.Images.Media.DISPLAY_NAME + "=?",
     *      arrayOf("avatar.jpg")
     * )
     * ```
     *
     * By default, [getFileUriAboveApi30] will only insert
     * the following columns: [MediaStore.Images.Media.DATA],
     * [MediaStore.Images.Media.DISPLAY_NAME],
     * [MediaStore.Images.Media.MIME_TYPE], If you want to customize, you
     * can refer to [getFileUriAboveApi30] by using saveOptions parameter.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    override fun getFileUriAboveApi30(file: File): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DATA, file.absolutePath)
            put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return try {
            ContextHelper.getAppContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Get the uri by [saveOptions].
     *
     * [getFileUriAboveApi30] will insert a new row into a table at
     * the given URL [MediaStore.Images.Media.EXTERNAL_CONTENT_URI].
     * So when you want to delete the image file, please also run
     * [android.content.ContentResolver.delete] to make sure that the
     * file-related information is completely deleted. For example:
     * ```kotlin
     * contentResolver.delete(
     *      MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
     *      MediaStore.Images.Media.DISPLAY_NAME + "=?",
     *      arrayOf("avatar.jpg")
     * )
     * ```
     *
     * @param saveOptions information about file.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    override fun getFileUriAboveApi30(saveOptions: MutableMap<String, String>.() -> Unit): Uri? {
        val values = ContentValues().apply {
            HashMap<String, String>().also(saveOptions).forEach { (key, value) ->
                put(key, value)
            }
        }
        return try {
            ContextHelper.getAppContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

}