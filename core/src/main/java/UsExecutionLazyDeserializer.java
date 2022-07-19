public class UsExecutionLazyDeserializer
//        implements Deserializer<UsStreetExecutionReport>
{

//    @Override
//    public UsStreetExecution deserialize(String topic, byte[] data) {
//        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
//        FbUsStreetExecution fbExecution = FbUsStreetExecution.getRootAsFbUsStreetExecution(byteBuffer);
//        return new UsStreetExecutionProxy(fbExecution);
//    }
//
//    private static class UsStreetExecutionProxy extends UsStreetExecution {
//
//        private FbUsStreetExecution fbExecution;
//
//        private UsStreetExecutionProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public FrontOffice getFrontOffice() {
//            return new FrontOfficeProxy(fbExecution);
//        }
//
//        @Override
//        public SourceSystem getSourceSystem() {
//            return new SourceSystemProxy(fbExecution);
//        }
//
//        @Override
//        public Order getOrder() {
//            return new OrderProxy(fbExecution);
//        }
//
//        @Override
//        public Exchange getExchange() {
//            return new ExchangeProxy(fbExecution);
//        }
//
//        @Override
//        public Execution getExecution() {
//            return new ExecutionProxy(fbExecution);
//        }
//    }
//
//    private static class ExecutionSystemProxy extends ExecutionSystem {
//
//        private FbUsStreetExecution fbExecution;
//
//        private ExecutionSystemProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public String getExecutionId() {
//            return fbExecution.frontOffice().executionSystem().executionId();
//        }
//
//        @Override
//        public String getOrderId() {
//            return fbExecution.frontOffice().executionSystem().orderId();
//        }
//
//
//        @Override
//        public String getName() {
//            return fbExecution.frontOffice().executionSystem().name();
//        }
//
//
//        @Override
//        public String getClientName() {
//            return fbExecution.frontOffice().executionSystem().clientName();
//        }
//    }
//
//    private static class FrontOfficeProxy extends FrontOffice {
//
//        private FbUsStreetExecution fbExecution;
//
//        private FrontOfficeProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public String getExecutionId() {
//            return fbExecution.frontOffice().executionId();
//        }
//
//        @Override
//        public String getOrderId() {
//            return fbExecution.frontOffice().orderId();
//        }
//
//        @Override
//        public ExecutionSystem getExecutionSystem() {
//            return new ExecutionSystemProxy(fbExecution);
//        }
//    }
//
//    private static class SourceSystemProxy extends SourceSystem {
//
//        private FbUsStreetExecution fbExecution;
//
//        private SourceSystemProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public String getId() {
//            return fbExecution.sourceSystem().id();
//        }
//
//        @Override
//        public String getExecutionId() {
//            return fbExecution.sourceSystem().executionId();
//        }
//
//        @Override
//        public String getOrderId() {
//            return fbExecution.sourceSystem().orderId();
//        }
//    }
//
//    private static class OrderProxy extends Order {
//
//        private FbUsStreetExecution fbExecution;
//
//        private OrderProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public Side getSide() {
//            return Side.values()[fbExecution.order().side()];
//        }
//
//        @Override
//        public OrderType getOrderType() {
//            return OrderType.values()[fbExecution.order().orderType()];
//        }
//
//        @Override
//        public TimeInForce getTimeInForce() {
//            return TimeInForce.values()[fbExecution.order().timeInForce()];
//        }
//
//        @Override
//        public Capacity getCapacity() {
//            return Capacity.values()[fbExecution.order().capacity()];
//        }
//
//        @Override
//        public long getInstrumentId() {
//            return fbExecution.order().instrumentId();
//        }
//    }
//
//    private static class ExchangeProxy extends Exchange {
//
//        private FbUsStreetExecution fbExecution;
//
//        private ExchangeProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public String getName() {
//            return fbExecution.exchange().name();
//        }
//
//        @Override
//        public String getExecutionId() {
//            return fbExecution.exchange().executionId();
//        }
//
//        @Override
//        public String getOrderId() {
//            return fbExecution.exchange().orderId();
//        }
//    }
//
//    private static class ExecutionProxy extends Execution {
//
//        private FbUsStreetExecution fbExecution;
//
//        private ExecutionProxy(FbUsStreetExecution fbExecution) {
//            this.fbExecution = fbExecution;
//        }
//
//        @Override
//        public long getId() {
//            return fbExecution.execution().id();
//        }
//
//        @Override
//        public ExecutionType getType() {
//            return ExecutionType.values()[fbExecution.execution().type()];
//        }
//
//        @Override
//        public int getLastQty() {
//            return fbExecution.execution().lastQty();
//        }
//
//        @Override
//        public double getLastPrice() {
//            return fbExecution.execution().lastPrice();
//        }
//
//        @Override
//        public long getTransactTime() {
//            return fbExecution.execution().transactTime();
//        }
//
//        @Override
//        public long getBookId() {
//            return fbExecution.execution().bookId();
//        }
//    }
}
