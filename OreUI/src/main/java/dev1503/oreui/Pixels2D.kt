package dev1503.oreui

class Pixels2D {
    var width: Int = 0
    var height: Int = 0
    var pixels: LongArray = longArrayOf()

    companion object {
        @JvmField
        val PIXELS_SWITCH_LEFT = fromText("""
            1
            1
            1
            1
            1
            1
        """.trimIndent(), '1')
        @JvmField
        val PIXELS_SWITCH_RIGHT = fromText("""
            011110
            100001
            100001
            100001
            100001
            011110
        """.trimIndent(), '1')
        @JvmField
        val PIXELS_SHORT_ARROW_DOWN = fromText("""
            1000001
            0100010
            0010100
            0001000
        """.trimIndent(), '1')
        @JvmField
        val PIXELS_SHORT_ARROW_UP = fromText("""
            0001000
            0010100
            0100010
            1000001
        """.trimIndent(), '1')

        @JvmField
        val PIXELS_CIRCULAR_PROGRESSES = listOf(
            fromText("""
                0011100
                0100010
                1000001
                1000001
                0000000
                0000000
                0000000
            """.trimIndent(), '1'),
            fromText("""
                0011100
                0100010
                0000001
                0000001
                0000001
                0000010
                0000000
            """.trimIndent(), '1'),
            fromText("""
                0001100
                0000010
                0000001
                0000001
                0000001
                0000010
                0001100
            """.trimIndent(), '1'),
            fromText("""
                0000100
                0000010
                0000001
                0000001
                0000001
                0000010
                0011100
            """.trimIndent(), '1'),
            fromText("""
                0000000
                0000000
                0000001
                0000001
                1000001
                0100010
                0011100
            """.trimIndent(), '1'),
            fromText("""
                0000000
                0000000
                0000000
                1000001
                1000001
                0100010
                0011100
            """.trimIndent(), '1'),
            fromText("""
                0000000
                0100000
                1000000
                1000000
                1000000
                0100010
                0011100
            """.trimIndent(), '1'),
            fromText("""
                0010000
                0100000
                1000000
                1000000
                1000000
                0100000
                0011100
            """.trimIndent(), '1'),
            fromText("""
                0011000
                0100000
                1000000
                1000000
                1000000
                0100000
                0011000
            """.trimIndent(), '1'),
            fromText("""
                0011100
                0100010
                1000000
                1000000
                1000000
                0100000
                0000000
            """.trimIndent(), '1')
        )

        @JvmStatic
        fun fromText(text: String, foregroundSymbol: Char): Pixels2D {
            val instance = Pixels2D()
            val lines = text.lines()

            instance.height = lines.size
            instance.width = lines.maxOfOrNull { it.length } ?: 0

            val tempPixels = mutableListOf<Long>()
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (char == foregroundSymbol) {
                        tempPixels.add((x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFFL))
                    }
                }
            }
            instance.pixels = tempPixels.toLongArray()
            return instance
        }
    }
}