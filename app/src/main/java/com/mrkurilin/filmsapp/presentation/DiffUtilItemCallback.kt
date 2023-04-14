package com.mrkurilin.filmsapp.presentation

import androidx.recyclerview.widget.DiffUtil
import com.mrkurilin.filmsapp.data.DataClassWrapper

class DiffUtilItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return DataClassWrapper(oldItem) == DataClassWrapper(newItem)
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}