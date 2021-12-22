package drama.github.user.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 리사이클러뷰 아이템 사이의 여백을 추가하는 Decoration
 */
class SpacingDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val layoutManager = parent.layoutManager
        var isGrid = false
        var orientation = RecyclerView.VERTICAL
        var spanCount = -1
        var isReverse = false

        if (layoutManager != null) {
            if (layoutManager is StaggeredGridLayoutManager) {
                isGrid = true
                spanCount = layoutManager.spanCount
                orientation = layoutManager.orientation
                isReverse = layoutManager.reverseLayout
            } else if (layoutManager is GridLayoutManager) {
                isGrid = true
                spanCount = layoutManager.spanCount
                orientation = layoutManager.orientation
                isReverse = layoutManager.reverseLayout
            } else if (layoutManager is LinearLayoutManager) {
                orientation = layoutManager.orientation
                isReverse = layoutManager.reverseLayout
            }
        }

        if (parent.adapter != null && parent.adapter!!.itemCount > 1) {
            if (isGrid) {
                if (spanCount >= parent.adapter!!.itemCount || spanCount < 0) {
                    //아이템 한줄
                    if (orientation == RecyclerView.HORIZONTAL) {
                        if (position < parent.adapter!!.itemCount - 1) {
                            outRect.set(0, 0, 0, padding)
                        }
                    } else if (orientation == RecyclerView.VERTICAL) {
                        if (position < parent.adapter!!.itemCount - 1) {
                            outRect.set(0, 0, padding, 0)
                        }
                    }
                } else {
                    if (orientation == RecyclerView.HORIZONTAL) {
                        val row = position / spanCount
                        val maxRow = parent.adapter!!.itemCount / spanCount
                        val rowPosition = position % spanCount

                        if (row == maxRow - 1) {
                            //마지막 줄
                            if (rowPosition < spanCount - 1) {
                                outRect.set(0, 0, 0, padding)
                            }
                        } else {
                            if (rowPosition < spanCount - 1) {
                                outRect.set(0, 0, padding, padding)
                            } else if (rowPosition == spanCount - 1) {
                                outRect.set(0, 0, padding, 0)
                            }
                        }
                    } else if (orientation == RecyclerView.VERTICAL) {
                        val row = position / spanCount
                        val rowPosition = position % spanCount

                        if (row == 0) {
                            //첫번째 줄
                            if (rowPosition == 0) {
                                outRect.set(0, 0, 0, 0)
                            } else if (rowPosition < spanCount - 1) {
                                outRect.set(padding, 0, padding, 0)
                            } else if (rowPosition == spanCount - 1) {
                                outRect.set(0, 0, 0, 0)
                            }
                        } else {
                            if (rowPosition == 0) {
                                outRect.set(0, padding, 0, 0)
                            } else if (rowPosition < spanCount - 1) {
                                outRect.set(padding, padding, padding, 0)
                            } else if (rowPosition == spanCount - 1) {
                                outRect.set(0, padding, 0, 0)
                            }
                        }
                    }
                }
            } else {
                if (orientation == RecyclerView.HORIZONTAL) {
                    if (isReverse) {
                        if (position > 0) {
                            outRect.set(0, 0, padding, 0)
                        }
                    } else {
                        if (position < parent.adapter!!.itemCount - 1) {
                            outRect.set(0, 0, padding, 0)
                        }
                    }
                } else if (orientation == RecyclerView.VERTICAL) {
                    if (isReverse) {
                        if (position > 0) {
                            outRect.set(0, 0, 0, padding)
                        }
                    } else {
                        if (position < parent.adapter!!.itemCount - 1) {
                            outRect.set(0, 0, 0, padding)
                        }
                    }
                }
            }
        }
    }
}