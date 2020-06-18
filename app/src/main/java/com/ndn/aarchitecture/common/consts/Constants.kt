package com.ndn.aarchitecture.common.consts

object Constants {
    /**
     * Default value
     */
    const val DEFAULT_INT = 0
    const val DEFAULT_LONG = 0L
    const val DEFAULT_STRING = ""
    const val DEFAULT_DOUBLE = 0.0
    const val DEFAULT_FLOAT = 0F
    const val DEFAULT_BOOLEAN = false

    const val RX_BINDING_TIME_DELAY = 1000L

    /**
     * Key Bundle
     */
    const val EXTRA_ARGS = "extra_args"

    /**
     * Animation
     */
    object AnimationType {
        const val FADE = 0x01
        const val SLIDE_TO_RIGHT = 0x02
        const val SLIDE_TO_LEFT = 0x03
        const val BOTTOM_UP = 0x04
    }
}
