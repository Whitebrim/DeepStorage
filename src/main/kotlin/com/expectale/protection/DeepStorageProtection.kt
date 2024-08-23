package com.expectale.protection

import com.expectale.tileentity.DeepStorageUnit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.api.protection.ProtectionIntegration
import xyz.xenondevs.nova.api.tileentity.TileEntity
import xyz.xenondevs.nova.world.format.WorldDataManager
import xyz.xenondevs.nova.world.pos

object DeepStorageProtection: ProtectionIntegration {
    
    override fun canBreak(player: OfflinePlayer, item: ItemStack?, location: Location): Boolean {
        val storageUnit = getStorageUnit(location) ?: return true
        if (!player.isOnline) return false
        return storageUnit.hasAccess(player as Player)
    }
    
    override fun canBreak(tileEntity: TileEntity, item: ItemStack?, location: Location): Boolean {
        return !isStorageUnit(location)
    }
    
    override fun canPlace(player: OfflinePlayer, item: ItemStack, location: Location): Boolean {
        return true
    }
    
    override fun canUseBlock(player: OfflinePlayer, item: ItemStack?, location: Location): Boolean {
        val storageUnit = getStorageUnit(location) ?: return true
        if (!player.isOnline) return false
        return storageUnit.hasAccess(player as Player)
    }
    
    override fun canUseItem(player: OfflinePlayer, item: ItemStack, location: Location): Boolean {
        return true
    }
    
    override fun canInteractWithEntity(player: OfflinePlayer, entity: Entity, item: ItemStack?): Boolean {
        return true
    }
    
    override fun canHurtEntity(player: OfflinePlayer, entity: Entity, item: ItemStack?): Boolean {
        return true
    }
    
    private fun getStorageUnit(location: Location): DeepStorageUnit? {
        val tileEntity = WorldDataManager.getTileEntity(location.pos) ?: return null
        if (tileEntity !is DeepStorageUnit) return null
        return tileEntity
    }
    
    private fun isStorageUnit(location: Location): Boolean {
        val tileEntity = WorldDataManager.getTileEntity(location.pos) ?: return false
        return tileEntity is DeepStorageUnit
    }
    
}