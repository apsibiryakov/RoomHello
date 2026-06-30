package ru.roomhello

import kotlinx.coroutines.flow.Flow

interface GreetingRepository {
    fun getGreeting(): Flow<GreetingEntity?>
    suspend fun updateGreeting(greeting: GreetingEntity)
}
