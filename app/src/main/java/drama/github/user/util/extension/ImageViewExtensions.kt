package drama.github.user.util.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import drama.github.user.R

/**
 * 이미지 로딩을 위한 확장 함수
 */
private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.load(url: String) {
    val tempPlaceholder = ContextCompat.getDrawable(context, R.drawable.ic_defualt_user)
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(tempPlaceholder)
        .into(this)
}
