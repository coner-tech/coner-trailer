package tech.coner.trailer.io.service

interface DeleteService<Model> {

    suspend fun delete(model: Model): Result<Unit>
}