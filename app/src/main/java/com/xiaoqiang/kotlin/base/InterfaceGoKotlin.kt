package com.xiaoqiang.kotlin.base

/**
 * description: Kotlin_接口
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 *http://www.jianshu.com/p/03ea019e7237
 */
class InterfaceGoKotlin {

    fun outLog():String{
//        return test1().B()
//        return test2().stu1
        return test3().foo2()
    }

    //定义一个接口
    //使用 interface 关键字定义接口，允许方法有默认实现
    interface inter1{
        fun A() //未实现
        fun B() : String{
            return "已实现"
        }
    }

    //实现接口
    class test1 : inter1{//类或者对象可以实现一个或多个接口。
        override fun A() {
            //必须实现
        }
        override fun B():String{
            //非必须实现，因为接口定义方法里有默认实现
//            return "实现B" //调用test2().B()，返回“实现B”
            return super<inter1>.B() //调用test2().B()，返回“已实现”
        }
    }

    //接口中属性
    //接口中的属性只能是抽象的，不允许初始化值，接口不会保存属性值，实现接口时，必须重写属性：
    interface inter2{
        //这里属性是抽象的，不能初始化值
        var stu1 : String
        val stu2 : String
    }

    class test2 : inter2{
        //必须初始化抽象属性
        override var stu1: String = "初始刷stu1"
        override val stu2: String = "初始刷stu2"
    }

    //解决覆盖冲突
    interface inter3{
        var a:String
        fun foo():String{
            return a
        } //未实现
        fun A()
        fun B(){}
        fun D() : String{
            return "已实现"
        }
    }

    interface inter4{
        var a:String
        var stu:String
        fun foo():String{
            stu+="+inter4"
            return "inter4"
        } //未实现
        fun A()
        fun B()
        fun C():String //有返回类型的接口定义
    }

    class test3 : inter3,inter4{
        override var a : String = "aa"
        override var stu: String=""
        override fun foo() : String{
            return super<inter3>.foo() //输出：aa
        }

        fun foo1() : String{
            return super<inter4>.foo() //输出：inter4
        }

        fun foo2():String{
            stu = super<inter3>.foo()
            super<inter4>.foo()
            return stu //输出：aa+inter4
        }

        override fun A() {
            //inter3与inter4接口里都有A()方法，因为其未实现的方法，所以实际上A方法不存在冲突，只需要实现A方法就行
        }

        override fun B() {
            //这里实现的是inter4里面的方法B
        }
        fun B3() {
            super<inter3>.B()//这样你就可以通过B3调用接口inter3里实现的方法B()
        }

        override fun C() : String{
            return ""
        }
    }

}