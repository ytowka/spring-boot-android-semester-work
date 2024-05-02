package com.danilkha.conentfrientdsclient.core.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class StorageModule {

    private val DATASTORE_NAME = "ketoDatastore"

    @Single
    fun datastore(
        context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(){
        context.preferencesDataStoreFile(DATASTORE_NAME)
    }
}