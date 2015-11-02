package me.drkmatr1984.wordbubbles;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

@SuppressWarnings("deprecation")
public class WordBubblesListener implements org.bukkit.event.Listener
{
    private WordClass plugin;
	
	public WordBubblesListener(WordClass plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
	  public void onChat(PlayerChatEvent e) {
	    if (plugin.wordconfig.bubblesEnabled && !plugin.wordconfig.disabledPlayers.contains(e.getPlayer().getUniqueId().toString())) {    
		    String msg = e.getMessage();
		    Player p = e.getPlayer();
		    WordBubblesAPI.createWordBubbleAPI(p, plugin.formatHeader(p, plugin.wordconfig.header), WordClass.plugin.formatMsg(p, Arrays.asList(new String[] { msg })));
		    if (plugin.wordconfig.cancelChat) {
		      e.setCancelled(true);
		    }
	    }
	  }
	  
	  @EventHandler
	  public void onClick(PlayerInteractEntityEvent e) {
	    for (Map.Entry<LivingEntity, Hologram> ent : plugin.Holograms.entrySet()) {
	      Hologram holo = (Hologram)ent.getValue();
	      if (e.getRightClicked().equals(holo)) {
	        return;
	      }
	    }
	  }
	  
	  @EventHandler
	  public void onLeftClick(EntityDamageByEntityEvent e) {
	    for (Map.Entry<LivingEntity, Hologram> ent : plugin.Holograms.entrySet()) {
	      Hologram holo = (Hologram)ent.getValue();
	      if(e.getDamager() instanceof Player || e.getEntity().hasMetadata("NPC")){
	    	 if(e.getEntity().equals(holo)){
	    		 e.setCancelled(true);
	    		 return;
	    	 }
	      }     
	    }
	  }
}