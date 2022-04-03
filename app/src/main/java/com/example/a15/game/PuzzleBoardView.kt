package com.example.a15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("ViewConstructor")
class PuzzleBoardView(context: Context, val n: Int) : View(context) {

    private val paint = Paint()
    private var containerWidth: Int = 0
    private var size = 0
    private val mat = Array(n) { Array(n) { PuzzleBlock(context, 0, 0F, 0F, 0F) } }
    private var emptyBlockIndex = Point(n - 1, n - 1)

    init {
        paint.isAntiAlias = true
    }

    fun initGame() {
        emptyBlockIndex = Point(n - 1, n - 1)
        size = containerWidth / n
        var x = 0
        var y = 0
        var ID = 1
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j] = PuzzleBlock(context, ID, x.toFloat(), y.toFloat(), size.toFloat())
                ID++
                ID %= n * n
                x += size
            }
            x = 0
            y += size
        }
            shuffleMat()
    }

    private fun shuffleMat() {

            val iteration = 100
            for (i in 0 until iteration) {
                val options = mutableListOf<Point>()
                if (emptyBlockIndex.x + 1 < n) {
                    options.add(Point(emptyBlockIndex.x + 1, emptyBlockIndex.y))
                }
                if (emptyBlockIndex.x - 1 >= 0) {
                    options.add(Point(emptyBlockIndex.x - 1, emptyBlockIndex.y))
                }
                if (emptyBlockIndex.y + 1 < n) {
                    options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y + 1))
                }
                if (emptyBlockIndex.y - 1 >= 0) {
                    options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y - 1))
                }
                options.shuffle()
                val selectedIndex = options[0]
                swapBlock(selectedIndex.x, selectedIndex.y)
            }

    }

    private fun isSolution(): Boolean {
            var count = 1
            for (i in 0 until mat.size) {
                for (j in 0 until mat[0].size) {
                    if (mat[i][j].ID != count && count != n * n) {
                        return false
                    }
                    count++
                }
            }
            return true
    }

    private fun swapBlock(i: Int, j: Int) {
        val ID = mat[i][j].ID
        mat[i][j].ID = 0
        mat[emptyBlockIndex.x][emptyBlockIndex.y].ID = ID
        emptyBlockIndex = Point(i, j)
    }

    private fun makeMove(i: Int, j: Int) {
            swapBlock(i, j)
            invalidate()

        if (isSolution()) {
            val alertDialogSound : MediaPlayer = MediaPlayer.create(context, R.raw.custom_alert_dialog)
            val alertDialog = AlertDialog.Builder(context).create()
            soundPlayAlertDialog(alertDialogSound)
            alertDialog.setIcon(R.mipmap.ic_launcher_round)
            alertDialog.window?.setBackgroundDrawableResource(R.drawable.gradient_background_start)
            alertDialog.setTitle(context.getString(R.string.you_win))
            alertDialog.setCancelable(false)
            alertDialog.setMessage(context.getString(R.string.again))
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.yes)) { dialog, _ ->
                // refresh the game
                initGame()
                invalidate()
                dialog.dismiss()
            }
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.no)) { dialog, _ ->
                // return to main menu
                (context as MainActivity).fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                dialog.dismiss()
            }
            alertDialog.show()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until mat.size) {
            for (j in 0 until mat[0].size) {
                mat[i][j].onDraw(canvas!!, paint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        containerWidth = measuredWidth
        if (containerWidth == 0) {
            return
        }
        initGame()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (size == 0) {
                    return false
                }
                    val i = (event.y / size).toInt()
                    val j = (event.x / size).toInt()
                    val clickSound : MediaPlayer = MediaPlayer.create(context, R.raw.click)

                    if (i + 1 < n && i + 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                        soundClick(clickSound)
                        makeMove(i, j)

                    } else if (i - 1 >= 0 && i - 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                        soundClick(clickSound)
                        makeMove(i, j)

                    } else if (j + 1 < n && i == emptyBlockIndex.x && j + 1 == emptyBlockIndex.y) {
                        soundClick(clickSound)
                        makeMove(i, j)

                    } else if (j - 1 >= 0 && i == emptyBlockIndex.x && j - 1 == emptyBlockIndex.y) {
                        soundClick(clickSound)
                        makeMove(i, j)
                    }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun soundPlayAlertDialog (sound : MediaPlayer) {
       CoroutineScope(Dispatchers.IO).launch {  sound.start() }
    }


    fun soundClick (sound : MediaPlayer) {
        CoroutineScope(Dispatchers.IO).launch {  sound.start() }
    }

}
