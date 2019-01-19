package tasneem.kurraz.com.capstone_stage2.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    private String id;
    private String order_date;
    private String delivery_date;
    private String delivery_time;
    private String totalPrice;
    private String delivery_address;
    private List<Product> products;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
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
        dest.writeString(this.id);
        dest.writeString(this.order_date);
        dest.writeString(this.delivery_date);
        dest.writeString(this.delivery_time);
        dest.writeString(this.totalPrice);
        dest.writeString(this.delivery_address);
        dest.writeTypedList(this.products);
    }

    private Order(Parcel in) {
        this.id = in.readString();
        this.order_date = in.readString();
        this.delivery_date = in.readString();
        this.delivery_time = in.readString();
        this.totalPrice = in.readString();
        this.delivery_address = in.readString();
        this.products = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
