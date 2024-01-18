package com.example.ivcare.display

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ivcare.R
import com.example.ivcare.databinding.ListItemBinding
import com.example.ivcare.uzerdatabase.Uzer

class MyRecyclerViewAdapter(
    //private val uzersList: List<Subscriber>,
    private val clickListener: (Uzer) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    private val uzersList = ArrayList<Uzer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return uzersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(uzersList[position], clickListener)
    }

    fun setList(uzers: List<Uzer>) {
        uzersList.clear()
        uzersList.addAll(uzers)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uzer: Uzer, clickListener: (Uzer) -> Unit) {
        binding.nameTextView.text = uzer.name
        binding.emailTextView.text = uzer.email
        binding.statusTextView.text = uzer.status
        binding.roleTextView.text = uzer.role
        binding.listItemLayout.setOnClickListener {
            clickListener(uzer)
        }
    }
}