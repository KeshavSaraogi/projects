package com.example.recipe.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class Repository {

    @ViewModelScoped
    class Repository @Inject constructor(
        remoteDataSource: RemoteDataSource,
    ) {
        val remote = remoteDataSource
    }
}