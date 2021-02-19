package com.coding.studymaterials.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.coding.girl.base.BaseFragment
import com.coding.studymaterials.databinding.FragmentBigpicBinding
import com.coding.studymaterials.util.BitmapUtil

/**
 * @author: Coding.He
 * @date: 2021/2/19
 * @emil: 229101253@qq.com
 * @des:
 */
class BigPicFragment : BaseFragment() {
    companion object {
        private const val REQ_PERMISSION = 200
    }

    private lateinit var viewBinding: FragmentBigpicBinding
    private lateinit var mBitmap: Bitmap
    private var mPicUrl = ""
    private var mPicName = ""
    private var mDesc = ""
    override fun bindLayout(): Int {
        return 0
    }

    override fun bindView(): View {
        viewBinding = FragmentBigpicBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (arguments != null) {
            mPicUrl = arguments!!.getString("url", "")
            mPicName = arguments!!.getString("name", "")
            if (!mPicName.endsWith(".jpg"))
                mPicName = "$mPicName.jpg"
            mDesc = arguments!!.getString("desc", "").replace("\n", "")
        }

        Glide.with(this)
            .load(mPicUrl)
            .into(viewBinding.imgDetailGirl)

        viewBinding.tvDesc.text = mDesc
    }

    override fun initListener() {
        viewBinding.imgBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        viewBinding.imgDetails.setOnClickListener {
            val builder = AlertDialog.Builder(ctx)
            builder.setNegativeButton("取消", null)
            builder.setPositiveButton(
                "保存"
            ) { dialog, which ->
                mBitmap = BitmapUtil.drawable2Bitmap(viewBinding.imgDetailGirl.drawable)
                if (ActivityCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSION
                    )
                } else {
                    addBitmapToAlbum(mBitmap, mPicName)
                }
            }
        }
    }

    private fun addBitmapToAlbum(bitmap: Bitmap, displayName: String) {
        try {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            } else {
                values.put(
                    MediaStore.MediaColumns.DATA,
                    "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName"
                )
            }
            val uri = activity!!.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            if (uri != null) {
                val outputStream = activity!!.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                    showTip("保存成功")
                }
            } else {
                Log.i(TAG, "保存失败,该图片已在图库中")
                showTip("保存失败,该图片已在图库中")
            }
        } catch (e: Exception) {
            Log.e(TAG, "e:$e")
            showTip("未知错误，保存失败")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_PERMISSION ->
                if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addBitmapToAlbum(mBitmap, mPicName)
                } else {
                    showTip("Unauthorized storage permission, save failed")
                }
        }
    }
}