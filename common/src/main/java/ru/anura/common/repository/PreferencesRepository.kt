package ru.anura.common.repository

interface PreferencesRepository {
    fun saveData(key: String, value: String)
    fun getData(key: String, value: String): String
}