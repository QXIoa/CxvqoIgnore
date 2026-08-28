package de.caydenno1.cxvqoignore;

import net.fabricmc.api.ModInitializer;

import net.minecraft.network.chat.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CxvqoIgnore implements ModInitializer {
	public static final String MOD_ID = "cxvqoignore";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static boolean enabled = true;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean value) {
		enabled = value;
	}

	public static boolean shouldIgnore(Component message) {
		return enabled && CxvqoIgnoreRegexes.shouldIgnore(message.getString());
	}

	@Override
	public void onInitialize() {
		LOGGER.info("CxvqoIgnore initialized and enabled");
	}
}