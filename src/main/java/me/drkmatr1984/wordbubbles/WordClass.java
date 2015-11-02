package me.drkmatr1984.wordbubbles;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import net.citizensnpcs.api.trait.TraitInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WordClass extends JavaPlugin
{
  public static WordClass plugin;
  String version = getDescription().getVersion();
  Map<LivingEntity, Hologram> Holograms = new HashMap<LivingEntity, Hologram>();
  Map<LivingEntity, Integer> Timers = new HashMap<LivingEntity, Integer>();
  ArrayList<Item> itemDrops = new ArrayList<Item>();
  Map<Item, Hologram> itemHolos = new HashMap<Item, Hologram>();
  public HolographicDisplays HD = null;
  public boolean placeAPI = false;
  public static boolean cit = false;
  public WordConfigAccessor wordconfig;
  
  public void onEnable()
  {
	plugin = this;
	wordconfig = new WordConfigAccessor(this);
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new WordBubblesListener(this), this);
    getCommand("wordbubbles").setExecutor(new WordCommandExecutor(this));
    wordconfig.initializeConfig();
    wordconfig.saveDefaultUserList();
    wordconfig.loadUserList();
    holoMover();
    wordconfig.repairDisabledWorldsList();   
    checkDependencies();
    System.out.println("[WordBubbles] Version " + this.version + " Enabled");
  }
  
  public void onDisable()
  {
    System.out.println("[WordBubbles] Version " + this.version + " Disabled");
    for (Hologram holo : HologramsAPI.getHolograms(this)) {
      holo.delete();
    }
    wordconfig.saveUserList();
  }
  
  public static WordClass getWordClass(){
	  return plugin;
  }
  
  public void checkDependencies() {
    PluginManager pm = getServer().getPluginManager();
    if ((pm.getPlugin("HolographicDisplays") == null) || (!pm.isPluginEnabled("HolographicDisplays"))) {
      System.out.println("[WordBubbles] Holographic Displays not found. This Plugin will not function without Holographic Displays!");
    }else{
    	HD = (HolographicDisplays) pm.getPlugin("HolographicDisplays");
    }
    if ((pm.getPlugin("ProtocolLib") == null) || (!pm.isPluginEnabled("ProtocolLib"))) {
      System.out.println("[WordBubbles] ProtocolLib not found. This Plugin will not function without ProtocolLib!");
    }
    if ((pm.getPlugin("Citizens") == null) || (!pm.isPluginEnabled("Citizens")))
    {
      System.out.println("[WordBubbles] Citizens not found - NPC WordBubbles and the wordbubble trait will not function!");
    }
    else {
      net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(WordBubblesTrait.class).withName("wordbubbles"));
      System.out.println("[WordBubbles] Citizens found! You will now be able to use /trait Wordbubbles to allow NPCs to speak in WordBubbles!");
      cit = true;
    }
    if ((pm.getPlugin("PlaceholderAPI") != null) && (pm.isPluginEnabled("PlaceholderAPI")))
    {
    	System.out.println("[WordBubbles] PlaceholderAPI found! This Plugin will now use Placeholders from PlaceholderAPI!");
    	placeAPI = true;
    }
    
  }
  
  public void holoMover() {
    getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      public void run()
      {
        for (Map.Entry<LivingEntity, Hologram> ent : WordClass.this.Holograms.entrySet()) {
          Hologram holo = (Hologram)ent.getValue();
          LivingEntity p = (LivingEntity)ent.getKey();
          Location loc = p.getEyeLocation().add(0.0D, wordconfig.hight, 0.0D);
          holo.teleport(loc); } } }, 0L, 20L * wordconfig.updateTimer);
  }
  
  public void createWordBubble(final LivingEntity p, String Header, List<String> Msg)
  {
    if (wordconfig.disabledWorlds.contains(p.getWorld().getName().toLowerCase())) {
      return;
    }
    if (Msg.toString().length() > wordconfig.maxLength) {
      return;
    }
    if (this.Holograms.containsKey(p)) {
      ((Hologram)this.Holograms.get(p)).delete();
      this.Holograms.remove(p);
      getServer().getScheduler().cancelTask(((Integer)this.Timers.get(p)).intValue());
      this.Timers.remove(p);
    }
    final Hologram holo = HologramsAPI.createHologram(plugin, p.getEyeLocation().add(0.0D, wordconfig.hight, 0.0D));
    holo.appendTextLine(wordconfig.headerColor.replaceAll("&", "§") + Header.replaceAll("&", "§"));
    for (String s : Msg) {
      if (wordconfig.smileys) {
        s = s.replace(":)", SmileyList.smiley);
        s = s.replace(":(", SmileyList.sad);
        s = s.replace("<3", SmileyList.heart);
        s = s.replace("<-", SmileyList.arrowLeft);
        s = s.replace("->", SmileyList.arrowRight);
        s = s.replace("(cloud)", SmileyList.cloud);
        s = s.replace("(sun)", SmileyList.sun);
        s = s.replace("(umbrella)", SmileyList.umbrella);
        s = s.replace("(snowman)", SmileyList.snowman);
        s = s.replace("(comet)", SmileyList.comet);
        s = s.replace("(star)", SmileyList.star);
        s = s.replace("(phone)", SmileyList.phone);
        s = s.replace("(skull)", SmileyList.skull);
        s = s.replace("(radioactive)", SmileyList.radioactive);
        s = s.replace("(biohazard)", SmileyList.biohazard);
        s = s.replace("(peace)", SmileyList.peace);
        s = s.replace("(yingyang)", SmileyList.yingyang);
        s = s.replace("(moon)", SmileyList.moon);
        s = s.replace("(crown)", SmileyList.crown);
        s = s.replace("(music)", SmileyList.music);
        s = s.replace("(scissor)", SmileyList.scissor);
        s = s.replace("(plane)", SmileyList.plane);
        s = s.replace("(mail)", SmileyList.mail);
        s = s.replace("(pencil)", SmileyList.pencil);
        s = s.replace("(check)", SmileyList.check);
        s = s.replace("(yuno)", SmileyList.yuno);
        s = s.replace("(tableflip)", SmileyList.tableflip);
        s = s.replace("(fuckyou)", SmileyList.fuckyou);
        s = s.replace("(meh)", SmileyList.meh);
        s = s.replace("(bear)", SmileyList.bear);
        s = s.replace("~", "≈");
        s = s.replace("*", "•");
      }
      holo.appendTextLine(wordconfig.msgColor.replaceAll("&", "§") + s);
    }
    int out = wordconfig.timeout;
    if ((wordconfig.increaseTime) && 
      (((String)Msg.get(0)).length() >= 40)) {
      out += wordconfig.increaseBy;
    }
    
    int id = getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
    {
      public void run()
      {
        WordClass.this.Holograms.remove(p);
        holo.delete(); } }, 20 * out);
    

    this.Timers.put(p, Integer.valueOf(id));
    this.Holograms.put(p, holo);
  }
  
  public String formatHeader(LivingEntity p, String header1) {
    String health = "";
    Date Ddate = new Date();
    DateFormat dfDate = new SimpleDateFormat("dd/MM/yyy");
    DateFormat dfTime = new SimpleDateFormat("HH:mm");
    String date = dfDate.format(Ddate);
    String time = dfTime.format(Ddate);
    if (((p instanceof Player)) && (!p.hasMetadata("NPC")))
    {
      health = "❤" + String.valueOf(((Player)p).getHealthScale() / 2.0D);
      header1 = header1.replaceAll("%DNAME", ((Player)p).getDisplayName());
      header1 = header1.replaceAll("%FOOD", String.valueOf(((Player)p).getFoodLevel()));
      header1 = header1.replaceAll("%LVL", String.valueOf(((Player)p).getLevel()));
      header1 = header1.replaceAll("%GAMEMODE", ((HumanEntity)p).getGameMode().name());
      if(placeAPI)
  		header1 = PlaceholderAPI.setPlaceholders((Player)p, header1);
    } else {
      health = "❤" + String.valueOf(p.getHealth() / 2.0D);
      header1 = header1.replaceAll("%DNAME", ChatColor.RESET + p.getCustomName());
      header1 = header1.replaceAll("%FOOD", String.valueOf(""));
      header1 = header1.replaceAll("%LVL", String.valueOf(""));
      header1 = header1.replaceAll("%GAMEMODE", String.valueOf(""));
      header1 = header1.replace(" ", "");
    }
    header1 = header1.replaceAll("%NAME", p.getName());
    header1 = header1.replaceAll("%HEALTH", health);
    header1 = header1.replaceAll("%DATE", date);
    header1 = header1.replaceAll("%TIME", time);
    header1 = header1.replaceAll("%PLAYERCOUNT", String.valueOf(getServer().getOnlinePlayers().size()));
    header1 = header1.replaceAll(p.getName(), "§c" + p.getName());    
    header1 = header1.replaceAll("&", "§");
    return header1;
  }
  
  public ArrayList<String> formatMsg(LivingEntity p, List<String> Msg) {
    String health = "";
    Date Ddate = new Date();
    DateFormat dfDate = new SimpleDateFormat("dd/MM/yyy");
    DateFormat dfTime = new SimpleDateFormat("HH:mm");
    String date = dfDate.format(Ddate);
    String time = dfTime.format(Ddate);
    ArrayList<String> newMsg = new ArrayList<String>();
    ItemStack hand = null;
    newMsg.clear();
    for (String s : Msg) {
      if ((p.hasPermission("Wordbubbles.color")) || (p.hasPermission("essentials.chat.color")) || (p.hasPermission("deluxechat.color")) || (p.hasPermission("herochat.color")) || (p.hasMetadata("NPC")) || (p.hasPermission("Wordbubbles.admin"))) {
        s = s.replaceAll("&", "§");
      } else {
        s = stripColors(s);
      }
      s = s.replaceAll("%NAME", p.getName());
      if (((p instanceof Player)) && (!p.hasMetadata("NPC"))) {
    	Player player = (Player) p;
    	if(placeAPI)
    	{
    		s = PlaceholderAPI.setPlaceholders(player, s);
    	}else{
		      s = s.replaceAll("%DNAME", player.getDisplayName());
		      s = s.replaceAll("%FOOD", String.valueOf((player).getFoodLevel()));
		      s = s.replaceAll("%LVL", String.valueOf((player).getLevel()));
		      s = s.replaceAll("%GAMEMODE", (player).getGameMode().name());
		      }
      }else {
      			s = s.replaceAll("%DNAME", ChatColor.RESET + p.getCustomName());
      			s = s.replaceAll("%FOOD", String.valueOf("N/A"));
      			s = s.replaceAll("%LVL", String.valueOf("N/A"));
      			s = s.replaceAll("%GAMEMODE", String.valueOf("N/A"));
      		}
      s = s.replaceAll("%HEALTH", health);
      s = s.replaceAll("%DATE", date);
      s = s.replaceAll("%TIME", time);
      s = s.replaceAll("%PLAYERCOUNT", String.valueOf(getServer().getOnlinePlayers().size()));
      if(p instanceof LivingEntity)
    	  hand = p.getEquipment().getItemInHand();
      if (s.contains("[item]"))
        if ((hand != null) && (hand.getType() != Material.AIR))
        {
          String beforeText = s.substring(0, s.indexOf("[item]"));
          beforeText = rainbow(beforeText, true);
          String afterText = s.substring(s.indexOf("]") + 1);
          afterText = rainbow(afterText, false);
          String newName = "";
          if (hand.hasItemMeta())
          {
            String name = hand.getItemMeta().getDisplayName();
            if ((name == null) || (name == "null"))
            {
              name = formatMaterialName(hand.getType());
            }
            newName = ChatColor.AQUA + "[" + ChatColor.RESET + name + ChatColor.AQUA + "]" + ChatColor.RESET;
            s = beforeText + newName + afterText;
          }
          else {
            newName = ChatColor.AQUA + "[" + ChatColor.RESET + formatMaterialName(hand.getType()) + ChatColor.AQUA + "]" + ChatColor.RESET;
            s = beforeText + newName + afterText;
          }
        } else {
          s.replaceAll("[item]", "N/A");
        }
      newMsg.add(s);
    }
    return newMsg;
  }
  
  public String formatMaterialName(Material mat) {
    if (mat.name().contains("_"))
    {
      String newName = mat.name();
      while (newName.contains("_")) {
        newName = newName.substring(0, 1).toUpperCase() + newName.substring(1, newName.indexOf("_")).toLowerCase() + " " + newName.substring(newName.indexOf("_") + 1, newName.indexOf("_") + 2).toUpperCase() + newName.substring(newName.indexOf("_") + 2, newName.length()).toLowerCase();
      }
      return newName;
    }
    
    return mat.name().substring(0, 1).toLowerCase() + mat.name().substring(1).toLowerCase();
  }
  
  public String rainbow(String input, boolean reset)
  {
    if (reset) {
    	wordconfig.currentColor = ChatColor.WHITE;
    }
    String newString = "";
    

    for (int i = 0; i < input.length(); i++) {
      if ((input.charAt(i) == '§') || (input.charAt(i) == '&'))
      {
        char colorChar = input.charAt(i + 1);
        wordconfig.currentColor = ChatColor.getByChar(colorChar);
        i++;
      } else {
        newString = newString + wordconfig.currentColor + "" + input.charAt(i);
      }
    }
    
    return newString;
  }
  
  public String stripColors(String input)
  {
    String newString = "";
    for (int i = 0; i < input.length(); i++) {
      if ((input.charAt(i) == '§') || (input.charAt(i) == '&'))
      {
        char symbol = input.charAt(i);
        char colorChar = input.charAt(i + 1);
        StringBuilder colorString = new StringBuilder();
        colorString.append(symbol);
        colorString.append(colorChar);
        input.replaceAll(colorString.toString(), "");
        i++;
      } else {
        newString = newString + ChatColor.RESET + "" + input.charAt(i);
      }
    }
    return newString;
  }
  
  public boolean wordsIsToggled(Player player)
  {
	  if(wordconfig.disabledPlayers.contains(player)){
		  return false;
	  }
	  return true;
  }
}
