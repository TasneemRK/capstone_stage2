package tasneem.kurraz.com.capstone_stage2.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Catagory implements Parcelable {

    private String name;
    private List<Product>products;

    public Catagory(String catagory_name, List<Product> products) {
        this.name = catagory_name;
        this.products = products;
    }


    public String getCatagory_name() {
        return name;
    }

    public void setCatagory_name(String catagory_name) {
        this.name = catagory_name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.products);
    }

    private Catagory(Parcel in) {
        this.name = in.readString();
        this.products = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<Catagory> CREATOR = new Parcelable.Creator<Catagory>() {
        @Override
        public Catagory createFromParcel(Parcel source) {
            return new Catagory(source);
        }

        @Override
        public Catagory[] newArray(int size) {
            return new Catagory[size];
        }
    };
}
