package tech.coner.trailer.io.service

interface CreateService<Payload, Model> {

    suspend fun create(payload: Payload): Result<Model>
}