package net.kunmc.lab.hypermobspawner;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.configlib.ConfigCommand;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Random;

public final class HyperMobSpawner extends JavaPlugin implements Listener {
    public static HyperMobSpawner instance;
    public static Config config;
    public boolean isEnabled = false;
    private int progressTick = 0;
    private final Random random = new Random();

    @Override
    public void onEnable() {
        instance = this;
        config = new Config(this);

        ConfigCommand configCommand = new ConfigCommandBuilder(config).build();
        CommandLib.register(this, new MainCommand(configCommand));

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onTick(ServerTickEndEvent e) {
        if (!isEnabled || config.centerOfSpawn.value() == null) {
            progressTick = 0;
            return;
        }
        progressTick++;

        config.entityType2SpawnFrequencyMap.entrySet().stream()
                .filter(x -> progressTick % x.getValue() == 0)
                .map(Map.Entry::getKey)
                .forEach(this::spawn);
    }

    private void spawn(EntityType type) {
        int minRange = config.spawnMinRangeAndMaxRange.getLeft();
        int maxRange = config.spawnMinRangeAndMaxRange.getRight();
        int amount = config.entityType2SpawnAmountMap.get(type);
        Location center = config.centerOfSpawn.value();

        for (int i = 0; i < amount; i++) {
            double rad = Math.toRadians(random.nextInt(360));
            double offsetX = Math.sin(rad) * (random.nextDouble() * (maxRange - minRange) + minRange);
            double offsetZ = Math.cos(rad) * (random.nextDouble() * (maxRange - minRange) + minRange);

            Location loc = center.clone().add(offsetX, 0, offsetZ);
            loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);

            loc.getWorld().spawnEntity(loc, type);
        }
    }
}