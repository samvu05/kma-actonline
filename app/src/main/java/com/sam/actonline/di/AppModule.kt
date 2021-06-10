package com.sam.actonline.di

import android.content.Context
import androidx.room.Room
import com.sam.actonline.BaseApplication
import com.sam.actonline.network.MoodleInterceptor
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.Constant
import com.sam.actonline.utils.PrefHelper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sam.actonline.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Dinh Sam Vu on 4/15/2021.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesPreferences(@ApplicationContext context: Context): PrefHelper {
        return PrefHelper(context.getSharedPreferences(PrefHelper.APP_PREFS, Context.MODE_PRIVATE))
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun providesMyDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "my_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesMoodleOkHttpClient(prefHelper: PrefHelper): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(MoodleInterceptor(prefHelper))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoodleService(client: OkHttpClient): MoodleService {
        val retrofitBuilder = Retrofit.Builder()
            .client(client)
            .baseUrl(Constant.BASE_SEVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofitBuilder.create(MoodleService::class.java)
    }
}
