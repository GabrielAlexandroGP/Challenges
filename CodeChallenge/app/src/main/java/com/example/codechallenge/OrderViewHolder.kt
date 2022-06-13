package com.example.codechallenge

import android.view.View
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.databinding.ItemOrderBinding

class OrderViewHolder(view: View):RecyclerView.ViewHolder(view){

    val binding = ItemOrderBinding.bind(view)
    fun bind(orderData:OrderModel){

        binding.tipoOrden.text = orderData.type
        binding.sauce.isVisible = false

        when(orderData.type){
            "salad" -> {binding.info.text = orderData.name
                var ingredients:String = ""
                for(ingredient in orderData.ingredients){
                    ingredients = ingredients+ingredient+" "
                }
                binding.toppings.text = "Ingredients: "+ingredients
                binding.costoEuros.text = "${orderData.cost}"+"€"
            }

            "pizza" -> {binding.info.text = orderData.size
                var toppings:String = ""
                var sauce:String = ""
                for(ingredient in orderData.toppings){
                    toppings = "$toppings$ingredient "
                }
                if(orderData.sauce != null){
                    for(sauceItem in orderData.sauce) {
                        sauce = sauce+sauceItem+" "
                    }
                    binding.sauce.text = "Sauce: "+sauce
                    binding.sauce.isVisible = true
                }
                binding.toppings.text = toppings
                binding.costoEuros.text = "${orderData.cost}"+"€"
            }
            "bread" -> {binding.info.text = "Shape: "+orderData.shape + " Grain: " + orderData.grain
                binding.costoEuros.text = "3.0€"
            }
            "sausage" -> {binding.info.text = "Meat: " + orderData.meat + " Lenght: "+ orderData.length
                binding.costoEuros.text = "${orderData.cost}"+"€"
            }
            else-> print("Tipo desconocido")
        }
    }

}