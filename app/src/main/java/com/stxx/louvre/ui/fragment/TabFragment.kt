package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.view.View
import com.stxx.louvre.R
import org.jetbrains.anko.bundleOf

/**
 * description: tab列表fragment
 * Created by liNan on 2018/2/28 8:43

 */
class TabFragment : LazyLoadFragment() {


    //tab索引
    private var tabIndex: Int = 0

    companion object {
        private val TAB_KEY = "tab_key"
        fun newsInstance(tab: Int): TabFragment {
            val obj = TabFragment()
            obj.arguments = bundleOf(Pair(TAB_KEY, tab))
            return obj
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            tabIndex = arguments!!.getInt(TAB_KEY)
        }
    }
    override fun loadData() {
    }

    override fun initView(view: View) {
    }

    override fun getLayoutResId(): Int =R.layout.fragment_tab

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_tab, null, false)
//    }



}