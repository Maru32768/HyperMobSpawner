package net.kunmc.lab.hypermobspawner;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.configlib.ConfigCommand;

public class MainCommand extends Command {
    public MainCommand(ConfigCommand configCommand) {
        super("hms");

        addChildren(new StartCommand(), new StopCommand(), configCommand);
    }
}
