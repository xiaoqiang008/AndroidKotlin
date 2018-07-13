package com.xiaoqiang.kotlin.base

import android.util.Log

/**
 * description: Kotlin_属性
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 */
class KotlinVa(stu : Int) {

    val TAG : String = "KotlinVa"

    fun log(log : String){
        Log.i(TAG,log)
    }
    /*
        var：可变变量
        val：不可变变量
         */
    val a : Int = 1 //立即赋值
    val a1 = 2 //Kotlin会自动识别为“Int”类型
    var a2 : Int //如何没有初始值，类型不能省略并且必须在init里复制
    var adress = Adress()
    var a3 : Int = 3
            get() = field*3
            set(value){
                field = value+1
            }
    var a4 : Int = 3
        get() = field*3
        set(value){
            field = value+1
        }

    init {
        a2 = stu //此处必须赋值
    }

    class Adress(){
        val a = 1
        val b = "Adress"
    }

    fun test(){
        //要使用一个属性，只要用名称引用它即可
        val a = adress.b
        log(a)
        log(a3.toString())
        a3 = 6
        log(a3.toString())
    }




}