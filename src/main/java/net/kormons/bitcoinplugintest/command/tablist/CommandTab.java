package net.kormons.bitcoinplugintest.command.tablist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements TabCompleter {

    List<String> arguments = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
       if(arguments.isEmpty()){
           arguments.add("구매");
           arguments.add("판매");
           arguments.add("정보");

       }
       List<String> result = new ArrayList<String>();

       if(args.length == 1){
           for (String a: arguments){
               result.add(a);
           }
        return result;
       }
        return arguments;
    }
}
