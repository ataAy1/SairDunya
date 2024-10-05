package com.sairdunyasi.sairlerindunyasi.presentation.profile.publish_poem.event


sealed class PublishPoemEvent {
    data class PublishPoem(val title: String, val content: String) : PublishPoemEvent()
    object Idle : PublishPoemEvent()
}
