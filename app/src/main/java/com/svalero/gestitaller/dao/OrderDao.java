package com.svalero.gestitaller.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gestitaller.domain.WorkOrder;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM WorkOrder")
    List<WorkOrder> getAll();

    @Query("SELECT * FROM WorkOrder WHERE orderDate LIKE :query")
    List<WorkOrder> getByDate(String query);

    @Query("SELECT * FROM WorkOrder WHERE description LIKE :query")
    List<WorkOrder> getByDescriptionString(String query);

    @Insert
    void insert(WorkOrder workOrder);

    @Update
    void update(WorkOrder workOrder);

    @Delete
    void delete(WorkOrder workOrder);
}
