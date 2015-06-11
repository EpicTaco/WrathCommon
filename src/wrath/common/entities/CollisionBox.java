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

import org.lwjgl.util.vector.Vector3f;

/**
 * Class to describe the collision boxes of entities.
 * @author Trent Spears
 */
public class CollisionBox
{
    private final Vector3f box = new Vector3f();
    private final Entity parentEntity;
    private boolean solid = false;
    
    /**
     * Constructor.
     * @param entity The {@link wrath.common.entities.Entity} to link to this Collision Box.
     * @param xoffset The amount to add to the X-coordinate of the entity to calculate the box's bottom right corner.
     * @param yoffset The amount to add to the Y-coordinate of the entity to calculate the box's bottom right corner.
     * @param zoffset The amount to add to the Z-coordinate of the entity to calculate the box's bottom right corner.
     */
    public CollisionBox(Entity entity, float xoffset, float yoffset, float zoffset)
    {
        this.parentEntity = entity;
        box.set(xoffset, yoffset, zoffset);
        solid = !(xoffset <= 0 || yoffset <= 0 || zoffset <= 0);
    }
    
    /**
     * Gets the X, Y and Z offsets of the Collision box.
     * The top-left corner is represented by the attached entity's Location.
     * The offsets are added to the entity's location to get the box's bottom-right corner.
     * This is probably going to change.
     * @return Returns the X, Y and Z offsets of the Collision box.
     */
    public Vector3f getBoxOffsets()
    {
        return box;
    }
    
    /**
     * Gets the {@link wrath.common.entities.Entity} linked to this Collision Box.
     * @return Returns the {@link wrath.common.entities.Entity} linked to this Collision Box.
     */
    public Entity getEntity()
    {
        return parentEntity;
    }
    
    /**
     * Tests if the specified box is intercepting with this collision box.
     * @param box The box to check against.
     * @return Return true if collision boxes are intercepting.
     */
    public boolean isColliding(CollisionBox box)
    {
        //TODO: Calculate collisions!
        return solid;
    }
    
    /**
     * If true, the entity can collide with things.
     * @return Returns true if collisions are enabled.
     */
    public boolean isSolid()
    {
        return solid;
    }
    
    /**
     * Sets whether or not collisions are enabled.
     * @param isSolid If true, then collisions will be enabled.
     */
    public void setSolid(boolean isSolid)
    {
        this.solid = isSolid;
    }
}
