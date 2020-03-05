package cl.reign.hackernews.services.model


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("exhaustiveNbHits")
    val exhaustiveNbHits: Boolean,
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("hitsPerPage")
    val hitsPerPage: Int,
    @SerializedName("nbHits")
    val nbHits: Int,
    @SerializedName("nbPages")
    val nbPages: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("params")
    val params: String,
    @SerializedName("processingTimeMS")
    val processingTimeMS: Int,
    @SerializedName("query")
    val query: String
)