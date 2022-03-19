package me.lukedluz.SpecterBans;

import me.lukedluz.SpecterBans.Comandos.PunirC;
import me.lukedluz.SpecterBans.Eventos.PunirE;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public void onEnable() {
            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new PunirE(), this);
            getCommand("punir").setExecutor(new PunirC());
            FileConfiguration config = getConfig();
            System.out.print(punComandos("Flood").get(Integer.valueOf(2)));
            if (!(new File(getDataFolder(), "config.yml")).exists())
                saveDefaultConfig();

            Bukkit.getConsoleSender().sendMessage("§b[SpecterPlugins] §fO plugin '§aSpecterBans§f' está ativo e funcional.");
            Bukkit.getConsoleSender().sendMessage("§b##############################################");
            Bukkit.getConsoleSender().sendMessage("§b#  §fPlugin idealizado por: §bLucas L.           #");
            Bukkit.getConsoleSender().sendMessage("§b#  §fPlugin desenvolvido por: §bLucas L.         #");
            Bukkit.getConsoleSender().sendMessage("§b#  §fPlugin publicado por: §bSpecterPlugins      #");
            Bukkit.getConsoleSender().sendMessage("§b#  §fAjudantes: §bNinguém                        #");
            Bukkit.getConsoleSender().sendMessage("§b##############################################");

        }

        public void onDisable(){
            Bukkit.getConsoleSender().sendMessage("§b[SpecterPlugins] §fO plugin '§cSpecterBans§f' foi desligado com êxito.");
        }

        public String msgProva = getConfig().getString("Mensagens.SemProva");

        public ArrayList<String> punicoes() {
            ArrayList<String> nomes = new ArrayList<>();
            for (String key : getConfig().getConfigurationSection("Punicoes").getKeys(false))
                nomes.add(key);
            return nomes;
        }


        public boolean SpecterProva(String nome) {
            return getConfig().getBoolean("Punicoes." + nome + ".Prova");
        }

        public int SpecterComandos(String nome) {
            int x = 0;
            for (String key : getConfig().getConfigurationSection("Punicoes." + nome + ".Comandos").getKeys(false))
                x++;
            return x;
        }

        public String msgSintaxe() {
            return getConfig().getString("Mensagens.ComandoErrado").replace("&", "§");
        }

        public String msgInvalido(String vitima) {
            return getConfig().getString("Mensagens.JogadorInvalido").replace("&", "§").replace("@user", vitima);
        }

        public HashMap<Integer, String> punComandos(String nome) {
            HashMap<Integer, String> comandos = new HashMap<>();
            for (String key : getConfig().getConfigurationSection("Punicoes." + nome + ".Comandos").getKeys(false)) {
                String comand = (String)getConfig().get("Punicoes." + nome + ".Comandos." + key);
                comandos.put(Integer.valueOf(Integer.parseInt(key)), comand);
            }
            return comandos;
        }
}