package lesson1_2;

import java.util.Date;

public class Order {
    private long id;
    private long customerId;
    private int amount;
    private Date dealDate;

    public Order(long id, long customerId, int amount, Date dealDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.dealDate = dealDate;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDealDate() {
        return dealDate;
    }

    @Override
    public String toString() {
        return "lesson1_2.Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", dealDate=" + dealDate +
                '}';
    }
}
