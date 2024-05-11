package com.danilkha.conentfrientdsclient.core.network

import com.danilkha.contentfriends.ExceptionResponse

class ApiException (val exceptionBody: ExceptionResponse): RuntimeException(exceptionBody.message)