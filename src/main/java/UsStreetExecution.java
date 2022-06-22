public class UsStreetExecution {

    private String executionId;
    private String orderId;
    private String sourceSystemId;
    private Side side;
    private OrderType orderType;
    private TimeInForce timeInForce;
    private Capacity capacity;
    private long instrumentId;
    private String exchangeName;
    private long internalId;
    private ExecutionType executionType;
    private int lastQty;
    private double lastPrice;
    private long transactTime;
    private long bookId;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

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

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
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
        return "UsStreetExecution{" +
                "executionId='" + executionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", sourceSystemId='" + sourceSystemId + '\'' +
                ", side=" + side +
                ", orderType=" + orderType +
                ", timeInForce=" + timeInForce +
                ", capacity=" + capacity +
                ", instrumentId=" + instrumentId +
                ", exchangeName='" + exchangeName + '\'' +
                ", internalId=" + internalId +
                ", executionType=" + executionType +
                ", lastQty=" + lastQty +
                ", lastPrice=" + lastPrice +
                ", transactTime=" + transactTime +
                ", bookId=" + bookId +
                '}';
    }
}
