package com.xiaoqiang.kotlin.base

/**
 * Created by admin3 on 2018/3/14.
 */
class EmptySafeKotlin {

    var bob: Bob? = null

    inner class Bob{
        var worker: Worker? = null
        inner class Worker{
            var name: String? = ""
        }
    }

    fun test(){
        //非可空类型
        var a: String = ""
//        a = null //编译有错
        a.length //可以直接调用

        //可空类型
        var b: String? = ""
        b = null//编译可以通过
//        b.length //编译有错,那怎么才能正确调用呢

        //安全的调用
        var c = b?.length //此处获得的c的值也是一个可空的值
        var d = bob?.worker?.name

        //Elvis 操作符
        var e = b?.length ?: -1

        //!!操作符
//        var f = b!!.length
        //假设有个java方法test2，传入参数不能为空，那么结合kotlin怎么写呢，可以如下：
//        var g = test1(b?.length)//这种方式首先编译都不通过，更不用说运行
        var g = test1(b?.length ?: -1)//这样也可以
        test2()
//        var g1 = test1(b!!.length)//这种方式虽然可以编译通过，但是运行100%抛出NPE异常
    }

    fun test1(int: Int): Int{
        return int
    }

    fun test2(){
        //安全的类型转化
        val a= 0
        val b: Long? = a as? Long//可以这样
        val c: Long? = bob as? Long//甚至也可以这样
        //可空类型的集合
        val d: List<Int?> = listOf(1,null,2)
    }

}