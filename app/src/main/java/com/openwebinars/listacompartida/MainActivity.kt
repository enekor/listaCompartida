package com.openwebinars.listacompartida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var notas = ArrayList<Nota>()
    private lateinit var recycler:RecyclerView
    private val manager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nuevaNota -> {
                val nueva = NuevaNota()
                nueva.show(supportFragmentManager,null)
            }
            R.id.recibe -> {
                leer()
            }
            R.id.share -> {
                enviar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponents(){
        notas = Serializador(this).leer<Nota>("notas.json")
        Log.i("size","${notas.toString()}")

        recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = manager

        setAdapter()
    }

    private fun setAdapter(){
        val adaptador = Adaptador(notas,this)
        recycler.adapter = adaptador
    }

    fun nuevaNota(texto:String,color:String){
        notas.add(Nota(texto,color,false))
        guardarNotas()
        setAdapter()
    }

    fun noValido(contexto:String){
        when(contexto){
            "color" -> Toast.makeText(this, "El color debe seguir el patron #RRGGBB", Toast.LENGTH_SHORT).show()
            "texto" -> Toast.makeText(this, "No se puede crear una nota vacia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarNotas(){
        Serializador(this).guardar<Nota>(notas,"notas.json")
    }

    public fun editSelects(position:Int,selected:Boolean){
        notas[position].checked = selected
    }

    private fun leer(){
        val recibir = Recibir()
        try{
            Toast.makeText(this, "Recibiendo...", Toast.LENGTH_SHORT).show()
            recibir.execute()
            notas = recibir.get()
        }catch (e:Exception){
            Toast.makeText(this, "Espera que se conecte el emisor", Toast.LENGTH_SHORT).show()
            Log.i("error","${e.stackTraceToString()}")
        }
    }

    private fun enviar(){
        val enviar = Enviar(notas)
        Toast.makeText(this, "Enviando...", Toast.LENGTH_SHORT).show()
        enviar.execute()
    }
}