package com.iron.espresso

import android.content.Context
import java.io.File

/**
 * Created by jahun on 2016. 1. 8..
 */
object PrefUtil {
    fun getBoolean(context: Context, fileName: String?, key: String?): Boolean {
        return getBoolean(context, fileName, key, false)
    }

    fun getBoolean(context: Context, fileName: String?, key: String?, def: Boolean): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getBoolean(key, def)
    }

    fun setBoolean(context: Context, fileName: String?, key: String?, value: Boolean): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getInt(context: Context, fileName: String?, key: String?): Int {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }

    fun getInt(context: Context, fileName: String?, key: String?, defValue: Int): Int {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getInt(key, defValue)
    }

    fun setInt(context: Context, fileName: String?, key: String?, value: Int): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(key, value)
        return editor.commit()
    }

    fun getLong(context: Context, fileName: String?, key: String?): Long {
        return getLong(context, fileName, key, 0)
    }

    fun getLong(context: Context, fileName: String?, key: String?, def: Long): Long {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getLong(key, def)
    }

    fun setLong(context: Context, fileName: String?, key: String?, value: Long): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, value)
        return editor.commit()
    }

    fun getFloat(context: Context, fileName: String?, key: String?): Float {
        return getFloat(context, fileName, key, 0f)
    }

    fun getFloat(context: Context, fileName: String?, key: String?, def: Float): Float {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getFloat(key, def)
    }

    fun setFloat(context: Context, fileName: String?, key: String?, value: Float): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putFloat(key, value)
        return editor.commit()
    }

    fun getString(context: Context, fileName: String?, key: String?): String? {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getString(key, null)
    }

    fun setString(context: Context, fileName: String?, key: String?, value: String?): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getStringSet(context: Context, fileName: String?, key: String?): Set<String>? {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.getStringSet(key, null)
    }

    fun setStringSet(
        context: Context,
        fileName: String?,
        key: String?,
        value: Set<String?>?
    ): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putStringSet(key, value)
        return editor.commit()
    }

    fun remove(context: Context, fileName: String?, key: String?): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.edit().remove(key).commit()
    }

    fun removeAll(context: Context, fileName: String?): Boolean {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.edit().clear().commit()
    }

    fun getAll(context: Context, fileName: String?): Map<String, *> {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return pref.all
    }

    private fun getPreferenceFileNames(context: Context): Array<String>? {
        val prefsdir = File(context.applicationInfo.dataDir, "shared_prefs")
        return if (prefsdir.exists() && prefsdir.isDirectory) {
            prefsdir.list()
        } else null
    }
}