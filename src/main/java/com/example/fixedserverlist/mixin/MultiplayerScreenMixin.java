package com.example.fixedserverlist.mixin;

import net.minecraft.client.gui.screens.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin {

    @Shadow public abstract ServerSelectionList getServersList();

    @Shadow private List<ServerData> servers;

    @Inject(method = "edit", at = @At("HEAD"), cancellable = true)
    private void onEdit(CallbackInfo ci) {
        ServerSelectionList list = getServersList();
        ServerSelectionList.Entry entry = list.getSelected();
        if (entry != null) {
            try {
                ServerData data = ((AccessorEntry) entry).getServerData();
                if (data != null && ((ServerDataMixin) data).isFixed()) {
                    ci.cancel();
                }
            } catch (Exception ignored) {}
        }
    }

    @Inject(method = "remove", at = @At("HEAD"), cancellable = true)
    private void onRemove(CallbackInfo ci) {
        ServerSelectionList list = getServersList();
        ServerSelectionList.Entry entry = list.getSelected();
        if (entry != null) {
            try {
                ServerData data = ((AccessorEntry) entry).getServerData();
                if (data != null && ((ServerDataMixin) data).isFixed()) {
                    ci.cancel();
                }
            } catch (Exception ignored) {}
        }
    }

    @Inject(method = "onClose", at = @At("HEAD"))
    private void beforeSave(CallbackInfo ci) {
        servers.removeIf(sd -> ((ServerDataMixin) sd).isFixed());
    }
}
