public class Order {

    private Side side;

    private OrderType orderType;

    private TimeInForce timeInForce;

    private Capacity capacity;

    private long instrumentId;

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "side=" + getSide() +
                ", orderType=" + getOrderType() +
                ", timeInForce=" + getTimeInForce() +
                ", capacity=" + getCapacity() +
                ", instrumentId=" + getInstrumentId() +
                '}';
    }
}
