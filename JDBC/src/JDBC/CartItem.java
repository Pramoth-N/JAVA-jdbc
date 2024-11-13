package JDBC;

public class CartItem {
    private int cartId;
    private int productId;
    private int quantity;
    private double totalPrice;

    public CartItem(int cartId, int productId, int quantity, double totalPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getCartId() {
        return cartId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
