package com.openwebinars.listacompartida

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador(notas:ArrayList<Nota>,main:MainActivity): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    private var listaNotas = notas
    private val mainAc:MainActivity = main

    class ViewHolder(itemView: View,main:MainActivity): RecyclerView.ViewHolder(itemView){
         val seleccionado:CheckBox
         val texto:TextView

        init {
            seleccionado = itemView.findViewById<CheckBox>(R.id.checked)
            texto = itemView.findViewById<TextView>(R.id.texto)

            seleccionado.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{v,checked ->
                run {
                    main.editSelects(adapterPosition,checked)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.nota_preview,parent,false)
        return ViewHolder(view,mainAc)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texto.text = listaNotas[position].contenido
        when (listaNotas[position].color) {
            "red" -> {
                holder.texto.setTextColor(Color.RED)
            }
            "green" -> {
                holder.texto.setTextColor(Color.GREEN)
            }
            "blue" -> {
                holder.texto.setTextColor(Color.BLUE)
            }
            else -> {
                holder.texto.setTextColor(Color.parseColor(listaNotas[position].color))
            }
        }
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }
}