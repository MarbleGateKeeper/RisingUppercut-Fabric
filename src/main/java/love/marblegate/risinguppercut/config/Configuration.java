package love.marblegate.risinguppercut.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

//FIXME Strange Behavior of Mod Menu Lang Problem
@Config(name = "rising_uppercut")
public class Configuration implements ConfigData {

    @ConfigEntry.Category("rising_uppercut")
    @ConfigEntry.Gui.TransitiveObject
    public RisingUppercutConfig risingUppercutConfig = new RisingUppercutConfig();

    @ConfigEntry.Category("rocket_punch")
    @ConfigEntry.Gui.TransitiveObject
    public RocketPunchConfig rocketPunchConfig = new RocketPunchConfig();

    @ConfigEntry.Category("safe_landing")
    @ConfigEntry.Gui.TransitiveObject
    public SafeLandingConfig safeLandingConfig = new SafeLandingConfig();

    static class RisingUppercutConfig{
        public double damage = 8;
        public int cooldown = 60;
        public int uprisingTime = 8;
        public int floatingTime = 4;
        public double risingSpeedIndex = 0.1;
    }

    static class RocketPunchConfig{
        public double damage = 0.5;
        public int cooldown = 120;
        public int maxChargeTime = 20;
        public double movementSpeedIndex = 2;
        public double knockbackSpeedIndex =2;
    }

    static class SafeLandingConfig{
        public boolean doNotRequireEnchantment = false;
        public boolean disposableEffect = false;
        public int defaultDuration = 100;
    }
}
