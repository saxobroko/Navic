package paige.navic.data.database.entities

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class PodcastChannelEntity(
	@PrimaryKey val channelId: String,
	val title: String,
	val url: String,
	val description: String,
	val coverArtId: String?
)
