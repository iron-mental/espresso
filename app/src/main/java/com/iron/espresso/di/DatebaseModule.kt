package com.iron.espresso.di

import android.content.Context
import androidx.room.Room
import com.iron.espresso.local.model.ChatDatabase
import com.iron.espresso.local.model.ChatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideChatDao(appDatabase: ChatDatabase): ChatDao {
        return appDatabase.chatDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ChatDatabase {
        return Room.databaseBuilder(
            appContext,
            ChatDatabase::class.java,
            "chat_database"
        )
            .allowMainThreadQueries()
            .build()
    }
}