package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso

class GalleryAdapter(var context: Context, var imageUrls: MutableList<String>) : PagerAdapter() {

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
        Picasso.get()
            .load(imageUrls[position])
            .fit()
            .centerCrop()
            .into(imageView)
        container.addView(imageView)
        return imageView
    }
}