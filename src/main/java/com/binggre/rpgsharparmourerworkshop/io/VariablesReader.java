package com.binggre.rpgsharparmourerworkshop.io;

import com.binggre.rpgsharparmourerworkshop.objects.Variables;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class VariablesReader extends VariablesStream {

    public static void read() {
        File file = new File(FILE_PATH);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Variables variables = Variables.getInstance();
        variables.NBT_BASE.clear();
        for (final String KEY : yml.getKeys(false)) {
            variables.NBT_BASE.put(KEY, yml.getString(KEY));
        }
    }
}