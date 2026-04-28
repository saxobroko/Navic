package paige.navic.domain.repositories

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import paige.navic.data.database.dao.PodcastChannelDao
import paige.navic.data.database.mappers.toDomainModel
import paige.navic.domain.models.DomainPodcastChannel
import paige.navic.utils.UiState

class PodcastChannelRepository(
	private val podcastChannelDao: PodcastChannelDao,
	private val dbRepository: DbRepository
) {
	private suspend fun getLocalData(): ImmutableList<DomainPodcastChannel> {
		return podcastChannelDao.getChannels()
			.map { it.toDomainModel() }
			.toImmutableList()
	}

	private suspend fun refreshLocalData(): ImmutableList<DomainPodcastChannel> {
		//dbRepository.syncPodcasts().getOrThrow().forEach { podcast ->
		//	dbRepository.syncPodcastEpisodes(podcast.id).getOrThrow()
		//}
		return getLocalData()
	}

	fun getPodcastsFlow(
		fullRefresh: Boolean
	): Flow<UiState<ImmutableList<DomainPodcastChannel>>> = flow {
		val localData = getLocalData()
		if (fullRefresh) {
			emit(UiState.Loading(data = localData))
			try {
				emit(UiState.Success(data = refreshLocalData()))
			} catch (error: Exception) {
				emit(UiState.Error(error = error, data = localData))
			}
		} else {
			emit(UiState.Success(data = localData))
		}
	}.flowOn(Dispatchers.IO)
}
