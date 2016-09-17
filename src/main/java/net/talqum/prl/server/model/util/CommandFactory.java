package net.talqum.prl.server.model.util;

import net.talqum.prl.server.model.Command;
import net.talqum.prl.server.model.enums.CommandAction;
import net.talqum.prl.server.model.enums.CommandDirection;
import net.talqum.prl.server.model.enums.CommandType;

public class CommandFactory {

    public static Command startUpCommand() {
        return new Command(CommandType.cmd, CommandDirection.up, CommandAction.start);
    }

    public static Command stopUpCommand() {
        return new Command(CommandType.cmd, CommandDirection.up, CommandAction.stop);
    }

    public static Command startCWCommand() {
        return new Command(CommandType.cmd, CommandDirection.cw, CommandAction.start);
    }

    public static Command stopCWCommand() {
        return new Command(CommandType.cmd, CommandDirection.cw, CommandAction.stop);
    }

    public static Command startCCWCommand() {
        return new Command(CommandType.cmd, CommandDirection.ccw, CommandAction.start);
    }

    public static Command stopCCWCommand() {
        return new Command(CommandType.cmd, CommandDirection.ccw, CommandAction.stop);
    }

    public static Command startDownCommand() {
        return new Command(CommandType.cmd, CommandDirection.dow, CommandAction.start);
    }

    public static Command stopDownCommand() {
        return new Command(CommandType.cmd, CommandDirection.dow, CommandAction.stop);
    }


    public static Command resetCommand() {
        return new Command(CommandType.reset, null, null);
    }

    public static Command testCommand() {
        return new Command(CommandType.test, null, null);
    }
}

