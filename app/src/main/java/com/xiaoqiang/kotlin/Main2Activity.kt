package com.xiaoqiang.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xiaoqiang.kotlin.base.ClassGoJava
import com.xiaoqiang.kotlin.base.ClassGoKotlin
import com.xiaoqiang.kotlin.base.ClassGoKotlinEx
/**
 * description: 主页面
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 */
class Main2Activity : AppCompatActivity() {

    var ba = 1
    var a1 = ClassGoKotlin("aaaaa")
    //创建类
//    var classGoKotlins = ClassGoKotlin("1", "2","3","4",ClassGoKotlin("1","2",ClassGoKotlin("0")))
    var mClassGoJava = ClassGoJava()
    //传入自定义数据
    var a = ClassGoKotlinEx("测试", ClassGoKotlinEx())
    //不传入数据，使用ClassGoKotlinEx类里设好的固定数据
    var b = ClassGoKotlinEx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
//        ClassGoKotlin.Test()
        ClassGoJava.Tests()
        Log.i("test", b.Test())
    }
}
