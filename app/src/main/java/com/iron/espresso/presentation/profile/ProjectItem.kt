package com.iron.espresso.presentation.profile

import android.os.Parcelable
import com.iron.espresso.domain.entity.Project
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectItem(
    val id: Int = -1,
    val title: String = "",
    val contents: String = "",
    val githubUrl: String = "",
    val appStoreUrl: String = "",
    val playStoreUrl: String = ""
) : Parcelable {

    fun isEmptyItem(): Boolean =
        id == -1
            && title == ""
            && contents == ""
            && githubUrl == ""
            && appStoreUrl == ""
            && playStoreUrl == ""

    fun toProject(): Project =
        Project(id, title, contents, githubUrl, appStoreUrl, playStoreUrl)

    companion object {
        fun of(project: Project): ProjectItem =
            ProjectItem(project.id, project.title, project.contents, project.githubUrl, project.appStoreUrl, project.playStoreUrl)
    }
}
