package game.sniper_monkey.collision;

public class SpatialHash
{

}

/*#include "spatial_hash.h"

namespace ign
{
	spatial_hash::spatial_hash(float tile_width, float tile_height)
		: m_tile_width(tile_width)
		, m_tile_height(tile_height)
	{}

	void spatial_hash::insert(entt::entity entity, glm::vec2 position)
	{
		tile_position tile_pos;

		if (position.x < 0)
			tile_pos.components[0] = short(floor(position.x / m_tile_width));
		else
			tile_pos.components[0] = short(position.x / m_tile_width);
		if (position.y < 0)
			tile_pos.components[1] = short(floor(position.y / m_tile_width));
		else
			tile_pos.components[1] = short(position.y / m_tile_width);


		m_hash[tile_pos.combined].emplace_back(entity);
	}

	void spatial_hash::clear()
	{
		m_hash.clear();
	}

	std::vector<entt::entity> spatial_hash::query(glm::vec2 position)
	{
		tile_position tile_pos;

		if (position.x < 0)
			tile_pos.components[0] = short(floor(position.x / m_tile_width));
		else
			tile_pos.components[0] = short(position.x / m_tile_width);
		if (position.y < 0)
			tile_pos.components[1] = short(floor(position.y / m_tile_width));
		else
			tile_pos.components[1] = short(position.y / m_tile_width);

		return m_hash[tile_pos.combined];
	}

	std::vector<entt::entity> spatial_hash::query(glm::vec4 rect)
	{
		glm::ivec2 top_left_tile_pos;
		glm::ivec2 bottom_right_tile_pos;

		std::vector<entt::entity> result;

		if (rect.x < 0)
			top_left_tile_pos.x = short(floor(rect.x / m_tile_width));
		else
			top_left_tile_pos.x = short(rect.x / m_tile_width);

		if (rect.y < 0)
			top_left_tile_pos.y = short(floor(rect.y / m_tile_width));
		else
			top_left_tile_pos.y = short(rect.y / m_tile_width);


		if (rect.x + rect.z < 0)
			bottom_right_tile_pos.x = short(floor((rect.x + rect.z) / m_tile_width));
		else
			bottom_right_tile_pos.x = short((rect.x + rect.z) / m_tile_width);

		if (rect.y + rect.w < 0)
			bottom_right_tile_pos.y = short(floor((rect.y + rect.w) / m_tile_width));
		else
			bottom_right_tile_pos.y = short((rect.y + rect.w) / m_tile_width);

		for (int x = top_left_tile_pos.x; x <= bottom_right_tile_pos.x; ++x)
		{
			for (int y = top_left_tile_pos.y; y <= bottom_right_tile_pos.y; ++y)
			{
				tile_position tile_pos;
				tile_pos.components[0] = x;
				tile_pos.components[1] = y;

				std::vector<entt::entity> entities = m_hash[tile_pos.combined];
				result.insert(result.end(), entities.begin(), entities.end());
			}
		}

		return result;
	}
}*/
