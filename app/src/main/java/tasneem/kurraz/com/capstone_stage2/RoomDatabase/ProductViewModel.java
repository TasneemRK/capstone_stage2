package tasneem.kurraz.com.capstone_stage2.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Models.Product;

public class ProductViewModel extends AndroidViewModel{


    private final ProductRepository productRepository ;
    private final LiveData<List<Product>> products;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        products = productRepository.loadAllProducts();
    }

    public void insertProduct(Product product){
        productRepository.addToFav(product);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void removeFromFav(Product product){
        productRepository.RemoveFromFav(product);
    }

}
