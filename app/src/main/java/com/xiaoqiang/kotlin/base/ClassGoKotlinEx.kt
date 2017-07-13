package com.xiaoqiang.kotlin.base

/**
 * description: Kotlin_继承类
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * http://www.jianshu.com/p/904782a731db
 */
open class ClassGoKotlinEx : ClassGoKotlin{
    override var param3 : String? = null
    //必须至少要有一个次构造函数，否则要在上面继承的时候就ClassGoKotlin("必须实现")
    constructor(param0 : String) : super(param0){
        //这个就是ClassGoKotlinEx("")，因为没有主构造函数，这个就类似主构造函数
    }
    constructor() : super("这里固定好值"){
        //这个就是ClassGoKotlinEx("")，因为没有主构造函数，这个就类似主构造函数
    }
    //这样就可以传递参数到继承的类里了
    constructor(param1 : String, classGoKotlinEx: ClassGoKotlinEx) : super(param1) {
        //经过我实际测试，这个次构造函数必须基于上面的构造函数才可以实现，否则ClassGoKotlinEx("", ClassGoKotlinEx());这样是不能调用的
    }
    //这个和上面第二个实际上是一样的，没有区别，可以删除
    constructor(classGoKotlinEx: ClassGoKotlinEx) : this(){
    }

    //重写继承类方法，final关键字意思：再次被继承是不允许再重写
    final override fun nv(){}
}