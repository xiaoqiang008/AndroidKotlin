package com.xiaoqiang.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.xiaoqiang.kotlin.base.*
import com.xiaoqiang.kotlin.test.AndroidAdjustResizeBugFix
import kotlinx.android.synthetic.main.activity_main2.*

/**
 * description: 主页面
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 */
class Main2Activity : AppCompatActivity(){

    val TAG : String = "test"

    var ba : Int = 1
    var a1 = ClassGoKotlin("aaaaa")
    //创建类
//    var classGoKotlins = ClassGoKotlin("1", "2","3","4",ClassGoKotlin("1","2",ClassGoKotlin("0")))
    var mClassGoJava = ClassGoJava()
    //传入自定义数据
    var a = ClassGoKotlinEx("测试", ClassGoKotlinEx())
    //不传入数据，使用ClassGoKotlinEx类里设好的固定数据
    var b = ClassGoKotlinEx()
    var c = FunGoKotlin()
    var d : List<Int> = mutableListOf(17,12,5,67,3,10)
    var e = BaseTypeGoKotlin()
    var f = ControlGoKotlin()
    var g = InterfaceGoKotlin()
    var h = CoroutinesGoKotlin()
    var m = KotlinVa(2);
    var empt = EmptySafeKotlin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        AndroidAdjustResizeBugFix.assistActivity(this);
        1.shls(1);
//        ClassGoKotlin.Test()
        ClassGoJava.Tests()
//        Log.i(TAG, b.Test())
//        Log.i(TAG,funGouKotlin.test5(4).toString())
//        Log.i(TAG,FunGoKotlin.kt().test6())
//        Log.i(TAG,FunGoKotlin.kt().test9(4,7).toString())
//        Log.i(TAG, c.test10(3))
//        Log.i(TAG, c.test12())

//        Log.i(TAG, e.test10())

//        Log.i(TAG, f.test3(FunGoKotlin.kt()))
//        Log.i(TAG, f.test7())
//        Log.i(TAG, g.test4())
//        Log.i(TAG,)
//        h.test28()
        empt.test()
//        m.test()
        m.log("结束")
//        Log.i(TAG, g.test4())

        //将原java监听事件转化成Kotlin，如下
//        lambda_button.setOnClickListener (object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                toast("Lambda表达式");
//                toast("Lambda表达式....")
//            }
//        })
        //使用Lambda表达式，可以进行优化，如下
//        lambda_button.setOnClickListener({lambda_button -> toast("Lambda表达式");toast("Lambda表达式....")})
        //上面已经很简洁的，假设你不使用到参数，你甚至还可省去参数，如下
//        lambda_button.setOnClickListener({toast("Lambda表达式");toast("Lambda表达式....")})
        //当然你也可以把大括号放到圆括号外面
//        lambda_button.setOnClickListener(){toast("Lambda表达式");toast("Lambda表达式....")}
        //甚至你还可以省去圆括号，到这一步是不是感觉比java代码少很多很多，而且也很好理解
//        lambda_button.setOnClickListener{toast("Lambda表达式");toast("Lambda表达式....")}
        var funGoJava = FunGoJava()
    }

    fun toast(stu : String){
        Toast.makeText(this,stu,Toast.LENGTH_SHORT).show()
    }

    //Int的扩展函数  CX893A
    infix fun Int.shls(x:Int):Int{
        return x+1
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        m.log("按键：$keyCode")
        return super.onKeyDown(keyCode, event)
    }
}
