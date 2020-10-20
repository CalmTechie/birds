package ru.korolev.birds.database.mapper

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import ru.korolev.birds.entity.Specie

interface SpecieMapper {

    @Insert("INSERT into specie(id,name) VALUES(#{id}, #{name})")
    fun save(specie: Specie)

    @Select("SELECT * FROM specie WHERE id = #{id}")
    fun findById(id: String): Specie?

    @Delete("DELETE FROM specie WHERE id = #{id}")
    fun delete(id: String)

    @Update("UPDATE specie SET name=#{name} WHERE id =#{id}")
    fun update(specie: Specie)
}