package com.example.codechallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(val orders:List<OrderModel>, val listener: (OrderModel,Int) -> Unit):RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(layoutInflater.inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        when(orders[position].type){
            "salad" ->  {
                var extraCost: Double= 2.0
                var countExtra: Double = 0.0
                for(ingredient in orders[position].ingredients){
                    countExtra++
                }
                extraCost += (countExtra-2)*0.5
                orders[position].cost = extraCost
            }

            "pizza" -> {
                var totalCost = 0.0
                when(orders[position].size){
                    "small"-> totalCost = 5.0
                    "medium"-> totalCost = 10.0
                    "big"-> totalCost = 15.0
                }
                if(orders[position].sauce!=null){
                    totalCost+= (5*orders[position].sauce.size)
                }
                if(orders[position].toppings!=null){
                    totalCost += orders[position].toppings.size
                }
                orders[position].cost = totalCost
            }

            "bread" -> orders[position].cost = 3.0

            "sausage" -> {orders[position].cost = 2.0
                if(orders[position].length==10){
                    orders[position].cost = 3.0
                }
            }
        }

        val item = orders[position]

        holder.bind(item)
        holder.binding.addItem.setOnClickListener{ listener(orders[position],position)}
    }

    override fun getItemCount(): Int {
        return orders.size
    }

}