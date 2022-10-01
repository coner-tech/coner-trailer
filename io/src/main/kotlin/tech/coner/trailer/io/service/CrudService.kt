package tech.coner.trailer.io.service

interface CrudService<CreatePayload, Key, Model> :
    CreateService<CreatePayload, Model>,
    ReadService<Key, Model>,
    UpdateService<Model>,
    DeleteService<Model>