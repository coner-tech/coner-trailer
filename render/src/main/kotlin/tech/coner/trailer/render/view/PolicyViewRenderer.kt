package tech.coner.trailer.render.view

import tech.coner.trailer.Policy

interface PolicyViewRenderer : ViewRenderer<Policy>
interface PolicyCollectionViewRenderer : PolicyViewRenderer, CollectionViewRenderer<Policy>