package de.caydenno1.cxvqoignore.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import net.minecraft.network.chat.Component;

import de.caydenno1.cxvqoignore.CxvqoIgnore;
import de.caydenno1.cxvqoignore.CxvqoIgnoreRegexes;

public class CxvqoIgnoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) ->
			dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("cxvqoignore")
				.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("enable")
					.executes(CxvqoIgnoreClient::enable))
				.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("disable")
					.executes(CxvqoIgnoreClient::disable))
				.then(ClientCommands.argument("regex", StringArgumentType.greedyString())
					.executes(CxvqoIgnoreClient::addRegex))
				.then(ClientCommands.argument("index", IntegerArgumentType.integer(0))
					.suggests(CxvqoIgnoreClient::suggestIndexes)
					.executes(CxvqoIgnoreClient::removeRegex))
				.then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("listregex")
					.executes(CxvqoIgnoreClient::listRegex)))
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

	private static int addRegex(CommandContext<FabricClientCommandSource> ctx) {
		String regex = StringArgumentType.getString(ctx, "regex");
		try {
			CxvqoIgnoreRegexes.add(regex);
		} catch (PatternSyntaxException e) {
			ctx.getSource().sendError(Component.literal("Invalid regex: " + e.getDescription()));
			return 0;
		}
		ctx.getSource().sendFeedback(Component.literal("Added regex: " + regex));
		return 1;
	}

	private static int removeRegex(CommandContext<FabricClientCommandSource> ctx) {
		int index = IntegerArgumentType.getInteger(ctx, "index");
		if (CxvqoIgnoreRegexes.remove(index)) {
			ctx.getSource().sendFeedback(Component.literal("Removed regex at index " + index));
			return 1;
		}
		ctx.getSource().sendError(Component.literal("No regex at index " + index));
		return 0;
	}

	private static int listRegex(CommandContext<FabricClientCommandSource> ctx) {
		List<Pattern> patterns = CxvqoIgnoreRegexes.getPatterns();
		if (patterns.isEmpty()) {
			ctx.getSource().sendFeedback(Component.literal("No regex patterns configured"));
			return 1;
		}
		ctx.getSource().sendFeedback(Component.literal("Configured regex patterns (" + patterns.size() + "):"));
		for (int i = 0; i < patterns.size(); i++) {
			ctx.getSource().sendFeedback(Component.literal("[" + i + "] " + patterns.get(i).pattern()));
		}
		return 1;
	}

	private static CompletableFuture<Suggestions> suggestIndexes(CommandContext<FabricClientCommandSource> ctx, SuggestionsBuilder builder) {
		List<Pattern> patterns = CxvqoIgnoreRegexes.getPatterns();
		for (int i = 0; i < patterns.size(); i++) {
			builder.suggest(String.valueOf(i));
		}
		return builder.buildFuture();
	}
}