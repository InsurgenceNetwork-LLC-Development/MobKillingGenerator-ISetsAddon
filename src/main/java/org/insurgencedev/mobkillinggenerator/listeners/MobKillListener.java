package org.insurgencedev.mobkillinggenerator.listeners;

import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.insurgencedev.insurgencesets.api.FragmentGenerator;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.api.contracts.IArmorSet;
import org.insurgencedev.insurgencesets.api.contracts.IPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class MobKillListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onKill(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer == null) {
            return;
        }

        IPlayer cache = ISetsAPI.getCache(killer);
        IArmorSet armorSet = ISetsAPI.getArmorSetManager().getArmorSet(cache.getFragmentDataManager().getArmorSetFragmentGen());
        if (armorSet == null) {
            return;
        }

        final String type = armorSet.getFragmentGeneration().getString("Type");
        final String source = armorSet.getFragmentGeneration().getString("Source");
        final FragmentGenerator generator = ISetsAPI.getFragmentGeneratorManager().findFragmentGenerator(type, source);
        if (generator == null) {
            return;

        }

        List<String> disabledWorlds = armorSet.getFragmentGeneration().getStringList("Disabled_Worlds");
        if (!disabledWorlds.contains(killer.getWorld().getName())) {
            KillCache.add(killer, entity.getType());
            generator.handleGeneration(killer, armorSet.getFragmentGeneration());
        }
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
