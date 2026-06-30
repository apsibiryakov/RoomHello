package ru.roomhello

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GreetingDao{
    @Query("SELECT * FROM greetings WHERE id = 1 LIMIT 1")
    fun getGreeting(): Flow<GreetingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGreeting(greetingEntity: GreetingEntity)

    @Update
    suspend fun updateGreeting(greetingEntity: GreetingEntity)
}