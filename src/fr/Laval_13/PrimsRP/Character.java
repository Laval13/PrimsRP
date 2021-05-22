package fr.Laval_13.PrimsRP;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;

import fr.Laval_13.PrimsRP.Utils.FileUtils;

public class Character {

	private String nom, prenom, sexe;
	private int age;
	private Job job;
	private Player player;
	private CreatingStep step;

	public Character(Player player, String nom, String prenom, String sexe, int age, Job job){
		this.player = player;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.age = age;
		this.job = job;
	}
	
	public Character(Player player, String nom, String prenom, String sexe, int age, CreatingStep step){
		this.player = player;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.age = age;
		this.step = step;
	}

	
	public Player getPlayer(){
		return player;
	}
	
	public String getNom(){
		return nom;
	}

	public void setNom(String nom){
		this.nom = nom;
	}

	public String getPrenom(){
		return prenom;
	}

	public void setPrenom(String prenom){
		this.prenom = prenom;
	}

	public String getSexe(){
		return sexe;
	}

	public void setSexe(String sexe){
		this.sexe = sexe;
	}

	public int getAge(){
		return age;
	}

	public void setAge(int age){
		this.age = age;
	}

	public Job getJob(){
		if(job == null){
			return Job.Chomeur;
		}
		return job;
	}
	
	public void setJob(Job job){
		this.job = job;
	}
	
	public CreatingStep getStep(){
		return step;
	}
	
	public void setStep(CreatingStep step){
		this.step = step;
	}
	
	public void save(){

		File folder = new File(PrimsRP.getInstance().getDataFolder(), "Character");
		FileUtils character = new FileUtils(folder.getAbsolutePath(), getPlayer().getName() + ".yml");

		if(!folder.exists()){
			folder.mkdirs();
		}

		if(!character.exists()){
			try{
				character.getFile().createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}	
		
		character.set("Nom", WordUtils.capitalize(getNom()));
		character.set("Prenom", WordUtils.capitalize(getPrenom()));
		character.set("Sexe", WordUtils.capitalize(getSexe()));
		character.set("Age", getAge());
		character.set("Job", getJob().getName());
		
		character.save();
		
	}
	
	public enum Job {

		Maire("Maire"),
		Bucheron("Bucheron"),
		Mineur("Mineur"),
		Fermier("Fermier"),
		Médecin("Médecin"),
		Eboueur("Eboueur"),
		Cuisinier("Cuisinier"),
		
		//Police		
		Cadet("Cadet"),
		Sergent("Sergent"),
		Sergent_Chef("Sergent Chef"),
		Commandant("Commandant"),
		FBI("FBI"),
		
		Chomeur("Chomeur");
		
		private String name;
		private static Map<String, Job> FromName = new HashMap<String, Job>();
		
		private Job(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;			
		}
		
		static{
			for(Job Rank : values()){
				FromName.put(Rank.getName(), Rank);
			}
		}

		public static Job getFromName(String Name){
			for(String Names : FromName.keySet()){
				if(Name.equalsIgnoreCase(Names)){
					return FromName.get(Names);
				}
			}
			return null;
		}
		
	}
	
	public enum CreatingStep {

		Nom,
		Prenom,
		Sexe,
		Age;

	}

}
