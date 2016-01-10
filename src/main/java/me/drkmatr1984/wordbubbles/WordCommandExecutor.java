package me.drkmatr1984.wordbubbles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class WordCommandExecutor implements org.bukkit.command.CommandExecutor
{	
	WordMain word = WordMain.getWordClass();
	String perm = this.word.wordlang.PLPrefix + " " + this.word.wordlang.NoPerms;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {    
	    try {
	      if (cmd.getName().equalsIgnoreCase("wordbubbles")) {
	        if ((args.length == 0) || (args.equals(null)))
	        {
	          if ((!sender.hasPermission("Wordbubbles.use")) || (!sender.hasPermission("Wordbubbles.admin"))) {
	            sender.sendMessage(perm);
	            return true;
	          }
	          displayHelp(sender);
	          return true;
	        }
	        
	        if (args.length > 0) {
	          String arg = args[0];
	          if (arg.equalsIgnoreCase("help")) {
	            if ((!sender.hasPermission("Wordbubbles.use")) || (!sender.hasPermission("Wordbubbles.admin"))) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            displayHelp(sender);
	            return true;
	          }
	          
	          if (arg.equalsIgnoreCase("reload")) {
	            if (!sender.hasPermission("Wordbubbles.admin")) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            this.word.getPluginLoader().disablePlugin(this.word);
	            this.word.getPluginLoader().enablePlugin(this.word);
	            sender.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.Reloaded);
	            return true;
	          }
	          
	          if (arg.equalsIgnoreCase("version")) {
	            if (!sender.hasPermission("Wordbubbles.admin")) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            if (!(sender instanceof Player)) {
	              Bukkit.getServer().getConsoleSender().sendMessage(this.word.wordlang.PLPrefix + WordMain.plugin.getDescription().getVersion());
	              return true;
	            }
	            sender.sendMessage(this.word.wordlang.PLPrefix + ChatColor.LIGHT_PURPLE + "Version" + ChatColor.GRAY + ": " + ChatColor.RESET + WordMain.plugin.getDescription().getVersion());
	            return true;
	          }
	          

	          if (arg.equalsIgnoreCase("toggle")) {
	            if ((!sender.hasPermission("Wordbubbles.toggle")) && (!sender.hasPermission("Wordbubbles.admin"))) {
	              sender.sendMessage(perm);
	              return true;
	            }
	            if (!(sender instanceof Player)) {
	              sender.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.NotaPlayerToggle);
	              return true;
	            }
	            Player p = (Player)sender;
	            if (!this.word.wordconfig.disabledPlayers.contains(p.getUniqueId().toString())) {
	              p.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.BubblesName + " " + this.word.wordlang.Disabled);
	              this.word.wordconfig.disabledPlayers.add(p.getUniqueId().toString());
	            } else {
	              p.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.BubblesName + " " + this.word.wordlang.Enabled);
	              this.word.wordconfig.disabledPlayers.remove(p.getUniqueId().toString());
	            }
	            return true;
	          }
	          
	          if (arg.equalsIgnoreCase("nochat")) {
		            if ((!sender.hasPermission("Wordbubbles.nochat")) && (!sender.hasPermission("Wordbubbles.admin"))) {
		              sender.sendMessage(perm);
		              return true;
		            }
		            if (!(sender instanceof Player)) {
		              sender.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.NotaPlayerMCChat);
		              return true;
		            }
		            Player p = (Player)sender;
		            if (!this.word.wordconfig.disabledPlayersChat.contains(p.getUniqueId().toString())) {
		              p.sendMessage(this.word.wordlang.PLPrefix + " " + "§fMC Chat" + " " + this.word.wordlang.Disabled);
		              this.word.wordconfig.disabledPlayersChat.add(p.getUniqueId().toString());
		              if(this.word.wordconfig.disabledPlayers.contains(p.getUniqueId().toString())){
		            	  this.word.wordconfig.disabledPlayers.remove(p.getUniqueId().toString());
		            	  p.sendMessage(this.word.wordlang.PLPrefix + " " + this.word.wordlang.BubblesName + " §cForce §2Enabled");
		              }
		            } else {
		              p.sendMessage(this.word.wordlang.PLPrefix + " " + "§fMC Chat" + " " + this.word.wordlang.Enabled);
		              this.word.wordconfig.disabledPlayersChat.remove(p.getUniqueId().toString());
		            }
		            return true;
		          }
	          
	          if (arg.equalsIgnoreCase("nonpcchat")) {
		            if ((!sender.hasPermission("Wordbubbles.trait")) && (!sender.hasPermission("Wordbubbles.admin"))) {
		              sender.sendMessage(perm);
		              return true;
		            }
		            if (!(sender instanceof Player)) {
		              sender.sendMessage(this.word.wordlang.PLPrefix + " Youre not a player! You can't toggle this NPC''s MC Chat!");
		              return true;
		            }
		            Player p = (Player)sender;
		            NPC selected = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
		            if(selected!=null){
		            	if(selected.hasTrait(me.drkmatr1984.wordbubbles.WordBubblesTrait.class)){
			            	String NPCname = selected.getName();
			                if (!this.word.wordconfig.disabledNPCs.contains(selected.getUniqueId().toString())) {
			                  p.sendMessage(this.word.wordlang.PLPrefix + " " + "§fMC Chat for " + ChatColor.GREEN + NPCname + " §4Disabled");
			                  this.word.wordconfig.disabledNPCs.add(selected.getUniqueId().toString());
			                } else {
			                  p.sendMessage(this.word.wordlang.PLPrefix + " " + "§fMC Chat for " + ChatColor.GREEN + NPCname + " §2Enabled");
			                  this.word.wordconfig.disabledNPCs.remove(selected.getUniqueId().toString());
			                }
		                }else{
		                	sender.sendMessage(this.word.wordlang.PLPrefix + " Selected NPC is not able to use WordBubbles.");
		                }
		            }else{
		            	sender.sendMessage(this.word.wordlang.PLPrefix + " You do not have an NPC selected!");
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
	  
	  public void displayHelp(CommandSender sender){
		  sender.sendMessage(ChatColor.BLUE + "------  " + ChatColor.YELLOW + this.word.wordlang.PLPrefix + " Help  " + ChatColor.BLUE + "------" + ChatColor.RESET);
          sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles" + " " + ChatColor.RESET + "- Displays this Help");
          sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles help" + " " + ChatColor.RESET + "- Also Displays this Help");
          if(sender.hasPermission("Wordbubbles.toggle") || sender.hasPermission("Wordbubbles.admin"))
            	sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles toggle" + " " + ChatColor.RESET + "- Toggle your wordbubbles " + ChatColor.GREEN + "On" + ChatColor.RESET + "/" + ChatColor.RED + "Off" + ChatColor.RESET);
            if(sender.hasPermission("Wordbubbles.nochat") || sender.hasPermission("Wordbubbles.admin"))	
            	sender.sendMessage(ChatColor.AQUA + "/" + "WordBubbles nochat" + " " + ChatColor.RESET + "- Toggle your Chat " + ChatColor.GREEN + "On" + ChatColor.RESET + "/" + ChatColor.RED + "Off" + ChatColor.RESET + " (Only speak in WordBubbles)");
          if(this.word.cit && (sender.hasPermission("Wordbubbles.trait") || sender.hasPermission("Wordbubbles.admin"))){
        	sender.sendMessage(ChatColor.DARK_AQUA + "--------" + ChatColor.YELLOW + " NPC Help " + ChatColor.DARK_AQUA + "--------" + ChatColor.RESET);
        	sender.sendMessage(ChatColor.YELLOW + "/Trait wordbubbles " + ChatColor.RESET + "- Typing this while");
            sender.sendMessage("having a Citizens2 NPC selected will");
            sender.sendMessage("allow the NPC to speak in wordbubbles");
            sender.sendMessage(ChatColor.YELLOW + "/Wordbubbles noNPCchat" + ChatColor.RESET + "- Typing this while");
            sender.sendMessage("having a Citizens2 NPC selected");
            sender.sendMessage("will toggle text chat on/off for that");
            sender.sendMessage("Particular Citizens2 NPC");
          }
          if (sender.hasPermission("Wordbubbles.admin")) {
        	sender.sendMessage(ChatColor.YELLOW + "--------" + ChatColor.RED + " Admin Help " + ChatColor.YELLOW + "--------" + ChatColor.RESET);
            sender.sendMessage("§c/" + "WordBubbles" + " version§r - Shows the Plugin Version Number");
            sender.sendMessage("§c/" + "WordBubbles" + " reload§r - Reloads the Plugin Config");
          }
          sender.sendMessage(ChatColor.BLUE + "---------------------------------" + ChatColor.RESET);
	  }
}
