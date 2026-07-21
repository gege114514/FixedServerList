package com.example.fixedserverlist.mixin;

import net.minecraft.client.server.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerData.class)
public class ServerDataMixin {
    @Unique
    private boolean fixed = false;

    @Unique
    public boolean isFixed() {
        return fixed;
    }

    @Unique
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}