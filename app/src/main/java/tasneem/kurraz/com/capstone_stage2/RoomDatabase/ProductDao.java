package tasneem.kurraz.com.capstone_stage2.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Models.Product;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFav(Product product);

    @Delete()
    void deleteFromFav(Product product);

    @Query("select * from product_table")
    LiveData<List<Product>> getAllProducts();

    @Query("delete from product_table")
    void clearFav();

}
