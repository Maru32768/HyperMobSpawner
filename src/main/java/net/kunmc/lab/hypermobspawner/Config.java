package net.kunmc.lab.hypermobspawner;

import net.kunmc.lab.configlib.BaseConfig;
import net.kunmc.lab.configlib.value.LocationValue;
import net.kunmc.lab.configlib.value.map.Enum2IntegerMapValue;
import net.kunmc.lab.configlib.value.tuple.Integer2IntegerPairValue;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Config extends BaseConfig {
    public final Enum2IntegerMapValue<EntityType> entityType2SpawnAmountMap = new Enum2IntegerMapValue<>();
    public final Enum2IntegerMapValue<EntityType> entityType2SpawnFrequencyMap = new Enum2IntegerMapValue<>();
    public final LocationValue centerOfSpawn = new LocationValue();
    public final Integer2IntegerPairValue spawnMinRangeAndMaxRange = new Integer2IntegerPairValue(32, 64)
            .leftMin(0)
            .rightMin(1)
            .setValidator(v -> v.getLeft() < v.getRight());

    public Config(Plugin plugin) {
        super(plugin);

        Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                .filter(x -> x != EntityType.ENDER_DRAGON)
                .forEach(x -> {
                    entityType2SpawnAmountMap.put(x, 0);
                    entityType2SpawnFrequencyMap.put(x, 1200);
                });
    }
}
