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
package wrath.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import wrath.common.entities.events.EntityEventHandler;
import wrath.common.world.World;

/**
 * Class to represent anything that holds a location in the world.
 * @author Trent Spears
 */
public abstract class Entity implements Serializable
{
    private transient static final ArrayList<EntityEventHandler> entHandlers = new ArrayList<>();
    private transient static RootEntityEventHandler roothandler = null;
    
    public static void addEntityEventHandler(EntityEventHandler handler)
    {
        entHandlers.add(handler);
    }
    
    // Object
    
    private boolean hasChanged = true;
    
    private EntityDescriptor desc = null;
    private Vector3f location;
    private Vector3f orientation = new Vector3f(0f, 0f, 0f);
    private float sizeScale = 1f;
    private float speed = 0;
    private transient World world;
    
    /**
     * Constructor.
     * @param location The {@link org.lwjgl.util.vector.Vector3f} representation of the entity's 3D coordinates.
     * @param world The {@link wrath.common.world.World} the entity belongs to.
     * @param descriptor The {@link wrath.common.entities.EntityDescriptor} that describes the Entity to renderers. Can be null.
     */
    protected Entity(Vector3f location, World world, EntityDescriptor descriptor)
    {
        this.location = location;
        this.world = world;
        this.desc = descriptor;
        if(desc != null) sizeScale = desc.getDefaultScale();
        
        if(roothandler == null) roothandler = new RootEntityEventHandler();
    }
    
    /**
     * For internal Engine use.
     * @return For internal use!
     */
    public boolean changed()
    {
        return hasChanged;
    }
    
    /**
     * Gets the {@link wrath.common.entities.EntityDescriptor} linked to this Entity. Can be null.
     * @return Returns the {@link wrath.common.entities.EntityDescriptor} linked to this Entity. Can be null.
     */
    public EntityDescriptor getEntityDescriptor()
    {
        return desc;
    }
    
    /**
     * The {@link org.lwjgl.util.vector.Vector3f} representation of the entity's 3D coordinates.
     * @return Returns the {@link org.lwjgl.util.vector.Vector3f} representation of the entity's 3D coordinates.
     */
    public Vector3f getLocation()
    {
        return location;
    }
    
    /**
     * Gets the {@link org.lwjgl.util.vector.Vector3f} representation of the entity's pitch, yaw and roll.
     * @return Returns the {@link org.lwjgl.util.vector.Vector3f} representation of the entity's pitch, yaw and roll.
     */
    public Vector3f getOrientation()
    {
        return orientation;
    }
    
    /**
     * Gets the multiplier of this entity's size compared to the default model.
     * @return Returns the multiplier of this entity's size compared to the default model.
     */
    public float getSizeScale()
    {
        return sizeScale;
    }
    
    /**
     * Gets the entity's current movement speed.
     * @return Returns the entity's current movement speed.
     */
    public float getSpeed()
    {
        return speed;
    }
    
    /**
     * Gets the entity's current {@link wrath.common.world.World}.
     * @return Returns the entity's current {@link wrath.common.world.World}.
     */
    public World getWorld()
    {
        return world;
    }
    
    /**
     * Do not call! For internal use only!
     */
    public void resetChangeTracker()
    {
        hasChanged = false;
    }
    
    /**
     * Sets the Entity's {@link wrath.common.entities.EntityDescriptor}. Can be null.
     * @param descriptor The Entity's {@link wrath.common.entities.EntityDescriptor}. Can be null.
     */
    public void setEntityDescriptor(EntityDescriptor descriptor)
    {
        desc = descriptor;
        setSizeScale(desc.getDefaultScale());
    }
    
    /**
     * Sets the entity's 3D location in the world.
     * @param newLocation The {@link org.lwjgl.util.vector.Vector3f} representation of the entity's 3D coordinates.
     */
    public void setLocation(Vector3f newLocation)
    {
        hasChanged = true;
        this.location = newLocation;
    }
    
    /**
     * Sets the entity's 3D location in the world.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @param z The Z-coordinate.
     */
    public void setLocation(float x, float y, float z)
    {
        hasChanged = true;
        location.set(x, y, z);
    }
    
    /**
     * Sets the entity's orientation (pitch, yaw and roll).
     * @param newOrientation The {@link org.lwjgl.util.vector.Vector3f} representation of the entity's pitch, yaw and roll.
     */
    public void setOrientation(Vector3f newOrientation)
    {
        hasChanged = true;
        this.orientation = newOrientation;
    }
    
    /**
     * Sets the entity's orientation (pitch, yaw and roll).
     * @param pitch The pitch.
     * @param yaw The yaw.
     * @param roll The roll.
     */
    public void setOrientation(float pitch, float yaw, float roll)
    {
        hasChanged = true;
        orientation.set(pitch, yaw, roll);
    }
    
    /**
     * Sets the multiplier of this entity's size compared to the default model.
     * @param scale The multiplier of this entity's size compared to the default model.
     */
    public void setSizeScale(float scale)
    {
        hasChanged = true;
        this.sizeScale = scale;
    }
    
    /**
     * Sets the entity's current movement speed.
     * Any float number can be taken into here, but the absolute value of the argument is what is set.
     * @param speed The speed to set the entity to. Set to 0 for no movement.
     */
    public void setSpeed(float speed)
    {
        this.speed = Math.abs(speed);
    }
    
    /**
     * Sets the {@link wrath.common.world.World} the entity is in.
     * @param world The {@link wrath.common.world.World} the entity is in.
     */
    public void setWorld(World world)
    {
        this.world = world;
    }
    
    /**
     * Increments the position of the entity by the specified amount.
     * @param dx The amount to increase the position on the X-Axis.
     * @param dy The amount to increase the position on the Y-Axis.
     * @param dz The amount to increase the position on the Z-Axis.
     */
    public void translateLocation(float dx, float dy, float dz)
    {
        hasChanged = true;
        this.location.x += dx;
        this.location.y += dy;
        this.location.z += dz;
    }
    
    /**
     * Increments the orientation of the entity by the specified amount.
     * @param dx The amount to increase the pitch.
     * @param dy The amount to increase the yaw.
     * @param dr The amount to increase the roll.
     */
    public void translateOrientation(float dx, float dy, float dr)
    {
        hasChanged = true;
        this.orientation.x += dx;
        this.orientation.y += dy;
        this.orientation.z += dr;
    }
    
    // Event handler
    
    private class RootEntityEventHandler implements EntityEventHandler
    {
        
    }
}
