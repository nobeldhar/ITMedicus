package nobel.dhar.itmdicus.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import dagger.Module
import dagger.Provides
import nobel.dhar.itmdicus.BuildConfig
import nobel.dhar.itmdicus.data.local.SpliffDao
import nobel.dhar.itmdicus.data.local.SpliffDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    val callback = object : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("TAG", "onCreate Database: ")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            Log.d("TAG", "onOpen: ")
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): SpliffDatabase {
        return Room
            .databaseBuilder(application, SpliffDatabase::class.java, BuildConfig.DATABASE_NAME)
            .createFromAsset("database/spliff.db")
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideBlogDao(@NonNull database: SpliffDatabase): SpliffDao {
        return database.spliffDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@NonNull application: Application): SharedPreferences {
        return application.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    }
}