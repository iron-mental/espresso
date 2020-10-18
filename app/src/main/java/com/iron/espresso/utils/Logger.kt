package com.iron.espresso.utils

import android.util.Log
import com.iron.espresso.BuildConfig

object Logger {
    var lineNumber = 0
    var className: String? = null
    var methodName: String? = null
    private val isDebuggable: Boolean
        get() = BuildConfig.DEBUG

    private fun log(mode: Int, className: String?, msg: String) {
        if (isDebuggable) return
    }

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("[")
        buffer.append(methodName)
        buffer.append("():")
        buffer.append(lineNumber)
        buffer.append("]")
        buffer.append(log)
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName.replace(".java".toRegex(), "")
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (!isDebuggable) return

        // Throwable instance must be created before any methods
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
        log(Log.ERROR, className, createLog(message))
    }

    fun i(message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
        log(Log.INFO, className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
        log(Log.DEBUG, className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
        log(Log.VERBOSE, className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
        log(Log.WARN, className, createLog(message))
    }

    fun wtf(message: String) {
        if (!isDebuggable) return
        getMethodNames(Throwable().stackTrace)
        Log.wtf(className, createLog(message))
        log(Log.ERROR, className, createLog("WatTheFuk: $message"))
    }
}
