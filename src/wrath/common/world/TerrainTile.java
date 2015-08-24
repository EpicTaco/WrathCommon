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
import org.lwjgl.util.vector.Vector3f;

/**
 * Class to manage world data in smaller tiles.
 * @author Trent Spears
 */
public class TerrainTile implements Serializable
{
    public static final int TILE_DIMENSION = 800;
    
    private final Vector3f location;
    private final Vector3f verticies[][];
    private final int vertexCount;
    protected transient World world;
    
    /**
     * Creates a Tile in specified world.
     * @param location The {@link org.lwjgl.util.vector.Vector3f} representation of this Tile's location in the World.
     * @param world The {@link wrath.common.worlds.World} this Tile belongs to. Can be null.
     * @param vertexCount The number of verticies present in this Tile.
     */
    public TerrainTile(Vector3f location, World world, int vertexCount)
    {
        this.location = location;
        location.x = location.x * TILE_DIMENSION;
        location.y = location.y * TILE_DIMENSION;
        location.z = location.z * TILE_DIMENSION;
        this.world = world;
        if(vertexCount % 2 != 0) vertexCount++;
        this.vertexCount = vertexCount;
        this.verticies = new Vector3f[vertexCount/2][vertexCount/2];
    }
    
    /**
     * Gets the {@link org.lwjgl.util.vector.Vector3f} representation of this Tile's location in the World.
     * @return Returns the {@link org.lwjgl.util.vector.Vector3f} representation of this Tile's location in the World.
     */
    public Vector3f getTileLocation()
    {
        return location;
    }
    
    /**
     * Gets the Vertex data from a specific dimension of the tile.
     * @param x The X-position of the tile.
     * @param y The Y-position of the tile.
     * @return Returns the Vertex data from a specific dimension of the tile.
     */
    public Vector3f getVertex(int x, int y)
    {
        return verticies[x][y];
    }
    
    /**
     * Gets the number of verticies in this Tile.
     * @return Returns the number of verticies in this Tile.
     */
    public int getVertexCount()
    {
        return vertexCount;
    }
    
    /**
     * Gets the vertex data for this tile.
     * @return Returns the vertex data for this tile.
     */
    public Vector3f[][] getVerticies()
    {
        return verticies;
    }
    
    /**
     * Gets the {@link wrath.common.worlds.World} linked to this Tile. 
     * Can be null.
     * @return Returns the {@link wrath.common.worlds.World} linked to this Tile.
     */
    public World getWorld()
    {
        return world;
    }
    
    /**
     * Changes the Vertex data of a single vertex in the tile.
     * @param x The X-position of the tile.
     * @param y The Y-position of the tile.
     * @param vertex The new Vertex data in {@link org.lwjgl.util.vector.Vector3f} format.
     */
    public void setVertex(int x, int y, Vector3f vertex)
    {
        verticies[x][y] = vertex;
    }
}
