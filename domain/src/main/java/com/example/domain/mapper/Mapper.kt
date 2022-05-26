package com.example.domain.mapper

interface Mapper<C,D>{
    fun mapToEntity(type : C) : D
    fun mapFromEntity(type : D) : C
}