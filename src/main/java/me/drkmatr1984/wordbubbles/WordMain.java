package me.drkmatr1984.wordbubbles;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Item;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WordMain extends JavaPlugin
{
  public static WordMain plugin;
  String version = getDescription().getVersion();
  ArrayList<Item> itemDrops = new ArrayList<Item>();
  Map<Item, Hologram> itemHolos = new HashMap<Item, Hologram>();
  public HolographicDisplays HD = null;
  public boolean placeAPI = false;
  public boolean cit = false;
  public WordConfigAccessor wordconfig;
  public WordBubbles wordbubbles;
  public WordLang wordlang;
  
  public void onEnable()
  {
	plugin = this;
	checkDependencies();
	wordconfig = new WordConfigAccessor();
	wordlang = new WordLang();
	wordbubbles = new WordBubbles();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new WordBubblesListener(this), this);
    getCommand("wordbubbles").setExecutor(new WordCommandExecutor());
    wordconfig.initializeConfig();
    wordconfig.repairDisabledWorldsList();
    wordlang.InitializeLang();
    wordbubbles.holoMover();
    System.out.println("[WordBubbles] Version " + this.version + " Enabled");
  }
  
  public void onDisable()
  {
	for (Hologram holo : HologramsAPI.getHolograms(this)) {
	      holo.delete();
	    }
	wordconfig.saveUserList();
    System.out.println("[WordBubbles] Version " + this.version + " Disabled");
    
  }
  
  public static WordMain getWordClass(){
	  return plugin;
  }
  
  public void checkDependencies() {
    PluginManager pm = getServer().getPluginManager();
    if ((pm.getPlugin("HolographicDisplays") == null) || (!pm.isPluginEnabled("HolographicDisplays"))) {
      System.out.println("[WordBubbles] Holographic Displays not found. This Plugin will not function without Holographic Displays!");
      plugin.getPluginLoader().disablePlugin(plugin);
    }else{
    	HD = (HolographicDisplays) pm.getPlugin("HolographicDisplays");
    }
    if ((pm.getPlugin("ProtocolLib") == null) || (!pm.isPluginEnabled("ProtocolLib"))) {
      System.out.println("[WordBubbles] ProtocolLib not found. This Plugin will not function without ProtocolLib!");
      plugin.getPluginLoader().disablePlugin(plugin);
    }
    if ((pm.getPlugin("Citizens") == null) || (!pm.isPluginEnabled("Citizens")))
    {
      System.out.println("[WordBubbles] Citizens not found - NPC WordBubbles and the wordbubble trait will not function!");
    }
    else {
      if(net.citizensnpcs.api.CitizensAPI.getTraitFactory().getTrait(WordBubblesTrait.class)==null){
      net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(WordBubblesTrait.class).withName("wordbubbles"));
      }
      System.out.println("[WordBubbles] Citizens found! You will now be able to use /trait Wordbubbles to allow NPCs to speak in WordBubbles!");
      cit = true;
    }
    if ((pm.getPlugin("PlaceholderAPI") != null) && (pm.isPluginEnabled("PlaceholderAPI")))
    {
    	System.out.println("[WordBubbles] PlaceholderAPI found! This Plugin will now use Placeholders from PlaceholderAPI!");
    	placeAPI = true;
    }    
  }
}
