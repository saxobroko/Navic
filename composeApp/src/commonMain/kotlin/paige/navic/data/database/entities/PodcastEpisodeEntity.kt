package paige.navic.data.database.entities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlin.time.Instant

@Entity
data class PodcastEpisodeEntity(
	@PrimaryKey val episodeId: String,
	val streamId: String,
	val belongsToChannelId: String,
	val description: String?,
	val coverArtId: String?,
	val publishDate: Instant,
	val starredAt: Instant?,
	val musicBrainzId: String?
)
