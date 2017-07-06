package com.xiaoqiang.kotlin.base

import android.util.Log
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock


//在 Kotlin 中函数可以在文件顶层声明，这意味着你不需要像一些语言如 Java、C# 或 Scala 那样创建一个类来保存一个函数
fun topFun() : String{
    return "aa"
}
val TAG : String = "test"

class FunGoKotlin {

    /*** 函数声明关键字“fun” ***/
    //带返回的声明
    fun test0(a0 : Int) : Int{
        return a0
    }
    //不带返回的声明，tes1函数等同于test2
    fun test1(a1 : Int):Unit{
    }
    //不带返回的声明
    fun test2(a2 : Int){
    }

    //默认参数, b3 : Int = 10
    fun test3(a3 : Int, b3 : Int = 10, c3 : Int = 10,d3 : String = "默认值") : Int{
        return a3+b3
    }
    //调用默认参数
    val a3_1 : Int = test3(1)//使用默认参数值
    val a3_2 = test3(1,2);//不使用默认参数值
    val a3_3 = test3(1,c3 = 3, d3 = "")//假设我不想穿其中某个参数（这个参数最后一位d3），后面回去前面在赋值的时候可以使用参// 数“参数名称=赋值”进行调用函数

    //继承复写方法，override 关键字函数参数是不能有默认值的
    open class A {
        open fun foo(i: Int = 10) {  }
    }
    class B : A() {
        override fun foo(i: Int) {  }  // 不能有默认值
    }

    //单表达式函数
    fun test4(x : String) = "单表达式函数" + x
    fun test4(x : Int,y : Int) = x + y

    //中缀标示方法，必须要带参数才行，而且如果只有一个参数该参数是不能设置默认值，关键字：infix
    infix fun Int.test5_1(x : Int) = x+1
    infix fun Int.test5_2(x : Int) : Int{ return x * 2 }
    infix fun String.test5_2(x : Int) : Int{ return x * 2 }

    fun test5(x : Int) : Int{
        when(x){
            //中缀调用如下，总结是有两种：“xx 方法 传值”，“xx.方法(传值)”
            //这里提前用到了when控制流
            //也提前用到了retrun返回，如果在多个控制流里，具体如果返回不加“@”，默认是返回最近的一层控制流，“@”可以指定返回到某层
            0 -> return@test5 1 test5_2 4
            1 -> return@test5 2 test5_2 4
            2 -> return@test5 1.test5_2(4)
            3 -> return@test5 "" test5_2 6
            4 -> return@test5 "".test5_2(6)
            else -> return@test5 0
        }
    }

    //可变数量的参数,关键字varargs
    //一个函数里只能有一个vararg
    fun test6(a : String = "",  vararg b : String, c : String) : String{

        var stu : String = ""
        stu = stu + a
        stu += ","
        for (b0 in b){
            stu = stu + b0
        }
        stu += ","
        stu += c

        return stu
    }
    //Kotlin 支持局部函数，即一个函数在另一个函数内部
    fun test6() : String{
        fun test66() : String{
            return "test66()"
        }
        val log : String = test6("a", "b0","b1",c = "ad")
        return log+","+test66()
    }

    //调用顶层函数
    val test7 = topFun()

    //函数可以有泛型参数，通过在函数名前使用尖括号指定。
    fun <T> test8(t : T) : T{
        return t
    }
    fun <L> test8(l : List<L>) : List<L> {
        return l
    }

    //kotlin是支持尾递归函数的，这可以允许一些算法可以正常的使用循环而不是写一个递归函数，而且没有内存溢出的风险。
    // 如果一个函数用tailrec修饰符标记就满足了编译器优化递归的条件，并用高效迅速的循环代替它。
    //当函数被关键字tailrec修饰，同时满足尾递归(tail recursion)的函数式编程方式的形式时，编译器就会对代码进行优化,
    // 消除函数的递归调用, 产生一段基于循环实现的, 快速而且高效的代码。
    tailrec fun findFixPoint(x: Double = 1.0): Double
            = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))


    //lambda表达式,语法
    val sum = { x: Int, y: Int -> x + y }
    val sum1 : (Int, Int) -> Int = { x, y -> x + y }
    val sum2 : (Int, Int) -> Unit = {x,y -> Unit}
    val sum3 : (Int, Int) -> Int = { x,y ->
            val temp = x + y
            temp * 2//这个视为最后返回值
        }
    val sum4 : (Int) -> Int = { it * 10 }

    fun test9(a : Int, b : Int) : Int{

        return sum(a,b)
    }

    //高阶函数
    fun <T> lock(lock: Lock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        }
        finally {
            lock.unlock()
        }
    }

    //接受另一个函数作为参数的函数，我们必须为该参数指定函数类型
    fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection)
            if (max == null || less(max, it))
                max = it
        return max
    }

    fun test10(a : Int) : String{
        val numbers : List<Int> = mutableListOf(17,12,5,67,3,10)
        var stings : List<String> = arrayListOf("asd","dd","a","dddd")
        var times : List<String> = arrayListOf("15:00:01","15:00:00","4:10:00","17:00:00")

        fun dateScale(date1 : String, date2 : String) : Boolean{
            val df = SimpleDateFormat("HH:mm:ss")//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
            val dt1 = df.parse(date1)//将字符串转换为date类型
            val dt2 = df.parse(date2)
            if (dt1.getTime() > dt2.getTime())
            //比较时间大小,如果dt1大于dt2
            {
                return true
            }else{
                return false
            }
        }

        when(a){
            0 -> {
                //求Int集合里最大值，返回最大值
                return@test10 max(numbers, {x,y -> x>y}).toString()
            }
            1 -> {
                //求Int集合里最小值，返回最小值
                return@test10 max(numbers, {x,y -> x<y}).toString()
            }
            2 -> {
                //求字符串集合里长度最长的值，返回长度最长的字符
                return@test10 max(stings, {x,y -> x.length>y.length}).toString()
            }
            3 -> {
                //甚至你可以把你需要做的一些操作放在一个函数里进行调用，例如：dateScale(x,y)
                //求时间集合里最小时间，返回最小时间
                return@test10 max(times, {x,y -> dateScale(x,y)}).toString()
            }
            //当然你还可以实现很多很多，是不是感觉比java更加简洁和易懂啊，而且非常非常的节省代码，说实话我是喜欢上Kotlin风格
            else -> return@test10 ""
        }
    }

    //上面返回的是单个值，那么我是否可以写个公共函数用来返回满足条件的集合呢，当然可以，只需要对max稍作修改，如下
    fun <T> meetCons(collection: Collection<T>, less: (T) -> Boolean): List<T?> {
        val tmp: MutableList<T?> = mutableListOf()
        for (it in collection)
            if (less(it))
                tmp.add(it)
        return tmp
    }

    fun test11() : String {
        val stings : List<String> = arrayListOf("asd","dd","ass","dddd","125")
        //返回所有字符长度为3的集合
        //你可以这样返回
//        return meetCons(stings,less = {x -> x.length == 3}).toString()
        //当然你也可以省去less =
        return meetCons(stings){x -> x.length == 3}.toString()
    }

    //上面我将集合作为参数进行传递，那么是否可以对集合进行添加扩展函数呢，答案是肯定
    //我们将Collection<T>拿到方法名前，就相当于对Collection<T>进行扩展了，当然返回值啥地你都可与自由的扩展了，是不是觉得很神奇和好用
    fun <T> Collection<T>.meetCons(less: (T) -> Boolean): Collection<T?> {
        val tmp: MutableList<T?> = mutableListOf()
        for (it in this)
            if (less(it))
                tmp.add(it)
        return tmp
    }

    fun test12() : String {
        val stings : List<String> = arrayListOf("asd","dd","ass","dddd","125")

        //闭包
        var sum = ""
        stings.filter { it.length == 3 }.forEach {
            sum += it
        }

        //返回所有字符长度为3的集合
        //我们可以这样返回
//        return stings.meetCons{x -> x.length == 3}.toString()
        //当然只有一个参数的时候，kotlin是有个约定的，使用it代替单个参数，这样更加简洁
        return stings.meetCons{ it.length == 3 }.toString() + ">>" + sum
    }

    //匿名函数
    val test13 = fun(x : Int, y : Int):Int {  return x + y }
    //调用匿名函数
    val test14 = test13(1,2)
    //匿名函数可以省略返回类型，类型会自动推断
    val test15 = fun(x : Int, y : Int) = x + y

    //内联函数
    inline fun test16(inlined: () -> Unit, noinline notInlined: () -> Unit) {
        // ……
    }
}