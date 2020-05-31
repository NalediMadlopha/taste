package com.taste.app.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TasteDatabaseTest {

    private lateinit var _database: TasteDatabase

    val database: TasteDatabase
        get() = _database

    @Before
    open fun setUp() {
        _database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasteDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        _database.close()
    }

}