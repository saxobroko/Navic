package paige.navic.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import paige.navic.data.database.entities.PodcastChannelEntity
import paige.navic.data.database.relations.PodcastChannelWithEpisodes
import paige.navic.shared.Logger

@Dao
interface PodcastChannelDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertChannel(channel: PodcastChannelEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertChannels(channels: List<PodcastChannelEntity>)

	@Transaction
	@Query("SELECT * FROM PodcastChannelEntity ORDER BY title ASC")
	suspend fun getChannels(): List<PodcastChannelWithEpisodes>

	@Transaction
	@Query("SELECT * FROM PodcastChannelEntity WHERE channelId = :channelId LIMIT 1")
	suspend fun getChannelById(channelId: String): PodcastChannelWithEpisodes?

	@Query("DELETE FROM PodcastChannelEntity WHERE channelId = :channelId")
	suspend fun deleteChannel(channelId: String)

	@Query("DELETE FROM PodcastChannelEntity")
	suspend fun clearAllChannels()

	@Query("SELECT channelId FROM PodcastChannelEntity")
	suspend fun getAllChannelIds(): List<String>

	@Transaction
	suspend fun updateAllChannels(remoteChannels: List<PodcastChannelEntity>) {
		deleteObsoleteChannels(remoteChannels.map { it.channelId }.toSet())
		insertChannels(remoteChannels)
	}

	@Transaction
	suspend fun deleteObsoleteChannels(remoteIds: Set<String>) {
		getAllChannelIds().forEach { localId ->
			if (localId !in remoteIds) {
				Logger.w("PodcastChannelDao", "channel $localId no longer exists remotely")
				deleteChannel(localId)
			}
		}
	}
}
