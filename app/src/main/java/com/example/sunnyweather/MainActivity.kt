package com.example.sunnyweather

/**
 * @author LingZhi
 * @time 2022/3/3
 */
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Person
import com.example.sunnyweather.ui.weather.WeatherViewModel


class MainActivity : BaseActivity() {

    private val mViewModel: WeatherViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getAc().application)
        ViewModelProvider(getAc(), factory).get(WeatherViewModel::class.java)
    }

    /**
     * 清洗字符串
     */

    //纯数字
    private val DIGIT_REGEX by lazy { "[0-9]+" }

    //含有数字
    private val CONTAIN_DIGIT_REGEX = ".*[0-9].*"

    //纯中文
    private val CHINESE_REGEX by lazy { "[\u4e00-\u9fa5]" }


    private val TAG = "MainActivity"

    private val callUri = CallLog.Calls.CONTENT_URI

    //联系人
    val personMap = hashMapOf<String, Person>()

    var people = arrayListOf<Person>()

    var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), 1)
        } else {
            readContacts()
            if (people.isNotEmpty()) {
                people.sort()
                val intimatePerson = people.get(people.size - 1)
                number = intimatePerson.telephone
                mViewModel.searchNumberAddress(number)
                mViewModel.numberAddressLiveData.observe(this, Observer { result ->
                    val numberAddress = result.getOrNull()
                    if (numberAddress != null) {
                        mViewModel.numberAddress = numberAddress
                        mViewModel.searchNumberPlace(mViewModel.numberAddress)
                    }
                })
                mViewModel.numberPlaceLiveData.observe(this, Observer {
                    val placeList = it.getOrNull()
                    if (placeList != null) {
                        mViewModel.numberPlace = placeList.get(0)
                        mViewModel.numberPlace.name = mViewModel.numberAddress
                    }
                })
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts()
            } else {
                Toast.makeText(this, "没有通讯录权限", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun readContacts() {
        try {
            val cursor = contentResolver.query(
                callUri,
                null,
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER
            )
            Log.d(TAG, "cursor count:" + cursor?.count)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var name =
                        cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME))
                    val number =
                        cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                    val duration =
                        cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION))

                    if (name == null || Regex(DIGIT_REGEX).matches(name)) {
                        continue
                    }
                    if (!personMap.containsKey(name)) {
                        personMap.put(name, Person(name, number, 1, duration))
                    } else {
                        val count = personMap.get(name)!!.telephoneCount + 1
                        val sumDuration = personMap.get(name)!!.duration + duration
                        personMap.put(name, Person(name, number, count, sumDuration))
                    }
                }
                for (entry in personMap) {
                    people.add(entry.value)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
