package Adapters

import Models.Hayvon
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsh.HayvonotDunyosi.R
import com.mnsh.HayvonotDunyosi.databinding.ItemRvBinding
import kotlinx.android.synthetic.main.item_rv.view.*

class RvAdapter(var list:List<Hayvon>, val rvItemClick: RvItemClick) :RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(var itemView: ItemRvBinding): RecyclerView.ViewHolder(itemView.root){
        fun onBind(position: Int, hayvon: Hayvon){
            itemView.image_rv.setImageURI(Uri.parse(hayvon.imagePath))
            itemView.txt_hayvon_nomi.text = hayvon.name

            if (hayvon.like == 1){
                itemView.image_like.setImageResource(R.drawable.ic_like)
            }else{
                itemView.image_like.setImageResource(R.drawable.ic_not_like)
            }

            itemView.setOnClickListener {
                rvItemClick.itemClick(hayvon)
            }
            itemView.edit.setOnClickListener { rvItemClick.edit(hayvon) }
            itemView.delete.setOnClickListener { rvItemClick.delete(hayvon) }
            itemView.image_like.setOnClickListener {
                rvItemClick.like(hayvon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position, list[position])
    }

    override fun getItemCount(): Int = list.size
}

interface RvItemClick{
    fun edit(hayvon: Hayvon)
    fun delete(hayvon: Hayvon)
    fun like(hayvon: Hayvon)
    fun itemClick(hayvon: Hayvon)
}