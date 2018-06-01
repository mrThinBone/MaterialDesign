package com.example.android.design1.recycler

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.android.design1.R
import com.example.android.design1.model.Product

class ProductAdapter(private val products: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = products[position]
        holder.bind(p)
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.product_name)
        private val price: TextView = view.findViewById(R.id.product_price)
        private val image: ImageView = view.findViewById(R.id.product_img)

        fun bind(product: Product) {
            name.text = product.name
            price.text = product.price
            image.background = productBackground(product.color)
            image.setImageResource(product.image)
        }

        private fun productBackground(productColor: Int): GradientDrawable {
            val gradientDrawable = ContextCompat.getDrawable(view.context, R.drawable.bg_product) as GradientDrawable
            gradientDrawable.setColor(ContextCompat.getColor(view.context, productColor))
            gradientDrawable.setSize(view.height, drawableHeight())
            gradientDrawable.mutate()
            return gradientDrawable
        }

        private fun drawableHeight(): Int {
            val res = view.context.resources
            return if(adapterPosition % 2 == 0) res.getDimensionPixelOffset(R.dimen.product_regular_height) else
                res.getDimensionPixelOffset(R.dimen.product_large_height)
        }
    }
}