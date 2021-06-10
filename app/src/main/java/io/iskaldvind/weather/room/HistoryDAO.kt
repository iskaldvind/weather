package io.iskaldvind.weather.room

import android.database.Cursor
import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun clear()

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    fun getDataByWord(name: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, name, currentTemperature FROM HistoryEntity")
    fun getHistoryCursor(): Cursor

    @Query("SELECT id, name, currentTemperature FROM HistoryEntity WHERE id = :id")
    fun getHistoryCursor(id: Long): Cursor
}
