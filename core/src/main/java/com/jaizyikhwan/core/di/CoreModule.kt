package com.jaizyikhwan.core.di

import androidx.room.Room
import com.jaizyikhwan.core.BuildConfig
import com.jaizyikhwan.core.data.source.local.LocalDataSource
import com.jaizyikhwan.core.data.source.local.room.FilmDatabase
import com.jaizyikhwan.core.data.source.remote.RemoteDataSource
import com.jaizyikhwan.core.data.source.remote.network.ApiService
import com.jaizyikhwan.core.data.source.repository.FilmRepositoryImpl
import com.jaizyikhwan.core.domain.repository.FilmRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {

    // Provide Database
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("film".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            FilmDatabase::class.java,
            "film_database.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    // Provide DAO
    single { get<FilmDatabase>().FilmDao() }
}


// Modul Koin untuk Network
val networkModule = module {

    // Menyediakan HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    // Menyediakan TokenInterceptor
    single {
        val apiKey = BuildConfig.API_KEY
        Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(request)
        }
    }

    // Menyediakan OkHttpClient
    single {
        val loggingInterceptor: HttpLoggingInterceptor = get()
        val tokenInterceptor: Interceptor = get()

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }


    // Menyediakan Retrofit
    single {
        val okHttpClient: OkHttpClient = get()
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Menyediakan ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }

}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<FilmRepository> { FilmRepositoryImpl(get(), get()) }
}
