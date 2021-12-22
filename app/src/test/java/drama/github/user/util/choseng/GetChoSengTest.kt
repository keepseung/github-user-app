package drama.github.user.util.choseng

import org.junit.Assert.assertEquals
import org.junit.Test

class GetChoSengTest {

    @Test
    fun `Get Choseng Test`(){
        val wordList = listOf(
            "떡볶이", "돈까스", "치킨", "abc", "edf"
        )

        val item1: String = wordList[0]
        val item2: String = wordList[1]
        val item3: String = wordList[2]
        val item4: String = wordList[3]
        val item5: String = wordList[4]

        val choseng1 = GetChoSeng.getChoseng(item1)
        val choseng2 = GetChoSeng.getChoseng(item2)
        val choseng3 = GetChoSeng.getChoseng(item3)
        val choseng4 = GetChoSeng.getChoseng(item4)
        val choseng5 = GetChoSeng.getChoseng(item5)

        assertEquals(choseng1, "ㄸ")
        assertEquals(choseng2, "ㄷ")
        assertEquals(choseng3, "ㅊ")
        assertEquals(choseng4, "a")
        assertEquals(choseng5, "e")
    }
}