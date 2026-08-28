package com.caydenno1.cxvqoignore;

import java.util.regex.Pattern;

import net.fabricmc.api.ModInitializer;

import net.minecraft.network.chat.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CxvqoIgnore implements ModInitializer {
	public static final String MOD_ID = "cxvqoignore";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Pattern IGNORE_PATTERN = Pattern.compile(
		"(?:.*cxvqo.*)|(?:.*denisapain.*)",
		Pattern.CASE_INSENSITIVE
	);

	private static boolean enabled = true;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean value) {
		enabled = value;
	}

	public static boolean shouldIgnore(Component message) {
		return enabled && IGNORE_PATTERN.matcher(message.getString()).matches();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("CxvqoIgnore initialized and enabled");
	}
}