package love.marblegate.risinguppercut;

import love.marblegate.risinguppercut.config.Configuration;
import love.marblegate.risinguppercut.event.Event;
import love.marblegate.risinguppercut.registry.EnchantmentRegistry;
import love.marblegate.risinguppercut.registry.ItemRegistry;
import love.marblegate.risinguppercut.registry.StatusEffectRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RisingUppercut implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("rising_uppercut");
	public static final String MODID = "rising_uppercut";
	public static Configuration CONFIG;

	@Override
	public void onInitialize() {
		AutoConfig.register(Configuration.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(Configuration.class).getConfig();

		ItemRegistry.ini();
		EnchantmentRegistry.ini();
		StatusEffectRegistry.ini();

		Event.ini();
	}
}
