package paige.navic.data.database.mappers

import kotlinx.collections.immutable.toImmutableList
import paige.navic.data.database.entities.PodcastChannelEntity
import paige.navic.data.database.entities.PodcastEpisodeEntity
import paige.navic.data.database.relations.PodcastChannelWithEpisodes
import paige.navic.domain.models.DomainPodcastChannel
import paige.navic.domain.models.DomainPodcastEpisode
import dev.zt64.subsonic.api.model.PodcastChannel as ApiPodcastChannel
import dev.zt64.subsonic.api.model.PodcastEpisode as ApiPodcastEpisode

fun ApiPodcastChannel.toEntity() = PodcastChannelEntity(
	channelId = id,
	title = title,
	url = url,
	description = description,
	coverArtId = coverArtId
)

fun ApiPodcastEpisode.toEntity() = PodcastEpisodeEntity(
	episodeId = id,
	streamId = streamId,
	belongsToChannelId = channelId,
	description = description,
	coverArtId = coverArtId,
	publishDate = publishDate,
	starredAt = starredAt,
	musicBrainzId = musicBrainzId
)

fun PodcastEpisodeEntity.toDomainModel() = DomainPodcastEpisode(
	id = episodeId,
	streamId = streamId,
	channelId = belongsToChannelId,
	description = description,
	coverArtId = coverArtId,
	publishDate = publishDate,
	starredAt = starredAt,
	musicBrainzId = musicBrainzId
)

fun PodcastChannelWithEpisodes.toDomainModel() = DomainPodcastChannel(
	id = channel.channelId,
	title = channel.title,
	url = channel.url,
	description = channel.description,
	coverArtId = channel.coverArtId,
	episodes = episodes.map { it.toDomainModel() }.toImmutableList()
)
