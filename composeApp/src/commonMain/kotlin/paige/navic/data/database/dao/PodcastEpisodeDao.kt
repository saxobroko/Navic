package paige.navic.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow
import paige.navic.data.database.entities.PodcastEpisodeEntity
import paige.navic.shared.Logger

@Dao
interface PodcastEpisodeDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertEpisode(channel: PodcastEpisodeEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertEpisodes(channels: List<PodcastEpisodeEntity>)

	@Transaction
	@Query("SELECT * FROM PodcastEpisodeEntity ORDER BY publishDate ASC")
	fun getEpisodesFlow(): Flow<List<PodcastEpisodeEntity>>

	@Transaction
	@Query("SELECT * FROM PodcastEpisodeEntity WHERE episodeId = :episodeId LIMIT 1")
	suspend fun getEpisodeById(episodeId: String): PodcastEpisodeEntity?

	@Query("DELETE FROM PodcastEpisodeEntity WHERE episodeId = :episodeId")
	suspend fun deleteEpisode(episodeId: String)

	@Query("DELETE FROM PodcastEpisodeEntity")
	suspend fun clearAllEpisodes()

	@Query("SELECT episodeId FROM PodcastEpisodeEntity")
	suspend fun getAllEpisodeIds(): List<String>

	@Transaction
	suspend fun updateAllEpisodes(remoteEpisodes: List<PodcastEpisodeEntity>) {
		deleteObsoleteEpisodes(remoteEpisodes.map { it.episodeId }.toSet())
		insertEpisodes(remoteEpisodes)
	}

	@Transaction
	suspend fun deleteObsoleteEpisodes(remoteIds: Set<String>) {
		getAllEpisodeIds().forEach { localId ->
			if (localId !in remoteIds) {
				Logger.w("PodcastEpisodeDao", "episode $localId no longer exists remotely")
				deleteEpisode(localId)
			}
		}
	}
}
