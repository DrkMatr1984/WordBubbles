package me.drkmatr1984.wordbubbles;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WordLang
{	
	String prefix;
	String carryMsg;
	String dropMsg;
	String toggleOn;
	String toggleOff;
	String noPickUpNPC;
	String noPerms;
	String notAPlayer;
	String noPickUpPlayer;
	String error;
	
	WordMain word = WordMain.getWordClass();
	File lf = new File(this.word.getDataFolder().toString()+"/lang");
	private File languageFile = new File(lf, "language.yml");
	FileConfiguration language;
	
	public void loadLanguageFile() {
		  if (!lf.exists()){
			  lf.mkdir();
	      }
		  if (!languageFile.exists()) {           
	    	  this.word.saveResource("lang/language.yml", false);
	      }
        
		  language = YamlConfiguration.loadConfiguration(languageFile);
	  }
	
	public void InitializeLang(){
		loadLanguageFile();
		/*
		prefix = formatColor(language.getString("message.prefix"));      
		carryMsg = formatColor(language.getString("message.carry"));
		dropMsg = formatColor(language.getString("message.drop"));
		toggleOn = formatColor(language.getString("message.toggleOn"));
		toggleOff = formatColor(language.getString("message.toggleOff"));
		noPickUpNPC = formatColor(language.getString("message.noPickUpNPC"));
		noPerms = formatColor(language.getString("message.noPerms"));
		notAPlayer = formatColor(language.getString("message.notAPlayer"));
		noPickUpPlayer = formatColor(language.getString("message.noPickUpPlayer"));
		error = formatColor(language.getString("message.error"));*/
	}
	
	private String formatColor(String msg){
		String temp = msg;
		temp = temp.replaceAll("&", "§");
	    return temp;
	}
}