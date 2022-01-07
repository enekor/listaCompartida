package com.openwebinars.listacompartida

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket

class Enviar(lista:ArrayList<Nota>):AsyncTask<Unit,Integer,Unit>() {

    private val notas = lista
    private lateinit var server:ServerSocket
    private lateinit var cliente:Socket
    private val puerto = 5000

    override fun doInBackground(vararg params: Unit?) {
        server = ServerSocket(puerto)
        while(true){
            Log.i("info","esperando")
            cliente = server.accept()
            val outputCantidad = DataOutputStream(cliente.getOutputStream())
            outputCantidad.writeInt(notas.size)

            val output = ObjectOutputStream(cliente.getOutputStream())
            notas.forEach{
                    v-> output.writeObject(v)
            }

            cliente.close()
        }


    }

}