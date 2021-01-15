package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import br.com.salve_uma_vida_front.models.DialogGallery
import br.com.salve_uma_vida_front.models.ScaleType
import com.squareup.picasso.Picasso

class GalleryAdapter(
    var context: Context,
    var imageUrls: MutableList<String>,
    val fragmentManager: FragmentManager,
    val scale: ScaleType
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): ImageView {
        val imageView = ImageView(context)
        if (scale == ScaleType.CROPPED) {
            Picasso.get()
                .load(imageUrls[position])
                .fit()
                .centerCrop()
                .into(imageView)
            imageView.setOnLongClickListener {
                openGallery()
                true
            }
        } else if (scale == ScaleType.FULL_SIZE) {
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            Picasso.get()
                .load(imageUrls[position])
                .fit()
                .centerInside()
                .into(imageView)
        }
        container.addView(imageView)
        return imageView
    }

    fun openGallery() {
        val dialogGallery = DialogGallery(imageUrls)
        dialogGallery.show(fragmentManager, "Gallery")
    }
}