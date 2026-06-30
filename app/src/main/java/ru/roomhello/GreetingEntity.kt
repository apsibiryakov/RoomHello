package ru.roomhello
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "greetings")
data class GreetingEntity(
    @PrimaryKey val id: Int = 1,
    val message: String
)