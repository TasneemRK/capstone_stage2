package tasneem.kurraz.com.capstone_stage2.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import tasneem.kurraz.com.capstone_stage2.Models.Product;

@Database(entities = {Product.class},version = 1,exportSchema = false)
public abstract class ProductRoom extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "Product_DB";
    private static  ProductRoom instance;
    public abstract ProductDao productDao();


    public static ProductRoom getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(),ProductRoom.class,DB_NAME).build();
            }
        }
        return instance;
    }

}
