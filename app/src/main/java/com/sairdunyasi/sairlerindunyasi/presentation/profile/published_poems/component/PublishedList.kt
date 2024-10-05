package com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sairdunyasi.sairlerindunyasi.domain.model.PoemModel
import com.sairdunyasi.sairlerindunyasi.presentation.profile.published_poems.events.PublishedPoemEvent

@Composable
fun PublishedList(
    poems: List<PoemModel>,
    paddingValues: PaddingValues,
    onEvent: (PublishedPoemEvent) -> Unit,
    context: android.content.Context
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 8.dp)
    ) {
        items(poems) { poem ->
            PublishedListItem(
                item = poem.siirBasligi,
                onDetailClick = { onEvent(PublishedPoemEvent.DetailPoem(poem)) },
                onDeleteClick = { onEvent(PublishedPoemEvent.DeletePoem(poem.id)) },
                context = context
            )
        }
    }
}
