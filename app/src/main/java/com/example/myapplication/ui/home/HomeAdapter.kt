package com.example.myapplication.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Network.response.ItemsResponse
import com.example.myapplication.databinding.AdapterItemBinding
import com.example.myapplication.ui.proccess.AddEditActivity

class HomeAdapter (
    var items: ArrayList<ItemsResponse>,
    var viewModel: HomeViewModel

): RecyclerView.Adapter<HomeAdapter.ViewHolder>(), Filterable {


    private var itemsFilter = ArrayList<ItemsResponse>()

    init {
        itemsFilter = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterItemBinding.inflate( LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val item = itemsFilter[position]
        holder.binding.textKode.text = item.kode_barang
        holder.binding.textNama.text = item.nama_barang
        holder.binding.textHarga.text = item.harga_barang.toString()
        holder.binding.textJumlah.text = item.jumlah_barang.toString()
        holder.binding.buttonEdit.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, AddEditActivity::class.java)
            intent.putExtra("is_edit", "1")
            intent.putExtra("position", position.toString())
            intent.putExtra("kode_barang", item.kode_barang)
            intent.putExtra("nama_barang", item.nama_barang)
            intent.putExtra("jumlah_barang", item.jumlah_barang.toString())
            intent.putExtra("harga_barang", item.harga_barang.toString())
            intent.putExtra("satuan_barang", item.satuan_barang)
            intent.putExtra("status_barang", item.status_barang.toString())
            holder.itemView.context.startActivity(intent)
        })
        holder.binding.buttonDelete.setOnClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.apply {
                setTitle("Hapus Data")
                setMessage("Hapus Barang ${item.nama_barang}?")
                setPositiveButton("Hapus"){ _, _ ->
                    //viewModel.deleteWaybill(results)
                    removeData(position)
                    viewModel.fetchDelete(item.kode_barang)
                    Toast.makeText(holder.itemView.context, "Barang Telah Hapus", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Batal"){ _, _ ->

                }
                show()
            }
        }
    }

    override fun getItemCount() = itemsFilter.size

    class ViewHolder(val binding: AdapterItemBinding): RecyclerView.ViewHolder(binding.root)

    fun removeData (position: Int){
        itemsFilter.removeAt(position)
        notifyDataSetChanged()
    }
    fun setData (data: List<ItemsResponse>){
        items.clear()
        items.addAll( data )
        notifyDataSetChanged()
    }

    fun setDataSearch (data: ItemsResponse){
        items.clear()
        items.add( data )
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                itemsFilter = if (charSearch.isEmpty()) {
                    items
                } else {
                    val citiesFiltered = ArrayList<ItemsResponse>()
                    for (item in items) {
                        if (item.nama_barang.lowercase().contains(charSearch.lowercase())) {
                            citiesFiltered.add(item)
                        }
                    }
                    citiesFiltered
                }
                val citiesFilteredResult = FilterResults()
                citiesFilteredResult.values = itemsFilter
                return citiesFilteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFilter = results?.values as ArrayList<ItemsResponse>
                notifyDataSetChanged()
            }

        }
    }
}