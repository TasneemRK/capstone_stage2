package tasneem.kurraz.com.capstone_stage2.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity(tableName = "product_table")
public class Product implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String product_name;
    private String product_image;
    private String product_price;
    private String product_description;
    private int quantity;
    private boolean isFav = false;

    @Ignore
    public Product() {
    }

    @Ignore
    public Product(@NonNull String id, String product_name, String product_image, String product_price, String product_description) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_description = product_description;
    }

    @Ignore
    public Product(String product_image, String product_name, String product_price) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
    }

    @Ignore
    public Product(String product_image, String product_name, String product_price, String product_description) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_description = product_description;
    }


    public Product(@NonNull String id, String product_name, String product_image, String product_price, String product_description, int quantity) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_description = product_description;
        this.quantity = quantity;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_image);
        dest.writeString(this.product_price);
        dest.writeString(this.product_description);
        dest.writeInt(this.quantity);
        dest.writeByte(this.isFav ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.product_name = in.readString();
        this.product_image = in.readString();
        this.product_price = in.readString();
        this.product_description = in.readString();
        this.quantity = in.readInt();
        this.isFav = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
