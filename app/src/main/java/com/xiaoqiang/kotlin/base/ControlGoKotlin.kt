package com.xiaoqiang.kotlin.base

import android.util.Log

/**
 * description: Kotlin_控制流及返回跳转
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * http://www.jianshu.com/p/54f57af44de3
 */
class ControlGoKotlin {

    //if表达式
    //if是一个表达式，它会返回一个值
    fun test1() : String{
        val a = 2
        val b = 1
        //传统用法
        var max = a
        if(a < b) max = b
        //else 用法
        if(a > b){
            max = a
        }else{
            max = b
        }
        //表达式
        max = if(a > b) a else b

        //if的分支可以是代码块，最后的表达式作为该块的值
        return if(a > b){
            a//这个是返回值
        }else {
            b //这个不是最后一个不作为返回值
            false//这个是返回值
        }.toString()//这个是将返回值转化成字符串
    }

    fun tests() : Int{
        return 10
    }

    //When表达式
    //when取代switch，Kotlin里是没有switch操作符的，用when替代
    fun test2(i : Int) : String{
        when(i){
            1 -> return "1"
            //还可以检查一系列值
            2,3 -> return "2,3"
            else -> {
                val a = "else"
                return a
            }
        }

        //你可以返回另外一个函数值，也可以用任意表达式作为分支条件
        when(i){
            tests() -> return "10"
            3 -> return test1()
            else -> {
                val a = "else"
                return a
            }
        }
        //in
        //(in,!in)检查一个值是否在某个区间或者集合里
        val nums = listOf(11,12,13,14)
        when(i){
            in 1..10 -> return "在1..10"
            in nums -> return "nums"
            !in 15..20 -> return "不在15..20"
            else -> {
                val a = "else"
                return a
            }
        }
    }
    //is
    //(is,!is)检查一个值是否特定的类型
    fun <T> test3(x : T) : String{
        when(x){
            //能检测基本类型
            is String -> return "String"
            is Int -> return "Int"
            //还能检测自定义类类型
            is FunGoKotlin -> return "FunGoKotlin"
            //还可以嵌套使用
            is Float -> {
                when(x) {
                    in 1..10 -> return "Float:" + x
                }
            }
        }
        return "其他"
    }

    //For循环
    //for 循环可以对任何提供迭代器（iterator）的对象进行遍历
    fun test4() : String{
        val nums = listOf(1,2,3,4,9,"结束")
        var temp : String = ""
        for ( i in nums){//i的类型与nums里对应位置类型一样
            temp += i
        }
        temp = temp + ","
        //只是遍历集合中某部分
        for ( i in 1..nums.size-1){//这里i就变了哦，不是nums的值，而是对应的位置
            temp += nums[i]
        }
        return temp
    }

    //While循环
    //while 和 do..while 照常使用
    fun test5(a : Int):String{
        var x = a
        while (x > 0){
            x --
        }
        return x.toString()

        do {
         x--
        }while (x != -10)
        return x.toString()
    }

    //返回跳转
//    Kotlin 有三种结构化跳转表达式：
//    return。默认从最直接包围它的函数或者匿名函数返回。
//    break。终止最直接包围它的循环。
//    continue。继续下一次最直接包围它的循环。


    // Kotlin 中任何表达式都可以用标签（label）来标记。
    // 标签的格式为标识符后跟 @ 符号
    fun test6(a: Int) : String{
        abc@ for (i in 1..5){
            abcd@ for (j in 1..5){
                if(i==a && j == a){
//                    break@abcd //结束abcd本次循环，继续执行循环外的abc循环
//                    break@abc //结束abc本次循环，整个循环就结束了
//                    continue@abcd //继续下一次abcd循环
                    continue@abc //继续下一次abc循环
                }
                Log.i(TAG, i.toString()+">>>"+j.toString())
            }
        }
        return ""
    }

    fun <T> Collection<T>.meetCons(less: (T) -> Boolean): List<T?> {
        val tmp: MutableList<T?> = mutableListOf()
        for (it in this)
            if (less(it))
                tmp.add(it)
        return tmp
    }
     fun test7() : String{
        val numbers : List<Int> = mutableListOf(17,12,5,67,3,10)
        return numbers.meetCons lit@{
            if(it == 5){
                return@lit false
            }else{
                true
            }
        }.toString()

         numbers.meetCons{
             if(it == 5){
                 return@meetCons false
             }else{
                 true
             }
         }.toString()

        return "test7"
    }


}