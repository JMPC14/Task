package com.jack.utilitatask.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Klaxon
import com.google.gson.JsonElement
import com.jack.utilitatask.api.UtilitaApi
import com.jack.utilitatask.model.UtilitaStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListViewModel: ViewModel() {

    var dbs = MutableLiveData<HashMap<String, UtilitaStatus>>()
    var sites = MutableLiveData<HashMap<String, UtilitaStatus>>()

    init {
        dbs.value = HashMap()
        sites.value = HashMap()
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://private-176645-utilita.apiary-mock.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(UtilitaApi::class.java)

    fun getPosts() {

        val call = api.getPosts()
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                val text = response.body()
                if (text != null) {
                    val jsonObject = text.asJsonObject
                    val first = jsonObject.get("APIs & DBs").asJsonObject
                    val second = jsonObject.get("Sites").asJsonObject
                    first.keySet().forEach {
                        val status = Klaxon().parse<UtilitaStatus>(first.get(it).toString())
                        if (status != null) {
                            dbs.value!![it] = status
                        }
                    }

                    second.keySet().forEach {
                        val status = Klaxon().parse<UtilitaStatus>(second.get(it).toString())
                        if (status != null) {
                            sites.value!![it] = status
                        }
                    }
                    dbs.notifyObserver()
                    sites.notifyObserver()
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("TAG", t.localizedMessage!!)
            }
        })
    }
}