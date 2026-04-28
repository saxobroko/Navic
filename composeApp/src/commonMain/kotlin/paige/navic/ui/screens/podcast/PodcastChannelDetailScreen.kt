package paige.navic.ui.screens.podcast

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import paige.navic.ui.screens.podcast.viewmodels.PodcastChannelDetailViewModel

@Composable
fun PodcastChannelDetailScreen(channelId: String) {
	val viewModel = koinViewModel<PodcastChannelDetailViewModel>(
		parameters = { parametersOf(channelId) }
	)
}
