package com.example.jpsubmission2.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteRepo: RemoteRepository,
    //localRepo: LocalDataSource
){
    val remote = remoteRepo
    //val local = localRepo

}