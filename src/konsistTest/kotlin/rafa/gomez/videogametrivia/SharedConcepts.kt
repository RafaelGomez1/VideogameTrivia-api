package rafa.gomez.videogametrivia

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration

object SharedConcepts {

    // CQRS Concepts
    const val CQRS_SUFFIX = "Handler"
    const val COMMAND_SUFFIX = "Command"
    const val QUERY_SUFFIX = "Query"

    // Hexagonal Architecture Concepts
    const val DOMAIN_LAYER = "..domain.."
    const val APPLICATION_LAYER = "..application.."
    const val PRIMARY_ADAPTER_LAYER = "..primaryadapter.."
    const val SECONDARY_ADAPTER_LAYER = "..secondaryadapter.."
    const val DATABASE_PACKAGE = "database"

    // Spring Concepts
    const val CONTROLLER_SUFFIX = "Controller"
    const val REPOSITORY_SUFFIX = "Repository"

    // Data type concepts
    const val EITHER_TYPE = "Either<"
    const val RESPONSE_TYPE = "Response<"
    const val VALUE_CLASS_PROPERTY_NAME = "value"
    const val APPLICATION_SERVICE_METHOD_NAME = "invoke"

    // Sealed Class Concepts
    const val SEALED_CLASS_ERROR_SUFFIX = "Error"
    const val SEALED_CLASS_UNKNOWN_ERROR = "Unknown"


    fun KoClassDeclaration.isNotCQRSClass(): Boolean =
        !name.contains(CQRS_SUFFIX) && !name.contains(COMMAND_SUFFIX) && !name.contains(QUERY_SUFFIX)

    fun KoClassDeclaration.isApplicationService(): Boolean =
        isNotCQRSClass() && !name.contains(SEALED_CLASS_ERROR_SUFFIX)
}
