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

package com.ave.vastgui.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.app.R
import com.ave.vastgui.app.databinding.ActivityFileBinding
import com.ave.vastgui.app.log.mLogFactory
import com.ave.vastgui.tools.viewbinding.viewBinding

// Author: SakurajimaMai
// Email: guihy2019@gmail.com
// Date: 2022/5/31
// Documentation: https://ave.entropy2020.cn/documents/VastTools/core-topics/app-data-and-files/file-manager/file-mgr/

class FileActivity : AppCompatActivity(R.layout.activity_file) {

    private val mLogger = mLogFactory.getLog(FileActivity::class.java)
    private val mBinding by viewBinding(ActivityFileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.button.setOnClickListener {
            mLogger.d("日志测试")
        }
//        lifecycleScope.launch {
//            repeat(1000) {
//                mLogger.d("日志测试 $it")
//            }
//        }
//        getBinding().button.setOnClickListener {
//            getBinding().before.setImageBitmap(viewSnapshot(getBinding().root))
//            getBinding().image.setImageResource(R.drawable.img_standard)
//            getBinding().after.setImageBitmap(viewSnapshot(getBinding().root))
//            getBinding().editView.hideKeyBroad()
//            ContextCompat.getDrawable(getContext(), R.drawable.ic_github)?.toBitmap()?.apply {
//                val bmpString = BmpUtils.getBase64FromBitmap(this)
//                mLogger.d(bmpString ?: "")
//            }
//            mLogger.json(LogLevel.INFO, UserBean("小明", "123456"))
//            thread(name = "测试线程1") {
//                repeat(100) {
//                    val bitmap = ResourcesCompat
//                        .getDrawable(resources, R.drawable.ic_github, theme)!!.toBitmap()
//                    val bmpString = BmpUtils.getBase64FromBitmap(bitmap)
//                    mLogger.d(bmpString ?: "")
//                }
//            }
//            thread(name = "测试线程2") {
//                repeat(100) {
//                    mLogger.json(LogLevel.INFO, UserBean("小明", "123456"))
//                }
//            }
    }
    // 保存文件
//        val save = saveFile(File(appInternalFilesDir().path, "save.txt"))
//        logger.d("文件保存结果 $save")
    // 移动文件
//        val move = moveFile(File(appInternalFilesDir().path, "save.txt"), appInternalCacheDir().path)
//        logger.d("文件移动结果 $move")
    // 创建文件夹
//        val saveDir = makeDir(File(appInternalFilesDir().path, "newDir"))
//        logger.d("文件夹创建结果 $saveDir")
    // 复制文件夹
//        val copyDir = copyDir(File(appInternalFilesDir().path), File(appInternalFilesDir().path,"newDir2"))
//        logger.d("文件夹复制结果 $copyDir")
    // 删除文件
//        val delete = FileMgr.deleteFile(File(appInternalFilesDir().path, "save.txt"))
//        logger.d(getDefaultTag(), "文件删除结果 $delete")
    // 重命名文件
//        val rename = FileMgr.rename(File(appInternalFilesDir().path, "save.txt"), "newname.txt")
//        logger.d( "文件重命名结果 $rename")
    // 删除文件夹
//        val deleteDir = deleteDir(File(appInternalFilesDir().path, "newDir"))
//        logger.d("文件夹删除结果 $deleteDir")
    // 复制文件
//        val copyFile = copyFile(
//            File(appInternalFilesDir().path, "save.txt"),
//            File(appInternalFilesDir().path, "save_copy.txt")
//        )
//        logger.d("文件复制结果 $copyFile")
    // 移动文件夹
//        val moveDir = moveDir(
//            File(appInternalFilesDir().path),
//            "${appInternalFilesDir().path}${File.separator}moveDir"
//        )
//        logger.d("文件夹移动结果 $moveDir")
//        saveBitmap()
}

//    private fun saveBitmap() {
//        val bitmap = BmpUtils.getBitmapFromDrawable(R.drawable.ic_github, this)
//        getBinding().image.setImageBitmap(bitmap)
//        BmpUtils.saveBitmapAsFile(bitmap, File(appInternalFilesDir(), "bitmap.jpg"))?.apply {
//            logger.d("图像${name}保存成功")
//        }
//    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (MotionEvent.ACTION_DOWN == event?.action) {
//            val view = currentFocus
//            if (null != view && view is EditText) {
//                if (view.isShouldHideKeyBroad(event)) {
//                    view.hideKeyBroad()
//                }
//            }
//        }
//        return super.onTouchEvent(event)
//    }
//
//}