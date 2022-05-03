package net.kunmc.lab.hypermobspawner;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.CommandContext;

public class StartCommand extends Command {
    public StartCommand() {
        super("start");
    }

    @Override
    protected void execute(CommandContext ctx) {
        if (HyperMobSpawner.instance.isEnabled) {
            ctx.sendFailure("すでに有効です.");
            return;
        }

        if (HyperMobSpawner.config.centerOfSpawn.value() == null) {
            ctx.sendFailure("中心地が設定されていません.");
            return;
        }

        HyperMobSpawner.instance.isEnabled = true;
        ctx.sendSuccess("有効化しました.");
    }
}
