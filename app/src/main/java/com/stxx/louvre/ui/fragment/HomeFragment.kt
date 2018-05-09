package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.stxx.louvre.R
import com.stxx.louvre.adapter.HomeFragmentAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.ui.contract.HomeContact
import com.stxx.louvre.utils.UploadImgUtil
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * description: 首页
 * Created by liNan on 2018/2/27 15:20

 */
class HomeFragment : BaseFragment(), ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, HomeContact.View {
    private lateinit var mAdapter: HomeFragmentAdapter //tab fragment adapter

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rbLu -> vpTab.setCurrentItem(0, true)
            R.id.rbArticle -> vpTab.setCurrentItem(1, true)
            R.id.rbProduct -> vpTab.setCurrentItem(2, true)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, null, false)
    }


    override fun initView() {
        val fragmentList = arrayListOf<Fragment>(HomeTabLfgFragment(), HomeTabArticleFragment(), HomeTabProtfolioFragment())
        mAdapter = HomeFragmentAdapter(childFragmentManager, fragmentList)
        vpTab.adapter = mAdapter
        vpTab.currentItem = 0
        vpTab.addOnPageChangeListener(this)
        rb.setOnCheckedChangeListener(this)
        rb.check(R.id.rbLu)
        camera.setOnClickListener {
            UploadImgUtil(this.activity!!)
                    .choosePicture()
        }
        goSearch(tvSearch)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val btn: RadioButton = rb.getChildAt(position) as RadioButton
        btn.isChecked = true
    }
}