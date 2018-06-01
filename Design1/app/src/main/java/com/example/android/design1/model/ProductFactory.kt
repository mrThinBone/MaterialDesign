package com.example.android.design1.model

import com.example.android.design1.R

object ProductFactory {

    fun populate(): List<Product> {
        return listOf(
                Product("Shooting Stars", "$ 45", R.drawable.img_sneaker, R.color.product_yellow),
                Product("Pictures in Sky", "$ 575", R.drawable.img_sandal, R.color.product_green),
                Product("The basics of buying a telescope", "$ 892", R.drawable.img_shoe, R.color.product_blue),
                Product("The skyrider", "$ 23", R.drawable.img_ice_skate, R.color.product_purple)
        )
    }
}