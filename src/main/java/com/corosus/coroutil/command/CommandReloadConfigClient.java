package com.corosus.coroutil.command;

import com.corosus.coroutil.util.CULog;
import com.corosus.modconfig.ConfigMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

public class CommandReloadConfigClient {
	public static void register(final CommandDispatcher<FabricClientCommandSource> dispatcher) {
		LiteralArgumentBuilder<FabricClientCommandSource> root = LiteralArgumentBuilder.literal(getCommandName());

		LiteralArgumentBuilder<FabricClientCommandSource> config = LiteralArgumentBuilder.literal("config");

		LiteralArgumentBuilder<FabricClientCommandSource> reload = LiteralArgumentBuilder.literal("reload");

		LiteralArgumentBuilder<FabricClientCommandSource> reloadClient = LiteralArgumentBuilder.literal("client");

		reloadClient.executes(c -> {
					CULog.log("reloading all mods client configurations from disk");
					ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.CLIENT, FabricLoader.getInstance().getConfigDir());

					c.getSource().sendFeedback(new TextComponent("Reloading all client configs from disk"));

			return 1;
		});
		root.then(config.then(reload.then(reloadClient)));

		LiteralArgumentBuilder<FabricClientCommandSource> save = LiteralArgumentBuilder.literal("save");

		LiteralArgumentBuilder<FabricClientCommandSource> saveClient = LiteralArgumentBuilder.literal("client");
		saveClient.executes(context -> {
			CULog.log("saving all coro mods runtime configs to disk");
			/** dummy literal for autocomplete sake, see EventHandlerForge.clientChat for what actually "intercepts" this */
			ConfigMod.forceSaveAllFilesFromRuntimeSettings();
			context.getSource().sendFeedback(new TextComponent("Saving all client coro configs to disk"));
			return 1;
		});
		root.then(config.then(save.then(saveClient)));

		dispatcher.register(root);
	}

	public static String getCommandName() {
		return "coro";
	}
}
