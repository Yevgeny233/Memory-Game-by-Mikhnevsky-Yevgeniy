package com.example.memorygamebymikhnevskyyevgeniy.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.memorygamebymikhnevskyyevgeniy.R

enum class Status {
    CELL_CLOSE, CELL_OPEN, CELL_DELETE
}

class GridAdapter(
    private val context: Context,
    private val cols: Int,
    private val rows: Int,

    ) : BaseAdapter() {
    private var arrStatus: ArrayList<Status> = ArrayList()
    private var arrPicture: ArrayList<String> = ArrayList()

    private val pictureCollection: String = "image"
    private val res: Resources = context.resources

    init {
        makePictArray()
        closeAllCells()
    }

    private fun makePictArray() {
        arrPicture.clear()

        for (i in 1..(count / 2)) {
            arrPicture.add(pictureCollection + i.toString())
            arrPicture.add(pictureCollection + i.toString())
        }
        arrPicture.shuffle()
    }

    private fun closeAllCells() {
        arrStatus.clear()
        for (i in 1..count) {
            arrStatus.add(Status.CELL_CLOSE)
        }
    }


    override fun getCount(): Int {
        return cols * rows
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val image: ImageView = if (convertView == null) {
            ImageView(context).apply {
                layoutParams = AbsListView.LayoutParams(185, 285)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setPadding(8, 8, 8, 8)
            }
        } else {
            convertView as ImageView
        }

        when (arrStatus[position]) {
            Status.CELL_OPEN -> {
                val drawableId =
                    res.getIdentifier(arrPicture[position], "drawable", context.packageName)
                image.setImageResource(drawableId)
            }
            Status.CELL_CLOSE -> {
                image.setImageResource(R.drawable.icon)
            }
            Status.CELL_DELETE -> image.setImageResource(android.R.drawable.btn_star_big_on)
        }

        return image
    }

    fun checkOpenCells() {
        val first = arrStatus.indexOf(Status.CELL_OPEN)
        val second = arrStatus.lastIndexOf(Status.CELL_OPEN)
        if (first == second) return
        if (arrPicture[first] == arrPicture[second]) {
            arrStatus[first] = Status.CELL_DELETE
            arrStatus[second] = Status.CELL_DELETE
        } else {
            arrStatus[first] = Status.CELL_CLOSE
            arrStatus[second] = Status.CELL_CLOSE
        }
        return
    }

    fun openCell(position: Int) {

        if (arrStatus[position] != Status.CELL_DELETE) arrStatus[position] = Status.CELL_OPEN

        notifyDataSetChanged()
        return
    }

    fun checkGameOver(): Boolean {
        if (arrStatus.indexOf(Status.CELL_CLOSE) < 0) return true
        return false
    }

}