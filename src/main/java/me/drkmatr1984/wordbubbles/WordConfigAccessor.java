package me.drkmatr1984.wordbubbles;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WordConfigAccessor{
	
	WordMain word = WordMain.getWordClass();
	String PLPrefix = "";
	boolean bubblesEnabled = true;
	public boolean cancelNPCChat = false;
	double height = 1.5D;
	String headerColor = "&6&l";
	String header = "-";
	String NPCheaderColor = "&6&l";
	String NPCheader = "-";
	public long updateTimer = 0L;
	boolean increaseTime = true;
	int increaseBy = 3;
	int timeout = 4;
	int maxLength = 4;
	String msgColor = "-";
	boolean cancelChat = false;
	public ChatColor currentColor = ChatColor.WHITE;
	File fc = new File(this.word.getDataFolder().toString()+"/data");
	private File usersFile = new File(fc, "toggles.yml");
	private FileConfiguration users;
	List<String> disabledWorlds = new ArrayList<String>();
	List<String> disabledPlayers = new ArrayList<String>();
	List<String> disabledPlayersChat = new ArrayList<String>();
	List<String> disabledNPCs = new ArrayList<String>();
	
	public void initializeConfig() {
		File file = new File(this.word.getDataFolder(), "config.yml");
	       if(!file.exists()) {
	           this.word.saveDefaultConfig();
	       }
	    FileConfiguration f = YamlConfiguration.loadConfiguration(file);
	    this.bubblesEnabled = f.getBoolean("General.EnableWordBubbles");
	    String reformatPlPrefix = f.getString("General.ChatPrefix");
	    reformatPlPrefix = reformatPlPrefix.replaceAll("&", "§");
	    this.PLPrefix = reformatPlPrefix;
	    this.height = f.getDouble("General.Height");
	    this.updateTimer = f.getLong("General.RefreshTimer");
	    this.increaseTime = f.getBoolean("General.IncreaseTimeIfMessageLong");
	    this.increaseBy = f.getInt("General.IncreaseBy");
	    this.timeout = f.getInt("General.TimeTilFade");
	    this.maxLength = f.getInt("General.MaxLength");
	    this.header = f.getString("General.Header");
	    this.headerColor = f.getString("General.HeaderColor");
	    this.msgColor = f.getString("General.MsgColor");
	    this.cancelChat = f.getBoolean("General.CancelChat");
	    this.disabledWorlds = f.getStringList("General.DisabledWorlds");
	    if(this.word.cit){
	        this.NPCheader = f.getString("CitizenNPCs.NPCHeader");
	        this.NPCheaderColor = f.getString("CitizenNPCs.NPCHeaderColor");
	        this.cancelNPCChat = f.getBoolean("CitizenNPCs.CancelNPCChat");
	    }
	    loadUserList();
	  }
	  
	  public void loadUserList(){
		  if (!fc.exists()){
			  fc.mkdir();
	      }
	      if (!usersFile.exists()) { 
	    	  this.word.saveResource("data/toggles.yml", true);
	      }
		  users = YamlConfiguration.loadConfiguration(usersFile);
		  disabledPlayers = users.getStringList("DisabledPlayers");
		  disabledNPCs = users.getStringList("DisabledNPCs");
		  disabledPlayersChat = users.getStringList("DisabledPlayersChat");
	  }
	  
	  public void saveUserList(){
		  if(disabledPlayers!=null)
		  {
			  users.set("DisabledPlayers",disabledPlayers);
		  }
		  if(disabledPlayersChat!=null)
		  {
			  users.set("DisabledPlayersChat",disabledPlayersChat);
		  }
		  if(disabledNPCs!=null)
		  {
			  users.set("DisabledNPCs",disabledNPCs);
		  }
		  if (!fc.exists()){
			  fc.mkdir();
	      }
		  if(usersFile.exists())
			  usersFile.delete();
		  try {
			users.save(usersFile);
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  if (!usersFile.exists()){
			  try {
				usersFile.createNewFile();
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			  }
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