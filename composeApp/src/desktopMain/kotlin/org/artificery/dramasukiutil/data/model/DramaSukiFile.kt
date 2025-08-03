package org.artificery.dramasukiutil.data.model

data class DramaSukiFile(
    val folderName: String,
    val title: String,
    val year: Long,
    val alternativeTitle: String? = null,
)
