package com.example.fixedserverlist;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class FixedServerConfig {

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final FixedServerConfig INSTANCE;

    public final ForgeConfigSpec.ConfigValue<String> fixedIP;
    public final ForgeConfigSpec.ConfigValue<String> fixedName;
    public final ForgeConfigSpec.ConfigValue<Integer> color;

    static {
        Pair<FixedServerConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder()
                .configure(FixedServerConfig::new);
        CLIENT_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    private FixedServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Fixed Server");
        fixedIP = builder
                .comment("IP address of the fixed server (e.g., localhost:25565)")
                .define("ip", "127.0.0.1:25565");
        fixedName = builder
                .comment("Display name of the fixed server")
                .define("name", "My Fixed Server");
        color = builder
                .comment("Text color as ARGB integer (e.g., 0xFFFF5555 for light red)")
                .define("color", 0xFF5555);
        builder.pop();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    public static int getFixedColor() {
        return INSTANCE.color.get();
    }
}