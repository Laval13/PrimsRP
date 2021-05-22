package fr.Laval_13.PrimsRP.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileUtils {

	private File file;
	private FileConfiguration config;

	public FileUtils(String path, String file){
		this.file = new File(path, file);
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	public FileUtils addDefault(String path, Object value){
		this.config.addDefault(path, value);
		return this;
	}

	public FileUtils copyDefaults(boolean copyDefaults){
		this.config.options().copyDefaults(copyDefaults);
		return this;
	}

	public FileUtils set(String path, Object value){
		this.config.set(path, value);
		return this;
	}

	public FileUtils save(){
		try{
			this.config.save(this.file);
		}catch(IOException e){
			e.printStackTrace();
		}
		return this;
	}

	public File getFile(){
		return this.file;
	}

	public void reload(){
		try{
			this.config.load(this.file);
		}catch(IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
	}

	public boolean exists(){
		return this.file.exists();
	}

	public boolean contains(String value){
		return this.config.contains(value);
	}

	public Object getObject(String path){
		return this.config.get(path);
	}

	public String getString(String path){
		return this.config.getString(path);
	}

	public int getInt(String path){
		return this.config.getInt(path);
	}

	public double getDouble(String path){
		return this.config.getDouble(path);
	}

	public long getLong(String path){
		return this.config.getLong(path);
	}

	public boolean getBoolean(String path){
		return this.config.getBoolean(path);
	}

	public List<String> getStringList(String path){
		return this.config.getStringList(path);
	}

	public List<Boolean> getBooleanList(String path){
		return this.config.getBooleanList(path);
	}

	public List<Double> getDoubleList(String path){
		return this.config.getDoubleList(path);
	}

	public List<Integer> getIntegerList(String path){
		return this.config.getIntegerList(path);
	}

	public Set<String> getKeys(boolean keys){
		return this.config.getKeys(keys);
	}

	public ConfigurationSection getConfigurationSection(String section){
		return this.config.getConfigurationSection(section);
	}

	public void delete(){
		this.file.delete();
	}
}
