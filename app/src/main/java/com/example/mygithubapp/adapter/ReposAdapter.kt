package com.example.mygithubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithubapp.models.Repository
import com.example.mygithubapp.databinding.ItemReposBinding
import com.squareup.picasso.Picasso

class ReposAdapter (var list: List<Repository>, var onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ReposAdapter.Vh>() {
    inner class Vh(var itemReposBinding: ItemReposBinding) :
        RecyclerView.ViewHolder(itemReposBinding.root) {

        fun onBind(repository: Repository) {
            itemReposBinding.repoName.text = repository.name
            itemReposBinding.codeLanguage.text = repository.language
            Picasso.get().load(repository.owner.avatar_url).into(itemReposBinding.userImage)
            itemReposBinding.repository.setOnClickListener {
                onItemClickListener.onItemClick(repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onItemClick(repository: Repository)
    }
}