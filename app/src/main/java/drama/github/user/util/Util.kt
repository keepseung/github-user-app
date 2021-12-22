package drama.github.user.util

import android.content.Context
import android.util.TypedValue

object Util {

    //dp값을 pixel값으로 전환
    fun dpToPixel(context: Context, size: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            size,
            context.resources.displayMetrics
        )
    }

    //dp값을 pixel값으로 전환
    fun dpToPixel(context: Context, size: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            size.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}