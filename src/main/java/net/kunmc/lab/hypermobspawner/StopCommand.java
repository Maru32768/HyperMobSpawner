package net.kunmc.lab.hypermobspawner;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.CommandContext;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop");
    }

    @Override
    protected void execute(CommandContext ctx) {
        if (!HyperMobSpawner.instance.isEnabled) {
            ctx.sendFailure("すでに無効です.");
            return;
        }

        HyperMobSpawner.instance.isEnabled = false;
        ctx.sendSuccess("無効化しました.");
    }
}
