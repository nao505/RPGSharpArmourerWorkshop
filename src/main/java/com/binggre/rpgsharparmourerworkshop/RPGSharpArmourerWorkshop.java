package com.binggre.rpgsharparmourerworkshop;

import com.binggre.rpgsharparmourerworkshop.commands.ArmourerWorkShopCommand;
import com.binggre.rpgsharparmourerworkshop.io.VariablesReader;
import com.binggre.rpgsharparmourerworkshop.listeners.RPGItemUpdateListener;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGSharpArmourerWorkshop extends JavaPlugin {

    private static RPGSharpArmourerWorkshop instance;

    public static RPGSharpArmourerWorkshop getInstance() {
        return instance;
    }

    public String getPath() {
        return this.getDataFolder().getPath();
    }

    @Override
    public void onEnable() {
        instance = this;
        if (!RPGSharpAPI.isModBukkit()) {
            System.out.println("모드 서버 (CatBukkit) 에서만 적용됩니다.");
            return;
        }
        saveDefaultConfig();
        VariablesReader.read();
        Bukkit.getPluginManager().registerEvents(new RPGItemUpdateListener(), this);
        getCommand("워크샵스킨").setExecutor(new ArmourerWorkShopCommand());
    }

    @Override
    public void onDisable() {
    }
}