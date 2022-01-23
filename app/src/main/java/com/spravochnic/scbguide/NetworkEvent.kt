package com.spravochnic.scbguide

class NetworkEvent<T>(
    content: T,
    val error: String? = null
) : Event<T>(content)