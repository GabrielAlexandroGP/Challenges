package com.example.codechallenge

data class OrderModel(
    var type : String,
    var name : String,
    var ingredients : List<String>,
    var size : String,
    var toppings : List<String>,
    var sauce : List<String>,
    var shape : String,
    var grain : String,
    var meat : String,
    var length: Int,
    var cost: Double = 0.0
)