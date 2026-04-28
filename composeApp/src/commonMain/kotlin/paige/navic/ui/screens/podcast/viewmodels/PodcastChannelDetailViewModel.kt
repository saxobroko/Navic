package paige.navic.ui.screens.podcast.viewmodels

import androidx.lifecycle.ViewModel
import paige.navic.domain.repositories.PodcastChannelRepository

class PodcastChannelDetailViewModel(
	channelId: String,
	private val repository: PodcastChannelRepository
) : ViewModel() {
}
