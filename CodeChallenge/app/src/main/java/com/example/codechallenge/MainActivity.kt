package com.example.codechallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var  binding:ActivityMainBinding
    private lateinit var adapter: OrderAdapter
    private val ordersList = mutableListOf<OrderModel>()
    val numbers: IntArray = intArrayOf(0, 0, 0, 0, 0,0,0)
    var totalCost = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }

    private fun initRecyclerView(){
        adapter = OrderAdapter(ordersList){ position,pos->
            if(numbers[pos]==0){
                numbers[pos]=1
                totalCost+=position.cost
            }else{
                numbers[pos]=0
                totalCost-=position.cost
            }
            binding.total.text = "Cost:"+"$totalCost"+"€"
            convertToDollars("")
        }
        binding.rvOrders.layoutManager = LinearLayoutManager(this)
        binding.rvOrders.adapter = adapter
        searchOrder("")
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/r-casarez-garcia-charter/57260b09dcbf415cef0f4fbe91ab468b/raw/7b27a6846259c6bd2b59d691f6e43c195e4e621d/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchOrder(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<OrderResponse> = getRetrofit().create(APIService::class.java).getOrders("order.json")
            val orders : OrderResponse? =  call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    //Show RecyclerView
                    val test = orders?.order ?: emptyList()
                    ordersList.clear()
                    ordersList.addAll(test)
                    adapter.notifyDataSetChanged()
                }else{
                    //show Error
                   showError()
                }
            }
        }
    }

    private fun showError(){
        Toast.makeText(this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofitDollar():Retrofit{
        return Retrofit.Builder().baseUrl("https://gist.githubusercontent.com/darkalor/8b916a24ee746c432165ecefeeb5831a/raw/992f1ca2db87c83d58af8cb8d105dd88790a3195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun convertToDollars(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ExchangesResponse> = getRetrofitDollar().create(APIService::class.java).getExchanges("euroPrice.json")
            val dollar : ExchangesResponse? =  call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    //Show RecyclerView
                    val results = dollar?.USD ?: 0.00

                    if(results==0.00){
                        binding.total.text = "Empty list"
                    }else{
                        binding.total.text = "Cost:"+"$totalCost"+"€"+" USD: $"+"${results*totalCost}"
                    }
                }else{
                    //show Error
                    showError()
                }
            }
        }
    }
}