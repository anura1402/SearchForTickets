package ru.anura.feature_search.usecases

import ru.anura.common.repository.PreferencesRepository
import javax.inject.Inject

class SaveDataUseCase @Inject constructor(private val repository: PreferencesRepository) {
    operator fun invoke(key: String, value: String) = repository.saveData(key, value)
}