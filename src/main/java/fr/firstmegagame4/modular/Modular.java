package fr.firstmegagame4.modular;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.firstmegagame4.modular.events.OnPlayerPlaceBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Modular implements ModInitializer {
    public static final Path MODULAR_MODULES_DIRECTORY_PATH = Paths.get(FabricLoader.getInstance().getGameDir().toString() + "/modular_modules");
    public static final Map<String, JsonObject> MODULAR_MODULES = new HashMap<>();
    public static final Logger LOGGER = LoggerFactory.getLogger("modular");

    @Override
    public void onInitialize() {
        if (Files.exists(MODULAR_MODULES_DIRECTORY_PATH)) {
            LOGGER.info("Modular Modules Directory Detected !");
        }
        else {
            try {
                Files.createDirectories(MODULAR_MODULES_DIRECTORY_PATH);
                LOGGER.info("Modular Modules Directory Created !");
            } catch (IOException error) {
                LOGGER.error("Modular Modules Directory Creation Error :\n");
                error.printStackTrace();
            }
        }
        UseBlockCallback.EVENT.register(new OnPlayerPlaceBlock());
        try {
            this.loadModules();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void loadModules() throws IOException {
        for (Path path: Files.list(MODULAR_MODULES_DIRECTORY_PATH).toList()) {
            JsonObject currentModule = JsonParser.parseReader(new FileReader(path.toString())).getAsJsonObject();
            Modules.setupModule(currentModule);
            MODULAR_MODULES.putIfAbsent(currentModule.get("name").getAsString(), currentModule);
        }
        String modulesList = "Modular Modules Loaded :";
        for (String modularModule: MODULAR_MODULES.keySet()) {
            modulesList = modulesList.concat(" " + modularModule + ",");
        }
        modulesList = StringUtils.chop(modulesList);
        LOGGER.info(modulesList);
    }
}
