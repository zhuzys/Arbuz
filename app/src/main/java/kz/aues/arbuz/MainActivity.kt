package kz.aues.arbuz

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.FrameMetricsAggregator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    private lateinit  var wmList: ArrayList<WaterMelon>
    var status = WaterMelonState.RIPE
    var myCalendar = Calendar.getInstance()
    var quantity = 0
    var cuteChecked :Boolean =false
    var weight =0.0
    var count = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wmList = arrayListOf()
        setContentView(R.layout.activity_main)
        rb_cut.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            cuteChecked = b
        }

        etPlace.setOnClickListener {
            if(quantity%2 ==0) {
                rv_wm_place.visibility = View.VISIBLE
            }else {
                rv_wm_place.visibility = View.GONE
            }
            quantity++
        }
        var date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                setDate()
            }
        etDate.setOnClickListener {
            DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        var n = 1
        while (n <= 50) {
            when(n) {
                in 1..10 -> {
                    wmList.add(WaterMelon(n, 7.5 +  n*0.1, WaterMelonState.RIPE))
                }
                in 10..20 -> {
                    wmList.add(WaterMelon(n, 5.5 +n*0.01, WaterMelonState.UNRIPE))
                }
                in 20..30 -> {
                    wmList.add(WaterMelon(n, 4.0 +n * 0.01, WaterMelonState.RIPE))
                }
                in 30..40 -> {
                    wmList.add(WaterMelon(n, 7.0 +n * 0.01, WaterMelonState.UNRIPE))
                }
                in 40..45 -> {
                    wmList.add(WaterMelon(n, 6.5 + n *0.01, WaterMelonState.RIPE))
                }
                in 45..49 -> {
                    wmList.add(WaterMelon(n, 0.0, WaterMelonState.THWARTED))
                }
                else -> {
                wmList.add(WaterMelon(n, 5.5, WaterMelonState.RIPE))
                }
            }
            n++
            Log.i("dss0", "$wmList")
            Log.i("fdsfs", "${n%0.1}")
        }

        rv_wm_place.layoutManager =
            GridLayoutManager(this, 5, RecyclerView.VERTICAL, false)
        rv_wm_place.setHasFixedSize(true)
        val adapter = PlaceAdapter(this, wmList)
        rv_wm_place.adapter = adapter



        adapter.setOnClickListener(object : PlaceAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                tv_weight.text = ""
                tvState.text = ""
                rv_wm_place.visibility = View.GONE
                wmList[position].isChoice = true
                etPlace.setText(position.toString())
                tv_weight.visibility = View.VISIBLE
                tvState.visibility = View.VISIBLE
                weight = wmList[position].weight.toDouble()
                count += weight
                Log.i("all count:","$weight  and $count")
                tv_weight.text = "Вес: " +weight
                tvState.text ="Состояние:  "+ wmList[position].status.toString().toLowerCase()
                Log.i("posit","$position")
                adapter.notifyDataSetChanged()
            }
        })
        var plus = 1
        iv_units_plus.setOnClickListener {
            if(plus <= 2) {
                plus++
                et_units.setText(plus.toString())
                rv_wm_place.visibility = View.VISIBLE
            }else {
                et_units.setText(plus.toString())
            }

        }
        iv_units_minus.setOnClickListener {
            if(plus >0) {
                plus--
                et_units.setText(plus.toString())
            }else {
                et_units.setText(plus.toString())
            }
        }
        btn_save.setOnClickListener {
            if(et_units.text.isNullOrEmpty() || etAddress.text.isNullOrEmpty() || etNumber.text.isNullOrEmpty() || etPlace.text.isNullOrEmpty() || etDate.text.isNullOrEmpty()) {
                Toast.makeText(this,"Заполните все поля!",Toast.LENGTH_SHORT).show()

            }else {

                etPrice.visibility = View.VISIBLE
                btn_save.visibility = View.GONE
                etPrice.setText(count.roundToLong().toString())
            }
            /*if(cuteChecked == true) {
                val amount = et_units.text.toString().toDouble() * 180
                Log.i("posi1t","$amount")
                var summa = 100 + et_units.text.toString().toInt()* tv_weight.text.toString().toFloat() * 180
                etPrice.setText(summa.toString())
                Log.i("posit2","$summa")
            }else {
                var summa = et_units.text.toString().toDouble() * 180
                etPrice.setText(summa.toString())
            }*/

        }

    }


    private fun setDate() {
        val myFormat = "dd MMMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        etDate.setText(sdf.format(myCalendar.getTime()))
    }
}