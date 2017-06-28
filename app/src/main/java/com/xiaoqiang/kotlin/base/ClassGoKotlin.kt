package com.xiaoqiang.kotlin.base

/**
 * description: Kotlin_类
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 */
open class ClassGoKotlin(param0: String, var param00: String = "赋默认初始值") {

    var param1: String? = param0.toUpperCase()
    var param2: String? = null
    open val param3: String? = null

    init {
        //主构造函数初始化
        this.param1 = param0.toUpperCase()
    }

    //每个次构造函数需要委托给主构造函数
    constructor(param1: String, classGoKotlin: ClassGoKotlin) : this(param1) {
        this.param1 = param1.toUpperCase()
    }

    //每个次构造函数也可以委托给其他次造函数
    constructor(param1: String, param2: String, classGoKotlin: ClassGoKotlin) : this(param1, classGoKotlin) {
        this.param2 = param2
    }

    constructor(param1: String, param2: String, param3: String, classGoKotlin: ClassGoKotlin) : this(param1, param2, classGoKotlin) {
        this.param2 = param2
    }

    constructor(param1: String, param2: String, param3: String, param4: String, classGoKotlin: ClassGoKotlin) : this(param1, param2, param3, classGoKotlin) {
//        this.param3 = param3
    }

    fun Test(): String? {
        var classGoKotlin0 = ClassGoKotlin("0")
        var classGoKotlin00 = ClassGoKotlin("0", "可有可以无参数，看需求")
        var classGoKotlin1 = ClassGoKotlin("1", "2", "4", ClassGoKotlin("0"))
        var classGoKotlin2 = ClassGoKotlin("1", "2", "3", "4", ClassGoKotlin("0"))
        var classGoKotlin3 = ClassGoKotlin("1", "2", "3", "4", ClassGoKotlin("1", "2", ClassGoKotlin("0")))
        return this.param1
    }

    //继承方法
    fun v() {}

    open fun nv() {}

    //类继承，方法覆盖规则
    open class A {
        open fun f() {}
        fun a() {}
    }

    interface B {
        fun f() {} // 接口成员默认就是“open”的
        fun b() {}
    }

    class C() : A(), B {
        // 编译器要求覆盖 f()：
        override fun f() {
            super<A>.f() // 调用 A.f()
            super<B>.f() // 调用 B.f()
        }
    }

    //抽象类
    open class Base {
        open fun f() {}
    }

    abstract class Derived : Base() {
        override abstract fun f()
    }

    //同伴对象（Companion Object）
    companion object {
        //companion object静态方法集合关键字
        fun Test1(): String? {
            return ""
        }
    }

    //封闭类
    sealed class Expr {
        class Const(val number: Double) : Expr()
        class Sum(val e1: Expr, val e2: Expr) : Expr()
        object NotANumber : Expr()
    }
}