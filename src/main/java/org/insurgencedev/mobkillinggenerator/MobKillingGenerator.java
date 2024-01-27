package org.insurgencedev.mobkillinggenerator;

import org.bukkit.entity.Player;
import org.insurgencedev.insurgencesets.api.FragmentGenerator;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.api.contracts.IArmorSet;
import org.insurgencedev.insurgencesets.api.contracts.IFragment;
import org.insurgencedev.insurgencesets.api.contracts.IPlayer;
import org.insurgencedev.insurgencesets.libs.fo.Common;
import org.insurgencedev.insurgencesets.libs.fo.collection.SerializedMap;
import org.insurgencedev.mobkillinggenerator.listeners.MobKillListener;
import org.jetbrains.annotations.NotNull;

public class MobKillingGenerator extends FragmentGenerator {

    public static final String NAMESPACE = "IGen";

    public MobKillingGenerator() {
        super(NAMESPACE, "Slaying");
    }

    @Override
    public void handleGeneration(@NotNull Player player, @NotNull SerializedMap map) {
        boolean enabled = map.getBoolean("Enabled");
        if (!enabled) {
            return;
        }

        double chance = map.getDouble("Chance");
        double random = Math.random();

        if (random <= chance / 100) {
            IPlayer cache = ISetsAPI.getCache(player);
            String selectedGen = cache.getFragmentDataManager().getArmorSetFragmentGen();
            IArmorSet armorSet = ISetsAPI.getArmorSetManager().getArmorSet(selectedGen);

            if (armorSet != null) {
                IFragment fragment = armorSet.getFragment();
                boolean isPhysical = map.getBoolean("Physical");
                String message = map.getString("Give_Message");
                int amount = getAmount(player, map);

                if (isPhysical) {
                    fragment.giveOrUpdateFragment(player, amount, false);
                } else {
                    cache.getFragmentDataManager().updateFragmentAmount(selectedGen, amount);
                }

                Common.tellNoPrefix(player, message.replace("{amount}", "" + amount));
            }
        }
    }

    private int getAmount(Player player, SerializedMap map) {
        int defaultAmount = map.getInteger("Amount_To_Give");
        boolean isDynamic = map.getBoolean("Dynamic_Amount", false);
        Integer i = GeneratorConfig.mobFragmentAmounts.get(MobKillListener.KillCache.get(player).name());

        return isDynamic && i != null ? i : defaultAmount;
    }
}
