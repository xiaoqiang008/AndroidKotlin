package com.xiaoqiang.kotlin.base

import java.util.*
import kotlin.collections.HashSet

/**
 * description: Kotlin_基本类型与集合
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * http://www.jianshu.com/p/aed2b18d568d
 */
class BaseTypeGoKotlin {
    //数字类型
    val doubles : Double = 123.5e10 //Double,64
    val floats : Float = 1.0f //Float,32
    val longs : Long = 1L //Long,64
    val ints : Int = 1 //Int,32
    val shorts : Short = 1 //Short,16
    val bytes : Byte = 1 //Byte,8

    val tenType : Int = 10 //支持十进制，默认是十进制
    val sixteenType : Byte = 0x22 //支持十六进制,0x开头标示十六进制
    val twoType : Int = 0b00000010 //支持二进制,0b开头表示二进制
    //目前不支持八进制

    //数字类型支持字面值中的下划线，这样让长数字常量更已读
    val ints1 = 1_001_000
    val longs2 = 1234_5678_9012_3456L
    val hexBytes = 0xFF_EC_DE_5E
    val bytew = 0b11010010_01101001_10010100_10010010

    fun test() : String{
        return twoType.toString()
    }

    //“==”与“===”区别


    fun test1() : String{
        val a : Int = 100
        val b : Int? = a
        val c : Int? = a
        val d : Int? = 100

        return "== >>>"+(a == a).toString()+","+
                (b == a).toString()+","+
                (a == d).toString()+","+
                (c == d).toString()+","+
                (c == b).toString()+",=== >>>"+
                (a === a).toString()+","+
                (b === a).toString()+","+
                (a === d).toString()+","+
                (c === d).toString()+","+
                (c === b).toString()
    }

    val a: Int? = 1
//    val b: Long? = a//这样隐式转换有问题，不能编译
//    val c: Long? = (Long)a//像java一样强转也不行，不能编译

    //当然Kotlin提供了强大的转化功能
//    每个数字类型支持如下的转换:
//    toByte(): Byte
//    toShort(): Short
//    toInt(): Int
//    toLong(): Long
//    toFloat(): Float
//    toDouble(): Double
//    toChar(): Char

    val d: Long? = a?.toLong()

    //算术运算会有重载做适当转换
    val l = 1L + 3 // Long + Int => Long

//    这是完整的位运算列表（只用于 Int 和 Long）：
//    shl(bits) – 有符号左移 (Java 的 <<)
//    shr(bits) – 有符号右移 (Java 的 >>)
//    ushr(bits) – 无符号右移 (Java 的 >>>)
//    and(bits) – 位与
//    or(bits) – 位或
//    xor(bits) – 位异或
//    inv() – 位非
    val x = (1 shl 2) and 0x000FF000

    fun test2() : String{
        return x.toString()
    }


    //字符类型,单引号括起来且只能一个字符
    //特殊字符可以用反斜杠转义。 支持这几个转义序列：\t、 \b、\n、\r、\'、\"、\\ 和 \$
    val char1 : Char = 'a'
    val char2 : Char = '1'
    val cInt : Int = char2.toInt()//字符类型转Int
    //字符类型转Int方法
    fun CharToInt(c: Char): Int {
        if (c !in '0'..'9')
            throw IllegalArgumentException("Out of range")
        return c.toInt() - '0'.toInt() // 显式转换为数字
    }
    fun test3() : String{
        return CharToInt('1').toString()
    }


    //布尔类型
    val bool1 : Boolean = true
    val bool2 : Boolean? = null//加上？就可以对布尔类型赋值null
    fun test4() : String{
        return bool2.toString()//就算调用null，输出打印，也不会报错，这就是Kotlin空安全强大之处
    }


    //集合、数组类型
    //与大多数语言不同，Kotlin 区分可变集合和不可变集合（lists、sets、maps 等）。
    // 精确控制什么时候集合可编辑有助于消除 bug 和设计良好的 API。

    //Kotlin 的 List<out T> 类型是一个提供只读操作如 size、get等的接口。
    // 和 Java 类似，它继承自 Collection<T> 进而继承自 Iterable<T>。
    // 改变 list 的方法是由 MutableList<T> 加入的。
    // 这一模式同样适用于 Set<out T>/MutableSet<T> 及 Map<K, out V>/MutableMap<K, V>。
    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    val OnlyReadList: List<Int> = numbers
    fun test5() : String{
//        readOnlyView.clear()    // -> 不能编译
//        return numbers.toString()       // 输出 "[1, 2, 3]"
        numbers.add(4)
        return OnlyReadList.toString()   // 输出 "[1, 2, 3, 4]"
    }

    //Arrays
    //Kotlin 标准库提供了arrayOf()创建数组， **ArrayOf创建特定类型数组
    fun test6() : String{
        val array = arrayOf(1,2,3)
        //和Java不一样，Kotlin 的数组是容器类
        // 提供了 ByteArray, CharArray, ShortArray, IntArray, LongArray, BooleanArray, FloatArray, and DoubleArray。
        val intArray = intArrayOf(1,2)
        //创建一个具有指定[size]的新数组，其中每个元素通过调用指定的值来计算。
        // 函数的作用是:返回给定索引的数组元素。
        val array1 = Array(10, { k -> k * k })//这里k代表元素的索引位置
//        return array1.get(2).toString()//输出4，因为索引是2，输出：k*k = 2*2 = 4
        val array2 = Array(10,{k -> "位置:"+(k+1) })
//        return array2.get(5).toString() //输出"位置:6"，因为索引是5，输出："位置:"+(k+1) = "位置:"+(6+1) = 位置:6
        val emptyArray = emptyArray<Long>()
        class Student(val name : String?){
        }
        //当然你也创建对象数组
        val studentArray = Array<Student>(10,{k -> Student("学生编号："+k)})
//        return studentArray.get(6).name.toString()
        studentArray[6] = Student("张三")
        return studentArray.get(6).name.toString()

        return ""
    }

    //list
    //List是有序容器，Kotlin 标准库通过listOf()创建list
    fun test7() : String{
        //你可以这样申明
        val list1 = listOf<Int>()
        //当然也可以这样，list1与list2意思一样
        val list2 : List<Int> = listOf(1,2,3,4,5,6)
        //当然你也可以付初始默认值，也可以不指定类型
        val list3 = listOf(1,2,"3")
        //如果你想对list2进行添加或者修改时，就不行了，因为List<T>只能读，这时候你需要声明个可以操作的属性
        val list4 : MutableList<Any> = list3.toMutableList()//这里将List转换出可以编辑的
        list4.add("4")
//        list4.set(0,10)
//        return list4.toString()//输出：[1, 2, 3, 4]
//        return list3.toString()//输出：[1, 2, 3],对list4操作不会影响list3，list4相当于复制出来的
//        return (list4.first() == 1).toString()//当然也有list4.last()
//        return list2.filter { (it % 2) == 0 }.toString()//当然也可以按一定条件筛选

        val list5 : MutableList<Int> = mutableListOf(1,2,3,4)
        val list6 : List<Int> = list5
        list5.add(5)
//        return list5.toString()//输出：[1, 2, 3, 4, 5]
        return list6.toString()//输出：[1, 2, 3, 4, 5]

        val emptyList: List<String> = emptyList<String>()
        val nonNulls: List<String> = listOfNotNull<String>(null, "a", "b", "c")
        return ""
    }


    //Maps
    //Map是<key, value>容器， Kotlin提供mapOf创建map
    fun test8() : String{
        val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
//        return map1.toString()
        val map2 = hashMapOf("a" to 1, "b" to 2, "c" to 3)
//        return map2.toString()
        val map3 : MutableMap<String, String> = mutableMapOf("a" to "a", "b" to "b", "c" to "c")
//        return map3.toString()
        val map4 : LinkedHashMap<String, String> = linkedMapOf("a" to "a", "b" to "b", "c" to "c")
//        return map4.toString()
        val map5 : SortedMap<String, String> = sortedMapOf("a" to "a", "b" to "b", "c" to "c")
        return map5.toString()
        return ""
    }


    //Sets
    //Set是没有重复项的容器， Kotlin提供setOf创建Set
    fun test9() : String{
        val set1: Set<Int> = setOf(1,2,3,3,4) //1,2,3,4
//        return set1.toString()
        val set2: HashSet<String> = hashSetOf("1","2","3","3")//3,2,1
//        return set2.toString()
        val set3: SortedSet<Int> = sortedSetOf(11, 0, 9, 11, 9, 8)//0,8,9,11
        return set3.toString()
        return ""
    }

    //字符串类型
    //支持这几个转义序列：\t、 \b、\n、\r、\'、\"、\\ 和 \$
    fun test10() : String{
        val string1 : String = "1"
        val string2 = "Hello, world!\n"
        //字符串模板表达式
        val string3 = "a = $string1"
//        return string3
        val string4 = "$string3.length is ${string3.length}"
        return string4 //输出a = 1.length is 5
        return ""
    }

}