package xyz.madki.notes.ui.base

import android.support.v7.app.AppCompatActivity

interface IActivityInjector<in A: AppCompatActivity> {
    fun inject(a: A)
}