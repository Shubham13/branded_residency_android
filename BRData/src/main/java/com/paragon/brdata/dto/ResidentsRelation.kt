package com.paragon.brdata.dto

import java.io.Serializable

class ResidentsRelationMapper : Serializable {
    var residentId: String? = null
    var name: String? = null
    var type: String? = null
    var image: String? = null
    var address: String? = null
    var birthday: String? = null
    var email: String? = null
    var gender: String? = null
    var mobile: String? = null
    var occupation: String? = null
    var prefferedName: String? = null
    var relation: String? = null
    var status: String? = null
    var identification: List<Identification>? = null
}

class ResidentsRelationRoot : Serializable {
    var title: String? = null
    var data: List<ResidentsRelationMapper>? = null
}

class Identification : Serializable {
    val idImage: String? = null
    val idNumber: String? = null
    val idType: String? = null
}


