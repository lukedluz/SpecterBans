package me.lukedluz.SpecterBans.Eventos;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import me.lukedluz.SpecterBans.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.UUID;

public class PunirE implements Listener {

    Main plugin;

    HashMap<Player, Boolean> SpecterProva;

    HashMap<Player, String> SpecterProvaVit;

    HashMap<Player, String> SpecterPunicao;

    @EventHandler
    public void aoFalar(ChatMessageEvent e) {
        Player p = e.getSender();
        if (this.SpecterProva.containsKey(p) && (
                (Boolean) this.SpecterProva.get(p)).equals(Boolean.valueOf(true))) {
            e.setCancelled(true);
            punirProvas(p, this.SpecterProvaVit.get(p), this.SpecterPunicao.get(p), e.getMessage());
        }
    }

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().contains("estpunindo:")) {
            String vitima = e.getInventory().getName().replace("estpunindo:", "");
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(Material.WOOL)) {
                p.closeInventory();
            } else if (e.getCurrentItem().getType().equals(Material.REDSTONE)) {
                String punicao = e.getCurrentItem().getItemMeta().getDisplayName().replace("&", "§");
                p.closeInventory();
                if (this.plugin.SpecterProva(punicao)) {
                    p.sendMessage("\n\n\n" + this.plugin.getConfig().getString("Mensagens.SemProva").replace("&", "§"));
                    this.SpecterProva.put(p, Boolean.valueOf(true));
                    this.SpecterProvaVit.put(p, vitima);
                    this.SpecterPunicao.put(p, punicao);
                } else {
                    punir(p, vitima, punicao);
                }
            } else {
                return;
            }
        }

    }

    public void punir(Player staff, String vitima, String punicao) {
        Player vit = Bukkit.getPlayer(vitima);
        UUID uuid = vit.getUniqueId();
        if (this.plugin.getConfig().contains("FlatFile." + uuid + "." + punicao)) {
            int ordempunicao = this.plugin.SpecterComandos(punicao);
            int ordemocorrencia = this.plugin.getConfig().getInt("FlatFile." + uuid + "." + punicao + ".Ocorrencia");
            if (ordemocorrencia < ordempunicao) {
                ordemocorrencia++;
                this.plugin.getConfig().set("FlatFile." + uuid + "." + punicao + ".Ocorrencia", Integer.valueOf(ordemocorrencia));
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(ordemocorrencia))).replace("@user", vitima));
            } else if (ordemocorrencia == ordempunicao) {
                staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(ordemocorrencia))).replace("@user", vitima));
            }
        } else {
            this.plugin.getConfig().set("FlatFile." + uuid + "." + punicao + ".Ocorrencia", Integer.valueOf(1));
            staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(1))).replace("@user", vitima));
            this.plugin.saveConfig();
            this.plugin.reloadConfig();
        }
    }

    public void punirProvas(Player staff, String vitima, String punicao, String prova) {
        Player vit = Bukkit.getPlayer(vitima);
        UUID uuid = vit.getUniqueId();
        if (this.plugin.getConfig().contains("FlatFile." + uuid + "." + punicao)) {
            int ordempunicao = this.plugin.SpecterComandos(punicao);
            int ordemocorrencia = this.plugin.getConfig().getInt("FlatFile." + uuid + "." + punicao + ".Ocorrencia");
            if (ordemocorrencia < ordempunicao) {
                ordemocorrencia++;
                this.plugin.getConfig().set("FlatFile." + uuid + "." + punicao + ".Ocorrencia", Integer.valueOf(ordemocorrencia));
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(ordemocorrencia))).replace("@user", vitima).replace("@prova", prova));
            } else if (ordemocorrencia == ordempunicao) {
                staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(ordemocorrencia))).replace("@user", vitima).replace("@prova", prova));
            }
        } else {
            this.plugin.getConfig().set("FlatFile." + uuid + "." + punicao + ".Ocorrencia", Integer.valueOf(1));
            staff.performCommand(((String)this.plugin.punComandos(punicao).get(Integer.valueOf(1))).replace("@user", vitima).replace("@prova", prova));
            this.plugin.saveConfig();
            this.plugin.reloadConfig();
        }
        this.SpecterProva.remove(staff);
        this.SpecterPunicao.remove(staff);
        this.SpecterProvaVit.remove(staff);
    }

}
