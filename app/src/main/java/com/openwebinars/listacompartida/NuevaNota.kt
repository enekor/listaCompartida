package com.openwebinars.listacompartida

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class NuevaNota: DialogFragment() {

    private lateinit var grupo:RadioGroup
    private lateinit var texto:EditText
    private lateinit var otroColor:EditText
    private lateinit var ok:Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(Objects.requireNonNull(activity))
        val layout = activity?.layoutInflater!!
        val view = layout.inflate(R.layout.nueva_nota,null)!!
        Log.i("algo","algo ${view.findViewById<RadioGroup>(R.id.grupoColores)}")

        initComponents(view)

        builder.setView(view)

        return builder.show()
    }

    private fun initComponents(view:View) {
        Log.i("algo","algo desde dentro ${view?.findViewById<RadioGroup>(R.id.grupoColores)}")
        grupo = view.findViewById(R.id.grupoColores) as RadioGroup
        texto = view.findViewById(R.id.nuevoTexto) as EditText
        otroColor = view.findViewById(R.id.otroTexto) as EditText
        ok = view.findViewById(R.id.ok) as Button

        ok.setOnClickListener{
            val main = activity as MainActivity
            val color = color()
            if(texto.text.toString() == ""){
                main.noValido("texto")
            }
            if(color != "no") {
                main.nuevaNota(texto.text.toString(), color)
                dismiss()
            }
            else {
                main.noValido("color")
            }
        }
    }

    private fun color():String{
        return when(grupo.checkedRadioButtonId){
            R.id.rojo -> "red"
            R.id.azul -> "blue"
            R.id.verde -> "green"
            R.id.otro -> checkColor(otroColor.text.toString())

            else -> {"no"}
        }
    }

    private fun checkColor(color:String):String{
        val regexNotAlpha = """^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})${'$'}""".toRegex()

        return if(color.matches(regexNotAlpha)){color}else{"no"}
    }
}