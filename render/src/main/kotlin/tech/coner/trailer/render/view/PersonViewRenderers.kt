package tech.coner.trailer.render.view

import tech.coner.trailer.Person
import tech.coner.trailer.render.CollectionRenderer

interface PersonViewRenderer : ViewRenderer<Person>
interface PersonCollectionViewRenderer : PersonViewRenderer, CollectionRenderer<Person>