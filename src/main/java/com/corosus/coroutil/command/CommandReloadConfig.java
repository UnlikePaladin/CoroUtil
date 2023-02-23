package com.corosus.coroutil.command;

import com.corosus.coroutil.util.CULog;
import com.corosus.modconfig.ConfigMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

import static net.minecraft.commands.Commands.literal;

public class CommandReloadConfig {
	public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {

		dispatcher.register(
				Commands.literal(getCommandName())
						.then(literal("config")
								.then(literal("reload")
										.then(Commands.literal("common").executes(c -> {
											CULog.log("reloading all mods common configurations from disk");
											ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, FabricLoader.getInstance().getConfigDir());
											c.getSource().sendSuccess(new TextComponent("Reloading all common configs from disk"), true);
											return Command.SINGLE_SUCCESS;
										}))
								)
								.then(literal("save")
										.then(Commands.literal("common").executes(c -> {
											CULog.log("saving all coro mods runtime configs to disk");
											ConfigMod.forceSaveAllFilesFromRuntimeSettings();
											c.getSource().sendSuccess(new TextComponent("Saving all common coro configs to disk"), true);
											return Command.SINGLE_SUCCESS;
										}))
								)
						)
		);
	}

	public static String getCommandName() {
		return "coro";
	}
}
