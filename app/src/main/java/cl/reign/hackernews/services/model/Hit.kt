package cl.reign.hackernews.services.model


import com.google.gson.annotations.SerializedName

data class Hit(
    @SerializedName("author")
    val author: String,
    @SerializedName("comment_text")
    val commentText: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_at_i")
    val createdAtI: Int,
    @SerializedName("story_id")
    val storyId: Int,
    @SerializedName("story_title")
    val storyTitle: String,
    @SerializedName("story_url")
    val storyUrl: Any
)