package me.drkmatr1984.wordbubbles;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WordLang
{
  String PLPrefix = "§7[§4WordBubbles§7]§r";
  String NoPerms = "§cYou don't have Permission§r";
  String Reloaded = "§aReloaded§r";
  String NotaPlayerToggle = "Youre not a player! You can't toggle your WordBubbles!";
  String NotaPlayerMCChat = "Youre not a player! You can't toggle your MC Chat!";
  String Enabled = "§2Enabled§r";
  String Disabled = "§4Disabled§r";
  String BubblesName = "§fWordBubbles§r";
	  
  WordMain word = WordMain.getWordClass();
  File lf = new File(this.word.getDataFolder().toString() + "/lang");
  private File languageFile = new File(this.lf, "language.yml");
  FileConfiguration language;
  
  public void loadLanguageFile()
  {
    if (!this.lf.exists()) {
      this.lf.mkdir();
    }
    if (!this.languageFile.exists()) {
      this.word.saveResource("lang/language.yml", false);
    }
    this.language = YamlConfiguration.loadConfiguration(this.languageFile);
  }
  
  public void InitializeLang()
  {
    loadLanguageFile();
    if ((this.language.getString("General.ChatPrefix") != null) && (this.language.getString("General.ChatPrefix") != "")) {
      this.PLPrefix = formatColor(this.language.getString("General.ChatPrefix"));
    }
    if ((this.language.getString("General.BubblesName") != null) && (this.language.getString("General.BubblesName") != "")) {
        this.Disabled = formatColor(this.language.getString("General.BubblesName"));
    }
    if ((this.language.getString("General.NoPerms") != null) && (this.language.getString("General.NoPerms") != "")) {
      this.NoPerms = formatColor(this.language.getString("General.NoPerms"));
    }
    if ((this.language.getString("General.Reloaded") != null) && (this.language.getString("General.Reloaded") != "")) {
      this.Reloaded = formatColor(this.language.getString("General.Reloaded"));
    }
    if ((this.language.getString("General.NotaPlayerToggle") != null) && (this.language.getString("General.NotaPlayerToggle") != "")) {
      this.NotaPlayerToggle = formatColor(this.language.getString("General.NotaPlayerToggle"));
    }
    if ((this.language.getString("General.NotaPlayerMCChat") != null) && (this.language.getString("General.NotaPlayerMCChat") != "")) {
      this.NotaPlayerMCChat = formatColor(this.language.getString("General.NotaPlayerMCChat"));
    }
    if ((this.language.getString("General.Enabled") != null) && (this.language.getString("General.Enabled") != "")) {
      this.Enabled = formatColor(this.language.getString("General.Enabled"));
    }
    if ((this.language.getString("General.Disabled") != null) && (this.language.getString("General.Disabled") != "")) {
      this.Disabled = formatColor(this.language.getString("General.Disabled"));
    }
  }
  
  private String formatColor(String msg)
  {
	String temp = "";
    if(msg!=null && msg!=""){
	  temp = msg;
      temp = temp.replaceAll("&", "§");
    }else{
    	return msg;
    }
    return temp;
  }
}
