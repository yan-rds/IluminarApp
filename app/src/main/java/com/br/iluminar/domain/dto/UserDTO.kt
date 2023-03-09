package com.br.iluminar.domain.dto

data class UserDTO(
    var name: String? = null,
    var period: String? = null,
    var schoolYear: Int? = null,
    var responsibleName: String? = null,
    var responsiblePhone: String? = null,
    var profileUri: String? = null
)
