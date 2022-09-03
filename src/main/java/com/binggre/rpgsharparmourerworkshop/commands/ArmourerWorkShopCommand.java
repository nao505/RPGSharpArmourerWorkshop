package com.binggre.rpgsharparmourerworkshop.commands;

import com.binggre.rpgsharparmourerworkshop.io.VariablesWriter;
import com.binggre.rpgsharparmourerworkshop.objects.Variables;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGItemAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ArmourerWorkShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.isOp() || (!(sender instanceof Player))) {
            return false;
        }
        final String COMMAND_NAME = "/" + cmd.getName();
        if (args.length == 0) {
            sender.sendMessage(COMMAND_NAME + " 등록 <데이터코드>");
            sender.sendMessage(COMMAND_NAME + " 제거 <데이터코드>");
            return false;
        }
        RPGItemAPI rpgItemAPI = RPGSharpAPI.getRPGItemAPI();
        Variables variables = Variables.getInstance();
        final String DATA_CODE;
        final String ARG = args[0];
        switch (ARG) {
            case "등록": {
                if (args.length < 2) {
                    sender.sendMessage(COMMAND_NAME + " 등록 <데이터코드>");
                    sender.sendMessage("손에 아머러즈 워크샵 스킨을 들고 입력하세요.");
                    break;
                }
                ItemStack handItem = ((Player) sender).getInventory().getItemInMainHand();
                DATA_CODE = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                if (rpgItemAPI.getRPGItem(DATA_CODE) == null) {
                    sender.sendMessage("존재하지 않는 RPGItem 입니다.");
                    break;
                }
                if (handItem == null || handItem.getType() == Material.AIR) {
                    sender.sendMessage("워크샵 스킨을 들어주세요.");
                    break;
                }
                net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(handItem);
                NBTTagCompound tag = nmsItemStack.getTag();
                assert (tag != null);
                NBTBase nbtBase = tag.get(Variables.NBT_KEY);
                if (nbtBase == null) {
                    sender.sendMessage("워크샵 스킨이 아닙니다.");
                    break;
                }
                variables.NBT_BASE.put(DATA_CODE, nbtBase.toString());
                VariablesWriter.write(DATA_CODE);
                sender.sendMessage(DATA_CODE + " 아이템에 스킨을 적용했습니다.");
                sender.sendMessage("RPGItem 업데이트 스케줄러에의해 스킨이 적용되니 잠시 기다려주세요");
                break;
            }
            case "제거": {
                if (args.length < 1) {
                    sender.sendMessage(COMMAND_NAME + " 제거 <데이터코드>");
                    break;
                }
                DATA_CODE = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                if (!variables.NBT_BASE.containsKey(DATA_CODE)) {
                    sender.sendMessage("해당 RPGItem에 적용된 스킨이 없습니다.");
                    break;
                }
                variables.NBT_BASE.remove(DATA_CODE);
                VariablesWriter.writeAll();
                sender.sendMessage(DATA_CODE + " RPGItem 스킨을 제거했습니다.");
                break;
            }
        }

        return false;
    }

}