package me.drkmatr1984.wordbubbles;

import java.util.Arrays;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.event.NPCSpeechEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class WordBubblesTrait
  extends Trait
{
  WordMain word = WordMain.getWordClass();
  
  public WordBubblesTrait() {
    super("wordbubbles");
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onNPCSpeech(NPCSpeechEvent event) {
    if (this.npc != event.getNPC()) return;
    if ((event.getNPC() != null) && (event.getNPC().isSpawned())) {
      NPC talker = event.getNPC();
      if ((talker.getEntity() instanceof LivingEntity))
      {
        SpeechContext sp = event.getContext();
        String msg = sp.getMessage();
        LivingEntity p = (LivingEntity)talker.getEntity();
        WordBubblesAPI.createWordBubbleAPI(talker, this.word.wordbubbles.formatHeader(p, this.word.wordconfig.NPCheader), this.word.wordbubbles.formatMsg(p, Arrays.asList(new String[] { msg })));
        if (this.word.wordconfig.cancelNPCChat || this.word.wordconfig.disabledNPCs.contains(talker.getUniqueId().toString())) {
          event.setCancelled(true);
        }
      }
    }
  }
  
  
}
