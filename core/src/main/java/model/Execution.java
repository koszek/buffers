package model;

import annotation.GenerateHelper;

@GenerateHelper
public class Execution {

    private long id;

    private ExecutionType type;

    private int lastQty;

    private double lastPrice;

    private long transactTime;

    private long bookId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExecutionType getType() {
        return type;
    }

    public void setType(ExecutionType type) {
        this.type = type;
    }

    public int getLastQty() {
        return lastQty;
    }

    public void setLastQty(int lastQty) {
        this.lastQty = lastQty;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public long getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", lastQty=" + getLastQty() +
                ", lastPrice=" + getLastPrice() +
                ", transactTime=" + getTransactTime() +
                ", bookId=" + getBookId() +
                '}';
    }
}
