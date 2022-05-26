package com.example.musicapp.mapper

interface Mapper<C,D>{
    fun mapToEntity(type : C) : D
    fun mapFromEntity(type : D) : C
}