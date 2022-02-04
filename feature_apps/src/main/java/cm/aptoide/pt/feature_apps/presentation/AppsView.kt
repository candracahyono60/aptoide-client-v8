package cm.aptoide.pt.feature_apps.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cm.aptoide.pt.feature_apps.domain.Widget

@Composable
fun AppsScreen(viewModel: BundlesViewModel) {
  val bundles: List<Widget> by viewModel.bundlesList.collectAsState(initial = emptyList())
  val isLoading: Boolean by viewModel.isLoading
  BundlesScreen(isLoading, bundles)
}

@Composable
private fun BundlesScreen(
  isLoading: Boolean,
  bundles: List<Widget>
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .wrapContentSize(Alignment.Center)
  ) {
    if (isLoading)
      CircularProgressIndicator()
    else
      bundles.forEach {
        Text(it.title)
      }
  }
}

@Preview
@Composable
internal fun AppsScreenPreview() {
  BundlesScreen(
    false,
    listOf(
      createFakeWidget(),
      createFakeWidget(),
      createFakeWidget(),
      createFakeWidget(),
      createFakeWidget()
    )
  )
}

fun createFakeWidget(): Widget {
  return Widget(title = "Widget title")
}
