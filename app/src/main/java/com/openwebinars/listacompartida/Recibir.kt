package com.openwebinars.listacompartida

import android.os.AsyncTask
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.ObjectInputStream
import java.net.InetAddress
import java.net.Socket

class Recibir:AsyncTask<Unit,Int,ArrayList<Nota>>() {

    private lateinit var server:Socket
    private val puerto = 5000
    private lateinit var notas:ArrayList<Nota>

    override fun doInBackground(vararg params: Unit?): ArrayList<Nota> {
        val localhost = InetAddress.getLocalHost()
        server = Socket(localhost,puerto)

        val repeticiones:Int = DataInputStream(server.getInputStream()).readInt()

        notas = ArrayList<Nota>()
        val input = ObjectInputStream(server.getInputStream())
        for (i in 1..repeticiones){
            notas.add(input.readObject() as Nota)
        }

        return notas
    }

    override fun onPostExecute(result: ArrayList<Nota>?) {
        super.onPostExecute(result)
        if (result != null) {
            notas = result
        }
    }

    fun getNotas():ArrayList<Nota> = notas
}