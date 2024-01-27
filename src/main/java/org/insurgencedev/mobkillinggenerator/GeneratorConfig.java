package org.insurgencedev.mobkillinggenerator;

import org.insurgencedev.insurgencesets.api.addon.AddonConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorConfig extends AddonConfig {

    public static final Map<String, Integer> mobFragmentAmounts = new HashMap<>();

    public GeneratorConfig() {
        loadAddonConfig("config.yml", "config.yml");
    }

    @Override
    protected void onLoad() {
        mobFragmentAmounts.clear();
        List<String> pair = new ArrayList<>(getStringList("Custom_Amounts"));
        for (String str : pair) {
            String[] arr = str.split(":");
            String name = arr[0];
            Integer amount = Integer.parseInt(arr[1]);
            mobFragmentAmounts.put(name.toLowerCase(), amount);
        }
    }
}
