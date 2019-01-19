package tasneem.kurraz.com.capstone_stage2.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import tasneem.kurraz.com.capstone_stage2.Models.Product;
 class ProductRepository {

    private final ProductDao productDao;
    private final LiveData<List<Product>> getAllProducts;


    public ProductRepository(Application application) {
        ProductRoom productRoom = ProductRoom.getInstance(application);
        productDao = productRoom.productDao();
        getAllProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> loadAllProducts(){
        return getAllProducts;
    }

    public void addToFav(Product product){
        new AddToFavAsyncTask(productDao).execute(product);
    }

    public void ClearFav(){
        new AddToFavAsyncTask(productDao).execute();
    }

    public void RemoveFromFav(Product product){
        new RemoveFromFavAsyncTask(productDao).execute(product);
    }

    private static class AddToFavAsyncTask extends AsyncTask<Product,Void,Void> {

        private final ProductDao productDao;

        AddToFavAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.addToFav(products[0]);
            return null;
        }
    }
    private static class RemoveFromFavAsyncTask extends AsyncTask<Product,Void,Void> {

        private final ProductDao productDao;

        RemoveFromFavAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.deleteFromFav(products[0]);
            return null;
        }
    }

}
