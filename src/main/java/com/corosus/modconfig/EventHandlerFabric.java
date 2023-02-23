package com.corosus.modconfig;

import com.corosus.coroutil.command.CommandReloadConfig;
import com.corosus.coroutil.command.CommandReloadConfigClient;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.mixin.gametest.CommandManagerMixin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class EventHandlerFabric {

    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> CommandReloadConfig.register(dispatcher));
    }

    public void registerCommandsClient() {
        CommandReloadConfigClient.register(ClientCommandManager.DISPATCHER);
    }


}