package com.paragon.brdata.dto

import com.paragon.utils.expandable.ParentListItem
import java.io.Serializable

data class GuestAccess(
    val guestAccess: GuestAccessX
)

data class GuestAccessX(val commonAccess: List<CommonAccess>) : ParentListItem {

    override fun getChildItemList(): MutableList<*> {
        return commonAccess.toMutableList()
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }
}

data class CommonAccess(
    val accessId: String,
    val oneTimeGuestAllowed: Boolean,
    val title: String
) : Serializable