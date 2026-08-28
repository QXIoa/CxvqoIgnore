package de.caydenno1.cxvqoignore.client;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import net.minecraft.network.chat.Component;

import de.caydenno1.cxvqoignore.CxvqoIgnore;

public class CxvqoIgnoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) ->
			dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("cxvqoignore")
				.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("enable")
					.executes(CxvqoIgnoreClient::enable))
				.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("disable")
					.executes(CxvqoIgnoreClient::disable)))
		);

		ClientReceiveMessageEvents.ALLOW_CHAT.register((message, playerChatMessage, sender, boundChatType, timeStamp) ->
			!CxvqoIgnore.shouldIgnore(message)
		);
		ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) ->
			!CxvqoIgnore.shouldIgnore(message)
		);
	}

	private static int enable(CommandContext<FabricClientCommandSource> ctx) {
		CxvqoIgnore.setEnabled(true);
		ctx.getSource().sendFeedback(Component.literal("CxvqoIgnore enabled"));
		return 1;
	}

	private static int disable(CommandContext<FabricClientCommandSource> ctx) {
		CxvqoIgnore.setEnabled(false);
		ctx.getSource().sendFeedback(Component.literal("CxvqoIgnore disabled"));
		return 1;
	}
}