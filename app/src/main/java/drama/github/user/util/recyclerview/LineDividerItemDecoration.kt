package drama.github.user.util.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import drama.github.user.util.Util.dpToPixel

/**
 * 리사이클러뷰 아이템 간의 선을 생성하기 위한 Decoration 클래스
 */
class LineDividerItemDecoration : RecyclerView.ItemDecoration {
    private var orientation: Int = LinearLayout.HORIZONTAL
    private var mDivider: Drawable? = null
    private var startCount: Int = 0
    private var lineHeight: Float = 0f
    private var endCount: Int = -1
    private var upperList: Boolean = false
    private var bottomList: Boolean = false

    constructor(
        context: Context, orientation: Int, lineHeight: Float, color: Int,
        upperList: Boolean = false, bottomList: Boolean = false
    ) {
        this.orientation = orientation
        this.lineHeight = lineHeight
        this.mDivider = makeDivide(context, this.lineHeight, color)
        this.upperList = upperList
        this.bottomList = bottomList
    }

    constructor(
        context: Context, orientation: Int, lineHeight: Float, color: Int,
        startCount: Int, endCount: Int,
        upperList: Boolean = false, bottomList: Boolean = false
    ) {
        this.orientation = orientation
        this.lineHeight = lineHeight
        this.mDivider = makeDivide(context, this.lineHeight, color)
        this.startCount = startCount
        this.endCount = endCount
        this.upperList = upperList
        this.bottomList = bottomList
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        if (startCount < 0) {
            startCount = 0
        }

        if (parent.adapter != null) {
            if (endCount < 0) {
                endCount = parent.adapter!!.itemCount - 1
            }

            val lastPosition: Int
            if (bottomList) {
                lastPosition = parent.adapter!!.itemCount
            } else {
                lastPosition = -1
            }

            if ((position >= startCount) && (position <= endCount) ||
                position == lastPosition
            ) {
                outRect.set(0, 0, 0, lineHeight.toInt())
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        if (startCount <= endCount) {
            for (i in startCount until endCount) {
                val child = parent.getChildAt(i)

                if (child != null) {
                    val params = child.layoutParams as RecyclerView.LayoutParams

                    if (i == 0 && upperList) {
                        val top = child.top + params.topMargin
                        val bottom = top + mDivider!!.intrinsicHeight

                        mDivider!!.setBounds(left, top, right, bottom)
                        mDivider!!.draw(c)
                    }

                    val top = child.bottom + params.bottomMargin
                    val bottom = top + mDivider!!.intrinsicHeight

                    mDivider!!.setBounds(left, top, right, bottom)
                    mDivider!!.draw(c)
                }
            }

            if (bottomList) {
                val child = parent.getChildAt(endCount)
                if (child != null) {
                    val params = child.layoutParams as RecyclerView.LayoutParams

                    val top = child.bottom + params.bottomMargin
                    val bottom = top + mDivider!!.intrinsicHeight

                    mDivider!!.setBounds(left, top, right, bottom)
                    mDivider!!.draw(c)
                }
            }
        }
    }

    private fun makeDivide(context: Context, height: Float, color: Int): Drawable {
        var lineWidth = dpToPixel(context, 1f)
        var lineHeight = height
        if (lineWidth <= 0f) {
            lineWidth = 3f
        }
        if (lineHeight <= 0f) {
            lineHeight = 1.5f
        }
        val bitmap =
            Bitmap.createBitmap(lineWidth.toInt(), lineHeight.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas()
        canvas.setBitmap(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        canvas.drawRect(0f, 0f, lineWidth, lineHeight, paint)
        return BitmapDrawable(context.resources, bitmap)
    }
}