package com.example.newsrefresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsListAdapter(private val listener:newsItemClicked): RecyclerView.Adapter<newsViewHolder>() {
    private val items:ArrayList <News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_layout,parent,false)
        val viewHolder=newsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.textview.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
       return items.size
    }
    fun updateNews(updatedNews:ArrayList<News>){
       items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val textview:TextView=itemView.findViewById(R.id.textView)
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)
}
interface newsItemClicked{

    fun onItemClicked(item:News)
}