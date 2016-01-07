package me.drkmatr1984.wordbubbles;

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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.clip.placeholderapi.PlaceholderAPI;

public class WordBubbles
{
  public WordMain plugin = WordMain.getWordClass();
  private Map<LivingEntity, Hologram> Holograms = new HashMap<LivingEntity, Hologram>();
  Map<LivingEntity, Integer> Timers = new HashMap<LivingEntity, Integer>();
  
  public void createWordBubble(final LivingEntity p, String Header, List<String> Msg)
  {
    if (plugin.wordconfig.disabledWorlds.contains(p.getWorld().getName().toLowerCase())) {
      return;
    }
    if (Msg.toString().length() > plugin.wordconfig.maxLength) {
      return;
    }
    if (this.getHolograms().containsKey(p)) {
      ((Hologram)this.getHolograms().get(p)).delete();
      this.getHolograms().remove(p);
      plugin.getServer().getScheduler().cancelTask(((Integer)this.Timers.get(p)).intValue());
      this.Timers.remove(p);
    }
    final Hologram holo = HologramsAPI.createHologram(plugin, p.getEyeLocation().add(0.0D, plugin.wordconfig.height, 0.0D));
    holo.appendTextLine(plugin.wordconfig.headerColor.replaceAll("&", "§") + Header.replaceAll("&", "§"));
    for (String s : Msg) {
      holo.appendTextLine(plugin.wordconfig.msgColor.replaceAll("&", "§") + s);
    }
    int out = plugin.wordconfig.timeout;
    if ((plugin.wordconfig.increaseTime) && 
      (((String)Msg.get(0)).length() >= 40)) {
      out += plugin.wordconfig.increaseBy;
    }
    
    int id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
    {
      public void run()
      {
    	  getHolograms().remove(p);
        holo.delete(); } }, 20 * out);
    

    this.Timers.put(p, Integer.valueOf(id));
    this.getHolograms().put(p, holo);
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
      if(plugin.placeAPI)
  		header1 = PlaceholderAPI.setPlaceholders((Player)p, header1);
    }
    if(p.hasMetadata("NPC")){
      health = "❤" + String.valueOf(p.getHealth() / 2.0D);
      header1 = header1.replaceAll("%DNAME", ChatColor.RESET + p.getCustomName());
      header1 = header1.replaceAll("%FOOD", String.valueOf(""));
      header1 = header1.replaceAll("%LVL", String.valueOf(""));
      header1 = header1.replaceAll("%GAMEMODE", String.valueOf(""));
      header1 = header1.replace(" ", "");
      if(plugin.placeAPI)
    		header1 = PlaceholderAPI.setPlaceholders((Player)p, header1);
    }
    header1 = header1.replaceAll("%NAME", p.getName());
    header1 = header1.replaceAll("%HEALTH", health);
    header1 = header1.replaceAll("%DATE", date);
    header1 = header1.replaceAll("%TIME", time);
    header1 = header1.replaceAll("%PLAYERCOUNT", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
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
    	if(plugin.placeAPI)
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
      s = s.replaceAll("%PLAYERCOUNT", String.valueOf(plugin.getServer().getOnlinePlayers().size()));
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
    	plugin.wordconfig.currentColor = ChatColor.WHITE;
    }
    String newString = "";
    

    for (int i = 0; i < input.length(); i++) {
      if ((input.charAt(i) == '§') || (input.charAt(i) == '&'))
      {
        char colorChar = input.charAt(i + 1);
        plugin.wordconfig.currentColor = ChatColor.getByChar(colorChar);
        i++;
      } else {
        newString = newString + plugin.wordconfig.currentColor + "" + input.charAt(i);
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
  
  public void holoMover() {
	    plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	    {
	      public void run()
	      {
	        for (Map.Entry<LivingEntity, Hologram> ent : getHolograms().entrySet()) {
	          Hologram holo = (Hologram)ent.getValue();
	          LivingEntity p = (LivingEntity)ent.getKey();
	          Location loc = p.getEyeLocation().add(0.0D, plugin.wordconfig.height, 0.0D);
	          holo.teleport(loc); } } }, 0L, 20L * plugin.wordconfig.updateTimer);
	  }

  public Map<LivingEntity, Hologram> getHolograms() {
		return Holograms;
  }

  public void setHolograms(Map<LivingEntity, Hologram> holograms) {
	  Holograms = holograms;
  }
}