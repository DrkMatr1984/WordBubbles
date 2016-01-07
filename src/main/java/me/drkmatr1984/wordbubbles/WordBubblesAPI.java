package me.drkmatr1984.wordbubbles;

import java.util.Arrays;
import java.util.List;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;



public class WordBubblesAPI
{
  static WordMain b = WordMain.plugin;
  static WordBubbles c = new WordBubbles();
  
  public static void createWordBubbleAPI(Player p, String Header, List<String> Msg) {
    c.createWordBubble(p, Header, Msg);
  }
  
  public static void createWordBubbleAPI(LivingEntity p, String Header, List<String> Msg) {
    c.createWordBubble(p, Header, Msg);
  }
  
  public static void createWordBubbleAPI(HumanEntity p, String Header, List<String> Msg) {
    c.createWordBubble(p, Header, Msg);
  }
  
  public static void createWordBubbleAPI(NPC p, String Header, List<String> Msg)
  {
    c.createWordBubble((LivingEntity) p.getEntity(), Header, Msg);
  }
  
  public static void createWordBubbleAPI(Player p, List<String> Msg) {
    c.createWordBubble(p, "", Msg);
  }
  
  public static void createWordBubbleAPI(LivingEntity p, List<String> Msg) {
    c.createWordBubble(p, "", Msg);
  }
  
  public static void createWordBubbleAPI(HumanEntity p, List<String> Msg) {
    c.createWordBubble(p, "", Msg);
  }
  
  public static void createWordBubbleAPI(NPC p, List<String> Msg)
  {
    c.createWordBubble((LivingEntity) p.getEntity(), "", Msg);
  }
  
  public static void createWordBubbleAPI(Player p, String Header, String Msg) {
    c.createWordBubble(p, Header, Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(LivingEntity p, String Header, String Msg) {
    c.createWordBubble(p, Header, Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(HumanEntity p, String Header, String Msg) {
    c.createWordBubble(p, Header, Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(NPC p, String Header, String Msg)
  {
    c.createWordBubble((LivingEntity) p.getEntity(), Header, Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(Player p, String Msg) {
    c.createWordBubble(p, "", Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(LivingEntity p, String Msg) {
    c.createWordBubble(p, "", Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(HumanEntity p, String Msg) {
    c.createWordBubble(p, "", Arrays.asList(new String[] { Msg }));
  }
  
  public static void createWordBubbleAPI(NPC p, String Msg)
  {
    c.createWordBubble((LivingEntity) p.getEntity(), "", Arrays.asList(new String[] { Msg }));
  }
  
  public boolean wordsIsToggled(Player player)
  {
	  if(b.wordconfig.disabledPlayers.contains(player)){
		  return false;
	  }
	  return true;
  }
}
