package com.github.ramonrabello.favoritehero.data.model

sealed class AuthorizationError(val code:Int, val message:String) {
    class MissingApiKey : AuthorizationError(409, "Missing API Key")
    class MissingHash : AuthorizationError(409, "Missing Hash")
    class MissingTimestamp : AuthorizationError(409, "Missing Timestamp")
    class InvalidReferer : AuthorizationError(401, "Invalid Referer")
    class InvalidHash : AuthorizationError(401, "Invalid Hash")
    class MethodNotAllowed : AuthorizationError(405, "Method Not Allowed")
    class Forbidden : AuthorizationError(403, "Forbidden")
}