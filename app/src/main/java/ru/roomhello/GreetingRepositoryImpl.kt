package ru.roomhello

import kotlinx.coroutines.flow.Flow

class GreetingRepositoryImpl(private val greetingDao: GreetingDao) : GreetingRepository {
    override fun getGreeting(): Flow<GreetingEntity?> {
        return greetingDao.getGreeting()
    }

    override suspend fun updateGreeting(greeting: GreetingEntity) {
        greetingDao.updateGreeting(greeting)
    }


}