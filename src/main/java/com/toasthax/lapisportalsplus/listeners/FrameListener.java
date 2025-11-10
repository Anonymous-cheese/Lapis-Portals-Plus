package com.toasthax.lapisportalsplus.listeners;

import com.toasthax.lapisportalsplus.LapisPortalsPlus;
import com.toasthax.lapisportalsplus.PortalManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class FrameListener implements Listener {
    private final LapisPortalsPlus plugin;
    private final PortalManager portalManager;

    public FrameListener(LapisPortalsPlus plugin, PortalManager manager) {
        this.plugin = plugin;
        this.portalManager = manager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (!p.hasPermission("lapisportalsplus.create")) return;
        if (!portalManager.worldAllowed(b.getWorld())) {
            p.sendMessage(plugin.getConfig().getString("messages.world_blocked"));
            return;
        }

        var allowed = plugin.getConfig().getStringList("frame_blocks");
        if (!allowed.contains(b.getType().name())) return;

        // Check chest adjacency
        Block chestBlock = null;
        for (BlockFace face : BlockFace.values()) {
            Block relative = b.getRelative(face);
            if (relative.getType() == Material.CHEST) {
                chestBlock = relative;
                break;
            }
        }
        if (chestBlock == null) return;

        // Create portal (simplified nether-style)
        var interior = new com.toasthax.lapisportalsplus.model.Cuboid(
                b.getLocation().subtract(1, 1, 1),
                b.getLocation().add(1, 2, 1)
        );

        var portal = portalManager.createPortal(interior, chestBlock.getLocation());
        var result = portalManager.relinkFromChest(portal, p);
        if (result == PortalManager.RelinkResult.KEY_IN_USE)
            p.sendMessage(plugin.getConfig().getString("messages.key_in_use"));
        else if (result == PortalManager.RelinkResult.WAITING)
            p.sendMessage(plugin.getConfig().getString("messages.created"));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        portalManager.removePortalAt(e.getBlock());
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.blockList().forEach(portalManager::removePortalAt);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        e.blockList().forEach(portalManager::removePortalAt);
    }
}
