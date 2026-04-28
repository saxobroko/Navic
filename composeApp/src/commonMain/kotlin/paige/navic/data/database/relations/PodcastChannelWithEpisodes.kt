package paige.navic.data.database.relations

import androidx.room3.Embedded
import androidx.room3.Relation
import paige.navic.data.database.entities.PodcastChannelEntity
import paige.navic.data.database.entities.PodcastEpisodeEntity

data class PodcastChannelWithEpisodes(
	@Embedded val channel: PodcastChannelEntity,
	@Relation(
		parentColumn = "channelId",
		entityColumn = "belongsToChannelId"
	)
	val episodes: List<PodcastEpisodeEntity>
)
