package com.example.sunnyweather.nc

/**
 * @author LingZhi
 * @time 2022/3/8
 */
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_nc_bottomsheet_list_rich_style.view.*

object JobBottomSheet {

    fun showListBottomSheet(
        ac: Activity,
        data: ArrayList<NCCommonChooseListItem>,
        selected: NCCommonChooseListItem? = null,
        cancelCallback: ((Boolean) -> Unit)? = null,
        callback: (JobChooseItemNew?) -> Unit
    ) {
        val view =
            LayoutInflater.from(ac).inflate(R.layout.dialog_nc_bottomsheet_list_rich_style, null)
        val bottomSheetDialog = BottomSheetDialog(ac, R.style.bottomSheetStyleWrapper)
        bottomSheetDialog.setContentView(view)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as ViewGroup)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        val recycleView = view.findViewById<RecyclerView>(R.id.job_bottom_recycleView)
        recycleView.layoutManager = LinearLayoutManager(ac)

        recycleView.adapter = ListBottomSheetAdapter(ac) {
            bottomSheetDialog.dismiss()
        }.apply {
            setData(data, selected,callback)
        }
        //todo fix-me?
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}
        })
        view.job_choose_bottom_item_close.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.setOnCancelListener {
            callback.invoke(null)
        }
        bottomSheetDialog.show()
    }
    private class ListBottomSheetAdapter(
        val context: Context,
        val dismissCallback: () -> Unit
    ):RecyclerView.Adapter<ListBottomSheetAdapter.ListBottomSheetViewHolder>() {
        inner class ListBottomSheetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mTypeImg = view.findViewById<ImageView>(R.id.job_choose_bottom_item_image)
            val mChooseTitle = view.findViewById<TextView>(R.id.job_choose_bottom_item_title)
            val mChooseContent = view.findViewById<TextView>(R.id.job_choose_bottom_item_content)
            val mBgImageView = view.findViewById<ImageView>(R.id.job_choose_bottom_item_bg)

        }

        private val mDataList = ArrayList<NCCommonChooseListItem>(10)

        private var mSelected: NCCommonChooseListItem? = null
        private var callback: ((JobChooseItemNew?) -> Unit)? = null
        fun setData(
            data: ArrayList<NCCommonChooseListItem>,
            selected: NCCommonChooseListItem? = null,
            callback: (JobChooseItemNew?) -> Unit
        ) {
            mDataList.clear()
            mDataList.addAll(data)
            mSelected = selected
            this.callback = callback
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ListBottomSheetViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_job_type_choose_bottom, parent, false)
            return ListBottomSheetViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListBottomSheetViewHolder, position: Int) {
            val itemData = mDataList[position].value as JobChooseItemNew
            holder.mTypeImg.setImageDrawable(itemData.drawable)
            holder.mChooseTitle.text = itemData.title
            holder.mChooseContent.text = itemData.content
            holder.mBgImageView.visibility = View.GONE
            if (itemData.type == mSelected?.value) {
                holder.mBgImageView.visibility = View.VISIBLE
                holder.mChooseTitle.setTextColor(ValuesUtils.getColor(R.color.ncbottomsheet_item_selected_text, context))
                holder.mChooseContent.setTextColor(ValuesUtils.getColor(R.color.ncbottomsheet_item_selected_text, context))
                holder.itemView.background =
                    ValuesUtils.getDrawableById(R.drawable.bg_common_radius10_green_border)
            }

            holder.itemView.setOnClickListener {
                dismissCallback.invoke()
                callback?.invoke(itemData)
            }

        }

        override fun getItemCount(): Int {
            return mDataList.size
        }
    }

    data class JobChooseItemNew(
        val drawable: Drawable?,
        val title: String,
        val content: String,
        val name: String,
        val type: Int
    )
}


