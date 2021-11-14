package com.example.recyclviewexample

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_recycle_view.view.*

class ShoesListAdapter(private val dataList: List<Shoes>, private val context: Context) :
    RecyclerView.Adapter<ShoesListAdapter.ShoesViewHolder>(), Filterable {

    var shoesFilterList = listOf<Shoes>()

    init {
        shoesFilterList = dataList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_recycle_view,
                parent, false
            )
        return ShoesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        holder.image.setImageResource(dataList[position].img)
        holder.textName.text = dataList[position].itemName
        holder.textDes.text = dataList[position].itemDes
        holder.textPosition.text = holder.absoluteAdapterPosition.toString()
        holder.bind(shoesFilterList.get(position))



        holder.layout.setOnClickListener {
            val intent = Intent(context, DetailViewActivity::class.java)
            intent.putExtra("imgId", dataList[position].img)
            intent.putExtra("itemName", dataList[position].itemName)
            intent.putExtra("itemDes", dataList[position].itemDes)
            context.startActivity(intent)
        }
   }

    override fun getItemCount() = shoesFilterList.size


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    shoesFilterList = dataList as ArrayList<Shoes>
                } else {
                    val resultList = ArrayList<Shoes>()
                    for (row in dataList) {
                        if (row.itemName.toLowerCase()
                                .contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    shoesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = shoesFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    shoesFilterList = results?.values as List<Shoes>
                    notifyDataSetChanged()
                } else {
                    shoesFilterList = dataList
                }

            }
        }
    }

    /////////////////
    inner class ShoesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val textName: TextView = itemView.findViewById(R.id.itemNAme)
        val textDes: TextView = itemView.findViewById(R.id.itemDescription)
        val textPosition: TextView = itemView.findViewById(R.id.position)
        val layout: RelativeLayout = itemView.findViewById(R.id.mylinearlayout)


        fun bind(model: Shoes): Unit {
           itemView.image.setImageResource(model.img)
            itemView.itemNAme.text = model.itemName
            itemView.itemDescription.text = model.itemDes

        }
    }


}