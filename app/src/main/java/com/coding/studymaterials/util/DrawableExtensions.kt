package com.coding.studymaterials.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**
 * @author: Coding.He
 * @date: 2021/3/3
 * @emil: 229101253@qq.com
 * @des:
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) return bitmap
    val w: Int = intrinsicWidth
    val h: Int = intrinsicHeight
    val config =
        if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    val bitmap = Bitmap.createBitmap(w, h, config)
    //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
    //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
    val canvas = Canvas(bitmap)
    setBounds(0, 0, w, h)
    draw(canvas)
    return bitmap
}