package Adapters

import DB.MyDbHelper
import Models.Hayvon
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnsh.HayvonotDunyosi.databinding.ItemVp2Binding
import kotlinx.android.synthetic.main.item_vp_2.view.*
import java.io.Serializable

class ViewPager2HayvonAdapter(val context: Context?, val rvItemClick: RvItemClick) : RecyclerView.Adapter<ViewPager2HayvonAdapter.Vh>(), Serializable {

    inner class Vh(var itemView: ItemVp2Binding) : RecyclerView.ViewHolder(itemView.root) {
        fun onBind(position: Int) {
            val myDbHelper = MyDbHelper(context)
            val list = myDbHelper.getAllLabel()
            val ogohList = ArrayList<Hayvon>()
            val imtiyozList = ArrayList<Hayvon>()
            val taqiqList = ArrayList<Hayvon>()
            val buyurList = ArrayList<Hayvon>()
            for (belgi in list) {
                when(belgi.category){
                    0-> ogohList.add(belgi)
                    1-> imtiyozList.add(belgi)
                    2-> taqiqList.add(belgi)
                    3-> buyurList.add(belgi)
                }
            }
            when (position) {
                0 -> itemView.rv_hayvon.adapter = RvAdapter(ogohList, rvItemClick)
                1 -> itemView.rv_hayvon.adapter = RvAdapter(imtiyozList, rvItemClick)
                2 -> itemView.rv_hayvon.adapter = RvAdapter(taqiqList, rvItemClick)
                3 -> itemView.rv_hayvon.adapter = RvAdapter(buyurList, rvItemClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemVp2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = 4
}