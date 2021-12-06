package webkul.opencart.mobikul.eventbus

import org.greenrobot.eventbus.EventBus

object GlobalEventBus {
    val eventBus: EventBus? = EventBus.getDefault()
}