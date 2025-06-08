package kbs.apps.mobiledevelopment.mathsforkids

object RandomProvider {
    private lateinit var rnd: kotlin.random.Random

    fun setSeed(seed: Long) {
        rnd = kotlin.random.Random(seed)
    }

    fun nextInt(bound: Int): Int = rnd.nextInt(bound)

    fun nextInt(range: IntRange): Int = rnd.nextInt(range.first, range.last + 1)
}