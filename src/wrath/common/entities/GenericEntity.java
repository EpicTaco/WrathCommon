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

import org.lwjgl.util.vector.Vector3f;
import wrath.common.world.World;

/**
 * Class to represent an Entity with no complex behaviors or attributes.
 * @author Trent Spears
 */
public class GenericEntity extends Entity
{
    /**
     * Constructor.
     * @param location The {@link org.lwjgl.util.vector.Vector3f} representation of the Entity's World location. CANNOT be null.
     * @param world The {@link wrath.common.world.World} in which this Entity belongs. Can be null.
     * @param descriptor The {@link wrath.common.entities.Entity} that describes this Entity. Can be null.
     */
    public GenericEntity(Vector3f location, World world, EntityDescriptor descriptor)
    {
        super(location, world, descriptor);
    }
}
