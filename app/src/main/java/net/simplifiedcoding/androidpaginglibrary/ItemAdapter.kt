package net.simplifiedcoding.androidpaginglibrary

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide

class ItemAdapter protected constructor(private val mCtx: Context) : PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = getItem(position)

        if (item != null) {

            Glide.with(mCtx)
                    .load(item.owner.profile_image)
                    .into(holder.imageView)

            holder.textView.text = item.owner.display_name

        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show()
        }

    }


    internal inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView
        var textView: TextView

        init {

            imageView = itemView.findViewById(R.id.imageView)
            textView = itemView.findViewById(R.id.textViewName)
        }
    }

    companion object {


        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.answer_id == newItem.answer_id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}
