package com.example.fixedserverlist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.ServerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod("fixedserverlist")
public class FixedServerListMod {

    public FixedServerListMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        FixedServerConfig.init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        // 客户端专用初始化
    }

    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof MultiplayerScreen mpScreen) {
            addFixedServer(mpScreen);
        }
    }

    private void addFixedServer(MultiplayerScreen screen) {
        List<ServerData> servers = screen.getServers();
        // 检查是否已存在固定条目
        for (ServerData sd : servers) {
            if (((ServerDataMixin) sd).isFixed()) {
                return; // 已存在，不再添加
            }
        }
        // 创建新的固定服务器数据
        ServerData fixed = new ServerData(
                FixedServerConfig.fixedName.get(),
                FixedServerConfig.fixedIP.get(),
                false
        );
        ((ServerDataMixin) fixed).setFixed(true);
        servers.add(0, fixed); // 置顶
        screen.getServersList().refresh();
    }
}