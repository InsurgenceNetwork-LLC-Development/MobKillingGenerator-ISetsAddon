package org.insurgencedev.mobkillinggenerator.listeners;

import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class MobKillListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onKill(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        if (player == null) {
            return;
        }

        KillCache.add(player, entity.getType());
    }

    public static class KillCache {

        private static final Map<Player, EntityType> killedMob = new HashMap<>();

        public static void add(Player player, EntityType type) {
            killedMob.put(player, type);
        }

        public static EntityType get(Player player) {
            return killedMob.get(player);
        }
    }

}
