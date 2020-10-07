package com.example.core

class CryptanalysisAffineCipher {
    companion object {
        var decodeStrings: List<DecodeStrings> = listOf()
        private const val M: Int = 32
        private val LETTERS =
            listOf(
                "а", "б", "в", "г", "д", "е", "ж", "з", "и", "й", "к", "л", "м", "н",
                "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
            )
        private val FREQUENCY_LETTERS = listOf(
            "о", "е", "а", "и", "т", "н", "с", "р", "в", "л", "к", "м",
            "д", "п", "у", "я", "ы", "з", "ъ", "ь", "б", "г", "ч", "й", "х", "ж", "ю", "ш", "ц", "щ", "э", "ф"
        )
        private fun extendedGcd(a: Int, b: Int): Triple<Int, Int, Int> =
            if (a == 0) Triple(b, 0, 1)
            else {
                val (g, s, t) = extendedGcd(b % a, a)
                Triple(g, t - (b / a) * s, s)
            }

        private fun mmi(a: Int, m: Int) = extendedGcd(a, m).let { (g, x, _) ->

            x % m
        }

         fun decode(encodeString: String): List<DecodeStrings> {
            val frequencyLetters: IntArray = frequencyLetterInEncodeString(encodeString)
            val lets: String = getLets(frequencyLetters)

            for (j in 1..M-1)
            {
                for (i in j-1 downTo 0)
                {
                    Cryptanalysis(encodeString, lets, FREQUENCY_LETTERS[i], FREQUENCY_LETTERS[j]);
                }
            }
            return decodeStrings
        }

        private fun frequencyLetterInEncodeString(text: String):IntArray {
            val frequencyLetters = IntArray(32)
            for (i in text.indices) {
                val letter: Char = text[i]
                if (letter == 'а') frequencyLetters[0]++
                if (letter == 'б') frequencyLetters[1]++
                if (letter == 'в') frequencyLetters[2]++
                if (letter == 'г') frequencyLetters[3]++
                if (letter == 'д') frequencyLetters[4]++
                if (letter == 'е') frequencyLetters[5]++
                if (letter == 'ж') frequencyLetters[6]++
                if (letter == 'з') frequencyLetters[7]++
                if (letter == 'и') frequencyLetters[8]++
                if (letter == 'й') frequencyLetters[9]++
                if (letter == 'к') frequencyLetters[10]++
                if (letter == 'л') frequencyLetters[11]++
                if (letter == 'м') frequencyLetters[12]++
                if (letter == 'н') frequencyLetters[13]++
                if (letter == 'о') frequencyLetters[14]++
                if (letter == 'п') frequencyLetters[15]++
                if (letter == 'р') frequencyLetters[16]++
                if (letter == 'с') frequencyLetters[17]++
                if (letter == 'т') frequencyLetters[18]++
                if (letter == 'у') frequencyLetters[19]++
                if (letter == 'ф') frequencyLetters[20]++
                if (letter == 'х') frequencyLetters[21]++
                if (letter == 'ц') frequencyLetters[22]++
                if (letter == 'ч') frequencyLetters[23]++
                if (letter == 'ш') frequencyLetters[24]++
                if (letter == 'щ') frequencyLetters[25]++
                if (letter == 'ъ') frequencyLetters[26]++
                if (letter == 'ы') frequencyLetters[27]++
                if (letter == 'ь') frequencyLetters[28]++
                if (letter == 'э') frequencyLetters[29]++
                if (letter == 'ю') frequencyLetters[30]++
                if (letter == 'я') frequencyLetters[31]++
            }
            return frequencyLetters
        }

        fun getLets(frequencyLetters: IntArray): String {
            var a: Int = frequencyLetters[0]
            var indexA = 0
            var b: Int = frequencyLetters[0]
            var indexB: Int = 0
            var result :String = ""
            for (i in 1..M-1) {
                if (a < frequencyLetters[i]) {
                    a = frequencyLetters[i]
                    indexA = i
                }
            }
            result += LETTERS[indexA]
            for (i in 1..M-1) {
                if (b < frequencyLetters[i] && frequencyLetters[i] !== a) {
                    b = frequencyLetters[i]
                    indexB = i
                }
            }
            result += LETTERS[indexB]

            return  result
        }

        fun Cryptanalysis(text: String, let: String, firstLet: String, secondLet: String) {
            var a1: Int
            var a2: Int
            var c1: Int
            var c2: Int
            var a3: Int
            var c3: Int
            var a: Int
            var b: Int
            var firstString: String = ""
            var secondString: String = ""
                    a1 = LETTERS.indexOf(let[0].toString())
                    a2 = LETTERS.indexOf(let[1].toString())
                    c1 = LETTERS.indexOf(firstLet)
                    c2 = LETTERS.indexOf(secondLet)
                    a3 = (a1 - a2) % M
                    if (a3 < 0) a3 += M
                    c3 = (c1 - c2) % M
                    if (c3 < 0) c3 += M
                    a = mmi(a3, M) * c3 % M
                    if (a < 0) a += M
                    b = (c1 - a1 * a) % M
                    if (b < 0) b += M

                    for (k in text.indices) {
                        val symb: String = text.substring(k, k+1)
                        var decr: Int
                        decr = (a * LETTERS.indexOf(symb) + b) % M
                        if (decr < 0) decr += M
                        firstString+=LETTERS[decr]
                    }
                    c1 = LETTERS.indexOf(secondLet)
                    c2 = LETTERS.indexOf(firstLet)
                    c3 = (c1 - c2) % M
                    if (c3 < 0) c3 += M
                    a = mmi(a3, M) * c3 % M
                    if (a < 0) a += M
                    b = (c1 - a1 * a) % M
                    if (b < 0) b += M

                    for (k in text.indices) {
                        val symb: String = text.substring(k, k+1)
                        var decr: Int
                        decr = (a * LETTERS.indexOf(symb) + b) % M
                        if (decr < 0) decr += M
                        secondString+=LETTERS[decr]
                    }
            decodeStrings +=DecodeStrings(listOf(firstLet,secondLet).joinToString(""),firstString,secondString)
        }

    }
}