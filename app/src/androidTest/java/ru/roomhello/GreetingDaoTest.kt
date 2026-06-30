package ru.roomhello

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GreetingDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: GreetingDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.greetingDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadGreetings() = runBlocking {
        val fakeGreeting = GreetingEntity(id = 1, message = "hello test")

        dao.insertGreeting(fakeGreeting)

        val result = dao.getGreeting().first()
        assertEquals("hello test", result?.message)
    }
}