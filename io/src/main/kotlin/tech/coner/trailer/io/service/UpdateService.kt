package tech.coner.trailer.io.service

interface UpdateService<Model> {

    suspend fun update(model: Model): Result<Model>
}