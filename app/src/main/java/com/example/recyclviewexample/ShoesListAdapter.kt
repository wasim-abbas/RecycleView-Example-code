package com.example.recyclviewexample

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_diloge_box.*
import kotlinx.android.synthetic.main.custom_diloge_box.view.*
import kotlinx.android.synthetic.main.layout_recycle_view.*
import kotlinx.android.synthetic.main.layout_recycle_view.view.*

//val REQUEST_CODE = 42

class ShoesListAdapter(private val dataList: ArrayList<Shoes>, private val context: Context) :
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

        holder.layout.setOnLongClickListener {
            Toast.makeText(context, "on long press clicked", Toast.LENGTH_SHORT).show()

            val myDialogView=View.inflate(context, R.layout.custom_diloge_box, null)
            val myAlert = AlertDialog.Builder(context).setView(myDialogView).show()

            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

            myDialogView.myitemName.text = dataList[position].itemName.toEditable()
            myDialogView.myitemD.text = dataList[position].itemDes.toEditable()

            myDialogView.btnedit.setOnClickListener {
                ///code for image upload

                ///code her for edit button
                var itemN = myDialogView.myitemName.text.toString()
                var descrpItem = myDialogView.myitemD.getText().toString()

                dataList.set(position, Shoes(R.drawable.ic_launcher10, itemN, descrpItem))
                notifyItemChanged(position)
                Toast.makeText(context, "data inserted", Toast.LENGTH_SHORT).show()
                Log.e("hello", dataList.toString())
                myAlert.dismiss()
            }
            myAlert.btnDelete.setOnClickListener {
                dataList.removeAt(position)
                notifyItemChanged(position)
                myAlert.dismiss()
            }

            myAlert.show()
            return@setOnLongClickListener true
        }
    }


    override fun getItemCount() = shoesFilterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    shoesFilterList = dataList
                } else {
                    val resultList = ArrayList<Shoes>()
                    for (row in dataList) {
                        if (row.itemName.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
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
        val textDes: TextView = itemView.findViewById(R.id.myitemDescription)
        val textPosition: TextView = itemView.findViewById(R.id.position)
        val layout: RelativeLayout = itemView.findViewById(R.id.mylinearlayout)


        fun bind(model: Shoes): Unit {
            itemView.image.setImageResource(model.img)
            itemView.itemNAme.text = model.itemName
            itemView.myitemDescription.text = model.itemDes

        }
    }


}