package com.example.android.design1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.android.design1.model.ProductFactory
import com.example.android.design1.recycler.OnItemSelectedListener
import com.example.android.design1.recycler.ProductAdapter
import com.example.android.design1.recycler.ProductItemDecoration

class HomeActivity : AppCompatActivity() {

    private var mAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAdapter = ProductAdapter(ProductFactory.populate())
        val recyclerView = findViewById<RecyclerView>(R.id.product_list)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(ProductItemDecoration(this))
        recyclerView.adapter = mAdapter
        recyclerView.addOnItemTouchListener(object: OnItemSelectedListener(this) {

            override fun onItemSelected(view: View, position: Int) {
                FragmentOrderForm.newInstance(mAdapter!!.product(position)).show(supportFragmentManager, null)
            }

        })
    }
}