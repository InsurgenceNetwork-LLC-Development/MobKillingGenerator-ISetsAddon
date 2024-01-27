package org.insurgencedev.mobkillinggenerator;

import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.api.addon.ISetsAddon;
import org.insurgencedev.insurgencesets.api.addon.InsurgenceSetsAddon;
import org.insurgencedev.mobkillinggenerator.listeners.MobKillListener;

@ISetsAddon(
        name = "MobKillingGeneratorAddon", version = "1.0.0", author = "Insurgence Dev", description = "Earn fragments from killing mobs"
)
public class MobKillingGeneratorAddon extends InsurgenceSetsAddon {

    private static GeneratorConfig config;

    @Override
    public void onAddonStart() {
        config = new GeneratorConfig();
    }

    @Override
    public void onAddonReloadablesStart() {
        config.reload();
        registerEvent(new MobKillListener());
        ISetsAPI.getFragmentGeneratorManager().registerFragmentGenerator(new MobKillingGenerator());
    }
}
