package com.expectale.item

import com.expectale.registry.Items
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.serialization.cbf.NamespacedCompound
import xyz.xenondevs.nova.util.addToInventoryOrDrop
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent

object EmptySecurityCard: ItemBehavior {
    
    override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
        if (!player.isSneaking) return
        itemStack.subtract()
        val novaCard = Items.SECURITY_CARD
        val card = novaCard.createItemStack()
        novaCard.getBehaviorOrNull<SecurityCard>()?.apply { setOwner(card, player) }
        player.addToInventoryOrDrop(listOf(card))
    }
    
    override fun modifyClientSideStack(player: Player?, itemStack: ItemStack, data: NamespacedCompound): ItemStack {
        itemStack.setItemMeta(itemStack.itemMeta?.apply {
            lore(lore() ?: ArrayList<Component?>().apply {
                add(Component.translatable("item.deep_storage.empty_security_card.lore").color(NamedTextColor.GRAY))
            })
        })
        return itemStack
    }
}