package tech.coner.trailer.io.service

interface ReadService<Key, Model> {

    suspend fun findByKey(key: Key): Result<Model>
}