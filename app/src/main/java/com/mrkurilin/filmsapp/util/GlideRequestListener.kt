package com.mrkurilin.filmsapp.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mrkurilin.filmsapp.R

class GlideRequestListener(
    private val imageView: ImageView,
    private val manageVisibility: () -> Unit = {},
) : RequestListener<Drawable> {

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(
                imageView.context,
                R.drawable.ic_baseline_file_download_off_24
            )
        )
        manageVisibility()
        return true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        imageView.setImageDrawable(resource)
        manageVisibility()
        return true
    }
}