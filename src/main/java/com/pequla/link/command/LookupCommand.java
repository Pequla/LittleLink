package com.pequla.link.command;

import com.pequla.link.model.DataModel;
import com.pequla.link.service.DataService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LookupCommand implements CommandExecutor {

    public static final String COMMAND_PERMISSION = "littlelink.command.lookup";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("lookup") && sender.hasPermission(COMMAND_PERMISSION)) {
            // Responding to command in new thread
            new Thread(() -> {
                try {
                    if (args.length != 1)
                        throw new RuntimeException("Command only accepts one argument");

                    DataModel model = DataService.getInstance().getLookupData(args[0]);
                    sender.sendMessage("Following data was found:" + System.lineSeparator() +
                            "ID: " + ChatColor.AQUA + model.getId() + ChatColor.RESET + System.lineSeparator() +
                            "Name: " + ChatColor.AQUA + model.getName());
                } catch (RuntimeException e) {
                    sendError(sender, e.getMessage());
                } catch (Exception e) {
                    sendError(sender, "There was a communication error");
                }
            }).start();
            return true;
        }
        return false;
    }

    private void sendError(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.YELLOW + msg);
    }
}
