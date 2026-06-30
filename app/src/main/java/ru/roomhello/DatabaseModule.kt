package ru.roomhello

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        greetingDaoProvider: Provider<GreetingDao> // Используем Provider, чтобы избежать циклической ссылки внутри Callback
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "hello_database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Заполняем базу начальными данными при самом первом создании файла БД
                    CoroutineScope(Dispatchers.IO).launch {
                        greetingDaoProvider.get().insertGreeting(
                            GreetingEntity(id = 1, message = "Hello Android из базы данных!")
                        )
                    }
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideGreetingDao(database: AppDatabase): GreetingDao {
        return database.greetingDao()
    }

    @Provides
    @Singleton
    fun provideGreetingRepository(greetingDao: GreetingDao): GreetingRepository {
        return GreetingRepositoryImpl(greetingDao)
    }
}