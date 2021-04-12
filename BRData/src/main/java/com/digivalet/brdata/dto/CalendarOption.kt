package com.digivalet.brdata.dto

class RepeatOption {
    var title: String? = null
    var type: String? = null
    var isSubOption = false
}

class TimeOption {
    var title: String? = null
    var type: String? = null
    var isSubOption = false
}

class CalendarOptionRoot {
    var repeatOption: List<RepeatOption>? = null
    var timeOption: List<TimeOption>? = null
}
