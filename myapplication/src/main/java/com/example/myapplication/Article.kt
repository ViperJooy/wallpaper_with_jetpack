package com.example.myapplication

data class Article(val code: Int, val data: Data)
data class Data(val categoryname: String, val items: List<Item>)
data class Item(val title: String, val brief: String)


