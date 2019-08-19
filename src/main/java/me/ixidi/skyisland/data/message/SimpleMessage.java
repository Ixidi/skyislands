package me.ixidi.skyisland.data.message;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class SimpleMessage implements Message {

    private String message;
    private MessageParser parser;

    public SimpleMessage(String message, MessageParser parser) {
        this.message = message;
        this.parser = parser;
    }

    @Override
    public void send(CommandSender sender, Map<String, Object> variables) {
        sender.spigot().sendMessage(get(variables));
    }

    @Override
    public void send(CommandSender sender) {
        sender.spigot().sendMessage(get());
    }

    @Override
    public BaseComponent get() {
        return parser.parse(message);
    }

    @Override
    public BaseComponent get(Map<String, Object> variables) {
        return parser.parse(message, variables);
    }

    @Override
    public String getRaw() {
        return message;
    }

    @Override
    public void sendToAll() {
        BaseComponent component = get();
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(component));
    }

    @Override
    public void sendToAll(Map<String, Object> variables) {
        BaseComponent component = get(variables);
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(component));
    }

    @Override
    public void sendToAllPermitted(String permission) {
        BaseComponent component = get();
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> player.spigot().sendMessage(component));
    }

    @Override
    public void sendToAllPermitted(String permission, Map<String, Object> variables) {
        BaseComponent component = get(variables);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> player.spigot().sendMessage(component));
    }

    @Override
    public void sendToAllNoPermitted(String permission) {
        BaseComponent component = get();
        Bukkit.getOnlinePlayers().stream().filter(player -> !player.hasPermission(permission)).forEach(player -> player.spigot().sendMessage(component));
    }

    @Override
    public void sendToAllNoPermitted(String permission, Map<String, Object> variables) {
        BaseComponent component = get(variables);
        Bukkit.getOnlinePlayers().stream().filter(player -> !player.hasPermission(permission)).forEach(player -> player.spigot().sendMessage(component));
    }
}
