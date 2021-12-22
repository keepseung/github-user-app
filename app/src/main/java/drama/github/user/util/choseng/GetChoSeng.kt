package drama.github.user.util.choseng

/**
 * 한글, 영어 등등의 초성을 가져오기 위함
 */
object GetChoSeng {
    private val CHOSEONG_COUNT = 19
    private val JUNGSEONG_COUNT = 21
    private val JONGSEONG_COUNT = 28
    private val HANGUL_SYLLABLE_COUNT = CHOSEONG_COUNT * JUNGSEONG_COUNT * JONGSEONG_COUNT
    private val COMPAT_CHOSEONG_COLLECTION = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    private val HANGUL_SYLLABLES_BASE = 0xAC00
    private val HANGUL_SYLLABLES_END: Int = HANGUL_SYLLABLES_BASE + HANGUL_SYLLABLE_COUNT

    /**
     * 주어진 문자가 한글 음절인지 검사한다.
     *
     * @param c 검사할 문자
     * @return {@code c}가 한글 음절이면 {@code true}, 아니면 {@code false}.
     */
    private fun isSyllable(c: Char): Boolean {
        return c.code in HANGUL_SYLLABLES_BASE until HANGUL_SYLLABLES_END
    }

    /**
     * 주어진 한글 음절로부터 Unicode Hangul Compatibility Jamo 초성을 추출한다.
     *
     * @param syllable 초성을 추출할 한글 음절
     * @return Unicode Hangul Compatibility Jamo 초성.
     * @throws IllegalArgumentException `syllable`이 한글 음절이 아닐 때.
     */
    fun getCompatChoseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }
        return COMPAT_CHOSEONG_COLLECTION[getChoseongIndex(syllable)]
    }

    private fun getChoseongIndex(syllable: Char): Int {
        val sylIndex = syllable.code - HANGUL_SYLLABLES_BASE
        return sylIndex / (JUNGSEONG_COUNT * JONGSEONG_COUNT)
    }

    /**
     * 한글인지 여부 확인함
     */
    fun isKorean(ch: Char): Boolean {
        return ch.code >= "AC00".toInt(16) && ch.code <= "D7A3".toInt(16)
    }

    fun getChoseng(word:String):String{
        // 단어의 첫 글자 추출
        var index = word.substring(0, 1)
        // char 형태 변환
        val c:Char = index[0]
        // 한글이면?
        if (isKorean(c)) {
            // 초성 추출
            return java.lang.String.valueOf(getCompatChoseong(c))

            // 한글이 아닌 영어 등등의 언어인 경우 첫 음절을 가져온다.
        }else{
            return index
        }

    }
}