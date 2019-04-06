package com.github.draylar.vh.config;

import com.google.gson.Gson;
import net.fabricmc.loader.FabricLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigLoader
{
    /**
     * Checks to see if the config folder exists.
     * If the config folder does not exist, it creates it.
     * It then moves on to checking the config file.
     */
    public void checkConfigFolder()
    {
        // ping config path is [run environment]/config/ping
        Path configPath = Paths.get(new File(FabricLoader.INSTANCE.getConfigDirectory().getAbsolutePath() + "//vanilla_hammers").getAbsolutePath());

        if (configPath.toFile().isDirectory()) checkConfigFile(configPath);

        else
        {
            configPath.toFile().mkdirs();
            checkConfigFile(configPath);
        }
    }


    /**
     * Checks the config file.
     * If the config file does not exist, it is created and filled with default values.
     * If the config file does exist, the values are transferred to the config holder.
     *
     * @param path
     */
    public void checkConfigFile(Path path)
    {
        Path jsonPath = Paths.get(path + "\\vanilla_hammers.json");

        if (!jsonPath.toFile().exists())
        {
            createNewFile(jsonPath);
            setBlankConfig(jsonPath, true);
        }

        try {
            // read config
            String input = new String(Files.readAllBytes(jsonPath));

            // save to current config holder
            Gson gson = new Gson();
            ConfigHolder.configInstance = gson.fromJson(input, ConfigGson.class);
        }

        catch (Exception e)
        {
            System.out.println("[Vanilla Hammers] Json is invalid! Try to fix from the error, or remove config file. Rolling with defaults!");
            e.printStackTrace();

            // to prevent future errors, just set filler values in current config.
            // we leave the config file alone and assume the user will see the errors in console
            // and either delete or fix it.
            setBlankConfig(jsonPath, false);
        }
    }


    /**
     * Sets the current config instance to blank values.
     * If writeConfig is true, we also write the config to the config file.
     * @param jsonPath
     * @param writeConfig
     */
    private void setBlankConfig(Path jsonPath, boolean writeConfig)
    {
        // create blank config
        ConfigGson config = new ConfigGson();

        config.enableExtraMaterials = true;
        config.enableTaterHammer = true;

        // write to config holder
        ConfigHolder.configInstance = config;

        if(writeConfig) writeConfigToPath(jsonPath, config);
    }


    /**
     * Writes the input config to our config file.
     * @param jsonPath
     * @param config
     */
    private void writeConfigToPath(Path jsonPath, ConfigGson config)
    {
        try
        {
            Gson gson = new Gson();
            Files.write(jsonPath, gson.toJson(config).getBytes());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Attempts to create a new file.
     * @param path
     */
    private void createNewFile(Path path)
    {
        try
        {
            path.toFile().createNewFile();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
