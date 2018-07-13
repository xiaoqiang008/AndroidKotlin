package com.xiaoqiang.kotlin.base

import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * description: Kotlin_协程
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * 协程1：http://www.jianshu.com/p/fa7fb9ec836f
 * 协程2：http://www.jianshu.com/p/08268685de26
 * 协程3：http://www.jianshu.com/p/9565d90e84ee
 */
class CoroutinesGoKotlin {

    val TAG : String = "test"

    fun log(log : String){
        Log.i(TAG,log)
    }
    /*****************   协程1(http://www.jianshu.com/p/fa7fb9ec836f)   **********************/

    //挂起函数
    suspend fun test1() : String{
        log("3")
        delay(1000L)
         return "aaa"
    }

    //launch
    //建立一个简单的协同程序
    fun test2(){
        launch(CommonPool){
            delay(3000L)
            log("1")
        }
        log("2")
        Thread.sleep(2000L)//阻塞主线程2秒，以保持JVM的生命
    }
    //launch(CommonPool) {} <-> thread {}
    //delay() <-> Thread.sleep()

    //官方为了方便大家在coroutine区分阻塞与非阻塞代码,而且减少使用Thread这种胖子,就推荐了以下的写法:
    //runBlocking为最高级的协程 (一般为主协程), 其他协程如launch {} 能跑在runBlocking (因为层级较低), 反过来不行
    fun test3() = runBlocking<Unit>{//<Unit>可以省略
        launch(CommonPool){
            delay(3000L)
            log("1")
        }
        log("2")
    }

    //等待完成
    //如果接下里的工作需要依赖于另外一个，我们可以使用join，让其等待launch的完成
    //当然join暂停功能是可以取消的，如果调用coroutine作业期间被取消或者完成，立即回复下面执行
    fun test4() = runBlocking<Unit>{
        var job = launch(CommonPool){
            delay(3000L)
            log("1")
        }
        log("2")
        job.join()
        log("3")
    }

    //当然如果有两个需要协同工作，也可以如下写：
    fun test5() = runBlocking<Unit>{
        var job1 = launch(CommonPool){
            log("job1 start")
            delay(3000,TimeUnit.MILLISECONDS)
            log("job1 end")
        }
        log("1")
        var job2 = launch(CommonPool) {
            log("job2 start,wait job1 end")
            job1.join()
            log("job2 end")
        }
        log("2")
        job2.join()
        log("3")
    }

    //提取函数
    //如果launch(CommonPool){}里面代码很多，我们可以提取出来，用suspend修饰，成为暂停函数
    suspend fun downUrl(){
        delay(3000L)
        log("1")
    }
    fun test6() = runBlocking<Unit>{
        var job = launch(CommonPool){
            downUrl()
        }
        log("2")
    }

    //协同程序是轻量级的
    fun test7() = runBlocking<Unit> {
        val jobs = List(100_00) {
            launch(CommonPool) {
                delay(1000L)
                log(".")
            }
        }
        jobs.forEach { it.join() }
    }

    //Coroutines就像守护线程
    //创建一个长时间工作的Coroutines
    fun test8() = runBlocking<Unit>{
        launch(CommonPool) {
            repeat(1000) { i ->
                log("$i ...")
                delay(500L)
            }
        }
    }

    /*****************   协程2(http://www.jianshu.com/p/08268685de26)   **********************/
    //取消和超时
    fun test9() = runBlocking<Unit>{
        var job = launch(CommonPool) {
            repeat(1000) { i ->
                log("$i ...")
                delay(500L)
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
    }

    //也不是所有的Coroutine可以取消，如果一个程序在计算工作不检查取消，就不能取消，如下：
    fun test10() = runBlocking<Unit>{
        var job = launch(CommonPool) {
            var i = 0
            var nextPrintTime = System.currentTimeMillis()
            while (i < 10) { // computation loop
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextPrintTime) {
                    log("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
    }

    //那么如果你想取消计算代码，第一个是周期性地调用暂停函数。
    fun test11() = runBlocking<Unit>{
        var job = launch(CommonPool) {
            var i = 0
            while (i < 10) { // computation loop
                log("I'm sleeping ${i++} ...")
                delay(500L)
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
    }
    // 。另一个是显式地检查取消状态。让我们来试试后面的方法。isActive
    fun test12() = runBlocking<Unit>{
        var job = launch(CommonPool) {
            var i = 0
            var nextPrintTime = System.currentTimeMillis()
            while (isActive) { // computation loop
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextPrintTime) {
                    log("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
    }

    //如果就这样取消，那么我们如何关闭launch里资源呢，我们可以用try{}finally{}监听launch取消操作，在里面进行资源关闭
    fun test13() = runBlocking<Unit> {
        var job = launch(CommonPool) {
            try {
                repeat(1000) { i ->
                    log("$i ...")
                    delay(500L)
                }
            }finally {
                //这里进行资源关闭
                log("job close")
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
        delay(1000L)
    }

    //如果你在取消coroutines时，需要长时间关闭资源，你可以用run{}来实现，例如：
    fun test14() = runBlocking<Unit> {
        var job = launch(CommonPool) {
            try {
                repeat(1000) { i ->
                    log("$i ...")
                    delay(500L)
                }
            }finally {
                //这里进行资源关闭
                run(NonCancellable) {
                    log("I'm running finally")
                    delay(1000L)
                    log("job close")
                }
            }
        }
        log("1")
        delay(1500L)
        log("job cancel")
        job.cancel()
        log("2")
        delay(1000L)
    }

    //超时
    //超时的时候回抛出TimeoutException异常，导致程序奔溃，你可以使用try{}catch(e:CancellationException){}捕获并进行想要的异常处理
    fun test15() = runBlocking<Unit>{
        try {
        withTimeout(3000L){
                repeat(1000) { i ->
                    log("$i ...")
                    delay(500L)
                }
        }
        }catch (e:CancellationException){
            log("CancellationException")
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        log("one")
        delay(1000L)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        log("two")
        delay(1000L)
        return 29
    }

    //顺序执行
    //measureTimeMillis:执行给定的块并以毫秒为单位返回运行时间。
    fun test16() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            log("one+two = ${one + two}")
        }
        log("耗时： $time ms")
    }

    //同步执行
    //这里会用到async，async与launch虽然都有异步的作用
    //返回不同：launch返回的是对其引用,async是用其未来的值作为返回，
    //当然async也包含了launch的一些功能(join/cancel等等)
    fun test17() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = async(CommonPool){ doSomethingUsefulOne() }
            val two = async(CommonPool){ doSomethingUsefulTwo() }
            log("one+two = ${one.await() + two.await()}")
        }
        log("耗时： $time ms")
    }

    //懒加载(CoroutineStart.LAZY)
    //Kotlin这里还提供了更加强大的功能“懒加载”，当你需要等待结果的时候，才开始执行，例如：
    fun test18() = runBlocking<Unit> {
        val time = measureTimeMillis {
            log("1")
            val one = async(CommonPool,CoroutineStart.LAZY){ doSomethingUsefulOne() }
            val two = async(CommonPool,CoroutineStart.LAZY){ doSomethingUsefulTwo() }
            log("2")
            log("one+two = ${one.await() + two.await()}")
        }
        log("耗时： $time ms")
    }

    //在这里你会发现每次异步async调用方法都要写async(){},如此是不是感觉很麻烦，我们可以进一步封装，例如：
    fun asyncSomethingUsefulOne() = async(CommonPool) {
        doSomethingUsefulOne()
    }

    fun asyncSomethingUsefulTwo() = async(CommonPool)  {
        doSomethingUsefulTwo()
    }

    fun test19() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = asyncSomethingUsefulOne()
            val two = asyncSomethingUsefulTwo()
            log("one+two = ${one.await() + two.await()}")
        }
        log("耗时： $time ms")
    }
    //因为这里asyncSomethingUsefulOne与asyncSomethingUsefulTwo不是suspend函数，所有你还可以这样做,结果与上面的一样：
    fun test20(){
        val time = measureTimeMillis {
            val one = asyncSomethingUsefulOne()
            val two = asyncSomethingUsefulTwo()
            runBlocking {
                log("one+two = ${one.await() + two.await()}")
            }
        }
        log("耗时： $time ms")
    }


    /*****************   协程3(http://www.jianshu.com/p/9565d90e84ee)   **********************/
    //线程的调度
    //在前面我们经常用到“CommonPool”共享的线程池，那么除了共享的线程池以为还有哪些呢，如下：
    fun test21() = runBlocking<Unit> {
        val jobs = arrayListOf<Job>()
        jobs += launch(Unconfined) { // not confined -- will work with main thread
            log("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch(coroutineContext) { // context of the parent, runBlocking coroutine
            log("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch(CommonPool) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
            log("      'CommonPool': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            log("          'newSTC': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs.forEach { it.join() }
    }

    //Unconfined:无限制的，自由的,不限于任何特定线程
    //coroutineContext:返回这个coroutine的上下文。
    //CommonPool:共享线程的公共池,类似java里线程池，只不过这里是公共的
    //newSingleThreadContext：创建一个单独的线程，在作业被取消时回收

    //Channels
    //Channels，它不是一个阻止放操作，而是一个挂起发送，而不是一个阻塞操作，它有一个暂停接收。可用于线程间传递数据
    //我们先来看一个简单的示例：
    fun test22() = runBlocking<Unit> {
        //定义一个通道
        val channel = Channel<Int>()
        launch(CommonPool) {
            for (x in 1..5) {
                delay(1000L)
                //在这里发送
                channel.send(x * x)
            }
        }
        repeat(5) {
            //在这里接收
            log(channel.receive().toString())
        }
        log("Done!")
    }

    //关闭通道
    fun test23() = runBlocking<Unit> {
        //定义一个通道
        val channel = Channel<Int>()
        launch(CommonPool) {
            for (x in 1..5) {
                delay(1000L)
                //在这里发送
                channel.send(x * x)
                if(x == 4){
                    log("关闭通道")
                    channel.close()
                }
            }
        }
        repeat(5) {
            //在这里接收
            try {
                var a = channel.receive()
                log(a.toString())
            }catch (e: ClosedReceiveChannelException){
                log("关闭通道报出异常ClosedReceiveChannelException")
            }
            log("等待接收")
        }
        log("Done!")
    }

    //我们也可把通道写成方法，方法里结束通道时在接收方可以不写抛出异常，例如：
    fun produceSquares() = produce<Int>(CommonPool) {
        for (x in 1..5) {
            delay(1000L)
            send(x * x)
            if (x == 4){
                channel.close()
            }
        }
    }
    fun test24() = runBlocking<Unit> {
        val squares = produceSquares()
        squares.consumeEach { log(it.toString()) }
        log("Done!")
    }

    //Pipelines
    //在这里我理解为管道运输，也就是说上面produce生成通道是可以两个链接起来，每块做不同的工作，然后输出，例如：
    fun produceSquares1() = produce<Int>(CommonPool) {
        for (x in 1..5) {
            delay(1000L)//这里延迟就失效了，具体原因不清楚为啥会失效
            send(x)
        }
    }
    fun produceSquares2(numbers: ReceiveChannel<Int>) = produce<Int>(CommonPool) {
        for (x in 1..5) {
            delay(1000L)
            send((x * x)+1)
        }
    }
    fun test25() = runBlocking<Unit> {
        //始发地
        val numbers = produceSquares1()
        //通道二次加工
        val squares = produceSquares2(numbers)
        //最后输出
        squares.consumeEach { log(it.toString()) }
        log("Done!")
        //关闭通道，回收
        numbers.cancel()
        squares.cancel()
    }

    //Fan-out
    //多个coroutines 可以从一个通道接收,例如：
    fun produceNumbers() = produce<Int>(CommonPool) {
        var a = 1
        while (true){
            delay(100L)
            send(a++)
        }
    }
    fun launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch(CommonPool) {
        channel.consumeEach {
            log("launch#$id:收到： $it")
        }
    }
    fun test26() = runBlocking<Unit> {
        val producer = produceNumbers()
        repeat(5) { launchProcessor(it, producer) }
        delay(950)
        producer.cancel() // cancel producer coroutine and thus kill them all
    }

    //Fan-in
    //多个coroutines 可以发送到一个通道，例如
    suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(s)
        }
    }
    fun test27() = runBlocking<Unit> {
        val channel = Channel<String>()
        //这里两个launch间隔着向通道发送数据
        launch(coroutineContext) { sendString(channel, "foo", 200L) }
        launch(coroutineContext) { sendString(channel, "BAR!", 500L) }
        repeat(6) {
            //循环接收数据
            log(channel.receive())
        }
    }

    //通道缓冲区
    //到目前为止所显示的通道没有缓冲区。当发送方和接收方相遇(又称会合)时，未缓冲通道传输元素。如果首先调用send，那么它将被挂起，直到被调用接收，如果首先调用接收，它将暂停，直到调用send。
    fun test28() = runBlocking<Unit> {
        //创建缓冲区大小为4的通道
        val channel = Channel<Int>(3)
        launch(CommonPool) {
            repeat(5) {
                log("Sending $it")
                channel.send(it) //当通道缓冲区满的时候挂起,直到下方接收的时候在继续
                delay(1000L+it*1000L)
            }
        }
        log("开始接收")
        launch(CommonPool) {
            repeat(10) {
                try {
                    withTimeout(3000L) {
                        val a = channel.receive()//开始接收通道缓冲区里数据，上方发送被激活，继续发送
                        log("Receive $a // $it")
                    }
                }catch (e : CancellationException){
                    log("CancellationException $it")
                }
            }
        }
    }

}