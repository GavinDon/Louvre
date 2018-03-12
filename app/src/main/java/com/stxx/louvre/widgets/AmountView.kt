package com.stxx.louvre.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.stxx.louvre.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


/**
 * description: 自定义一个购物车数量加减
 * Created by liNan on 2018/3/6 10:30

 */
class AmountView : LinearLayout, View.OnClickListener, TextWatcher {

    private val TAG = "AmountView"
    private var amount = 1 //购买数量
    private var goodsStorage = 1 //商品库存

    private var mListener: OnAmountChangeListener? = null

    private val etAmount by lazy { find<EditText>(R.id.etAmount2) }
    private val btnDecrease by lazy { find<Button>(R.id.btnDecrease) }
    private val btnIncrease by lazy { find<Button>(R.id.btnIncrease) }

    interface OnAmountChangeListener {
        fun onAmountChange(view: View, oldAmount:Int,amount: Int)
    }


    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_amount, this)
        btnDecrease.setOnClickListener(this)
        btnIncrease.setOnClickListener(this)
//        etAmount.addTextChangedListener(this)

        val obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView)
        val btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
        val tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 45)
        val tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0)
        val btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0)
        obtainStyledAttributes.recycle()

        val btnParams = LinearLayout.LayoutParams(btnWidth, LinearLayout.LayoutParams.MATCH_PARENT)
        btnDecrease.layoutParams = btnParams
        btnIncrease.layoutParams = btnParams
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize.toFloat())
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize.toFloat())
        }

        val textParams = LinearLayout.LayoutParams(tvWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
        textParams.gravity=Gravity.CENTER
        etAmount.layoutParams = textParams
        if (tvTextSize != 0) {
            etAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvTextSize.toFloat())
        }

    }

    fun setOnAmountChangeListener(onAmountChangeListener: OnAmountChangeListener) {
        this.mListener = onAmountChangeListener
    }

    fun setGoodsStorage(goods_storage: Int): AmountView {
        this.goodsStorage = goods_storage
        return this
    }

    override fun onClick(v: View?) {
        val i = v!!.id
        if (i == R.id.btnDecrease) {
            if (amount > 1) {
                amount--
                etAmount.setText(amount.toString())
                if (mListener != null) {
                    mListener!!.onAmountChange(this,amount-1, amount)
                }
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < goodsStorage) {
                amount++
                etAmount.setText(amount.toString())
            } else if (amount >= goodsStorage) {
                context.toast("已经达到库存上限,不能再添加了")
            }
            if (mListener != null) {
                mListener!!.onAmountChange(this, amount+1,amount)
            }

        }

        etAmount.clearFocus()
//        if (mListener != null) {
//            mListener!!.onAmountChange(this, amount)
//        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isEmpty()) {
            return
        }
        amount = Integer.valueOf(s.toString())
        if (amount > goodsStorage) {
            etAmount.setText(amount)
            return
        }
        if (mListener != null) {
//            mListener!!.onAmountChange(this, amount)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}