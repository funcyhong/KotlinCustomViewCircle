package com.example.kotlincustomvieweleven.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.example.base.dp2Px

private val OFFSET = 20f.dp2Px() //

private var C_R = 0f //弧形半径

private val C_S = 2f.dp2Px() //弧形线条宽度

private val DASH_WIDTH = 2f.dp2Px() //小刻度条宽度

private val DASH_HEIGHT = 10f.dp2Px() //小刻度条长度

private const val START_ANGLE = 120f //弧形开始角度

private const val SWEEP_ANGLE = 300f //弧形扫过的角度

private val path = Path() //弧形路径

private val dash = Path() //小刻度条路径

private lateinit var pathDashPathEffect: PathDashPathEffect

/**
 * 仪表盘
 */
class TestCustomView(context: Context, attr: AttributeSet? = null) : View(context, attr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = C_S
    }

    init {
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }

    /**
     * view的尺寸改变会调用改方法
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        C_R = (width / 2).toFloat() - OFFSET
        path.reset()
        path.addArc(width / 2 - C_R, height / 2 - C_R, width / 2 + C_R, height / 2 + C_R, START_ANGLE, SWEEP_ANGLE)
        //测量path长度,这里测量的是整个绘制路径的长度
        val pathMeasure = PathMeasure(path, false)
        //advance 每间隔多少长度画一次 phase 第一次画的起始点的偏移量（ps：这里谷歌字面参数意思和实际参数的作用是反的）
        pathDashPathEffect = PathDashPathEffect(dash, (pathMeasure.length - C_S) / 20f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画圆弧
        canvas.drawPath(path, paint)
        //添加小刻度条特效
        paint.pathEffect = pathDashPathEffect
        //画刻度
        canvas.drawPath(path, paint)
        paint.pathEffect = null
        //画指针
    }
}