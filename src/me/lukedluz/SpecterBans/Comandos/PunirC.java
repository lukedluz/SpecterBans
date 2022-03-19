package me.lukedluz.SpecterBans.Comandos;

import me.lukedluz.SpecterBans.Main;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class PunirC implements CommandExecutor {

    Main plugin;

    HashMap<Player, Boolean> SpecterProva;

    HashMap<Player, String> SpecterProvaVit;

    HashMap<Player, String> SpecterPunicao;

    public void Menu(Main instance) {
        this.SpecterProva = new HashMap<>();
        this.SpecterProvaVit = new HashMap<>();
        this.SpecterPunicao = new HashMap<>();
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (sender instanceof Player)
            if (p.hasPermission("specterbans.usar")) {
                if (cmd.getName().equalsIgnoreCase("punir"))
                    if (args.length == 1) {
                        String vitima = args[0];
                        if (Bukkit.getServer().getPlayer(vitima) != null) {
                            abrirMenu(p, vitima);
                        } else {
                            p.sendMessage(this.plugin.msgInvalido(vitima));
                        }
                    } else {
                        p.sendMessage(this.plugin.msgSintaxe());
                    }
            } else {
                p.sendMessage(this.plugin.getConfig().getString("Mensagens.SemPermissao").replace("&", "§"));
            }
        return false;
    }

    public void abrirMenu(Player l, String jogador) {
        Player p = Bukkit.getPlayer(jogador);
        Inventory menu = Bukkit.createInventory(null, this.plugin.getConfig().getInt("Menu.Tamanho")*9, this.plugin.getConfig().getString("Menu.Nome").replace("@user", p.getName()).replace("&", "§"));
                ArrayList<ItemStack> itens = new ArrayList<>();
        for (String nome : this.plugin.punicoes()) {
            if (l.hasPermission("specterbans." + this.plugin.getConfig().getBoolean("Punicoes." + nome + ".permissao"))) {
                ItemStack item = new ItemStack(Material.REDSTONE, 1);
                ItemMeta metaitem = item.getItemMeta();
                metaitem.setDisplayName("" + nome);
                        ArrayList<String> lore = new ArrayList<>();
                lore.add(this.plugin.getConfig().getString("Menu.Confirmar").replace("@user", p.getName()).replace("&", "§"));
                metaitem.setLore(lore);
                item.setItemMeta(metaitem);
                itens.add(item);
            }
        }
        int x = 1;
        int h = 1;
        for (ItemStack a : itens) {
            menu.setItem(x + 9 * h, a);
            x++;
            if (x == 7) {
                x = 1;
                h++;
            }
        }
        ItemStack fechar = new ItemStack(Material.WOOL, 1, DyeColor.LIME.getData());
        ItemMeta metafechar = fechar.getItemMeta();
        metafechar.setDisplayName("");
                ArrayList<String> lore1 = new ArrayList<>();
        lore1.add(this.plugin.getConfig().getString("Menu.Fechar").replace("@user", p.getName()).replace("&", "§"));
        metafechar.setLore(lore1);
        fechar.setItemMeta(metafechar);
        menu.setItem(this.plugin.getConfig().getInt("Menu.Slot"), fechar);
        l.openInventory(menu);
    }
}
