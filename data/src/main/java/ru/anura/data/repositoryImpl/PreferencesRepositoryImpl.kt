package ru.anura.data.repositoryImpl

import ru.anura.common.repository.PreferencesRepository
import ru.anura.data.PreferencesManager
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val preferencesManager: PreferencesManager) :
    PreferencesRepository {

    override fun saveData(key: String, value: String) {
        preferencesManager.saveData(key, value)
    }

    override fun getData(key: String, value: String): String {
        return preferencesManager.getData(key, value)
    }
}