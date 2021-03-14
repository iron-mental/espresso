package com.wswon.picker

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UtilModule {

    @ActivityScoped
    @Provides
    fun provideImageLoadManager(@ActivityContext context: Context): ImageLoadManager {
        return ImageLoadManager(context)
    }
}