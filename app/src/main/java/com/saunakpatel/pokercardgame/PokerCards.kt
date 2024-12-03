package com.saunakpatel.pokercardgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View


class PokerCards(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    enum class Suit { Diamond, Heart, Spade, Club }
    enum class Rank { King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two, Ace }
    private lateinit var pokercardBack: Bitmap
    private var pokercards: Bitmap
    private var pokercardsImgSizeW: Int = 0
    private var pokercardsImgSizeH: Int = 0

    private val colorBkgrd: Int = Color.rgb(4, 110, 62)

    // constructor
    private var paintBkgrd: Paint = Paint()
    var drawCardSuit:   Suit  = Suit.Club
    var drawCardRank:   Rank  = Rank.Queen
    var drawCardAtX:    Int   = 0
    var drawCardAtY:    Int   = 0
    var drawCardScale:  Float = 1.0f
    var cardsToDraw: List<Pair<Suit, Rank>> = emptyList()
    var showCards: Boolean = true
    init {
        paintBkgrd.color = colorBkgrd
        pokercards = BitmapFactory.decodeResource(resources, R.drawable.pokercards)
        pokercardBack = BitmapFactory.decodeResource(resources, R.drawable.card_back)
        pokercardsImgSizeW = pokercards.width
        pokercardsImgSizeH = pokercards.height
    }
    val canvasSizeW = 2000f
    val canvasSizeH = 2800f


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, canvasSizeW, canvasSizeH, paintBkgrd) // paint the green background

        // Card dimensions in the grid
        val cardWidth = pokercardsImgSizeW / 13
        val cardHeight = pokercardsImgSizeH / 4

        // Map Suit and Rank to grid positions
        val row = drawCardSuit.ordinal
        val column = drawCardRank.ordinal

        // Calculate source rectangle
        val srcXSm = column * cardWidth
        val srcYSm = row * cardHeight
        val srcXBg = srcXSm + cardWidth
        val srcYBg = srcYSm + cardHeight
        val srcRect = Rect(srcXSm, srcYSm, srcXBg, srcYBg)

        // Calculate destination rectangle
        val dstXSm = drawCardAtX
        val dstYSm = drawCardAtY
        val dstXBg = dstXSm + (cardWidth * drawCardScale).toInt()
        val dstYBg = dstYSm + (cardHeight * drawCardScale).toInt()
        val dstRect = Rect(dstXSm, dstYSm, dstXBg, dstYBg)

        // Draw the selected card
        canvas.drawBitmap(pokercards, srcRect, dstRect, null)
    }
}