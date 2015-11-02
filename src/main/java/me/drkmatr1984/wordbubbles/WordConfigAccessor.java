package me.drkmatr1984.wordbubbles;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WordConfigAccessor{
	
	  String PLPrefix = "";
	  boolean bubblesEnabled = true;
	  public boolean cancelNPCChat = false;
	  double hight = 1.5D;
	  String headerColor = "&6&l";
	  String header = "-";
	  public long updateTimer = 0L;
	  boolean increaseTime = true;
	  int increaseBy = 3;
	  int timeout = 4;
	  int maxLength = 4;
	  String msgColor = "-";
	  boolean smileys = true;
	  boolean cancelChat = false;
	  public ChatColor currentColor = ChatColor.WHITE;
	  private File usersFile;
	  private FileConfiguration users;
	  private WordClass plugin;
	  ArrayList<String> disabledWorlds = new ArrayList<String>();
	  List<String> disabledPlayers = new ArrayList<String>();
	
    public WordConfigAccessor(WordClass plugin) {
	    this.plugin = plugin;
    }
	public void initializeConfig() {
	    FileConfiguration f = plugin.getConfig();
	    f.addDefault("EnableWordBubbles", Boolean.valueOf(true));
	    f.addDefault("ChatPrefix", "&7[&4WordBubbles&7]&r");
	    f.addDefault("Header", "&6[&4%HEALTH&6] &6[&a%NAME&6] &6[&d%LVL&6]");
	    f.addDefault("HeaderColor", "&6&l");
	    f.addDefault("MsgColor", "&b");
	    f.addDefault("Height", Double.valueOf(1.5D));
	    f.addDefault("TimeTilFade", Integer.valueOf(4));
	    f.addDefault("IncreaseTimeIfMessageLong", Boolean.valueOf(true));
	    f.addDefault("IncreaseBy", Integer.valueOf(3));
	    f.addDefault("MaxLength", Integer.valueOf(50));
	    f.addDefault("RefreshTimer", Integer.valueOf(0));
	    f.addDefault("Smileys", Boolean.valueOf(true));
	    f.addDefault("CancelChat", Boolean.valueOf(false));
	    f.addDefault("CancelNPCChat", Boolean.valueOf(false));
	    ArrayList<String> TEMP = (ArrayList<String>)f.getStringList("DisabledWorlds");
	    if ((TEMP == null) || (TEMP.size() == 0)) {
	      TEMP.add("world_1");
	    }
	    f.set("DisabledWorlds", TEMP);
	    f.options().copyDefaults(true);
	    plugin.saveConfig();
	    this.bubblesEnabled = f.getBoolean("EnableWordBubbles");
	    String reformatPlPrefix = f.getString("ChatPrefix");
	    reformatPlPrefix = reformatPlPrefix.replaceAll("&", "§");
	    this.PLPrefix = reformatPlPrefix;
	    this.hight = f.getDouble("Height");
	    this.updateTimer = f.getLong("RefreshTimer");
	    this.increaseTime = f.getBoolean("IncreaseTimeIfMessageLong");
	    this.increaseBy = f.getInt("IncreaseBy");
	    this.timeout = f.getInt("TimeTilFade");
	    this.maxLength = f.getInt("MaxLength");
	    this.header = f.getString("Header");
	    this.headerColor = f.getString("HeaderColor");
	    this.msgColor = f.getString("MsgColor");
	    this.disabledWorlds = TEMP;
	    this.smileys = f.getBoolean("Smileys");
	    this.cancelChat = f.getBoolean("CancelChat");
	    this.cancelNPCChat = f.getBoolean("CancelNPCChat");
	  }

	  public void saveDefaultUserList() {
	      if (usersFile == null) {
	          usersFile = new File(plugin.getDataFolder(), "toggles.yml");
	      }
	      if (!usersFile.exists()) {           
	          plugin.saveResource("toggles.yml", false);
	      }   
	  }
	  
	  public void loadUserList(){
		  users = YamlConfiguration.loadConfiguration(usersFile);
		  disabledPlayers = users.getStringList("DisabledPlayers");     
	  }
	  
	  public void saveUserList(){
		  if(disabledPlayers!=null)
		  {
			  users.set("DisabledPlayers",disabledPlayers);
		  }
		  if(usersFile.exists())
			  usersFile.delete();
		  try {
			users.save(usersFile);
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  try {
			usersFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public void repairDisabledWorldsList()
	  {
	    ArrayList<String> n = new ArrayList<String>();
	    for (String s : this.disabledWorlds) {
	      s = s.toLowerCase();
	      n.add(s);
	    }
	    this.disabledWorlds = n;
	  }
}