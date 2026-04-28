package paige.navic.ui.screens.podcast.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import paige.navic.data.session.SessionManager
import paige.navic.domain.models.DomainPodcastChannel
import paige.navic.domain.repositories.PodcastChannelRepository
import paige.navic.utils.UiState

class PodcastChannelListViewModel(
	private val repository: PodcastChannelRepository
) : ViewModel() {
	private val _channelsState = MutableStateFlow<UiState<ImmutableList<DomainPodcastChannel>>>(UiState.Loading())
	val channelsState = _channelsState.asStateFlow()

	init {
		viewModelScope.launch {
			SessionManager.isLoggedIn.collect { if (it) refreshChannels(false) }
		}
	}

	fun refreshChannels(fullRefresh: Boolean) {
		viewModelScope.launch {
			repository.getPodcastsFlow(fullRefresh).collect {
				_channelsState.value = it
			}
		}
	}

	fun clearError() {
		_channelsState.value = UiState.Success(_channelsState.value.data ?: persistentListOf())
	}
}
