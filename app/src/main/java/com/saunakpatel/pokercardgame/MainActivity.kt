package com.saunakpatel.pokercardgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var PC1: PokerCards
    private lateinit var PC2: PokerCards
    private lateinit var PC3: PokerCards
    private lateinit var PC4: PokerCards
    private lateinit var PC5: PokerCards
    private lateinit var dealBtn: Button
    private lateinit var resultText: TextView

    private val arr = ArrayList<PokerCards>()
    private val dealtCards = mutableSetOf<Int>()
    private val dealtCardDetails = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dealBtn = findViewById(R.id.dealBtn)
        resultText = findViewById(R.id.resultText)

        // Initialize PokerCards custom view
        PC1 = findViewById(R.id.PC1)
        arr.add(PC1)
        PC2 = findViewById(R.id.PC2)
        arr.add(PC2)
        PC3 = findViewById(R.id.PC3)
        arr.add(PC3)
        PC4 = findViewById(R.id.PC4)
        arr.add(PC4)
        PC5 = findViewById(R.id.PC5)
        arr.add(PC5)


        dealBtn.setOnClickListener {
            if (dealtCards.size > (52 - 6)) {
                dealtCards.clear()
                dealtCardDetails.clear()
                print("dealtCards cleared!")
            }
            dealtCardDetails.clear()
            for (item in arr) generate(item)
            resultText.text = evaluatePokerHand()
        }
    }

    private fun generate(card: PokerCards){
        var num: Int
        var newCard: Pair<PokerCards.Suit, PokerCards.Rank>
        do {
            num = (0..51).random()
            newCard = PokerCards.Suit.entries[num/13] to PokerCards.Rank.entries[num%13]
        } while (dealtCards.contains(num))

        dealtCards.add(num)

        card.drawCardSuit = newCard.first
        card.drawCardRank = newCard.second
        card.invalidate()

        val cardDetail = "${newCard.second} of ${newCard.first}"
        dealtCardDetails.add(cardDetail)
    }

    private fun evaluatePokerHand(): String {
        val rankCounts = dealtCardDetails.groupingBy { it.split(" ")[0] }.eachCount()
        val suitCounts = dealtCardDetails.groupingBy { it.split(" ")[2] }.eachCount()

        val isFlush = suitCounts.any { it.value == 5 }
        val isStraight = isStraight(rankCounts.keys)

        return when {
            isFlush && isStraight -> "Straight Flush"
            rankCounts.containsValue(4) -> "Four of a Kind"
            rankCounts.containsValue(3) && rankCounts.containsValue(2) -> "Full House"
            isFlush -> "Flush"
            isStraight -> "Straight"
            rankCounts.containsValue(3) -> "Three of a Kind"
            rankCounts.values.count { it == 2 } == 2 -> "Two Pair"
            rankCounts.containsValue(2) -> "One Pair"
            else -> "High Card"
        }
    }

    private fun isStraight(ranks: Set<String>): Boolean {
        val rankOrder = listOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
        val rankIndices = ranks.map { rankOrder.indexOf(it) }.sorted()
        return rankIndices.zipWithNext().all { it.second - it.first == 1 }
    }
}