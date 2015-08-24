/**
 *  Wrath Engine 
 *  Copyright (C) 2015  Trent Spears
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wrath.common.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import wrath.common.entities.Entity;
import wrath.util.Logger;

/**
 * Class to track Worlds and a convenient class to carry/save data.
 * @author Trent Spears
 */
public class World implements Serializable
{
    private static transient final ArrayList<WorldEventHandler> handlerList = new ArrayList<>();
    private static transient RootWorldEventHandler roothandler;
    
    /**
     * Adds a {@link wrath.common.world.WorldEventHandler} to handle events that occur in any World.
     * Does not carry over after a previously saved World is loaded.
     * @param handler The {@link wrath.common.world.WorldEventHandler} to add to the World Handlers list.
     */
    public static void addWorldEventHandler(WorldEventHandler handler)
    {
        handlerList.add(handler);
    }
    
    /**
     * Gets the root instance of {@link wrath.common.world.WorldEventHandler} to report events to.
     * @return Returns the root instance of {@link wrath.common.world.WorldEventHandler} to report events to.
     */
    public static WorldEventHandler getWorldEventHandler()
    {
        return roothandler;
    }
    
    // Object
    
    private String name;
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final WorldType type;
    
    private World(String worldName, WorldType type)
    {
        if(roothandler == null) roothandler = new RootWorldEventHandler();
        
        this.name = worldName;
        File file = new File("etc/worlds/" + name);
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException e)
            {
                Logger.getErrorLogger().log("Could not create new file @ '" + file.getAbsolutePath() + "'! I/O Error!");
            }
        }
        
        this.type = type;
        
        generateWorld();
    }
    
    private World afterLoad()
    {
        if(roothandler == null) roothandler = new RootWorldEventHandler();
        return this;
    }
    
    private void generateWorld()
    {
        
    }
    
    /**
     * Gets the name of this World.
     * @return Returns the name of this World.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the type of World this is, as defined by {@link wrath.common.world.WorldType}.
     * @return Returns the type of World.
     */
    public WorldType getWorldType()
    {
        return type;
    }
    
    /**
     * Saves the World and all data in a compressed format to the previously specified {java.io.File}.
     */
    public void save()
    {
        FileOutputStream fos = null;
        GZIPOutputStream gout = null;
        ObjectOutputStream out = null;
        
        try
        {
            out = new ObjectOutputStream((gout = new GZIPOutputStream((fos = new FileOutputStream(new File("etc/worlds/" + name))))));
            out.writeObject(this);
            gout.finish();
            out.flush();
        }
        catch(IOException e)
        {
            Logger.getErrorLogger().log("Could not save World! I/O Error!");
        }
        
        if(out == null || fos == null || gout == null)
        {
            Logger.getErrorLogger().log("Could not save World! Streams can not bind!");
            return;
        }
        
        try
        {
            out.close();
            gout.close();
            fos.close();
        }
        catch(IOException e)
        {
            Logger.getErrorLogger().log("Could not close World streams! I/O Error!");
        }
    }
    
    
    //Static Methods
    
    /**
     * Reads and returns all World data from the specified World.
     * Returns null if corrupt/invalid.
     * @param name The name of the world.
     * @param type The type of World to generate if one is not loaded from a file. This can be null if you know the World already exists.
     * @return Returns {@link wrath.common.world.World} loaded from a file (if present), null if invalid or corrupt.
     */
    public static World loadWorld(String name, WorldType type)
    {
        File file = new File("etc/worlds/" + name);
        if(!file.exists()) return new World(name, type).afterLoad();
        
        World ret = null;
        FileInputStream fis = null;
        GZIPInputStream in = null;
        ObjectInputStream is = null;
        
        try
        {
            is = new ObjectInputStream((in = new GZIPInputStream(new FileInputStream(file))));
            Object genObj = is.readObject();
            if(genObj instanceof World) ret = (World) genObj;
        }
        catch(IOException | ClassNotFoundException e)
        {
            Logger.getErrorLogger().log("Could not load World! I/O Error!");
        }
        
        if(in == null || fis == null || is == null)
        {
            Logger.getErrorLogger().log("Could not load World! Streams could not bind!");
            return ret;
        }
        
        try
        {
            is.close();
            in.close();
            fis.close();
        }
        catch(IOException e)
        {
            Logger.getErrorLogger().log("Could not close World streams! I/O Error!");
        }
        
        if(ret != null) ret.afterLoad();
        return ret;
    }
    
    // Root handler
    
    private class RootWorldEventHandler implements WorldEventHandler, Serializable
    {
        
    }
}
