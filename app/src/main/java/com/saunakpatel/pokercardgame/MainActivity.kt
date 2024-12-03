package com.saunakpatel.pokercardgame

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    private lateinit var PC1: PokerCards
    private lateinit var PC2: PokerCards
    private lateinit var PC3: PokerCards
    private lateinit var PC4: PokerCards
    private lateinit var PC5: PokerCards
    private lateinit var dealBtn: Button

    private val arr = ArrayList<PokerCards>()
    private val dealtCards = mutableSetOf<Pair<PokerCards.Suit, PokerCards.Rank>>()

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
        var suitNum: Int
        var rankNum: Int
        var newCard: Pair<PokerCards.Suit, PokerCards.Rank>

        do {
            suitNum = (0..3).random()
            rankNum = (0..12).random()
            newCard = PokerCards.Suit.entries[suitNum] to PokerCards.Rank.entries[rankNum]
        } while (dealtCards.contains(newCard))

        dealtCards.add(newCard)

        card.drawCardSuit = newCard.first
        card.drawCardRank = newCard.second
        card.invalidate()
    }
}