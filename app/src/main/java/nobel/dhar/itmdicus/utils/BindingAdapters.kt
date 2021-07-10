package nobel.dhar.itmdicus.utils

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

@BindingAdapter(value = ["bind:imageUrl", "bind:name"])
fun loadImage(view: ImageView, url: String?, name: String?) {
    if (!url.isNullOrBlank()) {
        view.load(url) {
            transformations(CircleCropTransformation())
        }
        Log.d("TAG", "loadImage: url:$url name:$name")
    }
}

@BindingAdapter(value = ["bind:RoundCornersImageUrl", "bind:name"])
fun loadImageRoundCorner(view: ImageView, url: String?, name: String?) {
    if (!url.isNullOrBlank()) {
        view.load(url) {
            transformations(RoundedCornersTransformation(radius = 10f))
        }
        Log.d("TAG", "loadImage: url:$url name:$name")
    }
    Log.d("TAG", "loadImage: url:$url name:$name")

}

