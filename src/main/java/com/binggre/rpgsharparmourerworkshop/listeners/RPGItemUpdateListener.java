package com.binggre.rpgsharparmourerworkshop.listeners;

import com.binggre.rpgsharparmourerworkshop.objects.Variables;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.rpgitem.RPGItemUpdateEvent;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class RPGItemUpdateListener implements Listener {

    @EventHandler
    public void onRPGItemUpdate(RPGItemUpdateEvent event) {
        String dataCode = event.getRPGItem().getDataCode();
        Variables variables = Variables.getInstance();
        if (!variables.NBT_BASE.containsKey(dataCode)) {
            return;
        }
        ItemStack itemStack = event.getItemStack();
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = nmsItemStack.getTag();
        NBTTagCompound identifier = new NBTTagCompound();
        NBTTagCompound skinType = new NBTTagCompound();
        NBTTagCompound dyeData = new NBTTagCompound();
//        skinType.setString("skinType", "armourers:sword");
//        skinType.setInt("globalId", 0);
//        skinType.setInt("localId", 298891667);
//        identifier.set("identifier", skinType);
//        identifier.set("dyeData", dyeData);
        nmsItemStack.setTag(tag);
        skinType.setString(Variables.SKIN_TYPE, getSkinType(dataCode));
        skinType.setInt(Variables.GLOBAL_ID, getGlobalId(dataCode));
        skinType.setInt(Variables.LOCAL_ID, getLocalID(dataCode));
        identifier.set(Variables.IDENTIFIER, skinType);
        identifier.set(Variables.DYE_DATA, dyeData);
        tag.set(Variables.NBT_KEY, identifier);
        event.setItemStack(CraftItemStack.asBukkitCopy(nmsItemStack));
    }

    private String getSkinType(String dataCode) {
        Variables variables = Variables.getInstance();
        String base = variables.NBT_BASE.get(dataCode);
        String[] arr = base.split(Variables.SKIN_TYPE + ":");
        String[] cutArr = arr[1].split(",");
        return cutArr[0].replace("\"", "");
    }

    private int getGlobalId(String dataCode) {
        Variables variables = Variables.getInstance();
        String base = variables.NBT_BASE.get(dataCode);
        String[] arr = base.split(Variables.GLOBAL_ID + ":");
        String[] cutArr = arr[1].split(",");
        return Integer.parseInt(cutArr[0].replace("\"", ""));
    }

    private int getLocalID(String dataCode) {
        Variables variables = Variables.getInstance();
        String base = variables.NBT_BASE.get(dataCode);
        String[] arr = base.split(Variables.LOCAL_ID + ":");
        String[] cutArr = arr[1].split(",");
        return Integer.parseInt(cutArr[0].replace("\"", "").replace("}", ""));
    }
}