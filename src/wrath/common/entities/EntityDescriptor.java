/**
 * Wrath Engine 
 * Copyright (C) 2015 Trent Spears
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package wrath.common.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

/**
 * Class to read Entity Data from the 'assets/entities' directory.
 * @author Trent Spears
 */
public class EntityDescriptor implements Serializable
{
    private final String modelName;
    private final String textureName;
    private final String jarPath;
    private final float defScale;
    private final float reflectivity;
    private final float shineDampening;
    
    /**
     * Constructor.
     * @param modelName The {@link java.lang.String} name of the Entity's model.
     * @param textureName the {@link java.lang.String} name of the Entity's texture.
     * @param jarPath The {@link java.lang.String} file-path of the Jar file containing the Entity's class. This is to ensure that clients joining a server containing this Entity has the correct code for it to work. This can be null.
     * @param defScale The default value of the entity's scale size compared to the unmodified model.
     * @param reflectivity The amount, in floating point number, the Entity is susceptible to having specular light reflect off of it. Default 0.
     * @param shineDampening The distance at which the renderer's camera can no longer see reflected light. Default 1.
     */
    public EntityDescriptor(String modelName, String textureName, String jarPath, float defScale, float reflectivity, float shineDampening)
    {
        this.modelName = modelName;
        this.textureName = textureName;
        this.jarPath = jarPath;
        this.defScale = defScale;
        this.reflectivity = reflectivity;
        this.shineDampening = shineDampening;
    }
    
    /**
     * Gets the default value of the entity's scale size compared to the unmodified model.
     * @return Returns the default value of the entity's scale size compared to the unmodified model.
     */
    public float getDefaultScale()
    {
        return defScale;
    }
    
    /**
     * Gets the {@link java.lang.String} file-path of the Jar file containing the Entity class.
     * @return Returns the {@link java.lang.String} file-path of the Jar file containing the Entity class.
     */
    public String getJarPath()
    {
        return jarPath;
    }
    
    /**
     * Gets the {@link java.lang.String} name of the Entity's model.
     * @return Returns the {@link java.lang.String} name of the Entity's model.
     */
    public String getModelName()
    {
        return modelName;
    }
    
    /**
     * Gets the {@link java.lang.String} name of the Entity's texture.
     * @return Returns the {@link java.lang.String} name of the Entity's texture.
     */
    public String getTextureName()
    {
        return textureName;
    }
    
    /**
     * Gets the amount, in floating point number, the Entity is susceptible to having specular light reflect off of it. Default 0.
     * @return Returns the amount, in floating point number, the Entity is susceptible to having specular light reflect off of it. Default 0.
     */
    public float getReflectivity()
    {
        return reflectivity;
    }
    
    /**
     * Gets the distance at which the renderer's camera can no longer see reflected light. Default 1.
     * @return Returns the distance at which the renderer's camera can no longer see reflected light. Default 1.
     */
    public float getShineDampening()
    {
        return shineDampening;
    }
    
    /**
     * Loads an EntityDescriptor from a file.
     * @param entityDescFile The {@link java.io.File} that contains the Entity's data.
     * @return Returns an EntityDescriptor based off of the File's data.
     */
    public static EntityDescriptor loadEntityDescriptor(File entityDescFile)
    {
        EntityDescriptor r = null;
        String model = "";
        String texture = "";
        String jarPath = "";
        float scale = 1f;
        float reflectivity = 0;
        float shineDampening = 1;
        
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(entityDescFile));
            String line;
            while((line = in.readLine()) != null)
            {
                String[] buf = line.split("=", 2);
                if(buf.length < 2) continue;
                
                if(buf[0].equalsIgnoreCase("model")) model = buf[1];
                else if(buf[0].equalsIgnoreCase("texture")) texture = buf[1];
                else if(buf[0].equalsIgnoreCase("jarfile")) jarPath = buf[1];
                else if(buf[0].equalsIgnoreCase("scale")) scale = Float.parseFloat(buf[1]);
                else if(buf[0].equalsIgnoreCase("reflectivity")) reflectivity = Float.parseFloat(buf[1]);
                else if(buf[0].equalsIgnoreCase("shinedampening")) shineDampening = Float.parseFloat(buf[1]);
            }
            in.close();
            
            if(model.trim() == "" || texture.trim() == "") return r;
            r = new EntityDescriptor(model, texture, jarPath, scale, reflectivity, shineDampening);
        }
        catch(IOException e)
        {
            System.err.println("Could not read Entity Descriptor File, I/O Error!");
        }
        
        return r;
    }
}
