package me.ixidi.skyisland.data.message;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Map;

public interface Message {

    void send(CommandSender sender, Map<String, Object> variables);

    void send(CommandSender sender);

    BaseComponent get();

    BaseComponent get(Map<String, Object> variables);

    String getRaw();

    void sendToAll();

    void sendToAll(Map<String, Object> variables);

    void sendToAllPermitted(String permission);

    void sendToAllPermitted(String permission, Map<String, Object> variables);

    void sendToAllNoPermitted(String permission);

    void sendToAllNoPermitted(String permission, Map<String, Object> variables);

}
