package com.saunakpatel.pokercardgame

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var PC1: PokerCards
    private lateinit var PC2: PokerCards
    private lateinit var PC3: PokerCards
    private lateinit var PC4: PokerCards
    private lateinit var PC5: PokerCards
    private lateinit var dealBtn: Button

    private val arr = ArrayList<PokerCards>()
    private val dealtCards = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dealBtn = findViewById(R.id.dealBtn)

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
                print("dealtCards cleared!")
            }
            for (item in arr) generate(item)
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
    }
}