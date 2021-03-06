package lt.ekgame.autohost;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class Settings {
	
	// account
	public String username, password, osuApi;
	
	// room
	public String roomName, roomPassword;
	public int roomSlots;
	public boolean freemodsEnabled;
	
	// general
	public List<Integer> operatorIds;
	public String infoText, helpText;
	
	// beatmap criteria
	public double minDifficulty, maxDifficulty;
	public int maxLength, gamemode;
	public boolean allowGraveyard;
	
	public Settings(String path) throws IOException, ObjectMappingException {
		ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(Paths.get(path)).build(); // Create the loader
		CommentedConfigurationNode node = loader.load();
		
		CommentedConfigurationNode account = node.getNode("account");
		username = account.getNode("username").getString();
		password = account.getNode("password").getString();
		osuApi =   account.getNode("osu-api-key").getString();
		
		CommentedConfigurationNode general = node.getNode("general");
		operatorIds = general.getNode("operators").getList(TypeToken.of(Integer.class));
		infoText =    general.getNode("info-text").getString();
		helpText =    general.getNode("help-text").getString();
		
		CommentedConfigurationNode room = node.getNode("room");
		roomName =     room.getNode("name").getString();
		roomPassword = room.getNode("password").getString();
		roomSlots =    room.getNode("slots").getInt();
		
		CommentedConfigurationNode criteria = node.getNode("beatmap-criteria");
		minDifficulty =  criteria.getNode("min-difficulty").getDouble();
		maxDifficulty =  criteria.getNode("max-difficulty").getDouble();
		maxLength =      criteria.getNode("max-length").getInt();
		gamemode =       criteria.getNode("gamemode").getInt();
		allowGraveyard = criteria.getNode("allow-graveyard").getBoolean();
		
		if (gamemode < 0 || gamemode > 3) {
			throw new IllegalArgumentException("Invalid gamemode \"" + gamemode + "\".");
		}
	}

}
