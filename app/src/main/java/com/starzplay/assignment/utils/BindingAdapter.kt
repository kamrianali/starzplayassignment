package com.starzplay.assignment.utils

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.starzplay.assignment.R
import com.starzplay.networking.models.Results

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:playbutton")
    fun bindPlayButtonVisibility(imageView: ImageView, results: Results?) {
        when (results?.mediaType) {
            MediaTypes.movie.name -> imageView.visibility = View.VISIBLE
            MediaTypes.tv.name -> imageView.visibility = View.VISIBLE
            else -> imageView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("bind:itemTittle")
    fun bindItemTittle(textView: TextView, results: Results?) {
        when (results?.mediaType) {
            MediaTypes.movie.name -> textView.text =
                (results.originalName ?: "Not Available").uppercase()
            MediaTypes.tv.name -> textView.text =
                (results.originalName ?: "Not Available").uppercase()
            else -> textView.text =
                (results?.name ?: "Not Available").uppercase()
        }
    }

    @JvmStatic
    @BindingAdapter("bind:itemDescription")
    fun bindItemDescription(textView: TextView, results: Results?) {
        when (results?.mediaType) {
            MediaTypes.movie.name -> textView.text =
                results.overview ?: "Not Available"
            MediaTypes.tv.name -> textView.text =
                results.overview ?: "Not Available"
            else -> textView.text =
                results?.known_for_department ?: "Not Available"
        }
    }

    @JvmStatic
    @BindingAdapter("bind:thumbnail")
    fun bindThumbnail(imageView: ImageView, results: Results?) {
        val finalUrl = if (results?.backdropPath != null)
            "https://image.tmdb.org/t/p/original${results.backdropPath}"
        else if (results?.profile_path != null)
            "https://image.tmdb.org/t/p/original${results.profile_path}"
        else
            ""
        Log.d("Glide", finalUrl)
        try {
            Glide.with(imageView)
                .load(finalUrl)
                .apply(
                    RequestOptions()
                        .placeholder(getImagePlaceHolderLoader(imageView.context))
                        .error(R.drawable.ic_tmdb)
                        .fallback(R.drawable.ic_tmdb)
                )
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .signature(ObjectKey(finalUrl))
                .into(imageView)
        } catch (e: Exception) {
            Log.e("Glide", "" + e.localizedMessage)
        }
    }

    private fun getImagePlaceHolderLoader(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.arrowEnabled = true
        circularProgressDrawable.setStartEndTrim(10f, 10f)
        circularProgressDrawable.setColorFilter(
            context.resources.getColor(
                R.color.black
            ), PorterDuff.Mode.ADD
        )
        circularProgressDrawable.progressRotation = CircularProgressDrawable.LARGE.toFloat()
        circularProgressDrawable.setTint(context.resources.getColor(R.color.black))
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}