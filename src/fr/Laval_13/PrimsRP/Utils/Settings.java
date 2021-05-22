// 
// Decompiled by Procyon v0.5.36
// 

package fr.Laval_13.PrimsRP.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.Laval_13.PrimsRP.PrimsRP;

public class Settings {

	private YamlConfiguration config;
	private File file;
	private PrimsRP u;

	public Settings(PrimsRP u, String s, boolean defaults){
		this.u = u;
		file = new File(u.getDataFolder(), s + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
		Reader reader = new InputStreamReader(getConfigContent(new InputStreamReader(u.getResource(s + ".yml"), StandardCharsets.UTF_8)));
		YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(reader);
		try{
			if(!file.exists()){
				config.addDefaults(loadConfiguration);
				config.options().copyDefaults(true);
				save();
			}else{
				if(defaults){
					config.addDefaults(loadConfiguration);
					config.options().copyDefaults(true);
					save();
				}
				config.load(file);
			}
		}catch(IOException ex){
		}catch(InvalidConfigurationException ex2){
		}
	}

	public File getFile(){
		return file;
	}

	public void reload(){
		try{
			config.load(file);
		}catch(IOException | InvalidConfigurationException ex){
			ex.printStackTrace();
		}
	}

	public InputStream getConfigContent(Reader reader){
		try{
			String pluginName = u.getDescription().getName();
			int commentNum = 0;
			StringBuilder whole = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(reader);
			String currentLine;
			while((currentLine = bufferedReader.readLine()) != null){
				if(currentLine.startsWith("#")){
					String addLine = currentLine.replaceFirst("#", pluginName + "_COMMENT_" + commentNum + ":");
					whole.append(addLine).append("\n");
					++commentNum;
				}else{
					whole.append(currentLine).append("\n");
				}
			}
			String config = whole.toString();
			InputStream configStream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
			bufferedReader.close();
			return configStream;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	private String prepareConfigString(String configString){
		String[] lines = configString.split("\n");
		StringBuilder config = new StringBuilder();
		for(String line : lines){
			if(line.startsWith(u.getDescription().getName() + "_COMMENT")){
				String comment = "#" + line.trim().substring(line.indexOf(":") + 1);
				String normalComment;
				if(comment.startsWith("# ' ")){
					normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
				}else{
					normalComment = comment;
				}
				config.append(normalComment).append("\n");
			}else{
				config.append(line).append("\n");
			}
		}
		return config.toString();
	}

	public void save(){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(prepareConfigString(config.saveToString()));
			writer.flush();
			writer.close();
		}catch(IOException ex){
		}
	}

	public YamlConfiguration getConfig(){
		return config;
	}

	public String get(String s){
		if(config.getString(s) == null){
			return "";
		}
		return config.getString(s).replaceAll("<l>", "¡").replaceAll("&", "§").replaceAll("-,-", "\u00f1");
	}

	public String getOrDefault(String s, String def){
		if(config.isSet(s)){
			return get(s);
		}
		set(s, def);
		save();
		return def;
	}

	public int getInt(String s){
		return config.getInt(s);
	}

	public int getIntOrDefault(String s, int def){
		if(config.isSet(s)){
			return getInt(s);
		}
		set(s, def);
		save();
		return def;
	}

	public List<String> getList(String s){
		return config.getStringList(s);
	}

	public List<String> getListOrDefault(String s, ArrayList<String> def){
		if(config.isSet(s)){
			return getList(s);
		}
		return def;
	}

	public boolean isSet(String s){
		return config.isSet(s);
	}

	public void set(String s, Object o){
		config.set(s, o);
	}

	public boolean getBoolean(String s){
		return config.getBoolean(s);
	}
}
