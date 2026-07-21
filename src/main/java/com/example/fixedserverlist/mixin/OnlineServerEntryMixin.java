package com.example.fixedserverlist.mixin;

import com.example.fixedserverlist.FixedServerConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerSelectionList.OnlineServerEntry.class)
public abstract class OnlineServerEntryMixin {

    @Shadow public abstract ServerData getServer();

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"
            )
    )
    private int drawStringWithColor(GuiGraphics graphics, Font font, Component text, int x, int y, int color, boolean shadow) {
        ServerData data = getServer();
        if (data != null && ((ServerDataMixin) data).isFixed()) {
            color = FixedServerConfig.getFixedColor(); // 使用配置颜色
        }
        return graphics.drawString(font, text, x, y, color, shadow);
    }
}
