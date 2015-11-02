package me.drkmatr1984.wordbubbles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WordCommandExecutor implements org.bukkit.command.CommandExecutor
{	
	private WordClass plugin;
	
	public WordCommandExecutor(WordClass plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
	    String perm = plugin.wordconfig.PLPrefix + " §cYou don't have Permission";
	    try {
	      if (cmd.getName().equalsIgnoreCase("wordbubbles")) {
	        if ((args.length == 0) || (args.equals(null)))
	        {
	          if ((!sender.hasPermission("Wordbubbles.use")) || (!sender.hasPermission("Wordbubbles.admin"))) {
	            sender.sendMessage(perm);
	            return true;
	          }
	          sender.sendMessage(ChatColor.BLUE + "------  " + ChatColor.YELLOW + plugin.wordconfig.PLPrefix + " Help  " + ChatColor.BLUE + "------" + ChatColor.RESET);
	          sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles" + " " + ChatColor.RESET + "- Displays this Help");
	          sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles help" + " " + ChatColor.RESET + "- Also Displays this Help");
	          sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles toggle" + " " + ChatColor.RESET + "- Toggle your wordbubbles " + ChatColor.GREEN + "On" + ChatColor.RESET + "/" + ChatColor.RED + "Off" + ChatColor.RESET);
	          if(WordClass.cit && (sender.hasPermission("Wordbubbles.trait") || sender.hasPermission("Wordbubbles.admin"))){
	        	sender.sendMessage(ChatColor.DARK_AQUA + "-----------------" + ChatColor.RESET);
	        	sender.sendMessage(ChatColor.YELLOW + "/Trait wordbubbles " + ChatColor.RESET + "- Typing this while");
	            sender.sendMessage("looking at a Citizens2 NPC will allow");
	            sender.sendMessage("the NPC to speak in wordbubbles");
	          }
	          if (sender.hasPermission("Wordbubbles.admin")) {
	        	sender.sendMessage(ChatColor.YELLOW + "-----------------" + ChatColor.RESET);
	            sender.sendMessage("§c/" + "WordBubbles" + " version§r - Shows the Plugin Version Number");
	            sender.sendMessage("§c/" + "WordBubbles" + " reload§r - Reloads the Plugin Config");
	          }
	          sender.sendMessage(ChatColor.BLUE + "---------------------------------" + ChatColor.RESET);
	          return true;
	        }
	        
	        if (args.length > 0) {
	          String arg = args[0];
	          if (arg.equalsIgnoreCase("help")) {
	            if ((!sender.hasPermission("Wordbubbles.use")) || (!sender.hasPermission("Wordbubbles.admin"))) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            sender.sendMessage(ChatColor.BLUE + "------  " + ChatColor.YELLOW + plugin.wordconfig.PLPrefix + " Help  " + ChatColor.BLUE + "------" + ChatColor.RESET);
	            sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles" + " " + ChatColor.RESET + "- Displays this Help");
	            sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles help" + " " + ChatColor.RESET + "- Also Displays this Help");
	            sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles toggle" + " " + ChatColor.RESET + "- Toggle your wordbubbles " + ChatColor.GREEN + "On" + ChatColor.RESET + "/" + ChatColor.RED + "Off" + ChatColor.RESET);
	            if(WordClass.cit && (sender.hasPermission("Wordbubbles.trait") || sender.hasPermission("Wordbubbles.admin"))){
	          	sender.sendMessage(ChatColor.DARK_AQUA + "-----------------" + ChatColor.RESET);
	          	sender.sendMessage(ChatColor.YELLOW + "/Trait wordbubbles " + ChatColor.RESET + "- Typing this while");
	              sender.sendMessage("looking at a Citizens2 NPC will allow");
	              sender.sendMessage("the NPC to speak in wordbubbles");
	            }
	            if (sender.hasPermission("Wordbubbles.admin")) {
	          	sender.sendMessage(ChatColor.YELLOW + "-----------------" + ChatColor.RESET);
	              sender.sendMessage("§c/" + "WordBubbles" + " version§r - Shows the Plugin Version Number");
	              sender.sendMessage("§c/" + "WordBubbles" + " reload§r - Reloads the Plugin Config");
	            }
	            sender.sendMessage(ChatColor.BLUE + "---------------------------------" + ChatColor.RESET);
	            return true;
	          }
	          
	          if (arg.equalsIgnoreCase("reload")) {
	            if (!sender.hasPermission("Wordbubbles.admin")) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            plugin.wordconfig.initializeConfig();
	            sender.sendMessage(plugin.wordconfig.PLPrefix + " §aReloaded");
	            return true;
	          }
	          
	          if (arg.equalsIgnoreCase("version")) {
	            if (!sender.hasPermission("Wordbubbles.admin")) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            if (!(sender instanceof Player)) {
	              Bukkit.getServer().getConsoleSender().sendMessage(plugin.wordconfig.PLPrefix + WordClass.plugin.getDescription().getVersion());
	              return true;
	            }
	            sender.sendMessage(plugin.wordconfig.PLPrefix + ChatColor.LIGHT_PURPLE + "Version" + ChatColor.GRAY + ": " + ChatColor.RESET + WordClass.plugin.getDescription().getVersion());
	            return true;
	          }
	          

	          if (arg.equalsIgnoreCase("toggle")) {
	            if ((!sender.hasPermission("Wordbubbles.use")) && (!sender.hasPermission("Wordbubbles.admin"))) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            if (!(sender instanceof Player)) {
	              sender.sendMessage(plugin.wordconfig.PLPrefix + " Youre not a player! You can't toggle your WordBubbles!");
	              return true;
	            }
	            Player p = (Player)sender;
	            if (!plugin.wordconfig.disabledPlayers.contains(p.getUniqueId().toString())) {
	              p.sendMessage(plugin.wordconfig.PLPrefix + " " + "§fWordBubbles" + " §4Disabled");
	              plugin.wordconfig.disabledPlayers.add(p.getUniqueId().toString());
	            } else {
	              p.sendMessage(plugin.wordconfig.PLPrefix + " " + "§fWordBubbles" + " §2Enabled");
	              plugin.wordconfig.disabledPlayers.remove(p.getUniqueId().toString());
	            }
	            return true;
	          }
	        }
	      }
	    }
	    catch (Exception e) {
	      sender.sendMessage(ChatColor.DARK_RED + "An Error has Occured");
	    }
	    return false;
	  }
}
