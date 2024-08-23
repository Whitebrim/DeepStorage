package com.expectale.registry

import com.expectale.DeepStorage
import com.expectale.tileentity.DeepStorageUnit
import org.bukkit.Material
import xyz.xenondevs.nova.addon.registry.BlockRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.world.block.NovaTileEntityBlock
import xyz.xenondevs.nova.world.block.NovaTileEntityBlockBuilder
import xyz.xenondevs.nova.world.block.TileEntityConstructor
import xyz.xenondevs.nova.world.block.behavior.BlockSounds
import xyz.xenondevs.nova.world.block.behavior.Breakable
import xyz.xenondevs.nova.world.block.behavior.TileEntityDrops
import xyz.xenondevs.nova.world.block.behavior.TileEntityInteractive
import xyz.xenondevs.nova.world.block.behavior.TileEntityLimited
import xyz.xenondevs.nova.world.block.sound.SoundGroup
import xyz.xenondevs.nova.world.item.tool.VanillaToolCategories
import xyz.xenondevs.nova.world.item.tool.VanillaToolTiers

@Init(stage = InitStage.PRE_PACK)
object Blocks : BlockRegistry by DeepStorage.registry {
    
    private val STORAGE_UNIT = Breakable(3.0, VanillaToolCategories.PICKAXE, VanillaToolTiers.IRON,
        false, Material.STONE)
    
    val DEEP_STORAGE_UNIT = interactiveTileEntity("deep_storage_unit", ::DeepStorageUnit) { behaviors(STORAGE_UNIT, BlockSounds(SoundGroup.STONE)) }
    
    private fun interactiveTileEntity(
        name: String,
        ctor: TileEntityConstructor,
        init: NovaTileEntityBlockBuilder.() -> Unit
    ): NovaTileEntityBlock = tileEntity(name, ctor) {
        init()
        behaviors(TileEntityLimited, TileEntityDrops, TileEntityInteractive)
    }
}