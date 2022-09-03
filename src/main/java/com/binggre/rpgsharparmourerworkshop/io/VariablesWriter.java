package com.binggre.rpgsharparmourerworkshop.io;

import com.binggre.rpgsharparmourerworkshop.objects.Variables;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VariablesWriter extends VariablesStream {

    public static void write(String dataCode) {
        try {
            File file = new File(FILE_PATH);
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            yml.set(dataCode, Variables.getInstance().NBT_BASE.get(dataCode).toString());
            yml.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAll() {
        try {
            File file = new File(FILE_PATH);
            file.delete();
            file.createNewFile();
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            Variables variables = Variables.getInstance();

            for (final String DATA_CODE : new ArrayList<>(variables.NBT_BASE.keySet())) {
                yml.set(DATA_CODE, variables.NBT_BASE.get(DATA_CODE));
            }
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}