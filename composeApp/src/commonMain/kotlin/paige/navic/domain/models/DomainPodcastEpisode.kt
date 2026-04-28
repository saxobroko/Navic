package paige.navic.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Immutable
@Serializable
data class DomainPodcastEpisode(
	val id: String,
	val streamId: String,
	val channelId: String,
	val description: String?,
	val coverArtId: String?,
	val publishDate: Instant,
	val starredAt: Instant?,
	val musicBrainzId: String?
)
