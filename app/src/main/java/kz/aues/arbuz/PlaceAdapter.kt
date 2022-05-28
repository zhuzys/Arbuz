package kz.aues.arbuz

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class PlaceAdapter(private val context: Context?, private val list: ArrayList<WaterMelon>) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>(){
    private lateinit var mListener :onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)

    }
    fun setOnClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_place, parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: PlaceAdapter.MyViewHolder, position: Int) {
        val currentItem = list[position]

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_watermelon)
        init {
            itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
        }

    }