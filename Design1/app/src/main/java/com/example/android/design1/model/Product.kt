package com.example.android.design1.model

import android.os.Parcel
import android.os.Parcelable

data class Product(val name: String, val price: String, val image: Int, val color: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeString(name)
            it.writeString(price)
            it.writeInt(image)
            it.writeInt(color)
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}