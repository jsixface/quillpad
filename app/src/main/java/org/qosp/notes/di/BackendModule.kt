package org.qosp.notes.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.qosp.notes.data.sync.fs.StorageBackend
import org.qosp.notes.data.sync.nextcloud.NextcloudAPI
import org.qosp.notes.data.sync.nextcloud.NextcloudBackend
import retrofit2.Retrofit
import retrofit2.create

object BackendModule {
    private val json = Json { ignoreUnknownKeys = true }

    val module = module {
        single { getRetrofitted<NextcloudAPI>() }

        single {
            NextcloudBackend(
                nextcloudAPI = get(),
                noteRepository = get(),
                notebookRepository = get(),
                idMappingRepository = get()
            )
        }

        single {
            StorageBackend(
                prefRepo = get(),
                androidContext(),
                noteRepository = get(),
                notebookRepository = get(),
                idMappingRepository = get()
            )
        }
    }

    private inline fun <reified T> getRetrofitted(): T {
        return Retrofit.Builder()
            .baseUrl("http://localhost/") // Since the URL is configurable by the user we set it later during the request
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create()
    }

}
