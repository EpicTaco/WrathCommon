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
package wrath.common.world;

import java.io.Serializable;

/**
 * Enumerator to describe what kind of World a {@link wrath.common.world.World} is.
 * @author Trent Spears
 */
public enum WorldType implements Serializable
{
    /**
     * Maps that are always loaded in a pre-defined state, but everything on the map can change according to user or environment actions.
     * This is currently not supported.
     */
    DYNAMIC,
    /**
     * Maps that are completely randomly generated and are destroyed upon closing the game.
     */
    RANDOMLY_GENERATED,
    /**
     * Maps that are completely randomly generated and are saved to a World file for later use.
     */
    RANDOMLY_GENERATED_SAVABLE,
    /**
     * Maps that do not change. The most change that can occur on this map is, for example, doors opening and windows breaking.
     * Much like maps supported by the Source Engine.
     * This is currently not supported.
     */
    STATIC;
}
