package com.example.ivcare.uzerdatabase

class UzerRepository(private val dao : UzerDAO) {

    val uzers = dao.getAllUzers()

    suspend fun insert(uzer: Uzer) : Long{
        return dao.insertUzer(uzer)
    }

    suspend fun update(uzer: Uzer) : Int{
        return dao.updateUzer(uzer)
    }

    suspend fun delete(uzer: Uzer) : Int{
        return dao.deleteUzer(uzer)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }
}