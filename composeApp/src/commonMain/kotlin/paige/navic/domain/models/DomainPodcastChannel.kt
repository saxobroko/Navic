package paige.navic.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class DomainPodcastChannel(
	val id: String,
	val title: String,
	val url: String,
	val description: String,
	val coverArtId: String?,
	val episodes: ImmutableList<DomainPodcastEpisode>
)
